<template>
    <v-container>
        <v-row justify="center">
            <v-col cols="10">
                <h1 class="text-center">그룹 게시판</h1>
            </v-col>
            <v-col cols="2">
                <v-btn color="primary" @click="writeBoard">글쓰기</v-btn>
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
                <v-card @click="showBoardDetail(post)" >
                    <v-card-title>{{ post.title }}</v-card-title>
                    <v-card-subtitle>{{ post.member.nickname }}</v-card-subtitle>
                    <v-card-text >{{ formatDateTime(post.updatedTime) }}</v-card-text>
                    <v-card-item v-if="post.file"> <img class="thumbnail" :src="post.file.url" :alt="post.title"></v-card-item>
                    <v-card-text>
                        <div>
                            <div v-html="post.content"></div>
                        </div>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>

        <v-btn icon class="left-arrow" @click="goBack">
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

onMounted(() => {
    boardStore.getBoardList(clubId);
})


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



</script>

<style lang="scss" scoped>
.left-arrow {
    position: fixed;
    left : 0;
    top : 50%;
}

.thumbnail{
    width : 100%;
    height: 100%;
}

</style>