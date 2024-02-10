<template>
  <v-card elevation="0" class="container" align="center">
    <!-- <h2 v-if="team.coach">감독: {{ team.coach.nameKr }}</h2> -->
    <h2>감독</h2>
    <v-table v-if="team.coach" class="mb-6">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">사진</th>
            <th class="text-center">이름(한)</th>
            <th class="text-center">이름(영)</th>
            <th class="text-center">국적</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="text-center image-container">
              <v-img :src="team.coach.photo" class="mr-2" 
              style="width: 80px; height: 80px;" contain>
              </v-img>
            </td>
            <td class="text-center">{{ team.coach.nameKr }}</td>
            <td class="text-center">{{ team.coach.nameEng }}</td>
            <td class="text-center">{{ team.coach.nationality }}</td>
          </tr>
        </tbody>
      </template>
    </v-table>
    <h3 v-else class="mb-6">감독 정보가 없습니다!</h3>
    <h2>선수 목록</h2>
    <v-table v-if="team.players">
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">사진</th>
            <th class="text-center">이름(한)</th>
            <th class="text-center">이름(영)</th>
            <th class="text-center">나이</th>
            <th class="text-center">키</th>
            <th class="text-center">몸무게</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="player in team.players" :key="player.number">
            <td class="text-center image-container">
              <v-img :src="player.photo" class="mr-2 photo" 
              style="width: 80px; height: 80px;" contain>
              </v-img>
            </td>
            <td class="text-center">{{ player.nameKr }}</td>
            <td class="text-center">{{ player.nameEng }}</td>
            <td class="text-center">{{ player.age }}</td>
            <td class="text-center">{{ player.height }}</td>
            <td class="text-center">{{ player.weight }}</td>
          </tr>
        </tbody>
      </template>
    </v-table>
    <h3 v-else class="mb-6">선수 정보가 없습니다!</h3>
  </v-card>
</template>
  
<script setup>
import { ref, watch } from 'vue';
import { useFootballStore } from '@/stores/football/football';

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
</script>
  
<style scoped>
.container {
  /* max-width: 300px; */
  padding: 10px 0 10px 0;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
}

.image-container {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  min-height: 80px;
}

.photo {
  min-width: 80px; /* 이미지의 최대 너비를 80px로 제한 */
  min-height: 80px; /* 이미지의 최대 높이를 80px로 제한 */
}

h2 {
  color: #1976D2;
  margin-bottom: 15px;
}

h3 {
  margin-bottom: 15px;
}

thead tr {
  background-color: #E0E0E0;
}

tbody tr {
  &:nth-child(odd) {
    background-color: #F5F5F5;
  }
}

tbody tr:hover {
  background-color: #B3E5FC;
}

tbody tr:hover {
  &:nth-child(odd) {
    background-color: #B3E5FC;
  }
}

th,
td {
  padding: 8px 12px;
  border-bottom: 1px solid #BDBDBD;
}

th {
  font-weight: 500;
}
</style>
  