<script setup>
import { RouterView } from 'vue-router';
import HeaderNav from "./components/common/HeaderNav.vue";

let global = globalThis
</script>
<template>
  <v-app>
  <div>
    <HeaderNav/>
    </div>
    <v-main>
      <RouterView/>
    </v-main>
  </v-app>
</template>

<style scoped>

</style>
