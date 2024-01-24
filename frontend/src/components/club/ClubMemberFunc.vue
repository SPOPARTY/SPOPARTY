<template>
    <v-card class="ma-5" outlined>
        <v-row class="pa-4" align="center" justify="start" no-gutters style="background-color: #E0E0E0;">
            <v-col cols="10">
                <div class="headline">그룹 이름</div>
                <div>정원 - 6/6</div>
            </v-col>
            <v-col cols="2">
                <v-icon large @click="showClubMemberFunc">mdi-cog</v-icon>
            </v-col>
        </v-row>
        
        <v-btn block color="pink lighten-3" @click="showClubMembers">그룹원 보기</v-btn>

        <v-btn block color="blue" style="margin-top:30px;">그룹으로 초대</v-btn>

        <v-btn block color="red" dark class="mt-4" style="height:100px;">
            파티를 열어보세요!
        </v-btn>
    </v-card>

    <v-dialog v-model="isClubMemberVisible">
        <v-card v-for="(member, index) in clubMembers" :key="index">
            <v-card-title>히히 그룹 멤버 발사</v-card-title>
            <v-card-item v-if="member.role === '그룹장'">
                <v-icon>mdi-star</v-icon>
            </v-card-item>
            <v-card-text>
                {{ member.name}}
            </v-card-text>
        </v-card>
    </v-dialog>
  </template>

<script setup>
import {ref} from 'vue'

const clubMembers = ref([
    {memberId : 1, name : "실버스타", role : "그룹원"},
    {memberId : 2, name : "제라드", role : "그룹장"},
    {memberId : 3, name : "벨타이거", role : "그룹원"},
    {memberId : 4, name : "램파드", role : "그룹원"},
    {memberId : 5, name : "별명별명", role : "그룹원"},
    {memberId : 6, name : "글로리맨유", role : "그룹원"},
])

const isClubMemberVisible = ref(false)

function showClubMembers() {
    isClubMemberVisible.value = true;
}

function showClubMemberFunc() {

}

</script>

<style lang="scss" scoped>

</style>