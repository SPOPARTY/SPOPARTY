<template>
    <v-container class="mypage-container">
        <v-card class="inner-card px-6 py-2" outlined>
            <h1>마이페이지</h1>
            <v-row>
                <v-col cols="12" md="8">
                    <v-text-field class="input" label="아이디" v-model="memberInfo.loginId" outlined dense disabled/>
                </v-col>
                <v-col cols="12" md="4">
                    <v-btn class="input" :disabled="memberInfo.provider !== 'basic'" color="primary" 
                    @click="showChangePwdModal" block elevation="1">비밀번호 수정</v-btn>
                </v-col>
            </v-row>
            <SetNewPwd 
                v-if="isPwdModalVisible"
                :current-pwd="memberInfo.loginPwd"
                @set-pwd-close="changePwd($event)"/>
            
            <v-row>
                <v-col cols="12">
                    <v-text-field :disabled="memberInfo.provider !== 'basic'" 
                        class="input" label="닉네임" v-model="memberInfo.nickname" outlined dense/>
                </v-col>
            </v-row>
            
            <v-row>
                <v-col cols="4" md="4">
                    <v-text-field class="input" label="이메일 아이디" v-model="memberInfo.email.split('@')[0]" outlined dense disabled></v-text-field>
                </v-col>
                <v-col cols="1" md="1" class="text-center" style="color:white; margin-top:10px;"><h4>@</h4></v-col>
                <v-col cols="4" md="4">
                    <v-text-field class="input" label="도메인" v-model="memberInfo.email.split('@')[1]" outlined dense disabled></v-text-field>
                </v-col>
                <v-col cols="3" md="3">
                    <v-btn :disabled="memberInfo.provider !== 'basic'" color="primary" style="margin-top:10px;" 
                        elevation="1" @click="showChangeEmailModal">이메일 수정</v-btn>
                </v-col>
            </v-row>
            <SetNewEmail 
                v-if="isEmailModalVisible" 
                @set-email-close="isEmailModalVisible = false"
                @update-email="updateEmail($event)"
                />  
            <v-row v-if="memberInfo.team.id !== ''" class="emblem-box mx-2 mb-2 my-6" justify="center" align="center" @click="showEmblemModal" >
                <v-tooltip location="top" activator="parent">대표 엠블럼을 선택하세요!</v-tooltip>
                    <v-col cols="2">
                        <v-img :src="memberInfo.team.logo" :alt="memberInfo.team.nameKr"  
                        style="width: 50px; height: 50px; transform: translateX(10px);"/>
                    </v-col>
                    <v-col cols="6">
                        <h2 style="color:white;">{{ memberInfo.team.nameKr }}</h2>
                    </v-col>
            </v-row>
            <v-row v-else class="emblem-box mx-2 mb-2 my-6" justify="center" align="center" @click="showEmblemModal">
                <v-tooltip location="top" activator="parent">대표 엠블럼을 선택하세요!</v-tooltip>
                <h2 style="color:white; cursor:pointer">원하는 구단의 엠블럼을 선택해보세요!</h2>
            </v-row>
            <EmblemList 
                v-if="isEmblemModalVisible"
                :team-list="teamList"
                :emblem-id="memberInfo.team.id"
                @emblem-list-close="isEmblemModalVisible = false"
                @select-emblem="setEmblem($event)"
                />
            
            <v-row class="follow-box justify-center">
                <v-col cols="6">
                    <v-btn variant="tonal"
                        style="width:100%; background-color:#B4B4B8; "
                        @click="showFollowModal"
                    >
                    <h3>팔로우 중인 구단 수 : {{followingClubNum}}</h3></v-btn>
                </v-col>
                <v-col cols="6">
                    <v-card-actions class="justify-center">
                        <v-spacer></v-spacer>
                        <v-btn color="darkgrey" @click="goBack" variant="tonal">이전</v-btn>
                        <v-btn color="primary" @click="updateChanges" variant="tonal">수정</v-btn>
                        <v-btn color="red" @click="Withdraw" variant="tonal">회원 탈퇴</v-btn>
                    </v-card-actions>
                </v-col>
            </v-row>
            <FollowList
                v-if="isFollowModalVisible"
                :team-list="teamList"
                :follow-list="followList"
                :member-id="memberId"
                @follow-list-close = "isFollowModalVisible = false"
                @follow-club="setFollowClubNumber($event)"
                @unfollow-club="setFollowClubNumber($event)"
            />
        </v-card>
    </v-container>
</template>

<script setup>
import {useRouter} from 'vue-router'
import { ref, computed, watch,onMounted } from 'vue';

import { httpStatusCode } from '@/util/http-status';
import {getMember, updateMember, deleteMember} from '@/api/member'

import {useFollowStore} from '@/stores/member/follows'

import SetNewPwd from '@/components/user/SetNewPwd.vue';
import SetNewEmail from '@/components/user/SetNewEmail.vue';
import EmblemList from '@/components/user/EmblemList.vue';
import FollowList from '@/components/user/FollowList.vue';

const followStore = useFollowStore();

const router = useRouter();
const memberId = ref("");
const teamList = ref([]);
const followList = ref([]);

onMounted(() => {
    const id = localStorage.getItem("id")
    memberId.value = localStorage.getItem("id");
    teamList.value = followStore.getTeamList();
    followStore.getFollowList(memberId.value);
    getMemberInfo();
})
// 전체 팀 리스트 관찰

