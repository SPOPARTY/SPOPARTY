<template>
    <v-container>
        <v-toolbar flat class="preview-toolbar">
            <v-toolbar-title>추억 아카이브</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
            <RouterLink :to="`/club/${clubId}/archieve`">
                <v-btn icon>
                    <v-icon>mdi-plus</v-icon>
                </v-btn>
            </RouterLink>
            </v-toolbar-items>
        </v-toolbar>

        <v-row>
            <v-col
                cols="12" sm="6" md="4" lg="3" 
                v-for="(item, index) in dummy.slice(0, 8)"
                :key="index"
                >
                <v-card class="thumbnail">
                    <v-card-text>{{ item.title }} {{ index + 1 }}</v-card-text>
                    <v-card-item>
                        <img :src="item.img" :alt="item.img" class="thumb_img">
                    </v-card-item>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
        
</template>

<script setup>
import {ref} from 'vue'
import {useRoute, useRouter} from 'vue-router';

const routes = useRoute();
const clubId = routes.params.clubId;

console.log("clubId -> ", clubId)

const dummy = ref([
    {title : "제목", img : "/src/assets/never_heard.jpg"},
    {title : "제목", img : "/src/assets/never_heard.jpg"},
    {title : "제목", img : "/src/assets/never_heard.jpg"},
    {title : "제목", img : "/src/assets/never_heard.jpg"},
    {title : "제목", img : "/src/assets/never_heard.jpg"},
    {title : "제목", img : "/src/assets/never_heard.jpg"},
    {title : "제목", img : "/src/assets/never_heard.jpg"},
    {title : "제목", img : "/src/assets/never_heard.jpg"},
    {title : "제목", img : "/src/assets/never_heard.jpg"},
])

</script>

<style lang="scss" scoped>
.preview-toolbar{
    background:white
}

.thumbnail {
    height:200px;
}

.thumb_img{
    height:100px;
    width:100px;
}


</style>