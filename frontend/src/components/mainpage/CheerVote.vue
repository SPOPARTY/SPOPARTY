<template>
  <v-container fluid class="pa-2 fill-height">
    <v-row justify="center">
      <v-col cols="12">
        <v-card color="grey lighten-1" class="d-flex flex-column align-center justify-center" height="300px">
          <v-card-title class="text-h4">응원 투표</v-card-title>
          <v-card-text class="d-flex justify-center align-center" style="width: 100%;">
            <v-btn icon @click="prevVote" class="mx-2">
              <v-icon size="x-large">mdi-chevron-left</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary" class="mx-2 team-name">{{ currentVote.team1 }}</v-btn>
            <v-btn color="primary" class="mx-2 team-name">{{ currentVote.team2 }}</v-btn>
            <v-spacer></v-spacer>
            <v-btn icon @click="nextVote" class="mx-2">
              <v-icon size="x-large">mdi-chevron-right</v-icon>
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
  
  <script setup>
  import { ref, reactive } from 'vue';
  
  // 임시 투표 목록
  const votes = reactive([
    { team1: '팀 A', team2: '팀 B' },
    { team1: '팀 C', team2: '팀 D' },
    { team1: '팀 E', team2: '팀 F' },
    // 다른 팀들의 투표를 추가할 수 있습니다.
  ]);
  
  const currentVoteIndex = ref(0);
  
  function prevVote() {
    if (currentVoteIndex.value > 0) {
      currentVoteIndex.value--;
    }

    else {
      currentVoteIndex.value = votes.length - 1;
    }
  }
  
  function nextVote() {
    if (currentVoteIndex.value < votes.length - 1) {
      currentVoteIndex.value++;
    }

    else {
      currentVoteIndex.value = 0;
    }
  }
  
  const currentVote = reactive({
    get team1() {
      return votes[currentVoteIndex.value].team1;
    },
    get team2() {
      return votes[currentVoteIndex.value].team2;
    },
  });
  </script>
  
  <style scoped>
  .item-vote {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 100%;
  }

  .team-name {
    /* width: 30%; */
    width: 350px;
  }
  </style>
  