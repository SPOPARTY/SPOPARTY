<script setup>
import { RouterView } from 'vue-router';
import HeaderNav from "./components/common/HeaderNav.vue";
import Footer from "./components/common/Footer.vue";
</script>
<template>
  <div class="header-nav">
    <HeaderNav/>
    </div>
    <div class="main-app">
    <RouterView/>
    </div>
</template>

<style scoped>
.header-nav {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #c7c7c7;
}

.main-app {
  margin: 20px 30px 0px 30px;
  
}

</style>
