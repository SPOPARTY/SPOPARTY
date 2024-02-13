<template>
    <v-container class="test">
        <h1 class="text-center" style="color:white;">추억 아카이브</h1>
        <v-row>
            <v-col
                cols="12" sm="4" md="4" lg="4"
                v-for="(detail, index) in archiveList"
                :key="index"
            >
                <ArchiveDetail 
                    v-if="isDetailVisible && currentDetail.id === detail.id"
                    :detail="currentDetail" 
                    @detail-close="isDetailVisible = false"
                />
                <v-card class="card" @click="showDetailModal(detail)">
                    <v-card-title v-if="detail.fixtureTitle" class="text-center">{{detail.fixtureTitle}}</v-card-title>
                    <v-card-title v-else class="text-center">이름없는 추억 하나</v-card-title>
                    <v-card-subtitle v-if="detail.partyTitle" class="text-right">파티명 : {{ detail.partyTitle }}</v-card-subtitle>
                    <v-card-subtitle v-else class="text-right">수많은 파티 그 중 하나</v-card-subtitle>
                    <v-card-text class="text-right">작성자 : {{ detail.member.nickname }}</v-card-text>
                    <v-card-subtitle class="text-right">생성 날짜 : {{ formatDateTime(detail.createdTime) }}</v-card-subtitle>
                    <v-img v-if="detail.file.type == 'image'" :src="detail.file.url" :alt="detail.file.url" class="thumb_img" cover height="200px"/>
                    <v-img v-else :src="detail.thumbnail.url" :alt="detail.thumbnail.url" class="thumb_img" cover height="200px"></v-img>                </v-card>
            </v-col>
        </v-row>

        <v-dialog v-model="downloadConfirm" persistent max-width="400px">
            <v-card class="text-center">
                <v-card-title >추억 보관하기</v-card-title>
                <v-card-text>선택하신 추억은 다음과 같습니다.</v-card-text>
                <p v-for="(item, index) in selectedPosts" :key="index">
                    [{{ item.fixtureTitle }}]
                </p>
                <v-card-text>추억하시겠습니까?</v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="downloadSelected">추억하기</v-btn>
                    <v-btn color="green darken-1" text @click="closeDownloadConfirm">취소</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <v-btn icon class="left-arrow" color="white" variant="outlined" @click="goBack">
            <v-icon>mdi-arrow-left</v-icon>
        </v-btn>
    </v-container>
</template>

<script setup>
import {ref, computed, watch, onMounted} from 'vue';
import {useRouter, useRoute} from 'vue-router'
import {useArchiveStore} from '@/stores/club/archives';
import {formatDateTime} from "@/util/tools.js"

import ArchiveDetail from '@/components/archive/ArchiveDetail.vue';

const archiveStore = useArchiveStore();

const router = useRouter();
const routes = useRoute();
const clubId = routes.params.clubId;


const archiveList = ref([]);
watch(() => archiveStore.archiveList,(newArchiveList) => {
    archiveList.value = newArchiveList;
    archiveList.value.forEach((archive) => {
        archive.selected = false;
    })
})

// 아카이브 상세 모달 보이기
const isDetailVisible = ref(false);

// 지금 선택한 모달
const currentDetail = ref(null);

// 아카이브 상세모달 on
const showDetailModal = (post) => {
    currentDetail.value = post
    isDetailVisible.value = true;
}

// 다운로드 확인 모달 on/off
const downloadConfirm = ref(false);

// 다운로드 확인 모달 on
function showDownloadConfirm() {
    downloadConfirm.value = true;
}

// 다운로드 확인 모달 off
function closeDownloadConfirm() {
    downloadConfirm.value = false;
}

// 선택한 추억들 반응형으로 탐색
const selectedPosts = computed(() => {
    return archiveList.value.filter(post => post.selected);
})

// 진짜 다운로드
function downloadSelected() {
    closeDownloadConfirm();
}

function goBack() {
    // console.log(`********${clubId}의 ArchiveList********`)
    router.push(`/club/${clubId}`)
}

onMounted(() => {
    archiveStore.getArchiveList(clubId);
})

</script>

<style lang="scss" scoped>
.card {
    min-height: 300px;
}

.download-button{
    color:white; 
    margin-top:10px;
    margin-left:110px;
}

.left-arrow {
    position: fixed;
    left : 0;
    top : 50%;
}

.thumb_img{
    height:100%;
    width:100%;
}
</style>
