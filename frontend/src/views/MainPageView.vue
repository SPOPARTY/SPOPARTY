<template>
  <v-app>
    <v-container fluid>
      <!-- 메인 컨텐츠 영역 -->
      <v-row
        align="center"
        justify="center"
        class="contents-section mx-12 my-8 pa-1">
        <!-- 비로그인 시 소개 출력 -->
        <v-col
          v-if="!isLogined"
          cols="12"
          class="my-1 mx-12">
          <Welcome />
        </v-col>
        <!-- 첫 번째 컨텐츠 영역: 응원 투표 -->
        <v-col
          cols="12"
          class="my-1 mx-12">
          <CheerVote />
        </v-col>

        <!-- 두 번째 컨텐츠 영역: 경기 일정 -->
        <v-col
          cols="12"
          class="my-1 mx-12">
          <MatchNear />
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script setup>
import MatchNear from '@/components/mainpage/MatchNear.vue'
import CheerVote from '@/components/mainpage/CheerVote.vue'
import Welcome from '@/components/mainpage/Welcome.vue'
import { ref, onMounted, watch } from 'vue'

// import { useManagementStore } from '@/stores/member/managements'

// const managementStore = useManagementStore()

// const isLogin = ref(managementStore.isLogin)
const isLogined = ref(localStorage.getItem("accessToken") !== null);

</script>

<style scoped></style>
