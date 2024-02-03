<template>
  <v-container fluid class="pa-2 fill-height part-section">
    <v-row justify="center">
      <v-col cols="12" class="d-flex flex-column align-center justify-center">
        <v-carousel v-model="model" class='carousel' cycle interval="6000" height="450px" hide-delimiter-background
          color="red">
          <v-carousel-item v-for="(match, index) in cheer" :key="match.cheerFixtureId">
            <div class="d-flex flex-column justify-center align-center" style="height: 100%;">
              <!-- 경기 기본 정보 -->
              <div class="match-info">
                <p class="match-details">
                  {{ formatDate(match.fixture.startTime) }}
                  <v-icon>mdi-circle-small</v-icon>
                  {{ match.fixture.league.nameKr }}
                  <v-icon>mdi-circle-small</v-icon>
                  <v-img src="/premier_league.png" class="league-logo"></v-img>
                </p>
              </div>
              <!-- 투표 상태 메시지 -->
              <div class="vote-message">
                <h1 v-if="match.alreadyCheer">이미 투표하셨습니다.</h1>
                <h1 v-else>팀을 선택해주세요!</h1>
              </div>
              <!-- 투표 버튼 및 득표율 -->
              <div class="d-flex justify-center align-center pb-12">
                <!-- 투표 버튼 -->
                <!-- 홈 팀 카드 -->
                <v-card :disabled="match.alreadyCheer" class="team-card text-center"
                  @click="() => voteForTeam(match, 'home')">
                  <v-img :src="match.fixture.homeTeam.logo" class="team-logo"></v-img>
                  <v-card-title :class="{ chosen: match.cheerTeamId == match.fixture.homeTeam.teamId }">
                    {{ match.fixture.homeTeam.nameKr }}
                  </v-card-title>
                  <v-card-text v-if="match.alreadyCheer">
                    <p>득표 : {{ votePercentage(match, 'home') }}</p>
                  </v-card-text>
                </v-card>
                <span class="VS">VS</span>
                <!-- 원정 팀 카드 -->
                <v-card :disabled="match.alreadyCheer" class="team-card text-center"
                  @click="() => voteForTeam(match, 'away')">
                  <!-- <p>{{ match.alreadyCheer }}</p> -->
                  <v-img :src="match.fixture.awayTeam.logo" class="team-logo"></v-img>
                  <v-card-title :class="{ chosen: match.cheerTeamId == match.fixture.awayTeam.teamId }">
                    {{ match.fixture.awayTeam.nameKr }}</v-card-title>
                    
                  <v-card-text v-if="match.alreadyCheer">
                    <p>득표 : {{ votePercentage(match, 'away') }}</p>
                  </v-card-text>
                </v-card>
              </div>
            </div>
          </v-carousel-item>
        </v-carousel>
      </v-col>
    </v-row>
  </v-container>
</template>


<script setup>
import { ref, reactive, computed, watch, onMounted, onUnmounted } from 'vue';
import { useFootballStore } from '@/stores/football/football';

const footballStore = useFootballStore();

const { getCheersData, postCheersData } = footballStore;

// 비동기 함수 호출
getCheersData();

// post 메서드 함수 관련
const isLogined = ref(sessionStorage.getItem("accessToken") !== null);
const memberId = ref(sessionStorage.getItem("id"));

const postCheers = async (data) => {
  await postCheersData(data);
  // await getCheersData();
  console.log("await끝남?")
  cheer.value = footballStore.cheersData;
};

const model = ref(0);

// cheersData가 업데이트 되면 cheer를 업데이트합니다.
const cheer = ref([]);
watch(() => footballStore.cheersData, (newVal) => {
  cheer.value = newVal;
}, { immediate: true }, { deep: true });

function formatDate(dateStr) {
  const date = new Date(dateStr);
  return date.toLocaleString();
}

