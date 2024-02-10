<template>
    <v-container>
        {{ props.clubId }}
         <v-row style="padding:15px;">
              <v-col cols="12" md="8">
                   <ArchivePreview />
              </v-col>
              <v-col cols="12" md="4">
                   <ClubMembersFunc :club-info="clubInfo" :club-member-list="clubMemberList" />
              </v-col>
         </v-row>
         <BoardPreview />
    </v-container>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';

import { useClubStore } from '@/stores/club/clubs';

import ArchivePreview from '@/components/archive/ArchivePreview.vue';
import ClubMembersFunc from '@/components/club/ClubMembersFunc.vue';
import BoardPreview from '@/components/board/BoardPreview.vue';

const route = useRoute();

const clubStore = useClubStore();

const props = defineProps({
    clubId: {
        type: String,
        required: true
    }
});

// 클럽 정보와 클럽 멤버 리스트를 저장할 반응형 변수
const clubInfo = ref({});
const clubMemberList = ref([]);

// route.params.clubId 변화를 감지하여 클럽 정보와 멤버 리스트를 업데이트하는 함수
const updateClubData = (clubId) => {
    clubStore.getClubInfo(clubId)
    clubStore.getClubMemberList(clubId)
};

watch(() => clubStore.clubInfo, (newClubs) => {
    clubInfo.value = newClubs;
}, { immediate: true, deep: true });

watch(() => clubStore.clubMemberList, (newClubMemberList) => {
    clubMemberList.value = newClubMemberList;
}, { immediate: true, deep: true });


// 컴포넌트가 마운트 될 때 처음 한 번 클럽 데이터를 불러옴
onMounted(() => {
    updateClubData(route.params.clubId);
});

// route.params.clubId가 변경될 때마다 클럽 데이터를 다시 불러오도록 설정
watch(() => route.params.clubId, (newClubId) => {
    updateClubData(newClubId);
});

</script>


<style scoped></style>
