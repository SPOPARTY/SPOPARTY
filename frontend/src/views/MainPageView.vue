<template>
     <div class="main-container">
          <div class="club-list">
               <GroupNav/>
               <div class="test">
                    <h1>Test 2048px</h1>
               </div>
          </div>
          <div class="main-contents">
               <div class="main-contents-1">
                    <CheerVote/>
               </div>
               <div class="main-contents-2">
                    <MatchNear/>
               </div>
               <div class="test">
                    <h1>Test 2048px</h1>
               </div>
          </div>
     </div>
</template>

<script setup>
import GroupNav from "@/components/common/GroupNav.vue";
import MatchNear from "@/components/mainpage/MatchNear.vue";
import CheerVote from "@/components/mainpage/CheerVote.vue";

</script>

<style>
.test {
     height: 2048px;
}

.main-container {
     display: flex;
     flex-direction: row;
     padding: 10px;
}

.club-list {
     position: fixed;
     top: 30px;
     left: 40px; /* 오른쪽 마진 + 패딩에 해당하는 값만큼 */
     width: 255px;
     height: 100%;
     overflow-y: auto; /* 세로 스크롤을 사용하기 위한 설정 */
     background-color: grey;
}

.main-contents {
     width: 100%;
     height: 100%;
     background-color: skyblue;
     padding: 0px 0px 10px 0px;
     margin-left: 270px;
}

.main-contents-1 {
     margin-bottom: 20px;
}

</style>