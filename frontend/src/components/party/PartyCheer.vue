<template>
    <p>경기 응원 페이지. fixtureId = {{ fixtureId ? fixtureId : 'null' }}</p>
    <p v-if="fixtureId == null" class="mb-6 alert-msg">경기를 선택해주세요.</p>
    <v-container v-else fluid class="pa-2 fill-height part-section">
        <v-row justify="center">
            <v-col cols="12" class="d-flex flex-column align-center justify-center">
                <v-carousel v-if="cheer" v-model="model" class='carousel' :show-arrows="false" height="500px" hide-delimiters 
                    hide-delimiter-background>
                    <v-carousel-item v-for="(match, index) in cheer" :key="match.cheerFixtureId">
                        <div class="d-flex flex-column justify-center align-center" style="height: 100%;">
                            <!-- 경기 기본 정보 -->
                            <div class="match-info">
                                <p class="match-details">
                                    {{ formatDate(match.fixture.startTime) }}
                                    <v-icon>mdi-circle-small</v-icon>
                                    {{ match.fixture.league.nameKr }}
                                    <v-icon>mdi-circle-small</v-icon>
                                    <v-img :src="match.fixture.league.logo" class="league-logo"></v-img>
                                </p>
                            </div>
                            <!-- 투표 상태 메시지 -->
                            <div class="vote-message">
                                <h1>{{ match.alreadyCheer ? '이미 투표하셨습니다' : '팀을 선택해주세요!' }}</h1>
                            </div>
                            <!-- 투표 버튼 및 득표율 -->
                            <div class="d-flex justify-center align-center pb-12">
                                <!-- 투표 버튼 -->
                                <!-- 홈 팀 카드 -->
                                <div class="wrapper pb-1">
                                    <div v-if="match.alreadyCheer" :class="{
                                        'barWin': votePercentage(match, 'home') > 50,
                                        'barLose': votePercentage(match, 'home') < 50,
                                        'barSame': votePercentage(match, 'home') == 50,
                                    }" :style="{ height: homePercentage * 2.5 + 'px' }" class="bar"></div>
                                </div>
                                <!-- 여기까지 득표율에 따른 막대기 -->
                                <v-card :disabled="match.alreadyCheer" class="team-card text-center"
                                    @click="() => voteForTeam(match, 'home')">
                                    <v-img :src="match.fixture.homeTeam.logo" class="team-logo"></v-img>
                                    <v-card-title :class="{ chosen: match.cheerTeamId == match.fixture.homeTeam.seasonLeagueTeamId }">
                                        {{ match.fixture.homeTeam.nameKr }}
                                    </v-card-title>
                                    <v-card-text v-if="match.alreadyCheer">
                                        <p>득표 : {{ votePercentage(match, 'home') }}%</p>
                                    </v-card-text>
                                </v-card>
                                <span class="VS">VS</span>
                                <!-- 원정 팀 카드 -->
                                <v-card :disabled="match.alreadyCheer" class="team-card text-center"
                                    @click="() => voteForTeam(match, 'away')">
                                    <v-img :src="match.fixture.awayTeam.logo" class="team-logo"></v-img>
                                    <v-card-title :class="{ chosen: match.cheerTeamId == match.fixture.awayTeam.seasonLeagueTeamId }">
                                        {{ match.fixture.awayTeam.nameKr }}
                                    </v-card-title>
                                    <v-card-text v-if="match.alreadyCheer">
                                        <p>득표 : {{ votePercentage(match, 'away') }}%</p>
                                    </v-card-text>
                                </v-card>
                                <!-- 득표율에 따른 막대기 -->
                                <div class="wrapper pb-1">
                                    <div v-if="match.alreadyCheer" :class="{
                                        'barWin': votePercentage(match, 'away') > 50,
                                        'barLose': votePercentage(match, 'away') < 50,
                                        'barSame': votePercentage(match, 'away') == 50
                                    }" :style="{ height: awayPercentage * 2.5 + 'px' }" class="bar"></div>
                                </div>
                                <!-- 여기까지 득표율에 따른 막대기 -->
                            </div>
                        </div>
                    </v-carousel-item>
                </v-carousel>
                <v-card v-else class="card-else" title="경기 응원 데이터가 없습니다.">
                    <!-- <v-card-title>경기 응원 데이터가 없습니다.</v-card-title> -->
                    <v-card-subtitle v-if="fixtureId">해당 경기에는 응원이 없습니다.</v-card-subtitle>
                    <v-card-subtitle v-else>경기를 선택해주세요.</v-card-subtitle>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>
  
  
<script setup>
import { ref, reactive, computed, watch, onMounted, nextTick } from 'vue';
import { useFootballStore } from '@/stores/football/football';
import { set } from 'date-fns';

const footballStore = useFootballStore();

const { getCheerData, postCheersData } = footballStore;

const fixtureId = ref(null)

const cheer = ref(null);
watch(() => footballStore.fixtureIdForParty, (newFixtureId) => {
    fixtureId.value = newFixtureId
    if (newFixtureId !== null) {
        getCheerData(newFixtureId);
    }
}, { immediate: true, deep: true })

watch(() => footballStore.oneCheerData, (newCheer) => {
    cheer.value = newCheer;
}, { immediate: true, deep: true })

// 비동기 함수 호출
if (fixtureId.value !== null) {
    getCheerData(fixtureId);
}
// post 메서드 함수 관련
const isLogined = ref(localStorage.getItem("accessToken") !== null);
const memberId = ref(localStorage.getItem("id"));

