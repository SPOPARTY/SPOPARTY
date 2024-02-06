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
                <v-btn v-if="props.detail.file" color="blue darken-1"
                        @click="downloadFile(props.detail.file.url)">
                        다운로드
                </v-btn>
                <v-btn v-if="props.detail.member.id == memberId" color="red darken-2" text @click="deleteProcess(props.detail.id)">삭제</v-btn>
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

const route = useRoute();

const memberId = sessionStorage.getItem("id")
const clubId = route.params.clubId;

// Detail on/off
const modalVisible = ref(true);

const props = defineProps({
    detail : Object
})

const emits = defineEmits([
    'detail-close'
])

// 다운로드 하기
const downloadFile = async (url) => {
    console.log(url)
    // 1. fetch 실행이 끝나면 FETCH API는 내부적으로 Body Object를 상속받아 Response 인스턴스를 생성
    const res = await fetch(url)
    // 2. blob() 메소드는 Body Object의 메서드로 상속이 되어있으므로 res.blob() 가능, blob 인스턴스 반환
    const blob = await res.blob()
    // 3. 여기서 이 작업을 해주지않으면 link.download에 있는 파일명으로 다운로드하지 못한다.
    // createObjectURL()는 URL을 DOMString으로 반환한다. (URL 해제는 revokeObjectURL())
    const downloadUrl = window.URL.createObjectURL(blob) // 이 과정이 필요하다.
    const link = document.createElement('a')
    link.href = downloadUrl
    link.download = 'video.mp4'
    link.click()
}


// 추억 삭제 process
const confirmDelete = ref(false)

function deleteProcess(archiveId) {
    if(confirm("우리들의 추억이 사라집니다") === true) {
        archiveStore.deleteArchive(archiveId,clubId)
    }
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