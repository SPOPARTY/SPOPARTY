<template>
  <v-container fluid class="vc">
    <p class="table-title">실시간 정보</p>
    <p v-if="fixtureId == null" class="alert-msg">경기를 선택해주세요.</p>
    <!-- <p>{{ footballStore.matchRealTimeData }}</p> -->
    <v-table v-if="fixtureId != null && sortedEvents && sortedEvents.length > 0">
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
            <td class="text-center">
              <img :src="event.team.logo" class="mx-3" alt="logo" style="height:30px;">
               <span :class="{'text-red' : checkTeam(event.team.nameKr) == 'home',
              'text-blue' : checkTeam(event.team.nameKr) == 'away' }">
                {{ event.team.nameKr }}
              </span>
            </td>
            <td class="text-center">{{ event.playerName }}</td>
            <td class="text-center">
              <div v-if="event.assistName">
                {{ event.assistName }}
              </div>
              <div v-else><v-icon>mdi-close</v-icon></div>
            </td>
            <td class="text-center">{{ checkType(event.type) }} : {{ event.detail }}</td>
          </tr>
        </tbody>
      </template>
    </v-table>
    <p v-else class="alert-msg">데이터가 없습니다.</p>
  </v-container>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useFootballStore } from '@/stores/football/football'

const footballStore = useFootballStore()

const { getMatchRealTimeData } = footballStore

const fixtureId = ref(null)
const teamIds = ref({home: null, homeName:null, away: null, awayName: null})
const timeLine = ref({})

watch(() => footballStore.fixtureIdForParty, async (newFixtureId) => {
  fixtureId.value = newFixtureId
  if (newFixtureId !== null) {
    await getMatchRealTimeData(newFixtureId)
  }
  footballStore.findTeamIdsByFixtureId(newFixtureId)
}, { immediate: true, deep: true })


watch(() => footballStore.matchRealTimeData, (newData) => {
  // console.log("#####@@@@######", newData)
  timeLine.value = newData
}, { immediate: true, deep: true })

watch(() => footballStore.teamIdsForParty, (newTeamIds) => {
  teamIds.value = newTeamIds
}, { immediate: true, deep: true })

// 데이터를 시간의 역순으로 정렬하는 computed 속성
const sortedEvents = computed(() => {
  // console.log("#####!!!######", timeLine.value?.sort((a, b) => b.time - a.time))
  return timeLine.value?.sort((a, b) => b.time - a.time)
})

const checkType = (type) => {
  if (type === 'goal') {
    return '골'
  } else if (type === 'Card') {
    return '경고'
  } else if (type === 'subst') {
    return '교체'
  }
}

const checkTeam = (teamName) => {
  if (teamName === teamIds.value.homeName) {
    return "home"
  } else if (teamName === teamIds.value.awayName) {
    return "away"
  }
}

// 예시 데이터
// const timeLine = ref({
// {
//   "team" : {
//     "nameKr" : "맨체스터 유나이티드",
//       "nameEng" : "manchester united",
//         "logo" : "어쩌구.jpg"
//   },
//   "playerName" : "이강인",
//     "assistName" : null,
//       "time" : 20,
//         "type" : "goal",
//           "detail" : "Normal goal"
// },
// 다음 데이터들...
// {
//   "team" : {
//     "seasonLeagueTeamId" : 1,
//       "nameKr" : "맨체스터 시티",
//         "nameEng" : "manchester city",
//           "logo" : "저쩌구.jpg"
//   },
//   "playerName" : "이강인",
//     "assistName" : null,
//       "time" : 25,
//         "type" : "goal",
//           "detail" : "Normal goal"
// }

</script>

<style lang="scss" scoped>
.vc {
  color: #121212;
}

.table-title {
  height: 25px;
  font-size: 1.5rem;
  font-weight: bold;
  margin: 10px;
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
.alert-msg {
  text-align: center;
  margin: 30px;
  font-size: 2rem;
  color: #292646;
}
.text-red {
  color: red;
  font-size: 1.1rem;
}
.text-blue {
  color: blue;
  font-size: 1.1rem;
}
</style>