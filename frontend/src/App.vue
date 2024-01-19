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
.header {
  background-color: #c7c7c7;
  margin-bottom: 6px;
}

.main {
  background-color: #c7c7c7;
}
</style>
