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
            v-for="(item, index) in dummy.slice(0,6)"
            :key="index"
            >
            <v-card class="thumbnail">
                <v-card-title>{{ item.title }} - {{index+1}}</v-card-title>
                <v-card-subtitle>{{ item.nickname }}</v-card-subtitle>
                <v-card-text>{{item.created_time}}</v-card-text>
                <v-card-text>{{ item.content }}</v-card-text>
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

const routes = useRoute();

const clubId = routes.params.clubId

const dummy = ref([
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