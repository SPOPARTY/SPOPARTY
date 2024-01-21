<template>
     <v-app>
          <v-container fluid>
               <!-- 메인 컨텐츠 영역 -->
               <v-row>
                    <!-- 첫 번째 컨텐츠 영역: 응원 투표 -->
                    <v-col cols="12" class="my-1">
                         <CheerVote />
                    </v-col>

                    <!-- 두 번째 컨텐츠 영역: 경기 일정 -->
                    <v-col cols="12" class="my-1">
                         <MatchNear />
                    </v-col>
               </v-row>

               <!-- 테스트 영역 -->
               <!-- <v-row>
                    <v-col cols="12">
                         <div class="test">
                              <h1>Test 2048px</h1>
                         </div>
                    </v-col>
               </v-row> -->
          </v-container>
     </v-app>
</template>
   
<script setup>
import MatchNear from "@/components/mainpage/MatchNear.vue";
import CheerVote from "@/components/mainpage/CheerVote.vue";
</script>
   
<style scoped>

</style>
   