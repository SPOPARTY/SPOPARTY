<template>
    <div class="signup-container">
      <h1>회원 가입</h1>
      <form @submit.prevent="submitSignup">
        <div class="form-group">
          <label for="userId">아이디</label>
          <input type="text" id="userId" v-model="signupRequest.loginId">
        </div>
        <div class="form-group">
          <label for="nickname">닉네임</label>
          <input type="text" id="nickname" v-model="signupRequest.nickname">
        </div>
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input type="password" id="password" v-model="signupRequest.loginPwd">
        </div>
        <div class="form-group">
          <label for="password">비밀번호 확인</label>
          <input type="password" id="password" v-model="confirmPwd">
        </div>
        <div class="form-group email-group">
            <label for="email">이메일</label>
            <div class="email-inputs">
                <input type="text" id="email" v-model="signupRequest.emailId" required placeholder="아이디">
                <span>@</span>
                <input type="text" v-model="signupRequest.emailDomain" required placeholder="도메인">
                <button type="button" @click="showEmailVerify">이메일 인증</button>
                <EmailVerify v-if="isEmailVerifyVisible" @close="isEmailVerifyVisible=false"/>
            </div>
        </div>
        <div class="form-group">
          <label for="team">대표 앰블럼</label>
          <select id="team" v-model="signupRequest.team">
            <option disabled value="">선택해주세요</option>
            <option>토트넘 FC</option>
            <option>리버풀 FC</option>
            <option>토론토 FC</option>
          </select>
        </div>
        <div class="button-group">
            <button type="submit">회원가입</button>
            <button type="button" @click="goBack">이전</button>
        </div>
      </form>
    </div>
  </template>
  
  <script setup>
  import {useRouter} from 'vue-router';
  import { ref } from 'vue';
  import EmailVerify from '@/components/user/EmailVerify.vue'

  const router = useRouter()
  
  const signupRequest = ref({
    loginId: '',
    nickname:'',
    loginPwd: '',
    emailId : '',
    emailDomain : '',
    team: ''
  });

  const confirmPwd = ref('');
  const requireCheckPwd = ref(false);
  
  function submitSignup() {
    if (signupRequest.value.loginId !== confirmPwd.value) {
        requireCheckPwd.value = true;
        return;
    }
    console.log(signupForm.value);
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
    max-width: 500px;
    margin: auto;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    background: #f7f7f7;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  .email-group {
  display: flex;
  flex-direction: column;
}

.email-inputs {
  display: flex;
  justify-content: space-between;
}

.email-inputs input[type="text"] {
  width: 48%; /* 입력 필드가 부모 컨테이너의 절반 크기를 차지하도록 설정 */
}

.email-inputs span {
  align-self: center;
}
  
  .form-group {
    width: 100%; /* 폼 그룹 너비를 부모 컨테이너에 맞춤 */
    max-width: 400px; /* 폼 그룹 최대 너비 설정 */
    margin-bottom: 15px;
    display: flex;
    flex-direction: column;
    align-items: center; /* 폼 그룹 내부 요소 중앙 정렬 */
  }
  
  .form-group label {
    align-self: flex-start; 
    margin-bottom: 5px;
  }
  
  .form-group input[type="text"],
  .form-group input[type="password"],
  .form-group input[type="email"],
  .form-group select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
.button-group {
  display: flex;
  justify-content: space-between; /* 버튼들 사이에 공간을 균등하게 배분 */
}

.button-group button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 10px; /* 오른쪽 마진으로 버튼 사이 간격 설정 */
}

.button-group button:last-child {
  margin-right: 0; /* 마지막 버튼은 오른쪽 마진 없음 */
}

  button {
    width: 100%;
    padding: 10px;
    background-color: #5c6bc0;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #3949ab;
  }
  </style>