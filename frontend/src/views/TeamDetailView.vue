<template>
    <v-container fluid>
        <!-- 타이틀 섹션 -->
        <v-row align="center" justify="space-around" class="py-4 mb-1 title-section">
            <v-col cols="1"></v-col>
            <!-- 리그 정보가 있다면 -->
            <!-- <v-col cols="3" class="d-flex justify-center align-center">
                <div :background-color="white">
                <v-img :src="leagueLogoPath" contain class="league-logo" @click="navigateToLeague"></v-img>
            </div>
            </v-col> -->
            <v-col cols="3" class="d-flex justify-center align-center ">
                <v-img :src="team.logo" contain class="team-logo"></v-img>
            </v-col>
            <v-col cols="3" class="d-flex justify-center align-center">
                <h1>{{ team.nameKr }}</h1>
            </v-col>
            <!-- 팔로우/언팔로우 버튼 -->
            <v-col cols="3" class="d-flex justify-center align-center">
                <v-hover open-delay="100" close-delay="100">
                    <template v-slot:default="{ isHovering, props }">
                        <v-btn v-bind="props" v-if="team.following" 
                        :color="isHovering ? 'grey' : 'pink'"
                        @click="changeFollowing(team)" x-large>
                        <v-icon x-large>mdi-heart</v-icon>
                    </v-btn>
                    <v-btn v-bind="props" v-else :color="isHovering ? 'red' : 'grey'"
                        @click="changeFollowing(team)" x-large>
                        <v-icon x-large>mdi-heart-outline</v-icon>
                    </v-btn>
                    </template>
                </v-hover>
            </v-col>
            <v-col cols="1"></v-col>
        </v-row>
        <!-- 컨텐츠 섹션 -->
        <v-row justify="center" class="py-4 mx-12 contents-section">
            <v-col cols="1"></v-col>
            <v-col cols="10">
                <TeamInfoSummary class="mb-6" :team-id="teamId" />
            </v-col>
            <v-col cols="1"></v-col>
            <v-col cols="10">
                <TeamInfoMembers class="m-6" :team-id="teamId" />
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useRouter } from 'vue-router';
import TeamInfoSummary from '@/components/league/TeamInfoSummary.vue';
import TeamInfoMembers from '@/components/league/TeamInfoMembers.vue';
// import TeamInfoMatches from '@/components/league/TeamInfoMatches.vue';

import { useFootballStore } from '@/stores/football/football';
import { useFollowStore } from '@/stores/member/follows';

const { doFollow, doUnFollow } = useFollowStore();

// 로그인 여부 감지
const isLogined = ref(localStorage.getItem("accessToken") !== null);

// 로그인한 사용자의 팔로우 목록 가져오기
// if (isLogined.value) {
//     // 사용자 id 가져오기
//     const memberId = sessionStorage.getItem("id");

//     // 사용자가 팔로우한 팀 목록 가져오기
//     getFollowList(memberId);
    
//     watch(() => followList.value, (newValue) => {
//         followTeams.value = newValue;
//     });
// }

// 미완성 로직 (팔로우 기능) 차후 수정 필요
// const checkFollowing = (item) => {
//     if (!isLogined.value) {
//         return false;
//     }
//     return followTeams.value.some(team => team.teamId === item.teamId);
// };

const changeFollowing = (item) => {
    if (!isLogined.value) {
        alert('로그인이 필요한 서비스입니다.');
        return;
    }
    const oldVal = item.following;
    const memberId = sessionStorage.getItem("id");
    if (item.following) {
        doUnFollow(memberId, item.teamId);
    } else {
        doFollow(memberId, item.teamId);
    }
    item.following = !oldVal;
};

const footballStore = useFootballStore();

const { getTeamDetail } = footballStore;

const route = useRoute();
const teamId = route.params.teamId; // URL에서 teamId 값을 얻습니다.

getTeamDetail(teamId);

const team = ref([]);

watch(() => footballStore.teamDetail, (newValue) => {
    team.value = newValue;
});

// 데이터 예시
// team
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
//     },
//     "coach": {
//         "coachId": 31,
//         "nameKr": "코치2",
//         "nameEng": "coach Two",
//         "photo": "https://source.unsplash.com/random/300x300?person",
//         "nationality": "Germany"
//     },
//     "players": [
//         {
//             "playerId": 1,
//             "nameKr": "선수1",
//             "nameEng": "seonsu1",
//             "age": 20,
//             "height": "199",
//             "weight": "88",
//             "photo": "https://source.unsplash.com/random/300x300?person",
//             "captain": false
//         },
//         {
//             "playerId": 2,
//             "nameKr": "이교환",
//             "nameEng": "dddd",
//             "age": 20,
//             "height": "183",
//             "weight": "80",
//             "photo": "https://source.unsplash.com/random/300x300?person",
//             "captain": true
//         },
//         { ...},
//     ]
// }

// 해당 리그로 보내기
// const router = useRouter();
// const navigateToLeague = () => {
//   router.push(`/league/${league.value.id}`);
// };

// const leagueLogoPath = computed(() => {
//     return `${league.value.logo}`;
// });

</script>

<style scoped>
.title-section,
.contents-section {
    /* background-color: #E0E0E0; */
    padding: 20px;
    white-space: nowrap;
}

.title-section {
    padding-left: 10vw;
    padding-right: 10vw;
}

.team-logo,
.league-logo {
    width: 110px;
    height: 110px;
}

.league-logo {
    cursor: pointer;
    background-color: white;
}

h1,
h2,
h3 {
    margin: 10px 0;
    color: white;
}

.v-simple-table {
    margin-top: 20px;
}
</style>
