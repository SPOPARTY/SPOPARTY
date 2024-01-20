<template>
  <div class="navigation">
    <RouterLink to="/">SPOPARTY | </RouterLink>
    <RouterLink to="/match">경기일정 | </RouterLink>
    <RouterLink to="/league">리그 목록 | </RouterLink>
    <RouterLink to="/signup">회원가입 | </RouterLink>
    <RouterLink to="/login">로그인 | </RouterLink>
  </div>
</template>

<script setup>

</script>

<style scoped>
스타일 코드
.navigation {
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  /* padding-left: 20px; */

  background-color: #c7c7c7;
}

/* 라우터링크에게 적용되는 css */
.navigation a {
  white-space: nowrap;
}
</style>