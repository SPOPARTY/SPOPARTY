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
                <v-card @click="showBoardDetail" >
                    <v-card-title>{{ post.title }} - {{ index+1 }}</v-card-title>
                    <v-card-subtitle>{{ post.nickname }}</v-card-subtitle>
                    <v-card-text>{{ post.created_time }}</v-card-text>
                    <v-card-text>{{ post.content }}</v-card-text>
                    <v-card-item> <img :src="post.img" :alt="post.title"></v-card-item>
                </v-card>
                    <BoardDetail
                        v-if="isDetailVisible"
                        :detail="post"
                        @detail-close="isDetailVisible = false"
                    />
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
    {id: 1,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {id: 2,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {id: 3,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {id: 4,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {id: 5,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {id: 6,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {id: 7,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {id: 8,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {id: 9,title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
])

const hasMore = ref(true);
const router = useRouter();
const routes = useRoute();

const clubId = routes.params.clubId;

function writeBoard() {
    router.push(`/club/${clubId}/board/write`)
}

function goBack() {
    router.push(`/club/${clubId}`)
}

const isDetailVisible = ref(false);
const showBoardDetail = () => {
    isDetailVisible.value  = true;
}

</script>

<style lang="scss" scoped>
.left-arrow {
    position: fixed;
    left : 0;
    top : 50%;
}



</style>