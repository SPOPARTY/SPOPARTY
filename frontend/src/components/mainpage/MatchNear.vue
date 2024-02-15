<template>
  <v-container class="match-near part-section" fluid>
    <v-row>
      <v-col cols="12">
        <h1 class="near-title pa-3">예정 경기 일정</h1>
      </v-col>
    </v-row>
    <v-row class="card-section">
      <v-col cols="12" md="6" v-for="match in matches" :key="match.fixtureId" class="pa-5 mb-4">
        <v-card class="pa-3 match-card" outlined tile>
          <div class="text-center">
            <!-- 경기 시작 시간 -->
            <p class="mb-2">{{ formatDate(match.startTime) }}</p>
            <!-- 남은 시간 표시 -->
            <p id="timeLeft" v-if="count" class="pb-6">{{ calculateTimeLeft(match.startTime) }}</p>
            <p id="timeLeft" v-else class="pb-6">{{ calculateTimeLeft(match.startTime) }}</p>
            <v-row class="league-round-details">
              <v-col cols="auto" align="center" class="pa-1 mx-4">
                <v-img :src="match.league.logo" class="league-logo" @click="toLDP(match.league.leagueId)"></v-img>
              </v-col>
              <v-col cols="auto">
                <span>{{ match.round }}</span>
              </v-col>
            </v-row>
            <v-row class="team-vs-team">
              <v-col cols="3" class="team-name" @click="toTDP(match.homeTeam.seasonLeagueTeamId)">
                <h3>{{ match.homeTeam.nameKr }}</h3>
              </v-col>
              <v-col cols="2" align="center">
                <v-img :src="match.homeTeam.logo" class="team-logo team-name"
                  @click="toTDP(match.homeTeam.seasonLeagueTeamId)"></v-img>
              </v-col>
              <v-col cols="1" class="pa-0 vs-col">
                <span class="vs-span">VS</span>
              </v-col>
              <v-col cols="2" align="center">
                <v-img :src="match.awayTeam.logo" class="team-logo team-name"
                  @click="toTDP(match.awayTeam.seasonLeagueTeamId)"></v-img>
              </v-col>
              <v-col cols="3" class="team-name" @click="toTDP(match.awayTeam.seasonLeagueTeamId)">
                <h3>{{ match.awayTeam.nameKr }}</h3>
              </v-col>
            </v-row>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
  
<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router';
import { useFootballStore } from '@/stores/football/football'

const router = useRouter();

const footballStore = useFootballStore()

const { getNextMatches } = footballStore

function formatDate(dateStr) {
  const date = new Date(dateStr);
  return date.toLocaleString();
}

getNextMatches();

const matches = ref([]);

watch(() => footballStore.nextMatches, (newVal) => {
  matches.value = newVal;
  // console.warn(matches.value)
}, { immediate: true }
);

// watch(() => footballStore.nextMatches, (newVal) => {
//   matches.value = newVal.map(match => ({
//     ...match,
//     timeLeft: calculateTimeLeft(match.startTime)
//   }));
//   updateTimeInterval();
// }, { immediate: true });

// let intervalId = null;

// function updateTimeInterval() {
//   // intervalId = null;
//   if (intervalId) clearInterval(intervalId);
//   intervalId = setInterval(() => {
//     matches.value.forEach(match => {
//       match.timeLeft = calculateTimeLeft(match.startTime);
//     });
//   }, 3000); // Update every minute
// }

// onUnmounted(() => {
//   clearInterval(intervalId);
// });

const count = ref(true);
let intervalId = null; // 인터벌 ID를 저장할 변수 선언

onMounted(() => {
  // setInterval 함수가 반환하는 ID를 intervalId에 저장
  intervalId = setInterval(() => {
    count.value = !count.value;
  }, 1000);
});

onUnmounted(() => {
  // intervalId를 사용하여 인터벌을 취소
  clearInterval(intervalId);
});



// 남은 시간 계산
function calculateTimeLeft(startTimeStr) {
  const startTime = new Date(startTimeStr).getTime();
  const now = Date.now();
  const timeDiff = startTime - now;

  // 시간 차이가 없거나 과거인 경우
  if (timeDiff <= 0) {
    return "경기 시작됨";
  }

  // 남은 시간을 일, 시, 분으로 계산
  const days = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
  const hours = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  const minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
  const seconds = Math.floor((timeDiff % (1000 * 60)) / 1000);

  const res1 = days > 0 ? `${days}일` : "";
  const res2 = hours + days > 0 ? `${hours}시간` : "";
  const res3 = minutes + hours + days > 0 ? `${minutes}분` : "경기 임박!";
  const res4 = seconds + minutes + hours + days > 0 ? `${seconds}초 남음!` : "";

  return `${res1} ${res2} ${res3} ${res4}`;
}

const toTDP = (teamId) => {
  router.push(`/team/${teamId}`);
};

const toLDP = (leagueId) => {
  router.push(`/league/${leagueId}`);
};

// 예정 경기 예시 데이터
// matches
// {
//     "fixtureId": 13,
//     "startTime": "2024-01-31T18:00:00",
//     "round": "6차전",
//     "status": "not start",
//     "homeTeamGoal": 0,
//     "awayTeamGoal": 0,
//     "league": {
//         "leagueId": 1,
//         "nameKr": "챔피언십",
//         "logo": "https://media.api-sports.io/football/leagues/40.png"
//     },
//     "homeTeam": {
//         "seasonLeagueTeamId": 4,
//         "teamId": 4,
//         "nameKr": "멍뭉",
//         "nameEng": "cccc",
//         "logo": "https://source.unsplash.com/random/300x300?emblem"
//     },
//     "awayTeam": {
//         "seasonLeagueTeamId": 1,
//         "teamId": 1,
//         "nameKr": "마루쉐",
//         "nameEng": "maroche",
//         "logo": "https://i1.sndcdn.com/avatars-000953353822-6fbf5r-t240x240.jpg"
//     }
// }

</script>
  
<style scoped>
.match-near {
  padding: 20px;
  overflow: hidden;
  /* min-width: 1000px; */
}

.near-title {
  text-align: center;
  margin-bottom: 30px;
  margin: auto;
  /* padding: 20px; */
  color: #292646;
  background-color: #F5F5F5;
  /* border: 1px solid #292646; */
  width: 250px;
  border-radius: 8px;
}

.match-card {
  border-radius: 12px;
  border: 4px solid plum;
}
.match-card:hover {
  border: 4px solid #D3AC2B;
  transform: scale(1.05);
}

.league-logo {
  width: 80px;
  height: 80px;
  cursor : pointer;
}

.team-logo {
  width: 60px;
  height: 60px;
  margin: 0 10px;
}

.league-round-details,
.team-vs-team {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

.vs-col {
  min-width: 60px;
}

.vs-span {
  font-size: 24px;
  font-weight: bold;
  margin: 0px;
  margin-left: 10px;
  min-width: 60px;
}
.team-name {
  cursor: pointer;
  padding: 0;
}
.card-section {
  background-image: url('/soccer-field-2.jpg');
  background-size: cover;
  border-radius: 12px;
}
.team-logo:hover, .team-name:hover, .league-logo:hover {
  transform: scale(1.15);
}
</style>
