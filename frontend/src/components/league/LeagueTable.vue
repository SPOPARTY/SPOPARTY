<template>
    <v-container fluid class="pa-2 mt-3">
        <v-row class="pb-1 table-section">
            <v-card flat class="card-ranking">
                <v-card-title>
                    <v-row align="center" justify="center" style="min-width: 800px;">
                        <v-col cols="3"></v-col>
                        <v-col cols="3" align="center">
                            <v-img :src="logoPath" class="league-logo mr-2" contain></v-img>
                        </v-col>
                        <v-col cols="3" :style="{ fontSize: '1.5rem' }">
                            {{ league.name }} Ranking
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
                </v-card-title>

                <v-data-table :headers="headers" :items="teams" :search="search" :items-per-page="-1" class="elevation-1"
                    hide-default-footer>
                    <!-- 팀 상세 페이지로 보내기 (밑줄 none)-->
                    <template v-slot:item.name="{ item }">
                        <router-link :to="`/team/${item.teamId}`" style="text-decoration: none;">{{ item.name }}</router-link>
                    </template>
                    <template v-slot:item.followed="{ item }">
                        <v-icon v-if="item.followed" color="pink" @click="item.followed = !item.followed">mdi-heart</v-icon>
                        <v-icon v-else color="grey" @click="item.followed = !item.followed">mdi-heart-outline</v-icon>
                    </template>
                    <template v-slot:no-results>
                        <v-alert :value="true" color="error" icon="mdi-alert">
                            Your search for "{{ search }}" found no results.
                        </v-alert>
                    </template>
                    <template #bottom></template>
                </v-data-table>
            </v-card>
        </v-row>
    </v-container>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

// Props 정의
const props = defineProps({
    leagueId: {
        type: Number,
        required: true,
    },
});

// 검색어 저장
const search = ref('');

// 예시 헤더
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
    { title: '최근 전적', value: 'recentForm' },
    { title: '팔로우', value: 'followed', sortable: true },
]);

// 예시 데이터 // 비로그인시 팔로우 불가능
const teams = ref([
    { teamId: 1, rank: 1, name: '팀 A', points: 30, wins: 9, losses: 3, draws: 3, goalsFor: 25, goalsAgainst: 10, goalDifference: 15, recentForm: 'W-W-D-L-W', followed: true },
    { teamId: 2, rank: 2, name: '팀 B', points: 28, wins: 8, losses: 4, draws: 4, goalsFor: 22, goalsAgainst: 12, goalDifference: 10, recentForm: 'L-W-W-W-D', followed: false },
    // 추가 팀 데이터...
]);

// 리그 예시 // id 값에 따옴표 붙여야함
const leagues = ref([
    {
        id: '1',
        name: '프리미어 리그',
        logo: 'premier_league.png',
    },
    {
        id: '2',
        name: '라 리가',
        logo: 'la_liga.svg',
    },
    {
        id: '3',
        name: '분데스리가',
        logo: 'bundesliga.svg',
    }
]);

// 수정된 computed 속성
const league = computed(() => {
    // props.leagueId와 일치하는 리그 객체 찾기
    const foundLeague = leagues.value.find(league => league.id === props.leagueId);
    // 리그 객체가 존재하지 않는 경우를 대비하여 기본값 설정
    return foundLeague || { name: 'Unknown League', logo: '' };
});

const logoPath = computed(() => `/${league.value.logo}`);
// const logoPath = '/public/premier_league.png';

</script>

<style>
.league-logo {
    width: 150px;
    height: 150px;
    min-width: 150px;
}

.table-section {
    white-space: nowrap;
}

.card-ranking {
    width: 100%;
}

.team-name-link {
    cursor: pointer;
    color: #1976D2;
    /* Vuetify 기본 색상 */
    /* text-decoration: underline; */
}
</style>