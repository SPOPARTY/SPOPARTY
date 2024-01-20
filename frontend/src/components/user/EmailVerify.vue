<template>
  <v-dialog
    v-model="modalVisible"
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
        ></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" @click="verifyEmail">인증</v-btn>
        <v-btn color="grey" @click="closeModal">취소</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref } from 'vue';

const modalVisible = ref(true); // 모달의 가시성을 제어하는 변수, 필요에 따라 부모 컴포넌트로부터 prop으로 받거나 다른 로직에 의해 변경될 수 있음

// 이벤트를 발생시키기 위한 함수 정의
const emit = defineEmits(['close']);

function verifyEmail() {
    // 여기에 인증 로직을 추가하세요
    closeModal();
}

function closeModal() {
    modalVisible.value = false; // 모달을 숨김
    emit('close'); // 부모 컴포넌트에 'close' 이벤트를 발생시킴
}
</script>

<style scoped>

</style>