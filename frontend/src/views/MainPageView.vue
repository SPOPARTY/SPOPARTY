<template>
     <div class="main-container">
          <div class="clubList">클럽리스트</div>
          <div class="main-contents">
               <div class="main-contents-1">응원하기</div>
               <div class="main-contents-2">오늘 예정 경기</div>
          </div>
     </div>
</template>

<script setup>

</script>

<style>

.main-container {
     display: flex;
     flex-direction: row;
     padding: 10px;
}

.clubList {
     width: 20%;
     height: 100%;
     background-color: white;
}

.main-contents {
     width: 80%;
     height: 100%;
     background-color: blue
}

</style>