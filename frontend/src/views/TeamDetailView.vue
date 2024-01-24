<template>
    <v-container fluid class="pa-2 mt-3">
        <!-- 타이틀 섹션 -->
        <v-row align="center" justify="space-around" class="py-4 mb-1 title-section">
            <v-col cols="1"></v-col>
            <v-col cols="3" class="d-flex justify-center align-center">
                <v-img :src="leagueLogoPath" contain class="league-logo" @click="navigateToLeague"></v-img>
            </v-col>
            <v-col cols="4" class="d-flex justify-center align-center">
                <v-img :src="teamLogoPath" contain class="team-logo"></v-img>
                <h1>{{ team.name }}</h1>
            </v-col>
            <!-- 팔로우/언팔로우 버튼 -->
            <v-col cols="2" class="d-flex justify-center align-center">
                <v-hover open-delay="100" close-delay="100">
                    <template v-slot:default="{ isHovering, props }">
                        <v-btn v-bind="props" :color="isHovering ? 'red' : 'grey'" v-if="team.followed"
                            @click="team.followed = !team.followed" x-large>
                            <v-icon x-large>mdi-heart-outline</v-icon>
                        </v-btn>
                        <v-btn v-bind="props" v-else :color="isHovering ? 'grey' : 'pink'"
                            @click="team.followed = !team.followed" x-large>
                            <v-icon x-large>mdi-heart</v-icon>
                        </v-btn>
                    </template>
                </v-hover>
            </v-col>
            <v-col cols="1"></v-col>
        </v-row>
        <!-- 컨텐츠 섹션 -->
        <v-row justify="center" class="py-4 mx-12 content-section">
            <v-col cols="1"></v-col>
            <v-col cols="10">
                <TeamInfoSummary class="mb-6" :team-id="teamId" />
            </v-col>
            <v-col cols="1"></v-col>
            <v-col cols="1"></v-col>
            <v-col cols="4">
                <TeamInfoMembers class="m-6" :team-id="teamId" />
            </v-col>
            <v-col cols="6">
                <TeamInfoMatches class="m-6" :team-id="teamId" />
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import { useRouter } from 'vue-router';
import TeamInfoSummary from '@/components/league/TeamInfoSummary.vue';
import TeamInfoMembers from '@/components/league/TeamInfoMembers.vue';
import TeamInfoMatches from '@/components/league/TeamInfoMatches.vue';

const route = useRoute();
const teamId = route.params.teamId; // URL에서 leagueId 값을 얻습니다.

// 예시 팀 데이터 // 비로그인시 팔로우 불가능
const teams = ref([
    { teamId: 1, leagueId: 1, logo: 'arsenal.png', rank: 1, name: '아스날 FC', points: 30, wins: 9, losses: 3, draws: 3, goalsFor: 25, goalsAgainst: 10, goalDifference: 15, recentForm: 'W-W-D-L-W', followed: true },
    { teamId: 2, leagueId: 2, logo: 'teamB.png', rank: 2, name: '팀 B', points: 28, wins: 8, losses: 4, draws: 4, goalsFor: 22, goalsAgainst: 12, goalDifference: 10, recentForm: 'L-W-W-W-D', followed: false },
    // 추가 팀 데이터...
]);

const team = computed(() => {
    return teams.value.find(team => team.teamId == teamId);
});


// 예시 로고 주소
const teamLogoPath = computed(() => {
    return `/src/assets/${team.value.logo}`;
});

const league = computed(() => {
    return leagues.value.find(league => league.id == team.value.leagueId);
});

// 해당 리그로 보내기
const router = useRouter();
const navigateToLeague = () => {
  router.push(`/league/${league.value.id}`);
};

const leagueLogoPath = computed(() => {
    return `${league.value.logo}`;
});

// 예시 리그 데이터
const leagues = ref([
    {
        id: 1,
        name: '프리미어 리그',
        logo: '/src/assets/premier_league.png'
    },
    {
        id: 2,
        name: '라 리가',
        logo: '/src/assets/la_liga.svg',
    },
    {
        id: 3,
        name: '분데스리가',
        logo: '/src/assets/bundesliga.svg',
    }
])

</script>

<style scoped>
.title-section,
.content-section {
    background-color: #E0E0E0;
    padding: 20px;
    white-space: nowrap;
}

.team-logo,
.league-logo {
    width: 80px;
    height: 80px;
}

.league-logo {
    cursor: pointer;
}

h1,
h2,
h3 {
    margin: 10px 0;
}

.v-simple-table {
    margin-top: 20px;
}
</style>
