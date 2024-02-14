<template>
  <v-container class="login-container" fluid fill-height>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6" lg="5">
        <v-card class="login-card pa-5" outlined>
          <v-card-title class="justify-center p-2"><h2>로그인</h2></v-card-title>
          <v-card-text>
            <v-form @submit.prevent="handleSubmit">

              <v-text-field
                label="아이디"
                v-model="loginRequest.loginId"
                outlined
                dense
                class="infos mb-4"
              ></v-text-field>

              <v-text-field
                label="비밀번호"
                v-model="loginRequest.loginPwd"
                :type="'password'"
                outlined
                dense
                class="infos mb-4"
              ></v-text-field>

              <v-row>
                <v-col cols="12" md="4">
                  <v-btn text color="#333D51" @click="showFindPwdModal" block>비밀번호 찾기</v-btn>
                </v-col>
                <v-col cols="12" md="4">
                  <v-btn text color="#333D51" @click="handleSignup" block>회원가입</v-btn>
                </v-col>
                <v-col cols="12" md="4">
                  <v-btn color="#333D51" type="submit" block @click="doLogin">로그인</v-btn>
                </v-col>
              </v-row>

              <TempPwd v-if="isPwdModalVisible" @close="isPwdModalVisible = false" />

            </v-form>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn 
              class="kakao" 
              justify="center"
              @click="showKakaoLoginModal"
              >
              카카오로 로그인하기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {ref} from 'vue';
import {useRouter} from 'vue-router'
import { useManagementStore } from '@/stores/member/managements';
import TempPwd from "@/components/user/TempPwd.vue";


const router = useRouter();

const managementStore = useManagementStore();

const loginRequest = ref({
  loginId : '',
  loginPwd:'',
})

const doLogin = () => {
  if (loginRequest.value.loginId === '') {
    alert("아이디를 입력해주세요!")
    return;
  }

  if (loginRequest.value.loginPwd === '') {
    alert("비밀번호를 입력해주세요!")
    return;
  }

  let loginMember = loginRequest.value;
  managementStore.login(loginMember)
}

function handleSubmit() {
  // console.log(loginRequest.value);
  // 서버에 로그인 정보를 보냄
}

function handleSignup() {
  router.push({name : "Signup"})
}

function goMyPage() {
  router.push({name : "Mypage"})
}

// 비밀번호 찾기 모달
const isPwdModalVisible = ref(false);

function showFindPwdModal() {
  isPwdModalVisible.value = true;
}

// 카카오 로그인 모달
const isKakaoLoginModalVisible = ref(false)
function showKakaoLoginModal() {
  isKakaoLoginModalVisible.value = true;
  window.location.replace("https://i10a802.p.ssafy.io/api/oauth2/authorization/kakao");
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  border-radius: 10px;
}

h2 {
  color : #292646;
}

.v-card{
  background-color: white;
  margin-top:100px;
}

.justify-center{
  text-align: center;
}

.infos {
  margin-top:20px;
  margin-bottom:20px;
  border-radius: 5px;
  background-color: #CBD0D8;
}

.kakao{
  background-color: yellow; 
  width:200px; 
  height:50px;
  font-weight: bold;
  align-items: center;
}
</style>
