<template>
    <v-container>
        <v-toolbar flat class="preview-toolbar">
            <v-toolbar-title>추억 아카이브</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
            <RouterLink :to="`/club/${clubId}/archive`">
                <v-btn icon>
                    <v-icon class="plus-btn">mdi-plus</v-icon>
                </v-btn>
            </RouterLink>
            </v-toolbar-items>
        </v-toolbar>

        <v-row>
            <v-col
                cols="12" sm="6" md="6" lg="6" 
                v-for="(detail, index) in archiveList.slice(0, 2)"
                :key="index"
                >
                <v-card class="thumbnail" @click="showDetailModal(detail)">
                    <v-card-title v-if="detail.fixtureTitle" class="text-center"> {{ detail.fixtureTitle }} </v-card-title>
                    <v-card-title v-else class="text-center"> 이름없는 추억 하나</v-card-title>
                    <v-card-subtitle v-if="detail.partyTitle" class="text-right"> {{detail.partyTitle}} </v-card-subtitle>
                    <v-card-subtitle v-else class="text-right">수많은 파티 그 중 하나</v-card-subtitle>
                    <v-card-text class="text-right"> {{ detail.member.nickname }} </v-card-text>
                    <v-card-text class="text-right"> {{formatDateTime(detail.createdTime)}} </v-card-text>
                    <v-img v-if="detail.file.type == 'image'" :src="detail.file.url" :alt="detail.file.url" class="thumb_img" cover height="200px"/>
                    <v-img v-else :src="detail.thumbnail.url" :alt="detail.thumbnail.url" class="thumb_img" cover height="200px"></v-img>
                </v-card>
                <ArchiveDetail 
                    v-if="isDetailVisible && currentDetail.id === detail.id"
                    :detail="currentDetail" 
                    @detail-close="isDetailVisible = false"
                />
            </v-col>
        </v-row>
    </v-container>
        
</template>

<script setup>
import {ref, onMounted, watch} from 'vue'
import {useRoute, useRouter} from 'vue-router';
import {useArchiveStore} from '@/stores/club/archives'

import {formatDateTime} from "@/util/tools.js"

import ArchiveDetail from '@/components/archive/ArchiveDetail.vue';

const archiveStore = useArchiveStore();

const routes = useRoute();
const clubId = ref(routes.params.clubId);

const archiveList = ref([]);
watch(() => archiveStore.archiveList,(newArchiveList) => {
    archiveList.value = newArchiveList;
},{immediate:true})

watch(() => routes.params.clubId, (newClubId) => {
    clubId.value = newClubId;
    archiveStore.getArchiveList(clubId.value);
});


// 아카이브 상세 모달 보이기
const isDetailVisible = ref(false);

// 지금 선택한 모달
const currentDetail = ref(null);

// 아카이브 상세모달 on
const showDetailModal = (detail) => {
    currentDetail.value = detail
    isDetailVisible.value = true;
}

onMounted(() => {
    archiveStore.getArchiveList(clubId.value);
})

</script>

<style lang="scss" scoped>
.v-container {
    padding-left:0px;
}
.v-toolbar-title{
    color:white;
    margin-left:0;
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