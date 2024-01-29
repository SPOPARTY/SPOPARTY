<template>
    <v-container>
        <v-row>
            <v-col cols="12" md="8">
                <ArchievePreview/>
            </v-col>
            <v-col cols="12" md="4">
                <ClubMembersFunc/>
            </v-col>
        </v-row>
    <BoardPreview/>
    </v-container>
</template>

<script setup>
import ArchievePreview from '@/components/archieve/ArchievePreview.vue';
import ClubMembersFunc from '@/components/club/ClubMembersFunc.vue';
import BoardPreview from '@/components/board/BoardPreview.vue';

</script>

<style scoped>

</style>
