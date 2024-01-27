<template>
    <v-container class="mypage-container">
        <v-card class="pa-4" outlined>
            <v-card-title class="text-h4 mb-6">마이페이지</v-card-title>
        
            <v-row>
                <v-col cols="12" md="8">
                    <v-text-field label="아이디" :value="memberInfo.loginId" outlined dense readonly></v-text-field>
                </v-col>
                <v-col cols="12" md="4">
                    <v-btn color="#393646" style="margin-top:10px" @click="showChangePwdModal" block>비밀번호 수정</v-btn>
                </v-col>
            </v-row>
            <SetNewPwd v-if="isPwdModalVisible" @setpwd-close="isPwdModalVisible = false"/>
            
            <v-row>
                <v-col cols="12">
                    <v-text-field label="닉네임" :value="memberInfo.nickname" outlined dense></v-text-field>
                </v-col>
            </v-row>
            
            <v-row>
                <v-col cols="4" md="4">
                    <v-text-field label="이메일 아이디" :value="memberInfo.email.split('@')[0]" outlined dense></v-text-field>
                </v-col>
                <v-col cols="1" md="1" class="text-center">@</v-col>
                <v-col cols="4" md="4">
                    <v-text-field label="도메인" :value="memberInfo.email.split('@')[1]" outlined dense></v-text-field>
                </v-col>
                <v-col cols="3" md="3">
                    <v-btn color="#123421" style="margin-top:10px;" @click="showChangeEmailModal">이메일 수정</v-btn>
                </v-col>
            </v-row>
            <SetNewEmail 
                v-if="isEmailModalVisible" 
                @set-email-close="isEmailModalVisible = false"
                @update-email="updateEmail($event)"
                />
            
            <v-row>
            </v-row>
            
            <v-row>
                <v-col cols="8">
                    <v-btn style="width:100%; height:100%;" @click="showEmblemModal">
                        <img :src="emblemIcon" :alt="emblemName" style="width:64px; height:64px;">
                        {{ emblemName }}
                    </v-btn>
                </v-col>
                <v-col cols="4" md="4">
                    <v-btn color="#4F4557" style="margin-top:10px;" @click="showEmblemModal">엠블럼 목록</v-btn>
                </v-col>
            </v-row>
            <EmblemList 
                v-if="isEmblemModalVisible"
                :emblem-icon="emblemIcon"
                :emblem-name="emblemName"
                @emblem-list-close="isEmblemModalVisible = false"
                @select-emblem="setEmblem($event)"
                />
            
            <v-row>
                <v-col cols="8">
                    <v-btn 
                        style="width:100% "
                        @click="showFollowModal"
                    >팔로우 중인 구단 수 : {{followingClubNum}}</v-btn>
                </v-col>
            </v-row>
            <FollowList
                v-if="isFollowModalVisible"
                @follow-list-close = "isFollowModalVisible = false"
                @follow-club="setFollowClubNumber($event)"
                @unfollow-club="setFollowClubNumber($event)"
            />

            <v-card-actions class="text-center">
                <v-spacer></v-spacer>
                <v-btn color="grey" @click="goBack">이전</v-btn>
                <v-btn color="primary" @click="updateChanges">수정</v-btn>
                <v-btn color="red" @click="Withdraw">회원 탈퇴</v-btn>
            </v-card-actions>
        </v-card>
    </v-container>
</template>

<script setup>
import {useRouter} from 'vue-router'
import { ref, computed, onBeforeMount } from 'vue';

import { httpStatusCode } from '@/util/http-status';
import {getMember, updateMember, deleteMember} from '@/api/members'

import SetNewPwd from '@/components/user/SetNewPwd.vue';
import SetNewEmail from '@/components/user/SetNewEmail.vue';
import EmblemList from '@/components/user/EmblemList.vue';
import FollowList from '@/components/user/FollowList.vue';

const router = useRouter();
const memberId = ref("");

onBeforeMount(async() => {
    console.log("memberInfo =>", memberInfo.value)
    await router.isReady()
    memberId.value = sessionStorage.getItem("id");
    console.log("id -> ",memberId.value)
    getMemberInfo();
})

const memberInfo = ref({
    id : "",
    loginId : "",
    loginPwd : "",
    nickname : "",
    email : "",
    team : {
        id : "",
        logo : "",
    },
    status : "",
})

const updatedMember = ref({
    id : "",
    loginId : "",
    loginPwd : "",
    nickname : "",
    email : "",
    team : {
        id : "",
        logo : "",
    },
    status : "",
})

const emailId = ref(memberInfo.value.email.split("@")[0]);
const emailDomain = ref(memberInfo.value.email.split("@")[1]);

const getMemberInfo = () => {
    console.log("이거 이용해서 바꿀꺼다 ->", memberId.value)
    getMember(memberId.value,({data}) => {
        console.log(data);
        memberInfo.value.id = data.id;
        memberInfo.value.loginId = data.loginId;
        memberInfo.value.loginPwd = data.loginPwd;
        memberInfo.value.nickname = data.nickname;
        memberInfo.value.email = data.email;
        memberInfo.value.team.id = data.team.id;
        memberInfo.value.team.logo = data.team.logo;
        memberInfo.value.team.status = data.status;
    },(error) => {
        console.log("살려줘")
        console.log(error)
    }
    )
}

const updateChanges = () => {

}

const Withdraw = () => {
    deleteMember(memberId.value,
    (res) => {
        if (res.status === httpStatusCode.OK) {
            console.log(res)
            sessionStorage.removeItem("accessToken");
            sessionStorage.removeItem("refreshToken");
            sessionStorage.removeItem("id")
            alert("함께해서 더러웠고 다신 만나지 말자")
            router.push("/")
        } 
    },
    (error) => {
        console.log(error)
        alert("어딜가려고?")
    }
    )
}


// 비밀번호 수정 모달 띄우기
const isPwdModalVisible = ref(false) // 비밀번호 수정 모달 보일까 말까

function showChangePwdModal() {
    isPwdModalVisible.value = true
}

// 이메일 수정 모달 띄우기
const isEmailModalVisible = ref(false) // 이메일 수정 모달 보일까 말까

function showChangeEmailModal() {
    isEmailModalVisible.value = true
}

function updateEmail(newEmailData) {
    emailId.value = newEmailData.newEmailId;
    emailDomain.value = newEmailData.newEmailDomain;
}

// 엠블럼 수정 모달 띄우기
const isEmblemModalVisible = ref(false) // 엠블럼 수정 모달 보일까 말까
function showEmblemModal() {
    isEmblemModalVisible.value = true;
}

const emblemIcon = ref('/src/assets/mancity.png');
const emblemName = ref('맨체스터 시티');

function setEmblem(newEmblem) {
    emblemIcon.value = newEmblem.newEmblemIcon;
    emblemName.value = newEmblem.newEmblemName;
}

// 구단 팔로우 모달
const isFollowModalVisible = ref(false)
function showFollowModal() {
    isFollowModalVisible.value = true;
}

// 팔로우 중인 구단 수 
// 실제로 유저 정보를 받아올 때 onMounted를 통해 업데이트 되도록 한다 현재는 더미값
const followingClubNum = ref(4); 

const setFollowClubNumber = (followingCount) => {
    followingClubNum.value = followingCount;
}


// 이전 화면으로 되돌아가기
function goBack() {
    router.back()
}


</script>

<style scoped>
.mypage-container{
    max-width: 600px;
}

</style>