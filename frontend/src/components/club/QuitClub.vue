<template>
    <v-dialog
        v-model="modalVisible"
        max-width="400px"
        max-height="600px"
        @click:outside="closeModal"
        persistent
    >
        <v-container v-if="isHost">
            <v-card>
                <v-card-text class="mention">
                    <br>
                    <h1 >잠깐!</h1> <br>
                    당신은 <b><u>그룹장</u></b> 아닙니까?<br>
                    어떻게 그룹을 <b><i>헌신짝</i></b><br>처럼
                    내칠 수가 있습니까?<br>
                    당신만을 바라보고 있는 그룹원이 많습니다.<br><br>
                    <b><h3>다시 생각해주세요!</h3></b><br>
                    <br>
                </v-card-text>
                <v-card-actions style="transform:translateX(-75px)">
                    <v-spacer></v-spacer>
                    <v-btn color="red" v-if="clubMemberList.length == 1" @click="showTakeOver">진행시켜</v-btn>
                    <v-btn color="red" v-else @click="showMemberList">진행시켜</v-btn>
                    <v-btn color="green" @click="closeModal"><h3>다시 생각해보자</h3></v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
        <v-container v-else>
            <v-card>
                <v-card-text class="mention">
                    <h2>정말로 탈퇴하시겠습니까?</h2><br>
                    우리의 <b>추억</b>이 고작 그 정도 입니까?<br>
                    <b>추억</b>을 되돌아본다면 <br>
                    생각이 바뀔 수도 있습니다.<br>
                    부디 다시 생각해주세요!<br>
                </v-card-text>
                <v-card-actions style="transform:translateX(-75px)">
                    <v-spacer></v-spacer>
                    <v-btn color="red" @click="showTakeOver">진행시켜</v-btn>
                    <v-btn color="green" @click="closeModal"><h3>다시 생각해보자</h3></v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
    </v-dialog>

    <!-- 그룹원 명단 => 그룹장으로 넘김 -->
    <v-dialog
        v-model="isMemberListVisible"
        max-width="400px"
    >  
        <v-container>
            <v-card>
                <v-card-text class="member-list-title">
                    <h1>그룹원 명단</h1> <br>
                    <div v-if="nextLeader.memberNickName !== ''">
                        <b>차세대 리더</b> : <b><u>{{ nextLeader.memberNickName }}</u></b> <br>
                    </div>
                </v-card-text>
                <v-card-text 
                    class="member"
                    v-for="(member, index) in clubMemberList" 
                    :key="index" 
                    @click="selectLeader(member)"
                    >
                    {{ member.memberNickName}}
                <v-icon v-if="member.role === 'host'" class="star">mdi-star</v-icon>
                </v-card-text>
                <v-card-actions style="transform:translateX(-40px)">
                    <v-spacer></v-spacer>
                    <v-btn color="red" @click="showTakeOver">진행시켜</v-btn>
                    <v-btn color="green" @click="closeModal"><h3>진짜 다시 생각해보자</h3></v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
    </v-dialog>
    <v-dialog
        v-model="isTakeOverVisible"
        max-width="400px"
        @click:outside="closeModal"
    >
        <v-card>
            <v-card-text class="text-center">
                <h1>마지막 인사</h1><br>
                별 하나의 추억과...<br>
                별 하나의 사랑과...<br>
                별 하나의 그룹...<br>
                헤아릴 수 없는 별들 만큼이나<br>
                많은 그룹이 있습니다.<br>
                그 안에서 만들어진 추억은 귀중한 것입니다.<br>
                이대로 홀연히 떠나시겠습니까?<br>
                
            </v-card-text>
            <v-card-actions style="transform:translateX(-40px)">
                    <v-spacer></v-spacer>
                    <v-btn color="red" @click="quitClub">진행시켜</v-btn>
                    <v-btn color="green" @click="closeModal"><h3>돌이킬 수 있는 마지막 기회</h3></v-btn>
            </v-card-actions>
        </v-card>

    </v-dialog>
    <v-dialog v-model="goodBye" max-width="600px" persistent>
        <v-container>
            <v-card align-items="center" class="text-center">
                <br>
                <v-card-title class="text-center">
                    <h2>함께해서 더러웠고</h2><br><br>
                    <h2>다시는 만나지 말자</h2>
                </v-card-title>
                <br>
                <v-card-actions>
                    <v-btn class="button" color="red" @click="leaveForever"><h2>확인</h2></v-btn>
                </v-card-actions>
            </v-card>
        </v-container>

    </v-dialog>

