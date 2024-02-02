<script setup>
import { RouterView } from 'vue-router';
import HeaderNav from "./components/common/HeaderNav.vue";
import Footer from "./components/common/Footer.vue";
</script>
<template>
  <v-app>
  <div>
    <HeaderNav/>
    </div>
    <v-main>
      <RouterView/>
    </v-main>
    <Footer/>
  </v-app>
</template>

<style scoped>

</style>
