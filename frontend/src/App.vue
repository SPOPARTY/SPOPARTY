<script setup>
import { RouterView } from 'vue-router';
import HeaderNav from "./components/common/HeaderNav.vue";
import Footer from "./components/common/Footer.vue";
</script>
<template>
  <div class="header">
    <HeaderNav/>
    </div>
    <div class="main">
    <RouterView/>
    </div>
</template>

<style scoped>
/* .logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
} */
</style>
