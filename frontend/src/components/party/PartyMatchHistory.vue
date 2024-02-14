<template>
    <v-container fluid class="vc">
        <!-- <p>경기 이력 페이지. fixtureId = {{ fixtureId ? fixtureId : 'null' }}</p> -->
        <p class="table-title">양 팀 경기 이력</p>
        <p v-if="fixtureId == null" class="alert-msg">경기를 선택해주세요.</p>
        <v-row v-else>
            <v-col cols="6">
                <p class="team-title text-center">Home {{ homeName }}</p>
                <v-card v-for="match in homeHistory" class="pa-3 mb-6 match-card" :key="match.id" 
                variant="outlined">
                    <!-- 경기 내역 표시 -->
                    <div class="text-center">
                        <!-- 경기 시작 시간 -->
                        <p class="mb-6 title-time">{{ formatDate(match.startTime) }}</p>
                        <v-row class="league-round-details">
                            <v-col cols="auto" align="center" class="pa-1 mx-4">
                                <v-img :src="match.league.logo" class="league-logo"></v-img>
                            </v-col>
                            <v-col cols="auto">
                                <span class="round">{{ match.round }}</span>
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
                            <v-col cols="1" class="pa-0">
                                <span class="vs">VS</span>
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
            <v-col cols="6">
                <p class="team-title text-center">Away {{ awayName }}</p>
                <v-card v-for="match in awayHistory" class="pa-3 mb-6 match-card" :key="match.id" 
                variant="outlined">
                    <!-- 경기 내역 표시 -->
                    <div class="text-center">
                        <!-- 경기 시작 시간 -->
                        <p class="mb-6 title-time">{{ formatDate(match.startTime) }}</p>
                        <v-row class="league-round-details">
                            <v-col cols="auto" align="center" class="pa-1 mx-4">
                                <v-img :src="match.league.logo" class="league-logo"></v-img>
                            </v-col>
                            <v-col cols="auto">
                                <span class="round">{{ match.round }}</span>
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
                            <v-col cols="1" class="pa-0">
                                <span class="vs">VS</span>
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
import { ref, watch } from 'vue'
import { useFootballStore } from '@/stores/football/football'
// import PartyMatchHistory from '@/components/party/PartyMatchHistory.vue'

const footballStore = useFootballStore()

const { matchWatchable, getMatchHistory } = footballStore

const matches = ref(matchWatchable)
const match = ref(null)
const homeHistory = ref(null)
const awayHistory = ref(null)
const homeName = ref(null)
const awayName = ref(null)
const fixtureId = ref(null)

watch(() => footballStore.fixtureIdForParty, (newFixtureId) => {
    fixtureId.value = newFixtureId
    if (fixtureId.value) {
        match.value = matches.value.find(match => match.fixtureId === fixtureId.value)
        getMatchHistory(match.value.homeTeam.nameKr, 'home')
        getMatchHistory(match.value.awayTeam.nameKr, 'away')
        homeName.value = match.value.homeTeam.nameKr
        awayName.value = match.value.awayTeam.nameKr
        // console.log('homeHistory', homeHistory.value)
        // console.log('awayHistory', awayHistory.value)
    }
}, { immediate: true })

watch(() => footballStore.matchHistory, (newVal) => {
    homeHistory.value = newVal.home.sort((a, b) => {
        // 날짜를 비교하여 역순으로 정렬
        return new Date(b.startTime) - new Date(a.startTime);
    });
    awayHistory.value = newVal.away.sort((a, b) => {
        // 날짜를 비교하여 역순으로 정렬
        return new Date(b.startTime) - new Date(a.startTime);
    });
    // console.log('!!!!History!!!!', homeHistory.value, awayHistory.value);
}, { immediate: true, deep: true });

function formatDate(dateStr) {
  const date = new Date(dateStr);
  return date.toLocaleString();
}

const toTDP = (teamId) => {
    router.push(`/team/${teamId}`);
};

// 데이터 예시
// match
// {
//     "fixtureId": 1048882,
//     "startTime": "2023-08-20T01:30:00",
//     "round": "Regular Season - 1",
//     "status": "Match Finished",
//     "homeTeamGoal": 1,
//     "awayTeamGoal": 0,
//     "league": {
//         "leagueId": 2,
//         "nameKr": "분데스리가",
//         "logo": "https://media.api-sports.io/football/leagues/78.png"
//     },
//     "homeTeam": {
//         "seasonLeagueTeamId": 4171,
//         "teamId": 165,
//         "nameKr": "Borussia Dortmund",
//         "nameEng": "Borussia Dortmund",
//         "logo": "https://media.api-sports.io/football/teams/165.png"
//     },
//     "awayTeam": {
//         "seasonLeagueTeamId": 4182,
//         "teamId": 192,
//         "nameKr": "1.FC Köln",
//         "nameEng": "1.FC Köln",
//         "logo": "https://media.api-sports.io/football/teams/192.png"
//     }
// }


</script>

<style lang="scss" scoped>
.vc {
    color: #121212;
}

.match-card {
  border-radius: 12px;
  color : #333D51;
  background-color: #CBD0D8;
  min-height: 280px;
}

.league-logo {
  width: 90px;
  height: 90px;
}

.team-logo {
  width: 70px;
  height: 70px;
  margin: 0 10px;
}

.league-round-details,
.team-vs-team {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
}

.vs {
  font-size: 24px;
  font-weight: bold;
  margin: 0 10px;
  min-width: 70px;
}
.team-name {
  cursor: pointer;
  padding: 0;
}
.team-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 30px;
}
.title-time {
  font-size: 1.25rem;
  font-weight: bold;
  margin-bottom: 20px;
  color : #121212;
}
.round {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 20px;
}
.alert-msg {
    text-align: center;
    margin: 30px;
    font-size: 2rem;
    color: #292646;
}
.table-title {
  height: 25px;
  font-size: 1.5rem;
  font-weight: bold;
  margin: 10px;
}
</style>