<template>
  <v-container fluid class="pa-2 fill-height">
    <v-row justify="center">
      <v-col cols="12">
        <v-card color="grey lighten-1" class="d-flex flex-column align-center justify-center" height="450px">
          <v-carousel class='carousel' cycle interval="7000" height="450px">
            <v-carousel-item v-for="(vote, index) in votes" :key="index">
              <div class="d-flex flex-column justify-center align-center" style="height: 100%;">
                <!-- 투표 상태 메시지 -->
                <div class="vote-message">
                  <h1>{{ vote.voted ? '이미 투표하셨습니다' : '팀을 선택해주세요!' }}</h1>
                </div>
                <!-- 투표 버튼 및 득표율 -->
                <div class="d-flex justify-center align-center">
                  <div class="vote-container">
                    <v-btn :disabled="vote.voted" class="team-name" @click="() => voteForTeam(index, 1)">
                      <span :style="{ color: vote.voted1 ? 'red' : 'black' }">{{ vote.team1 }}</span>
                    </v-btn>
                    <div class="vote-percentage">{{ votePercentage(vote, 1) }}</div>
                  </div>
                  <div class="vote-container">
                    <v-btn :disabled="vote.voted" class="team-name" @click="() => voteForTeam(index, 2)">
                      <span :style="{ color: vote.voted2 ? 'red' : 'black' }">{{ vote.team2 }}</span>
                    </v-btn>
                    <div class="vote-percentage">{{ votePercentage(vote, 2) }}</div>
                  </div>
                </div>
              </div>
            </v-carousel-item>
          </v-carousel>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>


<script setup>
import { ref, reactive, computed } from 'vue';

const votes = reactive([
  { team1: '팀 A', vote1: 600, team2: '팀 B', vote2: 500, voted1: false, voted2: false },
  { team1: '팀 C', vote1: 350, team2: '팀 D', vote2: 730, voted1: false, voted2: false },
  { team1: '팀 E', vote1: 70, team2: '팀 F', vote2: 25, voted1: false, voted2: false },
  // 추가 투표 데이터...
]);

function voteForTeam(voteIndex, team) {
  const vote = votes[voteIndex];
  if (team === 1) {
    vote.vote1++;
    vote.voted1 = true;
  } else {
    vote.vote2++;
    vote.voted2 = true;
  }
  vote.voted = true; // 투표 완료 상태 표시
}

const votePercentage = (vote, team) => {
  const totalVotes = vote.vote1 + vote.vote2;
  if (totalVotes === 0) return "0%";
  return team === 1
    ? ((vote.vote1 / totalVotes) * 100).toFixed(0) + "%"
    : ((vote.vote2 / totalVotes) * 100).toFixed(0) + "%";
};

</script>

<style scoped>
.carousel {

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
}

.vote-percentage {
  /* margin-top: 10px; */
  font-size: 2.5rem;
}

.team-name {
  font-size: 3rem;
  padding: 10px 20px;
  width: 300px;
  height: 200px;
  margin: 0px 30px;
}
</style>

