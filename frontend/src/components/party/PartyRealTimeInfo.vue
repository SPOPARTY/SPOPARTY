<template>
    <v-container fluid class="vc">
      <p>실시간 정보 페이지. fixtureId = {{ fixtureId? fixtureId : 'null'}}</p>
      <p>예시 테이블</p>
      <p>{{ footballStore.matchRealTimeData }}</p>
      <v-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-center">시간</th>
              <th class="text-center">팀</th>
              <th class="text-center">선수</th>
              <th class="text-center">어시스트</th>
              <th class="text-center">상세</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(event, index) in sortedEvents" :key="index">
              <td class="text-center">{{ event.time }}'</td>
              <td class="text-center"><img :src="event.team.logo" alt="logo" style="height:30px;"> {{ event.team.nameKr }}</td>
              <td class="text-center"><img :src="event.player.photo" alt="photo" style="height:30px;"> {{ event.player.nameKr }}</td>
              <td class="text-center">
                <div v-if="event.assist">
                  <img :src="event.assist.photo" alt="assist" style="height:30px;"> {{ event.assist.name }}
                </div>
                <div v-else><v-icon>mdi-close</v-icon></div>
              </td>
              <td class="text-center">{{ event.detail }}</td>
            </tr>
          </tbody>
        </template>
      </v-table>
    </v-container>
  </template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useFootballStore } from '@/stores/football/football'

const footballStore = useFootballStore()

const { getMatchRealTimeData } = footballStore

const fixtureId = ref(null)

watch (() => footballStore.fixtureIdForParty, (newFixtureId) => {
    fixtureId.value = newFixtureId
    if (newFixtureId !== null) {
        getMatchRealTimeData(newFixtureId)
    }
}, { immediate: true }, { deep: true })

// 데이터를 시간의 역순으로 정렬하는 computed 속성
const sortedEvents = computed(() => {
  return timeLine.value.data.sort((a, b) => b.time - a.time)
})

// 예시 데이터

const timeLine = ref({
  "data": [
    {
      "team": {
        "nameKr": "맨체스터 유나이티드",
        "nameEng": "manchester united",
        "logo": "어쩌구.jpg"
      },
      "player": {
        "nameKr": "이강인",
        "nameEng": "Lee Gangin",
        "photo": "이강인사진.jpg"
      },
      "assist": null,
      "time": 20,
      "type": "goal",
      "detail": "Normal goal"
    },
    {
      "team": {
        "seasonLeagueTeamId": 1,
        "nameKr": "맨체스터 시티",
        "nameEng": "manchester city",
        "logo": "저쩌구.jpg"
      },
      "player": {
        "seasonLeagueTeamPlayerId": 1,
        "name": "호날도",
        "photo": "호날도사진.jpg"
      },
      "assist": {
        "seasonLeagueTeamPlayerId": 2,
        "name": "호나우딩요",
        "photo": "호나우딩요사진.jpg"
      },
      "time": 25,
      "type": "goal",
      "detail": "Normal goal"
    }
  ]
})

</script>

<style lang="scss" scoped>
.vc {
    color : #121212;
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
  font-size: 1rem;
}
</style>