<template>
    <v-dialog 
        v-model="ModalVisible"
        max-width="400px"
        max-height="600px"
        @click:outside="closeModal"
        persistent
        >
        <v-card>
            <div class="title-container">
                <div class="flex-item title">
                    <v-card-title class="text-center"><h3>그룹원 기능</h3></v-card-title>
                </div>
                <div class="flex-item icon">
                    <v-btn :ripple="false" @click="closeModal" class="no-background-hover">
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </div>
            </div>

            <div class="feature-container" @click="showQuitClub" :hover="{hover:true}">
                <v-img class="img" src="/exit_club.png" alt="그룹 나가기"/>
                <v-card-text class="feature-text">그룹 나가기</v-card-text>
            </div>
            <QuitClub
                v-if="isQuitClubVisible"
                :club-member-list="clubMemberList"
                @quit-club-close="closeQuitClub"
            />

        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref, watch,onMounted} from 'vue';
import {useRouter, useRoute} from 'vue-router';
import {useClubStore} from '@/stores/club/clubs'

import QuitClub from '@/components/club/QuitClub.vue';

const route = useRoute();

const emits = defineEmits([
    'club-member-close'
])

const ModalVisible = ref(true)

const clubId = route.params.clubId;
const clubStore = useClubStore();

const clubMemberList = ref([]);
watch(() => clubStore.clubMemberList,(newClubMemberList) => {
    clubMemberList.value = newClubMemberList;
},{immediate:true})


function closeModal(){
    ModalVisible.value = false;
    emits('club-member-close')
}

const isQuitClubVisible = ref(false)

function showQuitClub(){
    isQuitClubVisible.value = true
}

function closeQuitClub() {
    isQuitClubVisible.value = false
}

onMounted(()=> {
    clubStore.getClubMemberList(clubId);
})

</script>

<style lang="scss">
.no-background-hover {
  box-shadow: none !important;

  &:hover {
    background-color: transparent !important;
  }
}

.v-card-title{
    margin-top:10px;
    margin-bottom:10px;
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
    &:hover{
        background-color:#FDFFAB;
    }
}

.feature-text {
    margin-top:10px;
    margin-left : 12px;
    transform: translateY(-12px); // 10px위로 올리기
}

.img{
    margin-top:15px;
    width : 40px;
    height: 30px;
}


</style>