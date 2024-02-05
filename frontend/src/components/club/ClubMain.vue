<template>
    <v-container>
        <v-row>
            <v-col cols="12" md="8">
                <ArchivePreview/>
            </v-col>
            <v-col cols="12" md="4">
                <ClubMembersFunc 
                    :club-info="clubInfo"
                    :club-member-list="clubMemberList"
                />
            </v-col>
        </v-row>
        <BoardPreview/>
    </v-container>
</template>

<script setup>
import {ref,onMounted, watch} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import {useClubStore} from '@/stores/club/clubs';

import ArchivePreview from '@/components/archieve/ArchivePreview.vue';
import ClubMembersFunc from '@/components/club/ClubMembersFunc.vue';
import BoardPreview from '@/components/board/BoardPreview.vue';

const router = useRouter();
const route = useRoute();

const clubStore = useClubStore();


const clubId = route.params.clubId;

const clubInfo = ref({});
watch(() => clubStore.clubInfo,(newClubs) => {
    clubInfo.value = newClubs;
},{immediate:true})

const clubMemberList = ref([]);
watch(() => clubStore.clubMemberList,(newClubMemberList) => {
    clubMemberList.value = newClubMemberList;
},{immediate:true})

onMounted(() => {
    clubStore.getClubInfo(clubId);
    clubStore.getClubMemberList(clubId);
})



</script>

<style scoped>

</style>
