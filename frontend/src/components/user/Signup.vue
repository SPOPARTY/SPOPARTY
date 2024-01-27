<template>
  <v-container class="signup-container">
    <v-card class="mx-auto" outlined>
      <v-card-title class="justify-center">회원 가입</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submitSignup">
          
          <v-text-field
            label="아이디"
            v-model="id"
            outlined
            required
          ></v-text-field>
          
          <v-text-field
            label="닉네임"
            v-model="nickname"
            outlined
            required
          ></v-text-field>
          
          <v-text-field
            label="비밀번호"
            v-model="password"
            :type="'password'"
            outlined
            required
          ></v-text-field>
          
          <v-text-field
            label="비밀번호 확인"
            v-model="password2"
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
            :items="teamIds"
            item-text="logo"
            item-value="teamId"
            label="대표 앰블럼"
            v-model="teamId"
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
  import {registMember} from '@/api/members'
  import EmailVerify from '@/components/user/EmailVerify.vue'
  import { httpStatusCode } from '@/util/http-status';

  const router = useRouter()

  const id = ref("");
  const nickname = ref("");
  const password = ref("");
  const password2 = ref("");
  const emailId = ref('');
  const emailDomain = ref('');
  const email = computed(() => {
    return `${emailId.value}@${emailDomain.value}`
  } )
  const teamId = ref('');

  const teamList = [
      {teamId : 1, logo : "토트넘 FC", "isMain" : false},
      {teamId : 2, logo : "리버풀", "isMain" : false},
      {teamId : 3, logo : "토론토", "isMain" : false},
    ]
  
  const teamIds = teamList.map(t => t.teamId);
  
  const isEmailValid = (email) => {
  const re =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  };

  const isPasswordValid = (pwd) => {
    return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
  };


  function submitSignup() {
      if (
      id.value === "" ||
      password.value === "" ||
      nickname.value === "" ||
      email.value === "" ||
      teamId.value === ""
      ) {
        alert("모든 내용을 입력해주세요");
        console.log("id -> ",id.value)
        console.log("password -> ",password.value)
        console.log("nickname -> ",nickname.value)
        console.log("email -> ",email.value)
        console.log("teamId -> ",teamId.value)
        return;
      }

    if (password.value !== password2.value) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }

    if (!isPasswordValid(password.value)) {
        alert("비밀번호는 8자리 이상이며, 특수문자를 포함해야 합니다.");
        return;
      }

    if (!isEmailValid(email.value)) {
      alert("올바른 이메일 형식을 입력해주세요.");
      return;
    }

    const member = {
      loginId : id.value,
      loginPwd : password.value,
      nickname : nickname.value,
      email : email.value,
      team : {
        id : teamId.value
      }
    }

    registMember(member,
      (res) => {
        if (res.status === httpStatusCode.OK) {
          console.log("히히 회원가입 성공")
          console.log(res);
          alert("히히 회원가입 성송")
          router.push("/")
        } 
        else if (res.status === httpStatusCode.CONFLICT) {
          console.log("이미 등록된 아이디 ㅠㅠ")
          alert("이미 등록된 아이디입니다. ")
          return;
        }
      },
      (error) => {
        console.log(error)
        console.log("ㅅㅂ")
        alert("히히 회원가입 실패 발사")
      }
    )

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