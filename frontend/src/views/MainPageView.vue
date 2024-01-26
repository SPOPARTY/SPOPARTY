<template>
     <v-app>
          <v-container fluid>
               <!-- 메인 컨텐츠 영역 -->
               <v-row>
                    <!-- 비로그인 시 소개 출력 -->
                    <v-col v-if="!isLogin" cols="12" class="my-1">
                         <Welcome />
                    </v-col>
                    <!-- 첫 번째 컨텐츠 영역: 응원 투표 -->
                    <v-col cols="12" class="my-1">
                         <CheerVote />
                    </v-col>

                    <!-- 두 번째 컨텐츠 영역: 경기 일정 -->
                    <v-col cols="12" class="my-1">
                         <MatchNear />
                    </v-col>
               </v-row>
          </v-container>
     </v-app>
</template>
   
<script setup>
import MatchNear from "@/components/mainpage/MatchNear.vue";
import CheerVote from "@/components/mainpage/CheerVote.vue";
import Welcome from "@/components/mainpage/Welcome.vue";

import { ref } from "vue";

const isLogin = ref(false);

</script>
   
<style scoped>

</style>
   