<template>
  <v-container class="match-near part-section" fluid>
    <v-row>
      <v-col cols="12">
        <h1 class="near-title">오늘 예정 경기</h1>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="6" v-for="match in 7" :key="match" class="mb-4">
        <v-card class="pa-3 match-card" outlined tile>
          <div class="text-center">경기 {{ match }}<br>상세내용 {{ match }}</div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
  
<script setup>
// 필요한 스크립트를 여기에 작성합니다.
</script>
  
<style scoped>
.match-near {
  /* background-color: #8b8b8b; */
  border-radius: 8px;
  /* 모서리를 둥글게 */
  padding: 20px;
  /* 내부 컨텐츠와의 간격을 조정하기 위해 패딩 추가 */
  overflow: hidden;
  /* 둥근 모서리를 적용한 후, 자식 요소가 밖으로 벗어나지 않도록 함 */
}

.near-title {
  /* color: #F4F3EA; */
  text-align: center;
  margin-bottom: 20px;
}

.match-card {
  border-radius: 12px;
  /* 카드 모서리 둥글게 */
}</style>
  