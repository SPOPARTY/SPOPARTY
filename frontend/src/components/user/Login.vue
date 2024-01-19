<template>
    <div class="login-form">
        <h1>로그인</h1>
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="loginId">아이디</label>
            <input type="text" id="loginId" v-model="loginRequest.loginId" >
          </div>
          <div>
            <label for="loginPwd">비밀번호</label>
            <input type="password" id="loginPwd" v-model="loginRequest.loginPwd" >
          </div>
          <div class="button-group">
            <button type="submit">로그인</button>
            <button type="button" @click="handleSignup">회원가입</button>
            <button type="button" @click="showFindPwdModal">비밀번호 찾기</button>
            <FindPwd v-if="isPwdModalVisible" @close="isPwdModalVisible = false" />
          </div>
        </form>
    </div>
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

const isPwdModalVisible = ref(false);

function showFindPwdModal() {
  isPwdModalVisible.value = true;
}

</script>

<style scoped lang="scss">
.login-form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width:500px;
  height:500px;
  margin:auto;
  padding:20px;
  box-sizing: border-box; // 패딩과 테두리가 너비와 높이에 포함되도록 설정
  background-color: #f9f9f9; // 배경색
  border-radius: 10px; // 테두리 둥글게 (필요에 따라 변경 가능)
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); // 그림자 효과 (필요에 따라 변경 가능)
  min-height: 100vh;

}

  div {
    display: flex; // 플렉스박스를 사용하여 내부 요소들을 나란히 정렬
    align-items: center; // 세로축을 중심으로 정렬
    margin-bottom: 15px;

  label {
    width: 100px; // 레이블의 너비를 고정
    margin-right: 10px;
  }


  input {
    flex-grow: 1; // 남은 공간을 모두 차지
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }

  .button-group {
    display: flex;
    justify-content: space-between; // 버튼 사이에 공간을 균등하게 분배

    button {
      padding: 10px 15px;
      background-color: #5c6bc0;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;

      &:not(:last-child) {
        margin-right: 10px; // 마지막 버튼을 제외하고 오른쪽에 여백을 추가
      }

      &:hover {
        background-color: #3949ab;
      }
    }
  }
}
</style>