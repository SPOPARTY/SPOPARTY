<template>
    <v-container fluid class="pa-2 mt-3">
        <v-row class="pb-1 table-section">
            <v-card flat class="card-ranking">
                <v-card-title>
                    <v-row class="my-1" align="center" justify="center" style="min-width: 800px;">
                        <v-col cols="3"></v-col>
                        <v-col cols="3" align="center">
                            <v-img :src="logoPath" class="league-logo mr-2" contain></v-img>
                        </v-col>
                        <v-col cols="3" :style="{ fontSize: '1.5rem' }">
                            {{ league.nameKr }} Ranking
                        </v-col>
                        <v-col cols="3"></v-col>
                    </v-row>
                    <v-spacer></v-spacer>
                    <v-row justify="center">
                        <v-col cols="6">
                            <v-text-field v-model="search" append-icon="mdi-magnify" label="Search" single-line hide-details
                                style="width: 600px;" align="center"></v-text-field>
                        </v-col>
                    </v-row>
                    <br>
                </v-card-title>

                <v-data-table :headers="headers" :items="teams" :search="search" :items-per-page="-1" 
                    class="elevation-1"
                    hide-default-footer>
                    <template v-slot:item.logo="{ item }">
                        <v-img :src="item.logo" class="mr-2 team-logo" @click="toTDP(item.seasonLeagueTeamId)" style="width: 50px; height: 50px;" contain></v-img>
                    </template>
                    <!-- 팀 상세 페이지로 보내기 (밑줄 none)-->
                    <template @click="toTDP(item.seasonLeagueTeamId)" v-slot:item.nameKr="{ item }">
                        <!-- <router-link :to="`/team/${item.seasonLeagueTeamId}`" style="text-decoration: none;">{{ item.nameKr }}</router-link> -->
                        <v-btn variant="text" :to="`/team/${item.seasonLeagueTeamId}`">{{ item.nameKr }}</v-btn>
                    </template>
                    <template v-slot:item.standing.form="{ item }">
                        {{ inputDash(item.standing.form) }}
                    </template>
                    <template v-slot:item.following="{ item }">
                        <v-icon v-if="item.following" color="pink" @click="changeFollowing(item)">mdi-heart</v-icon>
                        <v-icon v-else color="grey" @click="changeFollowing(item)">mdi-heart-outline</v-icon>
                    </template>
                    <template v-slot:no-results>
                        <v-alert :value="true" color="error" icon="mdi-alert">
                            Your search for "{{ search }}" found no results.
                        </v-alert>
                    </template>
                    <template #bottom><hr><br></template>
                </v-data-table>
            </v-card>
        </v-row>
    </v-container>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useRouter } from 'vue-router';

import { useFootballStore } from '@/stores/football/football';
import { useFollowStore } from '@/stores/member/follows';

const { doFollow, doUnFollow } = useFollowStore();

// 로그인 여부 감지
const isLogined = ref(sessionStorage.getItem("accessToken") !== null);

// 라우터로부터 리그 아이디 가져오기
const router = useRouter();
const leagueId = router.currentRoute.value.params.leagueId;

// 리그, 구단 정보 가져오기
const footballStore = useFootballStore();

const { getLeagueRanking, getLeagueList } = footballStore;

getLeagueRanking(leagueId);

const teams = ref([]);

watch(() => footballStore.leagueRanking, (newValue) => {
    teams.value = newValue;
});

getLeagueList();

const leagues = ref([]);

watch(() => footballStore.leagueList, (newValue) => {
    leagues.value = newValue;
});

// 검색어 저장
const search = ref('');

// 테이블 헤더
const headers = ref([
    { title: '순위', value: 'standing.rank', sortable: true },
    { title: '로고', value: 'logo'},
    { title: '팀 이름', value: 'nameKr', sortable: true },
    { title: '승점', value: 'standing.points', sortable: true },
    { title: '승', value: 'standing.win', sortable: true },
    { title: '패', value: 'standing.lose', sortable: true },
    { title: '무', value: 'standing.draw', sortable: true },
    { title: '득점', value: 'standing.goalsFor', sortable: true },
    { title: '실점', value: 'standing.goalsAgainst', sortable: true },
    { title: '득실차', value: 'standing.goalDiff', sortable: true },
    { title: '최근 전적', value: 'standing.form' },
    { title: '팔로우', value: 'following', sortable: true },
]);

// 예시 데이터 // 비로그인시 팔로우 불가능
// teams
// {
//     "seasonLeagueTeamId": 1,
//     "teamId": 1,
//     "nameKr": "마루쉐",
//     "nameEng": "maroche",
//     "logo": "https://i1.sndcdn.com/avatars-000953353822-6fbf5r-t240x240.jpg",
//     "following": false,
//     "standing": {
//         "rank": 1,
//         "points": 10,
//         "goalDiff": 10,
//         "form": "WWLDD",
//         "played": 10,
//         "win": 3,
//         "draw": 3,
//         "lose": 3,
//         "goalsFor": 1,
//         "goalsAgainst": 4
//     }
// }

// 수정된 computed 속성
const league = computed(() => {
    // leagueId와 일치하는 리그 객체 찾기
    const foundLeague = leagues.value.find(league => league.leagueId == leagueId);
    // 리그 객체가 존재하지 않는 경우를 대비하여 기본값 설정
    return foundLeague || { nameKr: 'Unknown League', logo: '' };
});

const logoPath = computed(() => league.value.logo);
// const logoPath = '/public/premier_league.png';

const inputDash = (form) => {
    let result = form[0];
    for (let i = 1; i < form.length; i++) {
        result += ' - ' + form[i];
    }
    return result;
};

const toTDP = (id) => {
    router.push(`/team/${id}`);
};

const changeFollowing = (item) => {
    if (!isLogined.value) {
        alert('로그인이 필요한 서비스입니다.');
        return;
    }
    // 팔로우 상태 변경 표시를 위해 값 저장
    const oldVal = item.following;

    if (item.following) {
        console.log("언팔로우")
        doUnFollow(item.teamId);
    } else {
        console.log("팔로우")
        doFollow(item.teamId);
    }
    item.following = !oldVal;
};

</script>

<style>
.league-logo {
    width: 125px;
    height: 125px;
    min-width: 125px;
}

.table-section {
    white-space: nowrap;
    /* min-width: 1100px */

}

.card-ranking {
    width: 100%;
    min-width: 1000px;
}

.team-name-link {
    cursor: pointer;
    color: #1976D2;
    /* Vuetify 기본 색상 */
    /* text-decoration: underline; */
}

hr {
    border: 1px solid #E0E0E0;
    /* 밑줄 색상 */
}
.team-logo {
    cursor: pointer;
}
</style>