<template>
    <v-container class="test">
        <v-row class="headers">
            <v-col cols="10">
                <h1 class="text-center" style="color:white; margin-left:100px;">추억 아카이브</h1>
            </v-col>
            <v-col cols="2">
                <v-btn class="download-button" variant="outlined" @click="showDownloadConfirm">
                    <v-icon>mdi-download</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        <v-row>
            <v-col
                cols="12" sm="6" md="4" lg="4"
                v-for="(detail, index) in dummy"
                :key="index"
            >
                <ArchiveDetail 
                    v-if="isDetailVisible && currentDetail.id === detail.id"
                    :detail="currentDetail" 
                    @detail-close="isDetailVisible = false"
                />
                <v-card @click="showDetailModal(detail)">
                    <v-row>
                        <v-col cols="10">
                            <v-card-item><v-img :src="detail.img" :alt="detail.title"/></v-card-item>
                        </v-col>
                        <v-col cols="2">
                            <v-checkbox
                                v-model="detail.selected"
                                @click.native.stop
                            />
                        </v-col>
                    </v-row>
                    <v-card-subtitle>{{ detail.title }}</v-card-subtitle>
                    <v-card-text>{{ detail.created_time }}</v-card-text>
                </v-card>
            </v-col>
        </v-row>

        <v-dialog v-model="downloadConfirm" persistent max-width="400px">
            <v-card class="text-center">
                <v-card-title >추억 보관하기</v-card-title>
                <v-card-text>선택하신 추억은 다음과 같습니다.</v-card-text>
                <p v-for="(item, index) in selectedPosts" :key="index">
                    [{{ item.title }}]
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
import {ref, computed} from 'vue';
import {useRouter, useRoute} from 'vue-router'

import ArchiveDetail from '@/components/archieve/ArchiveDetail.vue';

const router = useRouter();
const routes = useRoute();
const clubId = routes.params.clubId;

const dummy = ref([
    {id : 1, title : "제목 1", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
    {id : 2, title : "제목 2", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
    {id : 3, title : "제목 3", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
    {id : 4, title : "제목 4", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
    {id : 5, title : "제목 5", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
    {id : 6, title : "제목 6", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
    {id : 7, title : "제목 7", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
    {id : 8, title : "제목 8", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
    {id : 9, title : "제목 9", img : "/tear.png", created_time : "2024년 1월 24일", selected : false},
])

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
    return dummy.value.filter(post => post.selected);
})

// 진짜 다운로드
function downloadSelected() {
    console.log("히히 다운로드 발사 =>", selectedPosts)
    closeDownloadConfirm();
}

function goBack() {
    router.push(`/club/${clubId}`)
}

</script>

<style lang="scss" scoped>
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


</style>