// 팔로우 리스트 관찰
watch(() => followStore.followList, (newFollowList) => {
    followList.value = newFollowList
    // console.log("onMounted된 후 팔로우 리스트 -> ",followList.value);
},{immediate:true})

watch(() => followStore.teamList, (newTeamList) => {
    teamList.value = newTeamList
},{immediate:true})


const updatedPwd = ref("");

const memberInfo = ref({
    id : "",
    loginId : "",
    loginPwd : updatedPwd.value,
    nickname : "",
    provider : "",
    email : "",
    team : {
        id : "",
        logo : "",
        nameKr : "",
    },
    status : "",
})

const emailId = ref(memberInfo.value.email.split("@")[0]);
const emailDomain = ref(memberInfo.value.email.split("@")[1]);

const emblemId = ref(memberInfo.value.team.id);
const emblemName = ref(memberInfo.value.team.nameKr);
const emblemIcon = ref(memberInfo.value.team.logo);


const getMemberInfo = () => {
    getMember(
        memberId.value,
        ({data,status}) => {
        // console.log("data ==> ",data);
        // console.log("message ==> ",data.message);
        // console.log("status ==> ",status);
        memberInfo.value.id = data.data.id;
        memberInfo.value.loginId = data.data.loginId;
        memberInfo.value.loginPwd = data.data.loginPwd;
        memberInfo.value.nickname = data.data.nickname;
        memberInfo.value.provider = data.data.provider;
        memberInfo.value.email = data.data.email;
        memberInfo.value.team.id = data.data.team === null? emblemId.value : data.data.team.id;
        memberInfo.value.team.logo = data.data.team.logo;
        memberInfo.value.team.nameKr = data.data.team.nameKr;
        memberInfo.value.team.status = data.data.status;
        },
        (error) => {
        console.log(error)
        }
    )
}

const updateChanges = () => {
    // console.log("수정된 회원 정보")
    // console.log(memberInfo.value)
    updateMember(memberInfo.value, (response)=> {
        if(response.status === httpStatusCode.OK) {
            alert("회원정보 수정 완료!")
            window.location.replace("/")
        }
    },(error) => {
        console.log(error);
    })
}

const Withdraw = () => {
    deleteMember(memberId.value,
    (res) => {
        if (res.status === httpStatusCode.OK) {
            // console.log(res)
            localStorage.removeItem("accessToken");
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("id")
            alert("성공적으로 회원 탈퇴 되었습니다!")
            window.location.replace("/")
        } 
    },
    (error) => {
        console.log(error)
        alert("회원 탈퇴에 실패하였습니다!")
    }
    )
}


// 비밀번호 수정 모달 띄우기
const isPwdModalVisible = ref(false) // 비밀번호 수정 모달 보일까 말까

function showChangePwdModal() {
    isPwdModalVisible.value = true
}

function changePwd(newPwds){
    isPwdModalVisible.value = false;
    // console.log(newPwds)
    updatedPwd.value = newPwds.password;
    memberInfo.value.loginPwd = updatedPwd.value;
    // console.log(memberInfo.value.loginPwd)
    
}

// 이메일 수정 모달 띄우기
const isEmailModalVisible = ref(false) // 이메일 수정 모달 보일까 말까

function showChangeEmailModal() {
    isEmailModalVisible.value = true
}

function updateEmail(newEmail) {
    // console.log(newEmail.value)
    emailId.value = newEmail.value.split("@")[0];
    emailDomain.value = newEmail.value.split("@")[1];
    memberInfo.value.email = newEmail.value;
}

// 엠블럼 수정 모달 띄우기
const isEmblemModalVisible = ref(false) // 엠블럼 수정 모달 보일까 말까
function showEmblemModal() {
    isEmblemModalVisible.value = true;
}


function setEmblem(newEmblem) {
    // console.log("새로운 emblemId ->",newEmblem.emblemId)
    emblemId.value = newEmblem.emblemId
    emblemIcon.value = newEmblem.emblemIcon;
    emblemName.value = newEmblem.emblemName;
    memberInfo.value.team.id = emblemId.value;
    memberInfo.value.team.logo = emblemIcon.value
    memberInfo.value.team.nameKr = emblemName.value 
    // console.log("emblemId ->",memberInfo.value.team.id)
}

// 구단 팔로우 모달
const isFollowModalVisible = ref(false)
function showFollowModal() {
    // console.log("구단 팔로우 모달 띄우기!!!")
    isFollowModalVisible.value = true;
}

// 팔로우 중인 구단 수 
// 실제로 유저 정보를 받아올 때 onMounted를 통해 업데이트 되도록 한다 현재는 더미값
const followingClubNum = computed(() => {
    return followList.value ? followList.value.length : 0;
});


const setFollowClubNumber = (followingCount) => {
    followingClubNum.value = followingCount;
}


// 이전 화면으로 되돌아가기
function goBack() {
    router.back()
}


</script>

<style scoped>
h1 {
    text-align: center;
    margin-top:10px;
    margin-bottom:20px;
    color : #292646;
}

.inner-card{
    /* background-color: #292646 */
    background-color: #CBD0D8;
}

.input {
    border-radius: 5px;
    background-color:#F4F3EA;
    height:50px;
}
.mypage-container{
    margin-top:40px;
    max-width: 700px;
}

.emblem-box{
    cursor:pointer; 
    background-color: #474F7A;
    border-radius: 5px;
}
h4 {
    color: #292646;
    font-size: 1.5rem;
    font-weight: bold;
}
</style>
