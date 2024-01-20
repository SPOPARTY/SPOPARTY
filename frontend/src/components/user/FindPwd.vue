<template>
    <div class="modal">
      <div class="modal-content">
        <h2>비밀번호 재설정</h2>
        <form @submit.prevent="submitForm">
          <div>
            <label for="loginId">아이디:</label>
            <input type="text" id="loginId" v-model="loginId">
          </div>
          <div>
            <label for="email">이메일:</label>
            <input type="email" id="email" v-model="email">
          </div>
          <div class="button-group">
            <button type="button" @click="closeModal">이전</button>
            <button type="submit">인증번호 요청</button>
          </div>
        </form>
      </div>
      <div v-if="isMessageModalVisible" class="message-modal">
        <div class="message-modal-content">
            <p>임시 비밀번호가 발급되었습니다. 5분 동안 유효합니다.</p>
            <button @click="closeMessageModal">확인</button>
        </div>
     </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const loginId = ref('');
  const email = ref('');
  const isMessageModalVisible = ref(false); // 메시지 모달 표시 여부
  const emit = defineEmits(['close']);
  
  function submitForm() {
    console.log(`임시 비밀번호 발급 요청: 아이디=${loginId.value}, 이메일=${email.value}`);
    // 임시 비밀번호 발급 로직 여기에 추가...
    // 로직 완료 후 메시지 모달 표시
    isMessageModalVisible.value = true;
  }
  
  function closeModal() {
    emit('close');
  }

  function closeMessageModal() {
    isMessageModalVisible.value = false; // 메시지 모달만 닫습니다.
  }
  </script>
  
  <style scoped lang="scss">
  /* 모달 스타일 (기본 예시, 필요에 따라 수정) */
  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
  }
  .message-modal {
  position: absolute; // 모달 내부에 상대적인 위치 설정
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); // 정중앙에 배치
  width: 300px; // 메시지 모달의 너비
  z-index: 1000; // 다른 컨텐츠 위에 표시되도록 z-index 설정
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); // 그림자 효과
}

.message-modal-content {
  padding: 20px;
  text-align: center;
}

.message-modal-content p {
  margin-bottom: 20px;
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
</style>