<template>
  <v-container fluid class="pa-2 fill-height part-section">
    <v-row justify="center">
      <v-col cols="12" class="d-flex flex-column align-center justify-center">
        <v-carousel class='carousel' cycle interval="7000" height="450px" hide-delimiter-background color="red">
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
                <h1>{{ convertToBoolean(match.already_cheer) ? '이미 투표하셨습니다' : '팀을 선택해주세요!' }}</h1>
              </div>
              <!-- 투표 버튼 및 득표율 -->
              <div class="d-flex justify-center align-center">
                <!-- 투표 버튼 -->
                <!-- 홈 팀 카드 -->
                <v-card :disabled="convertToBoolean(match.already_cheer)" class="team-card"
                  @click="() => voteForTeam(index, 'home')">
                  <v-img :src="match.fixture.homeTeam.logo" class="team-logo"></v-img>
                  <v-card-title :class="{ chosen: match.fixture.chosenTeam == 'home' }">{{ match.fixture.homeTeam.nameKr
                  }}</v-card-title>
                  <v-card-text v-if="convertToBoolean(match.already_cheer)">{{ votePercentage(match, 'home')
                  }}</v-card-text>
                </v-card>
                <span class="VS">VS</span>
                <!-- 원정 팀 카드 -->
                <v-card :disabled="convertToBoolean(match.already_cheer)" class="team-card"
                  @click="() => voteForTeam(index, 'away')">
                  <v-img :src="match.fixture.awayTeam.logo" class="team-logo"></v-img>
                  <v-card-title :class="{ chosen: match.fixture.chosenTeam == 'away' }">{{ match.fixture.awayTeam.nameKr
                  }}</v-card-title>
                  <v-card-text v-if="convertToBoolean(match.already_cheer)">{{ votePercentage(match, 'away')
                  }}</v-card-text>
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
import { ref, reactive, computed } from 'vue';

function convertToBoolean(str) {
  return str === "true";
}

function formatDate(dateStr) {
  const date = new Date(dateStr);
  return date.toLocaleString();
}

// 예시 응원 데이터
const cheer = ref(
  [
    {
      "cheerFixtureId": 1,
      "already_cheer": "true",
      "homeTeamCount": 123,
      "awayTeamCount": 133,
      "fixture": {
        "fixtureId": "1",
        "startTime": "YYYY-MM-DD hh:mm:ss.000000",
        "round": "32강",
        "status": "경기 시작 상태",
        "homeTeamGoal": 1,
        "awayTeamGoal": 1,
        "chosenTeam": "home",
        "league": {
          "leagueId": "1",
          "nameKr": "프리미어 리그",
          "logo": "/premier_league.png"
        },
        "homeTeam": {
          "seasonLeagueTeamId": "1",
          "teamId": "1",
          "nameKr": "맨유",
          "logo": "/spo-icon.png",
        },
        "awayTeam": {
          "seasonaLeagueTeamId": "1",
          "teamId": "2",
          "nameKr": "토트넘",
          "logo": "/spo-icon.png",
        }
      }
    },
    {
      "cheerFixtureId": 2,
      "already_cheer": "false",
      "homeTeamCount": 280,
      "awayTeamCount": 72,
      "fixture": {
        "fixtureId": 3,
        "startTime": "2023-01-17 15:00",
        "chosenTeam": "null",
        "league": {
          "leagueId": "1",
          "nameKr": "프리미어 리그",
          "logo": "/premier_league.png"
        },
        "homeTeam": {
          "teamId": 1,
          "nameKr": "맨체스터 유나이티드",
          "nameEng": "manchester united",
          "logo": "/spo-icon.png",
        },
        "awayTeam": {
          "teamId": 4,
          "nameKr": "맨체스터 시티",
          "nameEng": "manchester city",
          "logo": "/spo-icon.png",
        }
      }
    }
  ]
)

function voteForTeam(matchIndex, team) {
  const match = cheer.value[matchIndex];
  if (team === 'home') {
    match.homeTeamCount++;
    match.fixture.chosenTeam = 'home';
  } else {
    match.awayTeamCount++;
    match.fixture.chosenTeam = 'away';
  }
  match.already_cheer = 'true';
}

const votePercentage = (match, team) => {
  const totalVotes = match.homeTeamCount + match.awayTeamCount;
  if (totalVotes === 0) return "0%";
  return team === 'home'
    ? ((match.homeTeamCount / totalVotes) * 100).toFixed(0) + "%"
    : ((match.awayTeamCount / totalVotes) * 100).toFixed(0) + "%";
};

</script>

<style scoped>
.carousel {}

.vote-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.vote-message h1 {
  margin-bottom: 20px;
  text-align: center;
  font-size: 2rem;
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

