<template>
    <v-dialog 
        v-model="modalVisible" 
        max-width="600px"
        max-height="500px"
        @click:outside="closeModal"
        persistent 
        >
        <v-card>
            <v-row>
                <v-col cols="10" style="margin-left:0px; padding-left:230px;">
                    <v-card-title> {{props.detail.fixtureTitle}} </v-card-title>
                </v-col>
                <v-col cols="2" style="margin-top:5px;">
                    <v-btn :ripple="false" @click="closeModal" class="no-background-hover">
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-col>
            </v-row>
            <v-card-text class="text-right"></v-card-text>
            <v-card-item class="text-center" v-if="props.detail.file">
                <v-img :src="props.detail.file.url" :alt="props.detail.title"/>
            </v-card-item>
            <v-card-subtitle class="text-right">파티명 : {{ props.detail.partyTitle }}</v-card-subtitle>
            <v-card-text class="text-right">작성자 : {{ props.detail.member.nickname }}</v-card-text>
            <v-card-subtitle class="text-right">
                생성 날짜 : {{ formatDateTime(props.detail.createdTime) }}
            </v-card-subtitle>
            <v-card-actions>
                <v-spacer/>
                <v-btn v-if="props.detail.file" color="blue darken-1"  :href="props.detail.file.url" downlaod>다운로드</v-btn>
                <v-btn v-if="props.detail.member.id === memberId" color="red darken-2" text @click="deleteProcess">삭제</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref, onMounted, watch} from 'vue'
import {useRoute, useRouter} from 'vue-router';
import { useArchiveStore } from '@/stores/club/archives';
import {formatDateTime} from "@/util/tools.js"

const archiveStore = useArchiveStore();

const memberId = sessionStorage.getItem("id")

// Detail on/off
const modalVisible = ref(true);

const props = defineProps({
    detail : Object
})

const emits = defineEmits([
    'detail-close'
])

// 다운로드 하기
// 추후 작성

// 추억 삭제 process
const confirmDelete = ref(false)

function deleteProcess() {
    
}

// ArchiveDetail.vue 닫기
function closeModal() {
    modalVisible.value = false;
    emits('detail-close')
}


onMounted(()=>{
    console.log("히히 모달 발사")
})

</script>

<style lang="scss" scoped>
.no-background-hover {
  box-shadow: none !important;

  &:hover {
    background-color: transparent !important;
  }
}

</style>