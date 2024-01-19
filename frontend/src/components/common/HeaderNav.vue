<template>
    <div class="container">
        <div class="box match">
            <RouterLink to="Match">경기일정</RouterLink>
        </div>
        <div class="box league">
            <RouterLink to="League">리그 목록</RouterLink>
        </div>
        <div class="box signup">
            <RouterLink to="signup">회원가입</RouterLink>
        </div>
        <div class="box login">
            <RouterLink to="login">로그인</RouterLink>
        </div>
    </div>
</template>

<script setup>
</script>

<style scoped>
.container{
    text-align: center;
    display:grid;
    grid-template-columns: repeat(4,100px);
    justify-content: right;
}

.box{
    
}
</style>