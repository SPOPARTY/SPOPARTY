<template>
  <v-card flat class="container">
    <v-card-title>
      {{ team.nameKr }} 요약
    </v-card-title>
    <v-table>
      <thead>
        <tr>
          <th v-for="header in headers" :key="header.value" class="text-center">
            {{ header.title }}
          </th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td v-for="header in headers" :key="header.value" class="text-center">
            <template v-if="header.value === 'logo'">
              <v-img :src="getValue(team, header.value)" class="mr-2" style="width: 50px; height: 50px;" contain></v-img>
            </template>
            <template v-else-if="header.value === 'standing.form'">
              {{ inputDash(getValue(team, header.value)) }}
            </template>
            <template v-else-if="header.value === 'following'">
              <v-icon v-if="getValue(team, header.value)" color="pink" @click="changeFollowing(team)">mdi-heart</v-icon>
              <v-icon v-else color="grey" @click="changeFollowing(team)">mdi-heart-outline</v-icon>
            </template>
            <template v-else>
              {{ getValue(team, header.value) }}
            </template>
          </td>
        </tr>
      </tbody>
    </v-table>
  </v-card>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useFootballStore } from '@/stores/football/football';
import { useFollowStore } from '@/stores/member/follows';

const { doFollow, doUnFollow } = useFollowStore();

// 로그인 여부 감지
const isLogined = ref(localStorage.getItem("accessToken") !== null);

const footballStore = useFootballStore();
const { getTeamDetail, teamDetail } = footballStore;
const props = defineProps({
  teamId: {
    type: String,
    required: true,
  },
});

getTeamDetail(props.teamId);
const team = ref([]);

watch(() => footballStore.teamDetail, (newValue) => {
  team.value = newValue;
});

// 객체에서 값을 가져오는 함수
const getValue = (obj, path) => {
  return path.split('.').reduce((o, i) => (o ? o[i] : ''), obj);
};

const inputDash = (form) => {
    let result = form[0];
    for (let i = 1; i < form.length; i++) {
        result += ' - ' + form[i];
    }
    return result;
};

const changeFollowing = (item) => {
    if (!isLogined.value) {
        alert('로그인이 필요한 서비스입니다.');
        return;
    }
    const oldVal = item.following;
    const memberId = localStorage.getItem("id");
    if (item.following) {
        doUnFollow(memberId, item.teamId);
    } else {
        doFollow(memberId, item.teamId);
    }
    item.following = !oldVal;
};

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

// 테이블 헤더
const headers = ref([
    { title: '순위', value: 'standing.rank' },
    { title: '로고', value: 'logo'},
    { title: '팀 이름', value: 'nameKr' },
    { title: '승점', value: 'standing.points' },
    { title: '승', value: 'standing.win' },
    { title: '패', value: 'standing.lose' },
    { title: '무', value: 'standing.draw' },
    { title: '득점', value: 'standing.goalsFor' },
    { title: '실점', value: 'standing.goalsAgainst' },
    { title: '득실차', value: 'standing.goalDiff' },
    { title: '최근 전적', value: 'standing.form' },
    { title: '팔로우', value: 'following' },
]);

</script>

<style scoped>
.container {
  padding: 20px;
  box-shadow: 0px 0px 15px rgba(0,0,0,0.2);
  border-radius: 8px;
}
</style>
