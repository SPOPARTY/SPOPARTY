<template>
    <v-container fluid class="pa-2 mt-3">
        <!-- 타이틀 -->
      <v-row align="center" justify="center" class="py-4 mb-1 title-section">
        <h1>리그 목록</h1>
      </v-row>
      <!-- 검색 필드 추가 -->
      <v-row justify="center" class="mb-2">
        <v-col cols="5">
          <v-text-field class="searchBar" v-model="search" label="리그 검색" append-icon="mdi-magnify" single-line hide-details></v-text-field>
        </v-col>
      </v-row>
      <!-- 컨텐츠 -->
      <v-row justify="center" class="py-4 mx-12 content-section">
        <v-col cols="10" v-for="league in filteredLeagues" :key="league.id">
          <v-card class="d-flex pa-6 flex-row align-center league-card">
            <v-img :src="league.logo" contain class="league-logo ml-12"></v-img>
            <v-card-title class="flex-grow-1" :style="{ fontSize: '2rem'}" align="center">{{ league.name }}</v-card-title>
            <v-card-actions>
              <v-btn class="mr-12" color="primary" :to="`/league/${league.id}`" icon>
                <v-icon class="btn-detail" size="50px" color="">mdi-arrow-right-bold-circle</v-icon>
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
        <v-col cols="10" v-if="filteredLeagues.length==0">
        <v-card class="d-flex pa-6 flex-row align-center league-card">
            <v-card-title class="flex-grow-1" :style="{ fontSize: '2rem'}" align="center">
                검색 결과가 없습니다.
            </v-card-title>
        </v-card>
    </v-col>
      </v-row>
    </v-container>
  </template>
  

<script setup>
import { ref, computed } from 'vue';

// 예시 데이터
const leagues = ref([
    {
        id: 1,
        name: '프리미어 리그',
        logo: 'src/assets/premier_league.png'
    },
    {
        id: 2,
        name: '라 리가',
        logo: 'src/assets/la_liga.svg',
    },
    {
        id: 3,
        name: '분데스리가',
        logo: 'src/assets/bundesliga.svg',
    }
])

const search = ref('');

// 리그 검색 필터링
const filteredLeagues = computed(() => {
  return leagues.value.filter(league => 
    league.name.toLowerCase().includes(search.value.toLowerCase())
  );
});

</script>


<style scoped>
.title-section, .content-section {
  min-width: 800px; /* 타이틀과 컨텐츠 섹션의 최소 너비를 설정 */
  background-color: #E0E0E0;
  white-space: nowrap; /* 내용을 한 줄로 유지 */
}

.league-card {
  border-color: aquamarine;
  background-color: white;
  margin-bottom: 20px;
  
}

.league-logo {
  width: 150px;
  height: 150px;
  margin-right: 20px;
}

.searchBar {
  min-width: 400px;
  white-space: nowrap;
}

</style>