<template>
  <v-container class="signup-container">
    <v-card class="mx-auto" outlined>
      <v-card-title class="justify-center">회원 가입</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submitSignup">
          
          <v-text-field
            label="아이디"
            v-model="signupRequest.loginId"
            outlined
            required
          ></v-text-field>
          
          <v-text-field
            label="닉네임"
            v-model="signupRequest.nickname"
            outlined
            required
          ></v-text-field>
          
          <v-text-field
            label="비밀번호"
            v-model="signupRequest.loginPwd"
            :type="'password'"
            outlined
            required
          ></v-text-field>
          
          <v-text-field
            label="비밀번호 확인"
            v-model="confirmPwd"
            :type="'password'"
            outlined
            required
          ></v-text-field>
          
          <v-row align="center">
            <v-col cols="12" md="4">
              <v-text-field
                label="이메일"
                v-model="emailId"
                outlined
                required
                dense
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="1" class="text-center">@</v-col>
            <v-col cols="12" md="4">
              <v-text-field
                label="도메인"
                v-model="emailDomain"
                outlined
                required
                dense
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="3">
              <v-btn class="email-verify"
                color="primary"
                @click="showEmailVerify"
                >이메일 인증</v-btn>
              <EmailVerify v-if="isEmailVerifyVisible" @close="isEmailVerifyVisible=false"/>

            </v-col>
          </v-row>

          <v-select
            :items="['토트넘 FC', '리버풀 FC', '토론토 FC']"
            label="대표 앰블럼"
            v-model="signupRequest.team"
            required
            return-object
            outlined
          ></v-select>
          
          <v-row>
            <v-col>
              <v-btn color="success" type="submit" block>회원가입</v-btn>
            </v-col>
            <v-col>
              <v-btn color="grey" @click="goBack" block>이전</v-btn>
            </v-col>
          </v-row>
          
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>
  
  <script setup>
  import {useRouter} from 'vue-router';
  import { ref,computed } from 'vue';
  import EmailVerify from '@/components/user/EmailVerify.vue'

  const router = useRouter()
  const emailId = ref('');
  const emailDomain = ref('');
  const email = computed(() => {
    return `${emailId.value}@${emailDomain.value}`
  } )

  
  const signupRequest = ref({
    loginId: '',
    nickname:'',
    loginPwd: '',
    email : email,
    team: ''
  });

  const confirmPwd = ref('');
  const requireCheckPwd = ref(false);
  
  function submitSignup() {
    if (signupRequest.value.loginId !== confirmPwd.value) {
        requireCheckPwd.value = true;
        return;
    }
    console.log(signupRequest.value);
  }

  const isEmailVerifyVisible = ref(false);
  
  function showEmailVerify() {
    isEmailVerifyVisible.value = true
  }

  function goBack(){
    router.back(); // 이전 페이지로 이동
  }
  </script>
  
<style scoped>
.signup-container {
  max-width: 600px;
}

.justify-center{
  text-align: center;
} 

.email-verify{
  margin-bottom:20px;
}
</style>