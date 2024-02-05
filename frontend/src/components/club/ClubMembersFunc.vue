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
            <div v-if="!isPartyExist">파티를 열어보세요!</div>
            <div v-else>
                <p>파티 페이지로 이동</p>
                <p class="party-title">{{ partyInfo.title }}</p>
                <p>{{ partyInfo.hostNickName }}</p>
                <p>{{ partyInfo.fixtureInfo?.leagueName }} {{ partyInfo.fixtureInfo?.round }}</p>
                <p v-if="partyInfo.fixtureInfo">{{ partyInfo.fixtureInfo?.homeTeam?.name }} vs {{ partyInfo.fixtureInfo?.awayTeam?.name }}</p>
                <p>{{ partyInfo.currentParticipants }} / {{ partyInfo.maxParticipants }}</p>
            </div>
        </v-btn>
        <!-- <div>
            {{ partyInfo }}
        </div> -->
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
import {ref,onMounted,computed, watch} from 'vue'
import { useRoute, useRouter } from 'vue-router';

import {useClubStore} from '@/stores/club/clubs';
import { usePartyStore } from '@/stores/club/party/party';

import ClubLeader from '@/components/club/ClubLeader.vue';
import ClubMember from '@/components/club/ClubMember.vue';

const props = defineProps({
    clubInfo:Object,
    clubMemberList:Object
})

const clubStore = useClubStore();
const partyStore = usePartyStore();

const router = useRouter();
const route = useRoute();
const clubId = route.params.clubId;

const clubInfo = ref([]);
const { getClubInfo } = clubStore;
getClubInfo(clubId);

watch(() => clubStore.clubInfo, (newClubInfo) => {
    clubInfo.value = newClubInfo;
}, { immediate: true, deep: true });

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

////////////////////////
//// 파티 페이지 로직

const { getPartyInfo, postPartyInfo } = partyStore;

const partyId = ref(clubInfo.value.partyId);

const isPartyExist = ref(false);

const partyInfo = ref(getPartyInfo(clubId, partyId.value));

watch(() => partyStore.partyInfo, (newPartyInfo) => {
    console.log("파티인포 와치", newPartyInfo)
    partyInfo.value = newPartyInfo;
    if (newPartyInfo) {
        isPartyExist.value = true;
        partyId.value = newPartyInfo.partyId;
    } else {
        isPartyExist.value = false;
    }
    console.log("파티id", partyId.value)
    console.log("파티유무", isPartyExist.value)
}, { immediate: true, deep: true });

watch(() => clubInfo.value, (newClubInfo) => {
    // console.log("클럽인포 와치", newClubInfo)
    const newPartyId = newClubInfo?.partyId;
    if (newPartyId) {
        // console.log("와치-파티가 있어요", newPartyId)
        isPartyExist.value = true;
        partyId.value = newPartyId;
        getPartyInfo(clubId, newPartyId);
    } else {
        isPartyExist.value = false;
    }
}, { immediate: true, deep: true });

onMounted(() => {
    if (clubInfo?.value.partyId) {
        isPartyExist.value = true;
        partyId.value = clubInfo.value.partyId;
        console.log("파티가 있어요", partyId.value)
    } else {
        isPartyExist.value = false;
        console.log("파티가 없어요")
    }
})

const goToPartyPage = async () => {
    if (isPartyExist.value) {
        await getClubInfo(clubId);
        console.log("파티가 있어요", clubInfo.value.partyId);
        // partyId.value = clubInfo.value.partyId;
    } else {
        await postPartyInfo(clubId);
        await getClubInfo(clubId);
        console.log("파티가 생성되었어요", partyId.value);
        isPartyExist.value = true;
    }
    console.log(partyId.value)
    openPartyPage(partyId.value);
}

const openPartyPage = (partyId) => {
    console.log("파티 페이지로 이동합니다", partyId); // partyId 값 확인
    if (!partyId) {
        console.error("partyId가 제공되지 않았습니다.");
        return;
    }
    const url = router.resolve({ name: 'PartyView', params: { partyId } }).href;
    window.open(url, '_blank');
}

// 파티 정보 예시
// "data": {
//     "partyId": 6,
//     "sessionId": "d3e07dd8-2e2b-4476-b694-cdd922efc8b0",
//     "title": "손흥민 폼 미쳤다이",
//     "maxParticipants": 6,
//     "currentParticipants": 0,
//     "hostNickName": "김종범",
//     "fixtureUrl": "https://www.coupangplay.com/titles/016d6d60-9a95-45ec-8e38-cd501fddb07c?type=LIVE&availability=&live=true&channel=&rowId=70bb548b-cf24-46e2-a656-040fa551d577&trackId=&src=page_discover_feed%3AMulti-Hero-Live-Event-Curation-0%3ALIVE&sourceType=&supportLive=",
//     "fixtureInfo": {
//       "leagueName": "챔피언십",
//       "round": "5차전",
//       "startTime": "2024-01-26 12:00:00.000000",
//       "homeTeam": {
//         "teamId": 1,
//         "name": "마루쉐"
//       },
//       "awayTeam": {
//         "teamId": 4,
//         "name": "멍뭉"
//       }
//     }
//   }

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

.party-title {
    margin : 10px;
    font-size: 2rem;
    font-weight: bold;
}
</style>