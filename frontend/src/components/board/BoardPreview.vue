<template>
    <v-toolbar flat class="preview-toolbar">
        <v-toolbar-title>그룹 게시판</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items>
            <RouterLink :to="`/club/${clubId}/board`">
                <v-btn icon>
                    <v-icon class="plus-btn">mdi-plus</v-icon>
                </v-btn>
            </RouterLink>
        </v-toolbar-items>
    </v-toolbar>
    <v-row>
        <v-col
            cols="12" sm="6" md="4" lg="4" 
            v-for="(post, index) in posts.slice(0,6)"
            :key="index"
            >
            <BoardDetail
                    v-if="isDetailVisible && currentPost.id === post.id" 
                    :post="currentPost"
                    @detail-close="isDetailVisible = false"
                    @delete-post="deletePost"
            />
            <v-card class="thumbnail" @click="showBoardDetail(post)">
                <v-card-title class="text-center">{{ post.title }}</v-card-title>
                <v-card-subtitle class=text-right>{{ post.member.nickname }}</v-card-subtitle>
                <v-card-text class="text-right">{{formatDateTime(post.file.updatedTime)}}</v-card-text>
                <v-card-item>
                    <v-img :src="post.file.url" class="thumb_img"/>
                </v-card-item>
                <v-card-text>{{ post.content }}</v-card-text>
            </v-card>
        </v-col>
    </v-row>
</template>

<script setup>
import {ref,onMounted,watch} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {useBoardStore} from '@/stores/club/boards';
import {formatDateTime} from "@/util/tools.js"

import BoardDetail from '@/components/board/BoardDetail.vue';

const boardStore = useBoardStore();

const routes = useRoute();
const clubId = routes.params.clubId

onMounted(() => {
    boardStore.getBoardList(clubId);
})


const posts = ref([]);
watch(() => boardStore.boardList,(newBoardList) => {
    posts.value = newBoardList;
})


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


<style>
.v-toolbar-title{
    color:white;
}

.preview-toolbar{
    background:white
}

.thumbnail {
    height:100%;
}

.thumb_img{
    width:100%;
}
</style>