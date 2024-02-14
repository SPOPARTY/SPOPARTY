<template>
    <v-container>
        <v-row justify="center" style="padding-right:15px;">
            <v-col cols="10">
                <h1 class="text-center" style="color:white; margin-left:150px;">그룹 게시판</h1>
            </v-col>
            <v-col cols="2">
                <v-btn class="write-board" variant="outlined" @click="writeBoard">글쓰기</v-btn>
            </v-col>
        </v-row>
        <v-row>
            <v-col
                cols="12"
                md="4"
                v-for="(post,index) in posts"
                :key="index"
            >   
                <BoardDetail
                    v-if="isDetailVisible && currentPost.id === post.id" 
                    :post="currentPost"
                    @detail-close="isDetailVisible = false"
                />
                <v-card class="card" @click="showBoardDetail(post)" >
                    <v-card-title class="text-center">{{ post.title }}</v-card-title>
                    <v-card-subtitle class="text-right">{{ post.member.nickname }}</v-card-subtitle>
                    <v-card-text class="text-right">{{ formatDateTime(post.updatedTime) }}</v-card-text>
                    <v-img v-if="post.file && post.file.type ==='image'" class="thumbnail" :src="post.file.url" :alt="post.title" cover height="200px"/>
                    <video v-if="post.file && post.file.type === 'video'" :src="post.file.url" cover height="200px"></video>
                </v-card>
            </v-col>
        </v-row>

        <v-btn icon class="left-arrow" color="white" variant="outlined" @click="goBack">
            <v-icon>mdi-arrow-left</v-icon>
        </v-btn>
    </v-container>
    
</template>

<script setup>
import {ref, onMounted,watch} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import {useBoardStore} from '@/stores/club/boards';
import {formatDateTime} from "@/util/tools.js"

import BoardDetail from '@/components/board/BoardDetail.vue';
const boardStore = useBoardStore();

const router = useRouter();
const routes = useRoute();

const clubId = routes.params.clubId;

const posts = ref([]);
watch(() => boardStore.boardList,(newBoardList) => {
    posts.value = newBoardList;
})

function writeBoard() {
    router.push(`/club/${clubId}/board/write`)
}

function goBack() {
    router.push(`/club/${clubId}`)
}

// 현재 게시글을 담을 변수 -> store로직 추가하면서 바꾸자
const currentPost = ref(null);

// 게시글 상세 on/off
const isDetailVisible = ref(false);

// 게시글 상세 on
const showBoardDetail = (post) => {
    currentPost.value = post 
    isDetailVisible.value  = true;
}

onMounted(() => {
    // console.log(`********${clubId}의 boardList********`)
    boardStore.getBoardList(clubId);
})

</script>

<style lang="scss" scoped>
h1{
    color:black
}

.card {
    min-height: 300px; /* 예시 높이, 필요에 따라 조정 */
    display: flex;
    flex-direction: column;
    background-color: #F4F3EA ;
}

.content{
    text-align: center;
    padding:0px;
}

.write-board{
    color:white; 
    margin-top:10px;
    margin-left:110px;
}

.left-arrow {
    position: fixed;
    left : 0;
    top : 50%;
}

.thumbnail{
    width : 100%;
    height: 100%;
    object-fit: cover;
}

.v-card-text{
    flex-grow: 1;
}

</style>