</template>

<script setup>
import {ref, computed,onMounted,watch} from 'vue'
import {useRouter, useRoute} from 'vue-router'

import {useClubStore} from '@/stores/club/clubs'


const router = useRouter();
const route = useRoute();

const props = defineProps({
    clubMemberList:Object
})

const emits = defineEmits([
    'quit-club-close'
])

const clubStore = useClubStore();


const clubMemberList = computed(() => {
    return props.clubMemberList
})

const clubId = route.params.clubId;

const loginUser = sessionStorage.getItem("id")
const isHost = props.clubMemberList.find((member) => member.memberId == loginUser)["role"] === 'host'

const modalVisible = ref(true)


// 그룹장 넘기기 위한 그룹원 명단 보이기
const isMemberListVisible = ref(false)

function showMemberList() {
    isTakeOverVisible.value = false;
    isMemberListVisible.value = true;
}

const nextLeader = ref({
    memberId : '',
    memberNickName : '',
    role : '',
    clubMemberId : '',
});


function selectLeader(member) {
    if (member.role === 'host'){
        return
    }
    nextLeader.value.memberId = member.memberId;
    nextLeader.value.memberNickName = member.memberNickName;
    nextLeader.value.role = member.role;
    nextLeader.value.clubMemberId = member.clubMemberId;
    // console.log("*****다음 그룹장은???*******")
    // console.log(nextLeader.value)
}

// 그룹장 넘기기
const isTakeOverVisible = ref(false)
function showTakeOver() {
    if (isHost&& nextLeader.value.name === '') {
        return
    }
    isTakeOverVisible.value = true
    isMemberListVisible.value = false

}

const goodBye = ref(false)

async function quitClub() {
    try{
        let nextHostId = nextLeader.value.clubMemberId;
        // console.log("그룹인원은??",clubMemberList.value)
        // console.log("그룹장인가? -> ",isHost)
        console.log("차세대 리더의 clubMemberId -> ",nextHostId)
        if (isHost && clubMemberList.value.length !== 1){ // 그룹장이면서 그룹 인원이 2명 이상일 때는 그룹장 넘기기 진행
            const takeOverSuccess = await clubStore.updateClubLeader(clubId,nextHostId);
            // console.log("그룹장 잘 넘겼나?? --> ",takeOverSuccess)
            if (!takeOverSuccess) {
                alert("그룹장 넘기기 실패!")
                return;
            }
        }

        console.log("****그룹장을 넘겼으니 그룹을 나가볼까?****")
        const leaveSuccess = await clubStore.leaveClub(clubId);
        if(leaveSuccess) {
            // alert("함께해서 더러웠고 다신 만나지 말자!")
            console.log("****히히 그룹 떠나기 발사*****")
            goodBye.value = true;
        }


    } catch(err){
        console.log("****그룹 나가기 실패!!!****")
        console.error(err)
        alert("그룹 나가기 실패!")
        closeModal()
    }
}

function leaveForever() {
    goodBye.value = false;
    window.location.replace("/")
}


// QuitClub.vue 닫음
function closeModal() {
    modalVisible.value = false
    emits('quit-club-close')
}

onMounted(() => {
    console.log("히히 그룹 탈퇴 발사")
})

</script>


<style scoped lang="scss">
h1{
    color:black;

}
.v-container {
    padding:0px;
}


.v-card-title {
    font-weight : bold;
    font-size : 2rem;
    text-align : center;
    margin-top : 10px;
}

.mention {
    font-size : 1rem;
    text-align : center;
    margin-top : 10px;
    padding:10px;
}

.member-list-title {
    text-align: center !important;
}

.member {
    margin-left : 150px;
    cursor: pointer;
}
.star{
    transform:translateY(-5px) translateX(-80px) !important 
} 

.text-center {
    text-align: center;
}

.button {
    display: flex;
    justify-content: center;
    align-items: center;
    margin:auto;
    box-shadow: none !important;
    background:transparent
}

</style>