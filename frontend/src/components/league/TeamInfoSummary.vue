<template>
  <v-card flat class="container">
    <v-card-title>
      요약
    </v-card-title>
    <v-data-table :headers="headers" :items="teamDataArray" :items-per-page="-1" class="elevation-1" hide-default-footer>
      <template #bottom></template>
    </v-data-table>
  </v-card>
</template>

  
<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
  teamId: {
    type: Number,
    required: true,
  },
});

// 예시 팀 데이터 // 비로그인시 팔로우 불가능
const teams = ref([
  { teamId: '1', leagueId: 1, logo: 'arsenal.png', rank: 1, name: '아스날 FC', points: 30, wins: 9, losses: 3, draws: 3, goalsFor: 25, goalsAgainst: 10, goalDifference: 15, recentForm: 'W-W-D-L-W', followed: true },
  { teamId: '2', leagueId: 2, logo: 'teamB.png', rank: 2, name: '팀 B', points: 28, wins: 8, losses: 4, draws: 4, goalsFor: 22, goalsAgainst: 12, goalDifference: 10, recentForm: 'L-W-W-W-D', followed: false },
  // 추가 팀 데이터...
]);

// const team = computed(() => {
//     return teams.value.find(team => team.teamId == teamId);
// });

// teamId를 숫자로 변환하여 비교
const teamData = computed(() => {
  return teams.value.find((team) => team.teamId == props.teamId.toString());
});

const teamDataArray = computed(() => [teamData.value]);

// 테이블 헤더
const headers = ref([
  { title: '순위', value: 'rank', sortable: true },
  { title: '팀 이름', value: 'name', sortable: true },
  { title: '승점', value: 'points', sortable: true },
  { title: '승', value: 'wins', sortable: true },
  { title: '패', value: 'losses', sortable: true },
  { title: '무', value: 'draws', sortable: true },
  { title: '득점', value: 'goalsFor', sortable: true },
  { title: '실점', value: 'goalsAgainst', sortable: true },
  { title: '득실차', value: 'goalDifference', sortable: true },
  { title: '최근 전적', value: 'recentForm', sortable: false },
  // { title: '팔로우', value: 'followed', sortable: false },
]);
</script>

<style scoped>
.container {
    padding: 20px;
    box-shadow: 0px 0px 15px rgba(0,0,0,0.2);
    border-radius: 8px;
}

</style>
  