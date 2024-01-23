<template>
    <v-container>
        <v-row>
            <v-col cols="12" md="8">
                <ArchievePreview/>
            </v-col>
            <v-col cols="12" md="4">
                <ClubMemberFunc/>
            </v-col>
        </v-row>
    <BoardPreview style="margin-top:100px;"/>
    </v-container>
</template>

<script setup>
import ArchievePreview from '@/components/archieve/ArchievePreview.vue';
import ClubMemberFunc from '@/components/club/ClubMemberFunc.vue';
import BoardPreview from '@/components/board/BoardPreview.vue';

</script>

<style scoped>

</style>