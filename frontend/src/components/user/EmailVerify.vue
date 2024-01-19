<template>
<div class="modal">
    <div class="modal-content">
    <h2>이메일 인증</h2>
    <p>인증 코드가 이메일 주소로 전송되었습니다. 코드를 입력해 주세요.</p>
    <div class="input-group">
      <input type="text" placeholder="인증 코드 입력" />
    </div>
    <div class="button-group">
        <button @click="verifyEmail">인증</button>
        <button @click="closeModal">취소</button>
    </div>
    </div>
</div>
</template>

<script setup>
const emit = defineEmits(['close']);

function verifyEmail() {
    closeModal();
}

function closeModal() {
    emit('close');
}
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.6); /* 오버레이 */
  z-index: 100; /* 레이어 순서 */
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2); /* 상자 그림자 */
  width: 90%; /* 모달 너비 */
  max-width: 400px; /* 모달 최대 너비 */
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.input-group {
  width: 100%; /* 입력 그룹 너비를 부모 요소에 맞춤 */
  display: flex; /* 플렉스박스를 사용하여 자식 요소들을 플렉스 아이템으로 만듦 */
  justify-content: center; /* 플렉스 아이템들을 가운데 정렬 */
  margin: 10px 0; /* 위, 아래 여백 추가 */
}

.input-group input[type="text"] {
  width: 80%; /* 입력 필드 너비를 input-group의 80%로 설정 */
  /* ... 기존 input 스타일 ... */
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  background-color: #5c6bc0;
  color: white;
  font-weight: bold;
}

button:hover {
  background-color: #3949ab;
}

button:active {
  background-color: #283593;
}

</style>