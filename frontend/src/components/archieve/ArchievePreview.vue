<template>
    <v-container>
        <v-toolbar flat class="preview-toolbar">
            <v-toolbar-title>추억 아카이브</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
            <RouterLink :to="`/club/${clubId}/archieve`">
                <v-btn icon>
                    <v-icon class="plus-btn">mdi-plus</v-icon>
                </v-btn>
            </RouterLink>
            </v-toolbar-items>
        </v-toolbar>

        <v-row>
            <v-col
                cols="12" sm="6" md="4" lg="3" 
                v-for="(detail, index) in dummy.slice(0, 8)"
                :key="index"
                >
                <v-card class="thumbnail" @click="showDetailModal(detail)">
                    <v-card-text class="text-center">{{ detail.title }}</v-card-text>
                    <v-card-item>
                        <v-img :src="detail.img" :alt="detail.img" class="thumb_img"/>
                    </v-card-item>
                </v-card>
                <ArchieveDetail 
                    v-if="isDetailVisible && currentDetail.id === detail.id"
                    :detail="currentDetail" 
                    @detail-close="isDetailVisible = false"
                />
            </v-col>
        </v-row>
    </v-container>
        
</template>

<script setup>
import {ref} from 'vue'
import {useRoute, useRouter} from 'vue-router';
import ArchieveDetail from '@/components/archieve/ArchieveDetail.vue';

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
const showDetailModal = (detail) => {
    currentDetail.value = detail
    isDetailVisible.value = true;
}

</script>

<style lang="scss" scoped>
.v-toolbar-title{
    color:white;
}
.preview-toolbar{
    background:white
}

.thumbnail {
    height:100%;
    width:100%
}

.thumb_img{
    height:100%;
    width:100%;
}


</style>