const postCheers = (data, team) => {
    postCheersData(data, team);
    setTimeout(() => {
        getCheerData(data.fixtureId);
  }, 100);
};

const model = ref(0);


function formatDate(dateStr) {
    const date = new Date(dateStr);
    return date.toLocaleString();
}

// 예시 응원 데이터
// cheer
// {
//     "cheerFixtureId": 10,
//     "alreadyCheer": false,
//     "homeCount": 0,
//     "awayCount": 0,
//     "cheerTeamId": null,
//     "fixture": {
//         "fixtureId": 20,
//         "startTime": "2024-02-06T08:00:00",
//         "round": "5차전",
//         "status": "not start",
//         "homeTeamGoal": 0,
//         "awayTeamGoal": 0,
//         "league": {
//             "leagueId": 1,
//             "nameKr": "월드컵",
//             "logo": "https://media.api-sports.io/football/leagues/1.png"
//         },
//         "homeTeam": {
//             "seasonLeagueTeamId": 4,
//             "teamId": 4,
//             "nameKr": "멍뭉",
//             "nameEng": "cccc",
//             "logo": "https://source.unsplash.com/random/300x300?emblem"
//         },
//         "awayTeam": {
//             "seasonLeagueTeamId": 1,
//             "teamId": 1,
//             "nameKr": "마루쉐",
//             "nameEng": "maroche",
//             "logo": "https://i1.sndcdn.com/avatars-000953353822-6fbf5r-t240x240.jpg"
//         }
//     }
// }

function voteForTeam(match, team) {
    if (match.alreadyCheer === 'true') return;
    if (isLogined.value === false || memberId === null) {
        alert("로그인이 필요한 서비스입니다.");
        return;
    }

    const cheerFixtureId = match.cheerFixtureId;
    const teamId = team === 'home' ? match.fixture.homeTeam.teamId : match.fixture.awayTeam.teamId;
    const fixtureId = match.fixture.fixtureId;
    // memberId.value = Number(memberId.value)

    const data = {
        memberId: memberId.value,
        teamId: teamId,
        cheerFixtureId: cheerFixtureId,
        fixtureId: fixtureId
    }

    console.log("data=", data);
    postCheers(data);
    // postCheers(data);
}

const homePercentage = ref(0);
const awayPercentage = ref(0);

const votePercentage = (match, team) => {
    const totalVotes = match.homeCount + match.awayCount;
    const result1 = ((match.homeCount / totalVotes) * 100).toFixed(0);
    const result2 = 100 - result1;
    if (totalVotes === 0) return 0;
    return team === 'home'
        ? result1
        : result2;
};

//// 응원 득표율을 Bar로 표현하기 위한 로직
onMounted(() => {
    // setInterval을 사용하여 cheer.value.length가 0보다 큰지 확인
    const checkCheerLength = setInterval(() => {
        if (cheer != null && cheer.value.length > 0) {
            // cheer.value.length가 0보다 크면 초기화 로직을 실행
            const currentMatch = cheer.value[model.value];
            if (currentMatch && currentMatch.alreadyCheer) {
                resetBarAnimation(currentMatch);
            }
            // 필요한 작업을 수행한 후, 더 이상 확인이 필요 없으므로 setInterval을 정리
            console.log("clearInterval")
            clearInterval(checkCheerLength);
        }
    }, 1000); // 1초 간격으로 확인
});

// 캐러셀의 현재 항목이 변경될 때 호출되는 함수
watch(model, async (newVal) => {
    await nextTick(); // 캐러셀 항목 변경 후 DOM 업데이트를 기다림
    const currentMatch = cheer.value[newVal];
    console.log("nextTick", newVal)
    if (currentMatch && currentMatch.alreadyCheer) {
        // 막대 애니메이션 초기화
        // 여기서 막대의 높이를 0으로 설정한 후 실제 높이로 변경
        console.log("reset")
        resetBarAnimation(currentMatch);
    }
}, { immediate: true, deep: true });

function resetBarAnimation(match) {
    // 막대의 높이를 0으로 초기화
    homePercentage.value = 0;
    awayPercentage.value = 0;

    // 약간의 지연 후 막대의 높이를 원래 값으로 설정하여 애니메이션을 생성합니다.
    setTimeout(() => {
        homePercentage.value = votePercentage(match, 'home');
        awayPercentage.value = votePercentage(match, 'away');
        // 막대 높이를 득표율에 맞게 조정하는 로직
        // {homePercentage * 2.5}px;
        // {awayPercentage * 2.5}px;
    }, 200);
    // 200ms의 지연은 애니메이션을 생성하기 위한 시간
    // 필요에 따라 수정 가능, 아래 .bar 스타일의 transition 속성도 함께 수정 가능
}


</script>
  
<style scoped>
/* .carousel {} */
.wrapper {
    margin-top: auto;
    width: 20px;
    /* 래퍼 너비 조정 */
}

.bar {
    width: 20px;
    /* background-color: rgb(61, 172, 186); */
    transition: height 1.0s ease;
    /* 애니메이션 효과 */
    bottom: 0;
}

.barWin {
    background-color: rgb(232, 20, 20);
}

.barLose {
    background-color: #333D51;
}

.barSame {
    background-color: #D3AC2B;
}

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
    width: 350px;
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
.card-else {
    padding: 30px;
    margin: 20px;
    text-align: center;
    /* font-size: 2rem; */
    color: #292646;
}
.alert-msg {
    text-align: center;
    margin: 30px;
    font-size: 2rem;
    color: #292646;
}
</style>
  
  