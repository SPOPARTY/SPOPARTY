<template>
    <v-toolbar flat class="preview-toolbar">
        <v-toolbar-title>그룹 게시판</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-toolbar-items>
            <RouterLink :to="`/club/${clubId}/board`">
                <v-btn icon>
                    <v-icon>mdi-plus</v-icon>
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
                <v-card-title>{{ post.title }} - {{index + 1}}</v-card-title>
                <v-card-subtitle>{{ post.nickname }}</v-card-subtitle>
                <v-card-text>{{post.created_time}}</v-card-text>
                <v-card-text>{{ post.content }}</v-card-text>
                <v-card-item>
                    <img src="@/assets/never_heard.jpg" class="thumb_img">
                </v-card-item>
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
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
    {title : "제목", nickname : "글쓴이" , created_time : "작성일", content:"작성내용", img : "/src/assets/never_heard.jpg"},
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

</script>


<style>
.preview-toolbar{
    background:white
}

.thumbnail {
    height:300px;
}

.thumb_img{
    height:100px;
    margin-left:50px;
}
</style>