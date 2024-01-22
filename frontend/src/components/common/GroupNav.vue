<template>
    <div>
        <h1>그룹 Nav</h1>
        <button @click="onClickRedirect()">클럽(새탭)</button>
        <br>
        <button>새 클럽</button>
    </div>
</template>

<script setup>
import router from "@/router/index";

// 클럽 페이지를 새 탭에서 열기
// 새 탭으로 가고나면 다른 페이지로 이동할 때 문제가 발생할 수 있음
// 그래서 router.resolve를 사용하여 클럽 페이지로 이동

const onClickRedirect = () => {
  const clubId = 123; // 클럽 ID 설정
  const url = router.resolve({ name: "ClubView", params: { clubId } }).href;
  window.open(url, "_blank");
};
</script>

<style scoped>

</style>