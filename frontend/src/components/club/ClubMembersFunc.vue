<template>
    <v-card class="background" outlined>
        <v-row class="pa-4" align="center" justify="start" no-gutters style="background-color: #E0E0E0;">
            <v-col cols="10">
                <div class="headline">그룹명 - {{ clubInfo.name }}</div>
                <div class="club-host-name">그룹장 - {{ clubInfo.hostName }}</div>
                <div>정원 - {{clubInfo.currentParticipants}}/{{ clubInfo.maxParticipants }}</div>
            </v-col>
            <v-col cols="2">
                <v-icon large @click="showClubMemberFunc">mdi-cog</v-icon>
                <ClubLeader v-if="isClubMemberFuncVisible && clubInfo.hostId == memberId"
                    @club-leader-close="closeClubMemberFunc" />
                <ClubMember v-if="isClubMemberFuncVisible && clubInfo.hostId != memberId"
                    @club-member-close="closeClubMemberFunc" />
            </v-col>
        </v-row>
        
        <v-btn block color="#81689D" @click="showClubMembers">그룹원 보기</v-btn>

        <div style="padding-left:50px; padding-right:50px;">
            <v-btn class="invite-button" block color="#6C22A6" 
                style="margin-top:30px; margin-bottom:30px;" 
                @click="showInvite">그룹으로 초대
            </v-btn>
        </div>

        <v-btn @mouseenter="newPartyInfo" v-bind="props" @click="goToPartyPage" block :color="isPartyExist ? 'primary' : 'red'"
            dark class="mt-4" style="height:100px;">
            <v-tooltip v-if="isPartyExist" activator="parent" location="top">
                <p>인원 : {{ partyInfo.currentParticipants }} / {{ partyInfo.maxParticipants }}</p>
                <p>{{ partyInfo.fixtureInfo?.leagueName }} {{ partyInfo.fixtureInfo?.round }}</p>
                <p v-if="partyInfo.fixtureInfo">{{ partyInfo.fixtureInfo?.homeTeam?.name }} vs {{
                    partyInfo.fixtureInfo?.awayTeam?.name }}</p>
            </v-tooltip>
            <div v-if="!isPartyExist">파티를 열어보세요!</div>
            <div v-else>
                <p>{{ partyInfo.title }}</p>
                <p>파티 페이지로 이동</p>
            </div>
        </v-btn>
    </v-card>

    <!-- 그룹원 보기 -->
    <v-dialog v-model="isClubMemberVisible" max-width="400px" max-height="400px">
        <v-card class="member-list">
            <v-card-title>그룹원 목록</v-card-title>
            <div v-for="(member, index) in clubMemberList" :key="index" class="member">
                <v-card-text>
                    {{ member.memberNickName }}
                    <v-icon v-if="member.role == 'host'" class="star">mdi-star</v-icon>
                </v-card-text>
            </div>
        </v-card>

    </v-dialog>

    <!-- 그룹으로 초대 -->
    <v-dialog v-model="isInviteVisible" max-width="400px" max-height="200px">
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
import { ref, onMounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router';

import { useClubStore } from '@/stores/club/clubs';
import { usePartyStore } from '@/stores/club/party/party';

import ClubLeader from '@/components/club/ClubLeader.vue';
import ClubMember from '@/components/club/ClubMember.vue';
import { nextDay, set } from 'date-fns';

const props = defineProps({
    clubInfo: Object,
    clubMemberList: Object
})

const clubStore = useClubStore();
const partyStore = usePartyStore();

const router = useRouter();
const route = useRoute();
const clubId = route.params.clubId;

const { getClubInfo } = clubStore;

const clubInfo = ref([]);
getClubInfo(clubId)

watch(() => clubStore.clubInfo, (newClubInfo) => {
    clubInfo.value = newClubInfo;
    console.log("000클럽인포 와치000", newClubInfo)
}, { immediate: true, deep: true });

const clubMemberList = computed(() => {
    return props.clubMemberList
})

const memberId = localStorage.getItem("id");

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
const copyText = async () => {
    try {
        await navigator.clipboard.writeText(inviteURL.value);
        alert("텍스트가 클립보드에 복사되었습니다.")
    } catch (err) {
        console.error('복사 실패 : ', err);
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
// const isPartyExist = ref(clubInfo.value.partyId ? true : false);
// computed(() => {
//     if (partyStore.partyInfo.value) {
//         isPartyExist.value = true;
//         partyId.value = partyStore.partyInfo.value.partyId;
//         console.log("파티가 있어요", partyId.value)
//     } else {
//         isPartyExist.value = false;
//         console.log("파티가 없어요")
//     }
// })

const partyInfo = ref(getPartyInfo(clubId, partyId.value));

// setInterval(() => {
//     count.value = !count.value;
// }, 1000);

watch(() => partyStore.partyInfo, (newPartyInfo) => {
    console.log("파티인포 와치", newPartyInfo)
    partyInfo.value = newPartyInfo;
    if (newPartyInfo && Object.keys(newPartyInfo).length > 0) {
        // if (newPartyInfo?.partyId) {
        console.log("11111", newPartyInfo)
        isPartyExist.value = true;
        partyId.value = newPartyInfo.partyId;
    } else {
        isPartyExist.value = false;
    }
    console.log("파티id", partyId.value)
    console.log("파티유무", isPartyExist.value)
    // getClubInfo(clubId);
}, { immediate: true, deep: true });

watch(() => clubInfo.value, (newClubInfo) => {
    // console.log("클럽인포 와치", newClubInfo)
    // clubInfo.value = newClubInfo;
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
    console.log("#########파티 페이지로 이동합니다#######")
    let startTime = Date.now(); // 시작 시간
    let timeoutDuration = 100; // 체크 간격: 0.1초
    let maxWaitTime = 2000; // 최대 대기 시간: 2초
    // let tempPartyId = null; 
    // partyId를 임시 저장할 변수

    if (isPartyExist.value) {
        await getClubInfo(clubId);
        console.log("파티가 있어요", clubInfo.value.partyId);
    } else {
        await postPartyInfo(clubId);
        await getClubInfo(clubId);
        console.log("파티가 생성되었어요", clubInfo.value.partyId);
        isPartyExist.value = true;
    }

    // partyId가 업데이트 될 때까지 기다리는 함수
    function waitForPartyIdUpdate() {
        let elapsedTime = Date.now() - startTime; // 경과 시간
        if (partyId.value) {
            console.log("업데이트된 partyId:", partyId.value);
            openPartyPage(partyId.value);
        } else if (elapsedTime > maxWaitTime) {
            console.error("파티 ID 업데이트 대기 시간 초과");
        } else {
            console.log("파티 ID 대기 중...");
            setTimeout(waitForPartyIdUpdate, timeoutDuration);
        }
    }

    // partyId 업데이트를 기다림
    waitForPartyIdUpdate();
};

const openPartyPage = (partyId) => {
    console.log("파티 페이지로 이동합니다", partyId);
    if (!partyId) {
        console.error("partyId가 제공되지 않았습니다.");
        return;
    }
    getPartyInfo(clubId, partyId);
    router.push({ name: 'PartyView', params: { clubId, partyId } });
    console.log("########종료########")
};

const newPartyInfo = async () => {
    console.log("버튼을 호버해 새 파티 정보를 가져옵니다.")
    const tempInfo = await getPartyInfo(clubId, partyId.value);
    setTimeout(() => {
        console.log("새 파티 정보", tempInfo)
        if (tempInfo == undefined) {
            console.log("#새 파티# 파티가 없습니다.")
            isPartyExist.value = false;
            partyId.value = null;
            clubInfo.value.partyId = null;
            console.log(clubInfo.value)
        } else {
            console.log("파티가 있습니다.")
            isPartyExist.value = true;
        }
    }, 500)
    // if (tempInfo) {
    //     console.log("파티가 있습니다.")
    //     if (!isPartyExist.value) {
    //         isPartyExist.value = true;
    //     }
    //     return
    // } else {
    //     console.log("파티가 없습니다.")
    //     if (isPartyExist.value) {
    //         isPartyExist.value = false;
    //     }
    // }
}


</script>


<style lang="scss" scoped>
.background {
    background-color : #08042B;
    margin-top:30px;
}

.invite-button{
    margin : auto;
}

.member-list{
    border:solid;
    border-radius: 40px;

    & .member {
        transform: translateX(150px)
    }

    & .star {
        transform: translateY(-3px) translateX(-80px) !important
    }
}

.v-card-title {
    text-align: center;
}

div.text-to-copy {
    text-align: center;
    margin-left: 10px;
    transform: translateY(-10px) translateX(10px);

}

.copy-btn {
    box-shadow: none !important;
    background: none;

    & hover {
        background-color: transparent !important
    }
}

.party-title {
    margin: 5px;
    font-size: 1rem;
    font-weight: bold;
}
</style>