// 예시 응원 데이터
// cheer
// {
//     "cheerFixtureId": 3,
//     "alreadyCheer": false,
//     "homeCount": null,
//     "awayCount": null,
//     "cheerTeamId": null,
//     "fixture": {
//         "fixtureId": 2,
//         "startTime": "2024-01-26T12:00:00",
//         "round": "5차전",
//         "status": "not start",
//         "homeTeamGoal": 0,
//         "awayTeamGoal": 0,
//         "league": {
//             "leagueId": 1,
//             "nameKr": "챔피언십",
//             "logo": "https://media.api-sports.io/football/leagues/40.png"
//         },
//         "homeTeam": {
//             "seasonLeagueTeamId": 1,
//             "teamId": 1,
//             "nameKr": "마루쉐",
//             "nameEng": "maroche",
//             "logo": "https://i1.sndcdn.com/avatars-000953353822-6fbf5r-t240x240.jpg"
//         },
//         "awayTeam": {
//             "seasonLeagueTeamId": 4,
//             "teamId": 4,
//             "nameKr": "멍뭉",
//             "nameEng": "cccc",
//             "logo": "https://source.unsplash.com/random/300x300?emblem"
//         }
//     }
// }

function voteForTeam(match, team) {
  if (match.alreadyCheer === 'true') return;
  if (isLogined.value === false) {
    alert("로그인이 필요한 서비스입니다.");
    return;
  }

  const cheerFixtureId = match.cheerFixtureId;
  const teamId = team === 'home' ? match.fixture.homeTeam.teamId : match.fixture.awayTeam.teamId;
  // const fixtureId = match.fixture.fixtureId;
  // memberId.value = Number(memberId.value)

  const data = {
    memberId: memberId.value,
    teamId: teamId,
    cheerFixtureId: cheerFixtureId,
    fixtureId: null
  }

  console.log("data=", data);
  postCheers(data);
  // postCheers(data);

  // match.alreadyCheer = true;
}

const votePercentage = (match, team) => {
  const totalVotes = match.homeCount + match.awayCount;
  const result1 = ((match.homeCount / totalVotes) * 100).toFixed(0);
  const result2 = 100 - result1;
  if (totalVotes === 0) return "0%";
  return team === 'home'
    ? result1 + "%"
    : result2 + "%";
};

// // 실시간 업데이트를 위한 변수와 함수
// const count = ref(true);
// let intervalId = null; // 인터벌 ID를 저장할 변수 선언

// onMounted(() => {
//   // setInterval 함수가 반환하는 ID를 intervalId에 저장
//   intervalId = setInterval(() => {
//     console.log("10초 경과", count)
//     cheer.value = footballStore.cheersData;
//     count.value = !count.value;
//   }, 10000);
// });

// onUnmounted(() => {
//   // intervalId를 사용하여 인터벌을 취소
//   clearInterval(intervalId);
// });

</script>

<style scoped>
/* .carousel {} */

.vote-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.vote-message h1 {
  margin-bottom: 20px;
  text-align: center;
  font-size: 2rem;
  color: #292646;
}

.team-card {
  width: 300px;
  cursor: pointer;
  /* 마우스 오버 시 커서 변경 */
  margin: 0px 30px;
  padding: 20px 20px 0px 20px;
}

.team-logo {
  width: 100%;
  height: 150px;
}

.chosen {
  color: red;
  font-size: 1.5rem;
  font-weight: bold;
}

.vote-percentage {
  font-size: 2.5rem;
}

.match-info .match-details {
  display: flex;
  /* Flexbox 레이아웃 사용 */
  align-items: center;
  /* 세로 중앙 정렬 */
  font-size: 1.5rem;
  text-align: center;
  margin-bottom: 15px;
}

.league-logo {
  /* 로고와 텍스트 사이의 간격 */
  /* margin-right: 10px;  */
  width: 10vw;
  /* 로고 크기 조정 */
  height: 10vh;
}

.VS {
  font-size: 3rem;
}
</style>

