<template>
  <v-container class="login-form" fluid fill-height>
    <v-row justify="center" align="center">
      <v-col cols="12" sm="8" md="6" lg="5">
        <v-card class="pa-5" outlined>
          <v-card-title class="justify-center">로그인</v-card-title>
          <v-card-text>
            <v-form @submit.prevent="handleSubmit">

              <v-text-field
                label="아이디"
                v-model="loginRequest.loginId"
                outlined
                dense
                class="mb-4"
              ></v-text-field>

              <v-text-field
                label="비밀번호"
                v-model="loginRequest.loginPwd"
                :type="'password'"
                outlined
                dense
                class="mb-4"
              ></v-text-field>

              <v-row>
                <v-col cols="12" md="4">
                  <v-btn color="primary" type="submit" block @click="goMyPage">로그인</v-btn>
                </v-col>
                <v-col cols="12" md="4">
                  <v-btn text color="primary" @click="handleSignup" block>회원가입</v-btn>
                </v-col>
                <v-col cols="12" md="4">
                  <v-btn text color="primary" @click="showFindPwdModal" block>비밀번호 찾기</v-btn>
                </v-col>
              </v-row>

              <FindPwd v-if="isPwdModalVisible" @close="isPwdModalVisible = false" />

            </v-form>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn class="kakao" justify="center">카카오로 로그인하기</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {ref} from 'vue';
import {useRouter} from 'vue-router'
import FindPwd from '@/components/user/FindPwd.vue';

const router = useRouter();

const loginRequest = ref({
  loginId : '',
  loginPwd:'',
})

function handleSubmit() {
  console.log(loginRequest.value);
  // 서버에 로그인 정보를 보냄
}

function handleSignup() {
  router.push({name : "Signup"})
}

function goMyPage() {
  router.push({name : "Mypage"})
}

const isPwdModalVisible = ref(false);

function showFindPwdModal() {
  isPwdModalVisible.value = true;
}

</script>

<style scoped>
.login-form {
  background-color: #f9f9f9;
  min-height: 100vh;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.v-card{
  margin-top:100px;
}

.justify-center{
  text-align: center;
}

.kakao{
  background-color: yellow; 
  width:200px; 
  height:50px;
  align-items: center;
}
</style>
