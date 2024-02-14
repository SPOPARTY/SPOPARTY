<template>
  <v-container fluid class="pa-2 fill-height part-section">
    <v-row justify="center" class="card-bg">
      <v-col cols="12" sm="8" md="6">
        <v-card class="text-center pa-12 elevation-12" color="#f5f5f5">
          <v-icon large color="green darken-2">mdi-soccer</v-icon>
          <h1 class="pt-2">축구 경기 응원하기</h1>
          <p class="subtitle-1 font-weight-medium pt-2">
            친구들과 함께 축구 경기를 응원하세요. <br>
            그리고 응원하는 팀의 승리를 같이 기뻐해 보세요!
          </p>
          <v-container class="pt-4">
            <v-row justify="center">
              <v-col cols="6">
                <v-btn outlined color="primary" to="/signup" block>회원가입</v-btn>
              </v-col>
              <v-col cols="6">
                <v-btn outlined color="secondary" to="/login" block>로그인</v-btn>
              </v-col>
            </v-row>
          </v-container>
          <p class="caption pt-4">
            이미 회원이신가요? 바로 <router-link to="/login">로그인</router-link>하고 경기를 즐기세요!
          </p>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

  
    
<script setup>

</script>
    
<style scoped>
.part-section {
  height: 100vh;
}

.card-bg {
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('/soccer-field.jpg');
  background-size: cover;
  height: 85vh;
}

h1 {
  color: #333D51;
}
</style>

    