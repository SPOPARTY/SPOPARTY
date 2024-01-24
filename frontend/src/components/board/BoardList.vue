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
                    @delete-post="deletePost"
                />
                <v-card @click="showBoardDetail(post)" >
                    <v-card-title>{{ post.title }}</v-card-title>
                    <v-card-subtitle>{{ post.nickname }}</v-card-subtitle>
                    <v-card-text>{{ post.created_time }}</v-card-text>
                    <v-card-text>{{ post.content }}</v-card-text>
                    <v-card-item> <img :src="post.img" :alt="post.title"></v-card-item>
                </v-card>
            </v-col>
        </v-row>

        <v-btn icon class="left-arrow" @click="goBack">
            <v-icon>mdi-arrow-left</v-icon>
        </v-btn>
    </v-container>
    
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import BoardDetail from '@/components/board/BoardDetail.vue';

const posts = ref([
    {id: 1,title : "제목 1", nickname : "글쓴이 1" , created_time : "작성일 1", content:"작성내용 1", img : "/src/assets/never_heard.jpg"},
    {id: 2,title : "제목 2", nickname : "글쓴이 2" , created_time : "작성일 2", content:"작성내용 2", img : "/src/assets/never_heard.jpg"},
    {id: 3,title : "제목 3", nickname : "글쓴이 3" , created_time : "작성일 3", content:"작성내용 3", img : "/src/assets/never_heard.jpg"},
    {id: 4,title : "제목 4", nickname : "글쓴이 4" , created_time : "작성일 4", content:"작성내용 4", img : "/src/assets/never_heard.jpg"},
    {id: 5,title : "제목 5", nickname : "글쓴이 5" , created_time : "작성일 5", content:"작성내용 5", img : "/src/assets/never_heard.jpg"},
    {id: 6,title : "제목 6", nickname : "글쓴이 6" , created_time : "작성일 6", content:"작성내용 6", img : "/src/assets/never_heard.jpg"},
    {id: 7,title : "제목 7", nickname : "글쓴이 7" , created_time : "작성일 7", content:"작성내용 7", img : "/src/assets/never_heard.jpg"},
    {id: 8,title : "제목 8", nickname : "글쓴이 8" , created_time : "작성일 8", content:"작성내용 8", img : "/src/assets/never_heard.jpg"},
    {id: 9,title : "제목 9", nickname : "글쓴이 9" , created_time : "작성일 9", content:"작성내용 9", img : "/src/assets/never_heard.jpg"},
])

const router = useRouter();
const routes = useRoute();

const clubId = routes.params.clubId;

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

const deletePost = (postId) => {
    posts.value = posts.value.filter(p => p.id != postId)
}



</script>

<style lang="scss" scoped>
.left-arrow {
    position: fixed;
    left : 0;
    top : 50%;
}



</style>