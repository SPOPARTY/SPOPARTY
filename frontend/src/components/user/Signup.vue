<template>
  <v-container class="signup-container">
    <v-card class="mx-auto" outlined>
      <v-card-title class="justify-center">회원 가입</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="submitSignup">
          <v-row>
            <v-col cols="9">
              <v-text-field
                label="아이디"
                v-model="id"
                outlined
                required
              ></v-text-field>
            </v-col>
            <v-col cols="3">
              <v-btn
                class="id-check"
                color="blue lighten-1"
                @click="checkId"
              >
                아이디 중복확인
              </v-btn>
            </v-col>
          </v-row>
          
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
            <v-col cols="12" md="4" sm="4" xs="4">
              <v-text-field
                label="이메일"
                v-model="emailId"
                outlined
                required
                dense
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="1" sm="1" xs="1" class="text-center">@</v-col>
            <v-col cols="12" md="4" sm="4" xs="4">
              <v-text-field
                label="도메인"
                v-model="emailDomain"
                outlined
                required
                dense
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="3" sm="3" xs="3">
              <v-btn class="email-verify"
                color="primary"
                @click="showEmailVerify"
                >이메일 인증</v-btn>
              <!-- <EmailVerify v-if="isEmailVerifyVisible" @close="isEmailVerifyVisible=false"/> -->

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

  <!-- ***********여기서부터 모달*********** -->
  <!-- 아이디를 안 적은 상태에서 아이디 중복검사를 한 경우 -->
  <v-dialog
    v-model="blankId"
    class="idCheckModal"
    persistent
  >
    <v-card>
      <v-card-text>
        아이디를 입력하세요!
      </v-card-text>
      <v-card-actions class="justify-center">
        <v-btn color="red" @click="blankId = false"> 확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <!-- 사용 가능한 아이디인 경우 -->
  <v-dialog
    v-model="validId"
    class="idCheckModal"
    persistent
  >
    <v-card>
      <v-card-text>
        사용 가능한 아이디입니다.
      </v-card-text>
      <v-card-actions class="justify-center">
        <v-btn color="blue" @click="validId = false">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <!-- 중복된 아이디인 경우 -->
  <v-dialog
    v-model="inValidId"
    class="idCheckModal"
    persistent
  >
    <v-card>
      <v-card-text>
        중복된 아이디입니다!
      </v-card-text>
      <v-card-actions class="justify-center">
        <v-btn color="red darken-1" @click="inValidId = false">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
    
  <!-- 이메일 인증 모달 -->
  <v-dialog
    v-model="isEmailVerifyVisible"
    width="400"
    persistent
  >
    <v-card class="justify-center">
      <v-card-title class="text-h5">이메일 인증</v-card-title>
      <v-card-text>
        <p>인증 코드가 이메일 주소로 전송되었습니다.</p>
        <p> 코드를 입력해 주세요.</p>
        <v-text-field
          solo
          placeholder="인증 코드 입력"
          type="text"
          v-model="verifyCode"
        ></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" @click="checkVerifyCode">인증</v-btn>
        <v-btn color="grey" @click="isEmailVerifyVisible = false">취소</v-btn>
      </v-card-actions>
    </v-card>

  </v-dialog>

</template>
  
  <script setup>
  import {useRouter} from 'vue-router';
  import { ref,computed } from 'vue';
  import {registMember} from '@/api/member'
  import {idCheck, emailCheck,verifyCodeCheck} from '@/api/authentication'
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

  // 아이디 중복 검사
  const idDuplicatedChecked = ref(false); // 아이디 검사 여부 flag
  const blankId = ref(false) // 아이디 칸이 공란인 경우
  const validId = ref(false); // 사용 가능한 아이디인 경우 모달 띄우기
  const inValidId = ref(false); // 중복된 아이디인 경우 모달 띄우기

  const checkId = () => {
    if(id.value === '') {
      blankId.value = true;
    }

    idCheck(
      id.value,
      (res) => {
        if (res.status === httpStatusCode.OK) {
          console.log("히히 아이디 중복 검사 발사")
          idDuplicatedChecked.value = true; // 아이디 중복 검사 완료
          validId.value = true // 아이디 검사 완료 모달
        }
      },
      (error) => {
        if (error.response.status === httpStatusCode.CONFLICT) {
          console.log("히히 이미 있는 아이디 발사")
          inValidId.value = true; // 아이디 중복 모달 
        }
        console.error("*******비상*******")
        console.error(error)
      }
    )
  }

  // 이메일 인증 모달
  const isEmailVerifyVisible = ref(false);
  
  function showEmailVerify() {
    // 둘 중 하나라도 공란이거나 형식이 잘못되면 alert
    if(emailId.value === '' || emailDomain.value === '' || !isEmailValid(email.value)) {
      alert("올바른 이메일을 입력해주세요!")
      return;
    }
    console.log("이메일 잘 왔나? -> ", email.value)

    // 이메일 ㄱㄱ
    emailCheck(
      email.value,
      (res) => {
        if(res.status === httpStatusCode.OK) {
          console.log("잘 발송 되었음")
          console.log(res.status)
          isEmailVerifyVisible.value = true;
        }
      },
      (error) => {
        if (error.response.status === httpStatusCode.CONFLICT) {
          console.log(error)
          alert("이미 사용 중인 이메일입니다!")
        }
      }
    ) 
  }

  // 이메일 인증 코드 적합성 검사
  const verifyCode = ref('')
  const validEmail = ref(false); 
  const emailVerified = ref(false)

  function checkVerifyCode () {
    const data = {
      email:email.value,
      code:verifyCode.value
    }
    console.log(data)
    verifyCodeCheck(
      data,
      (res)=>{
        if(res.status === httpStatusCode.OK){
          alert("이게 옳게된 인증코드지")
          validEmail.value = true
          emailVerified.value = true
          isEmailVerifyVisible.value = false;
        }
      },
      (error)=>{
        console.log(error)
        if (error.response.status === "400") {
          console.log(error)
          alert("인증번호가 다릅니다!")
        }
      }
    )

  }
  


  
  // 비밀번호 형식 검사
  const isPasswordValid = (pwd) => {
    return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
  };
  
  // 이메일 형식 검사
  const isEmailValid = (email) => {
  const re =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  };

  // 회원가입 최종
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
    if (!idDuplicatedChecked.value){
      alert("아이디 중복 검사를 하지 않으셨습니다!")
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

    if (!emailVerified.value) {
      alert("이메일 인증 검사를 하지 않으셨습니다!");
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
        if (res.status === httpStatusCode.CREATE) {
          console.log("히히 회원가입 성공")
          console.log(res);
          alert("히히 회원가입 성공")
          window.location.replace("/")
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


  function goBack(){
    router.back(); // 이전 페이지로 이동
  }
  </script>
  
<style scoped lang="scss">
.signup-container {
  max-width: 600px;
}

.justify-center{
  text-align: center;
} 

.id-check{
  margin-top : 10px;
}

.idCheckModal {
  max-width:400px;
  text-align: center;
}

.email-verify{
  margin-bottom:20px;
}

</style>
