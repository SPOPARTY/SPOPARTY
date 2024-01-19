<template>
    <div class="match-near">
        <h1 class="near-title">오늘 예정 경기</h1>
        <div class="near-item">
            경기
        </div>
        <div class="near-item">
            경기
        </div>
        <div class="near-item">
            경기
        </div>
    </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.match-near {
    display: flex;
    flex-wrap: wrap;
    margin: 10px;
    padding: 20px;
    background-color: #007d04;
    justify-content: space-around; /* 항목들 사이에 공간을 균등하게 배분 */
}
.near-title {
    width: 100%;
    color: white;
}
.near-item {
    width: 350px;
    height: 150px;
    margin: 10px;
    background-color: grey;
}
</style>