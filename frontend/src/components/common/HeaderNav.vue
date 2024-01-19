<template>
    <div>
      <RouterLink to="/">SPOPARTY | </RouterLink>
      <RouterLink to="/match">경기일정 | </RouterLink>
      <RouterLink to="/league">리그 목록 | </RouterLink>
      <RouterLink to="/signup">회원가입 | </RouterLink>
      <RouterLink to="/login">로그인 | </RouterLink>
      <button @click="onClickRedirect()">클럽(새탭)</button>
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
  /* 스타일 코드 */
  </style>
  