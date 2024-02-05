<template>
    <v-card class= "ma-5" outlined>
        <v-row class="pa-4" align="center" justify="start" no-gutters style="background-color: #E0E0E0;">
            <v-col cols="10">
                <div class="headline">{{ clubInfo.name }}</div>
                <div class="club-host-name">그룹장 : {{ clubInfo.hostName }}</div>
                <div>정원 - {{clubInfo.currentParticipants}}/{{ clubInfo.maxParticipants }}</div>
            </v-col>
            <v-col cols="2">
                <v-icon large @click="showClubMemberFunc">mdi-cog</v-icon>
                <ClubLeader 
                    v-if="isClubMemberFuncVisible && clubInfo.hostId == memberId"
                    @club-leader-close="closeClubMemberFunc"
                    />
                <ClubMember 
                    v-if="isClubMemberFuncVisible && clubInfo.hostId != memberId"
                    @club-member-close="closeClubMemberFunc"
                    />
            </v-col>
        </v-row>
        
        <v-btn block color="pink lighten-3" @click="showClubMembers">그룹원 보기</v-btn>

        <v-btn block color="blue" style="margin-top:30px;" @click="showInvite">그룹으로 초대</v-btn>

        <v-btn @click="goToPartyPage" block color="red" dark class="mt-4" style="height:100px;">
            파티를 열어보세요!
        </v-btn>
    </v-card>

    <!-- 그룹원 보기 -->
    <v-dialog 
        v-model="isClubMemberVisible"
        max-width="400px"
        max-height="400px"
        >
        <v-card class="member-list">
            <v-card-title>그룹원 목록</v-card-title>
            <div v-for="(member, index) in clubMemberList" :key="index" class="member">
                <v-card-text>
                    {{ member.memberNickName}}
                    <v-icon v-if="member.role == 'host'" class="star">mdi-star</v-icon>
                </v-card-text>
            </div>
        </v-card>
        
    </v-dialog>

    <!-- 그룹으로 초대 -->
    <v-dialog
        v-model="isInviteVisible"
        max-width="400px"
        max-height="200px"
    >   
        <v-card class="align-items-center">
            <v-card-title>초대 링크를 복사하세요!</v-card-title>
            <div class="text-to-copy">
                <v-card-text class="invite">
                    URL : <b><u>{{ inviteURL }}</u></b>
                    <v-btn @click="copyText" class="copy-btn">
                        <v-icon>mdi-content-copy</v-icon>
                    </v-btn>
                </v-card-text>
            </div>
        </v-card>

    </v-dialog>
  </template>

<script setup>
import {ref,onMounted,computed} from 'vue'
import { useRoute, useRouter } from 'vue-router';

import {useClubStore} from '@/stores/club/clubs';

import ClubLeader from '@/components/club/ClubLeader.vue';
import ClubMember from '@/components/club/ClubMember.vue';

const props = defineProps({
    clubInfo:Object,
    clubMemberList:Object
})

const clubStore = useClubStore();
const router = useRouter();
const route = useRoute();
const clubId = route.params.clubId;

const clubInfo = computed(()=> {
    return props.clubInfo
})

const clubMemberList = computed(() => {
    return props.clubMemberList
})


const memberId = sessionStorage.getItem("id");

// 그룹원 보기 on/off
const isClubMemberVisible = ref(false)

function showClubMembers() {
    isClubMemberVisible.value = true;
}

// 그룹원 초대 on/off
const inviteURL = computed(() => {
    return clubStore.clubInviteLink;
})

const isInviteVisible = ref(false)
async function showInvite() {
    isInviteVisible.value = true;

    await clubStore.getClubInviteLink(clubId);

}



// 그룹 초대 링크 복사
const copyText = async() => {
    try {
        await navigator.clipboard.writeText(inviteURL.value);
        alert("텍스트가 클립보드에 복사되었습니다.")
    } catch (err) {
        console.error('복사 실패 : ',err);
        alert('텍스트 복사에 실패했습니다.')
    }
}


// 그룹원 or 그룹장 세부기능 on/off
const isClubMemberFuncVisible = ref(false);

const showClubMemberFunc = () => {
    console.log("히히 그룹원/그룹장 세부기능 발사")
    isClubMemberFuncVisible.value = true;
}

function closeClubMemberFunc() {
    isClubMemberFuncVisible.value = false
}

// 파티 페이지 로직
const isPartyMade = ref(false)

const goToPartyPage = () => {
    const partyId = getPartyId();
    const url = router.resolve({ name: 'PartyView', params: { partyId } }).href;
    window.open(url, '_blank');
}

const getPartyId = () => {
    let partyId;
    if (isPartyMade.value) {
        // 이미 존재하는 파티 아이디 받아옴
        partyId = 2; // 실제 파티 ID를 여기에 할당해야 합니다.
    } else {
        // 새로운 파티 아이디 생성
        partyId = 1; // 새 파티 ID 생성 로직을 여기에 추가해야 합니다.
        isPartyMade.value = true; // 파티가 생성되었음을 상태로 저장
    }
    return partyId;
}

</script>


<style lang="scss" scoped>


.member-list{
    border:solid;
    border-radius: 40px;
    & .member {
        transform:translateX(150px)  
    }
    & .star {
        transform:translateY(-3px) translateX(-80px) !important
    }
}

.v-card-title {
    text-align: center;
}

div.text-to-copy {
    text-align: center;
    margin-left:10px;
    transform:translateY(-10px) translateX(10px);

}

.copy-btn {
    box-shadow: none !important;
    background:none;
    & hover {
        background-color : transparent !important
    }
}

</style>