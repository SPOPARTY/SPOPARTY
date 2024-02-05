<template>
    <v-container fluid>
        <!-- 타이틀 -->
      <v-row align="center" justify="center" class="py-4 mb-2 title-section">
        <h1>리그 목록</h1>
      </v-row>

      <!-- 컨텐츠 -->
      <v-row justify="center" class="py-4 mt-4 mx-12 contents-section">
        <!-- 검색 필드 추가 -->
        <v-col class="search-section" cols="6">
          <v-text-field class="searchBar" v-model="search" label="리그 검색" append-icon="mdi-magnify" single-line hide-details></v-text-field>
        </v-col>
        <v-col cols="10" v-for="league in filteredLeagues" :key="league.id">
          <v-card class="d-flex pa-6 flex-row align-center league-card">
            <v-img :src="league.logo" contain class="league-logo ml-12"></v-img>
            <v-card-title class="flex-grow-1" :style="{ fontSize: '2rem'}" align="center">{{ league.nameKr }}</v-card-title>
            <v-card-actions>
              <v-btn class="mr-12" color="primary" :to="`/league/${league.leagueId}`" icon>
                <v-icon class="btn-detail" size="50px" color="">mdi-arrow-right-circle-outline</v-icon>
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
import { ref, computed, watch } from 'vue';

import { useFootballStore } from '@/stores/football/football';

const footballStore = useFootballStore();

const { getLeagueList } = footballStore;

getLeagueList();

const leagues = ref([]);

watch(() => footballStore.leagueList, (leagueList) => {
    leagues.value = leagueList;
});

console.log(leagues.value);


// 예시 데이터
// leagues
// {
//     "leagueId": 1,
//     "nameKr": "챔피언십",
//     "logo": "https://media.api-sports.io/football/leagues/40.png"
// }

const search = ref('');

// 리그 검색 필터링
const filteredLeagues = computed(() => {
  return leagues.value.filter(league => 
    league.nameKr.toLowerCase().includes(search.value.toLowerCase())
  );
});

</script>


<style scoped>
.title-section, .contents-section {
   /* 타이틀과 컨텐츠 섹션의 최소 너비를 설정 */
  /* min-width: 800px; */
  /* background-color: #E0E0E0; */
  white-space: nowrap; /* 내용을 한 줄로 유지 */
}

h1 {
  color: white;
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

.search-section {
  background-color: white;
  border-radius: 4px;
}

</style>