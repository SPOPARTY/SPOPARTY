<template>
    <v-dialog
        v-model="ModalVisible"
        max-width="400px"
        max-height="1000px"
        @click:outside="closeModal"
        persistent
        >
        <v-card>
            <div class="title-container">
                <div class="flex-item title">
                    <v-card-title class="text-center">그룹장 기능</v-card-title>
                </div>
                <div class="flex-item icon">
                    <v-btn :ripple="false" @click="closeModal" class="no-background-hover">
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </div>
            </div>
            <div class="feature-container" @click.native="showChangeClubName">
                <v-img class="img" src="/change_club_name.png" alt="그룹명 바꾸기"/>
                <v-card-text class="feature-text">그룹명 바꾸기</v-card-text>
            </div>
            <div class="feature-container" @click="showQuitClub">
                <v-img class="img" src="/exit_club.png" alt="그룹 나가기"/>
                <v-card-text class="feature-text">그룹 나가기</v-card-text>
            </div>
            <div class="feature-container" @click="showBanClubMember">
                <v-img class="img" src="/ban_club_member.png" alt="그룹원 강퇴"/>
                <v-card-text class="feature-text">그룹원 강퇴</v-card-text>
            </div>
        </v-card>
    </v-dialog>

    <ChangeClubName
        v-if="isChangeClubNameVisible"
        @change-club-name-close="closeChangeClubName"
    />
    <QuitClub
        v-if="isQuitClubVisible"
        :club-member-list="clubMemberList"
        @quit-club-close="closeQuitClub"
    />
    <BanClubMemeber
        v-if="isBanClubMemberVisible"
        :club-member-list="clubMemberList"
        @ban-member-close="closeBanClubMember"
    />
</template>

<script setup>
import {ref, onMounted,watch} from 'vue';
import {useRouter, useRoute} from 'vue-router'

import ChangeClubName from '@/components/club/ChangeClubName.vue';
import QuitClub from '@/components/club/QuitClub.vue';
import BanClubMemeber from '@/components/club/BanClubMemeber.vue';

import {useClubStore} from '@/stores/club/clubs'

const router = useRouter();
const route = useRoute();

const emits = defineEmits([
    'club-leader-close'
])

const clubStore = useClubStore();

const clubId = route.params.clubId;

const ModalVisible = ref(true)

const clubMemberList = ref([]);
watch(() => clubStore.clubMemberList,(newClubMemberList) => {
    clubMemberList.value = newClubMemberList;
},{immediate:true})

// 그룹명 바꾸기 
const isChangeClubNameVisible = ref(false)

function showChangeClubName() {
    isChangeClubNameVisible.value = true
}

function closeChangeClubName() {
    isChangeClubNameVisible.value = false
}

// 그룹 나가기
const isQuitClubVisible = ref(false)

function showQuitClub(){
    isQuitClubVisible.value = true
}

function closeQuitClub() {
    isQuitClubVisible.value = false
}

// 그룹원 강퇴
const isBanClubMemberVisible = ref(false)

function showBanClubMember() {
    isBanClubMemberVisible.value = true
}

function closeBanClubMember() {
    isBanClubMemberVisible.value = false;
}


// 그룹장 기능 모달 닫기
function closeModal(){
    ModalVisible.value = false;
    emits('club-leader-close')
}

onMounted(()=> {
    clubStore.getClubMemberList(clubId);
})

</script>

<style scoped lang="scss">
.no-background-hover {
  box-shadow: none !important;

  &:hover {
    background-color: transparent !important;
  }
}

.title-container {
    display: flex;
    flex-direction: row; /* 가로 방향으로 요소들을 배치 */
    align-items: center; /* 세로 방향으로 요소들을 중앙 정렬 */
    justify-content: space-between; /* 첫 번째 요소는 왼쪽, 두 번째 요소는 오른쪽에 배치 */
}

.flex-item {
    margin-right: 6px; /* 요소들 사이의 간격 조정 */
    &.title {
        flex-grow: 1;
        text-align: center;
        padding-left:70px;
    }
}

.feature-container {
    display : flex;
    flex-direction : row;
    margin: 8px 4px;
    cursor: pointer;

}

.feature-text {
    margin-left : 12px;
    transform: translateY(-12px); // 10px위로 올리기
}

.img{
    width : 40px;
    height: 30px;
}

</style>