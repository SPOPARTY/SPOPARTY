<template>
  <v-container class="match-near part-section" fluid>
    <v-row>
      <v-col cols="12">
        <h1 class="near-title">오늘 예정 경기</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="6" v-for="match in matches" :key="match.fixtureId" class="mb-4">
        <v-card class="pa-3 match-card" outlined tile>
          <div class="text-center">
            <p class="mb-2">{{ formatDate(match.startTime) }}</p>
            <div class="league-round-details">
              <v-img :src="match.league.logo" class="league-logo"></v-img>
              <span>{{ match.round }}</span>
            </div>
            <div class="team-vs-team">
              <h3>{{ match.homeTeam.nameKr }}</h3>
              <v-img :src="match.homeTeam.logo" class="team-logo"></v-img>
              <span class="vs">VS</span>
              <v-img :src="match.awayTeam.logo" class="team-logo"></v-img>
              <h3>{{ match.awayTeam.nameKr }}</h3>
            </div>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
  
<script setup>
import { ref } from 'vue'

function formatDate(dateStr) {
  const date = new Date(dateStr);
  return date.toLocaleString();
}

// 예정 경기 예시 데이터
const matches = ref(
  [
    {
      "fixtureId": "1",
      "startTime": "YYYY-MM-DD hh:mm:ss.000000",
      "round": "32강",
      "status": "경기 시작 상태",
      "homeTeamGoal": 1,
      "awayTeamGoal": 1,
      "league": {
        "leagueId": "1",
        "nameKr": "프리미어 리그",
        "logo": "/Premier_League.png"
      },
      "homeTeam": {
        "seasonLeagueTeamId": "1",
        "teamId": "1",
        "nameKr": "맨유",
        "logo": "/spo-icon.png"
      },
      "awayTeam": {
        "seasonaLeagueTeamId": "1",
        "teamId": "2",
        "nameKr": "토트넘",
        "logo": "/spo-icon.png"
      }
    },
    {
      "fixtureId": "2",
      "startTime": "YYYY-MM-DD hh:mm:ss.000000",
      "round": "32강",
      "status": "경기 시작 상태",
      "homeTeamGoal": 1,
      "awayTeamGoal": 1,
      "league": {
        "leagueId": "1",
        "nameKr": "프리미어 리그",
        "logo": "/Premier_League.png"
      },
      "homeTeam": {
        "seasonLeagueTeamId": "1",
        "teamId": "1",
        "nameKr": "맨유",
        "logo": "/spo-icon.png"
      },
      "awayTeam": {
        "seasonaLeagueTeamId": "1",
        "teamId": "2",
        "nameKr": "토트넘",
        "logo": "/spo-icon.png"
      }
    },
    {
      "fixtureId": "3",
      "startTime": "YYYY-MM-DD hh:mm:ss.000000",
      "round": "32강",
      "status": "경기 시작 상태",
      "homeTeamGoal": 1,
      "awayTeamGoal": 1,
      "league": {
        "leagueId": "1",
        "nameKr": "프리미어 리그",
        "logo": "/Premier_League.png"
      },
      "homeTeam": {
        "seasonLeagueTeamId": "1",
        "teamId": "1",
        "nameKr": "맨유",
        "logo": "/spo-icon.png"
      },
      "awayTeam": {
        "seasonaLeagueTeamId": "1",
        "teamId": "2",
        "nameKr": "토트넘",
        "logo": "/spo-icon.png"
      }
    },
    {
      "fixtureId": "4",
      "startTime": "YYYY-MM-DD hh:mm:ss.000000",
      "round": "32강",
      "status": "경기 시작 상태",
      "homeTeamGoal": 1,
      "awayTeamGoal": 1,
      "league": {
        "leagueId": "1",
        "nameKr": "프리미어 리그",
        "logo": "/Premier_League.png"
      },
      "homeTeam": {
        "seasonLeagueTeamId": "1",
        "teamId": "1",
        "nameKr": "맨유",
        "logo": "/spo-icon.png"
      },
      "awayTeam": {
        "seasonaLeagueTeamId": "1",
        "teamId": "2",
        "nameKr": "토트넘",
        "logo": "/spo-icon.png"
      }
    },
    {
      "fixtureId": "5",
      "startTime": "YYYY-MM-DD hh:mm:ss.000000",
      "round": "32강",
      "status": "경기 시작 상태",
      "homeTeamGoal": 1,
      "awayTeamGoal": 1,
      "league": {
        "leagueId": "1",
        "nameKr": "프리미어 리그",
        "logo": "/Premier_League.png"
      },
      "homeTeam": {
        "seasonLeagueTeamId": "1",
        "teamId": "1",
        "nameKr": "맨유",
        "logo": "/spo-icon.png"
      },
      "awayTeam": {
        "seasonaLeagueTeamId": "1",
        "teamId": "2",
        "nameKr": "토트넘",
        "logo": "/spo-icon.png"
      }
    },
    {
      "fixtureId": "6",
      "startTime": "YYYY-MM-DD hh:mm:ss.000000",
      "round": "32강",
      "status": "경기 시작 상태",
      "homeTeamGoal": 1,
      "awayTeamGoal": 1,
      "league": {
        "leagueId": "1",
        "nameKr": "프리미어 리그",
        "logo": "/Premier_League.png"
      },
      "homeTeam": {
        "seasonLeagueTeamId": "1",
        "teamId": "1",
        "nameKr": "맨유",
        "logo": "/spo-icon.png"
      },
      "awayTeam": {
        "seasonaLeagueTeamId": "1",
        "teamId": "2",
        "nameKr": "토트넘",
        "logo": "/spo-icon.png"
      }
    },
  ]
)

</script>
  
<style scoped>
.match-near {
  padding: 20px;
  overflow: hidden;
}

.near-title {
  text-align: center;
  margin-bottom: 20px;
}

.match-card {
  border-radius: 12px;
}

.league-logo {
  width: 50px;
  height: 50px;
}

.team-logo {
  width: 50px;
  height: 50px;
  margin: 0 10px;
}

.league-round-details, .team-vs-team {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

.vs {
  font-size: 24px;
  font-weight: bold;
  margin: 0 10px;
}
</style>
