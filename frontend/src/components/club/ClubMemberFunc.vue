<template>
    <v-card class="ma-5" outlined>
      <!-- 상단 그레이 박스 -->
        <v-row class="pa-4" align="center" justify="start" no-gutters style="background-color: #E0E0E0;">
            <v-col cols="10">
                <div class="headline">그룹 이름</div>
                <div>정원 - 6/6</div>
            </v-col>
            <v-col cols="2">
                <v-icon large @click="showClubMemberFunc">mdi-cog</v-icon>
            </v-col>
        </v-row>
        
        <v-btn block color="pink lighten-3">그룹원 보기</v-btn>

        <v-btn block color="blue" style="margin-top:30px;">그룹으로 초대</v-btn>

        <v-btn block color="red" dark class="mt-4" style="height:100px;">
            파티를 열어보세요!
        </v-btn>
    </v-card>
  </template>

<script setup>


function showClubMemberFunc() {

}

</script>

<style lang="scss" scoped>

</style>