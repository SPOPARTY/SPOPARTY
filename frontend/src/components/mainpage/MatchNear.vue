<template>
    <v-container class="match-near" fluid>
      <v-row>
        <v-col cols="12">
          <h1 class="near-title">오늘 예정 경기</h1>
        </v-col>
      </v-row>
      <v-row>
        <!-- 한 줄에 최대 2개의 항목을 보여주도록 설정 -->
        <v-col cols="12" md="6" v-for="match in 7" :key="match" class="mb-4">
          <v-card class="pa-3" outlined tile>
            <!-- 경기 디테일을 보여주는 컴포넌트 필요 -->
            <div class="text-center">경기 {{ match }}</div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </template>
  
  <script setup>
  // 필요한 스크립트를 여기에 작성합니다.
  </script>
  
  <style scoped>
  .match-near {
    background-color: #8b8b8b;
  }
  
  .near-title {
    color: white;
    text-align: center;
    margin-bottom: 20px; /* 타이틀과 경기 항목 사이의 간격을 조정 */
  }
  </style>
  