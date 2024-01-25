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
                <v-card-subtitle class=text-right>{{ post.nickname }}</v-card-subtitle>
                <v-card-text class="text-right">{{post.created_time}}</v-card-text>
                <v-card-item>
                    <v-img :src="post.img" class="thumb_img"/>
                </v-card-item>
                <v-card-text>{{ post.content }}</v-card-text>
            </v-card>
        </v-col>
    </v-row>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import BoardDetail from '@/components/board/BoardDetail.vue';

const routes = useRoute();

const clubId = routes.params.clubId

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