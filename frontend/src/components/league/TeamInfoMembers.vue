<template>
    <v-card class="container" align="center">
      <h2>감독: {{ teamMembers.coach.name }}</h2>
      <h3>선수 목록</h3>
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">이름</th>
              <th class="text-left">포지션</th>
              <th class="text-left">번호</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="player in teamMembers.player" :key="player.number">
              <td>{{ player.name }}</td>
              <td>{{ player.position }}</td>
              <td>#{{ player.number }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-card>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  // 예시 팀 멤버 데이터 확장
  const teamMembers = ref({
    coach: { name: '최감독', position: 'Head Coach', number: 0 },
    player: [
      { name: '김철수', position: 'FW', number: 7 },
      { name: '이영희', position: 'MF', number: 10 },
      { name: '박민수', position: 'DF', number: 5 },
      { name: '홍길동', position: 'GK', number: 1 },
      // 추가 선수 데이터
      { name: '장민호', position: 'FW', number: 9 },
      { name: '서진용', position: 'MF', number: 17 },
      { name: '김태영', position: 'DF', number: 4 },
      { name: '박지성', position: 'MF', number: 13 },
      { name: '이동국', position: 'FW', number: 20 },
      { name: '손흥민', position: 'FW', number: 7 },
      { name: '이강인', position: 'MF', number: 18 },
      { name: '정우영', position: 'DF', number: 23 },
    ],
  });
  </script>
  
  <style scoped>
  .container {
    max-width: 300px;
    padding: 20px;
    box-shadow: 0px 0px 15px rgba(0,0,0,0.2);
    border-radius: 8px;
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
  
  th, td {
    padding: 8px 12px;
    border-bottom: 1px solid #BDBDBD;
  }
  
  td {
    font-weight: 500;
  }
  </style>
  