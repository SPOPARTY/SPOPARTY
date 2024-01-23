<template>
    <v-dialog
        v-model="isModalVisible"

        @click:outside="closeModal"
        persistent=""
    >
    <v-card>
        <div>
            <a id="custom-login-btn" @click="kakaoLogin()">
            <img
                src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
                width="222"
                alt="카카오 로그인 버튼"
            />
            </a>
            <div @click="closeModal()">로그아웃</div>
        </div>
    </v-card>
    </v-dialog>
</template>
  

<script setup>
import {ref, onMounted, onUnmounted} from 'vue';

const emit = defineEmits([
    'KakaoLoginClose'
])

const isModalVisible = ref(true);

const closeModal = () => {
    isModalVisible.value = false;
    emit('KakaoLoginClose')
}

const kakaoLogin = () => {
    window.Kakao.Auth.login({
        scope: "profile_image, account_email",
        success: getKakaoAccount,
    });
};

const getKakaoAccount = () => {
  window.Kakao.API.request({
    url: "/v2/user/me",
    success: (res) => {
      const kakao_account = res.kakao_account;
      const nickname = kakao_account.profile.nickname; // 오타 수정됨: ninkname -> nickname
      const email = kakao_account.email;
      console.log("nickname", nickname);
      console.log("email", email);

      // 로그인 처리 구현

      alert("로그인 성공!");
    },
    fail: (error) => {
      console.log(error);
    },
  });
};

const kakaoLogout = () => {
  window.Kakao.Auth.logout((res) => {
    console.log(res);
  });
};

onMounted(() => {
  // 컴포넌트가 마운트될 때 필요한 작업을 수행
});

onUnmounted(() => {
  // 컴포넌트가 언마운트될 때 필요한 작업을 수행
}); 

</script>

<style scoped>

</style>