<template>
    <h1> 카카오 로그인 진행 중...</h1>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRouter, useRoute} from 'vue-router';
import {useManagementStore} from '@/stores/member/managements'

const {login} = useManagementStore();

onMounted(() => {
    alert("카카오 로그인!!!!!!")
    const path = new URL(document.location).searchParams.get("path");
    const loginId = path;
    const loginPwd = path.split("_")[1];

    const data = JSON.stringify({
        "loginId" : loginId,
        "loginPwd" : loginPwd
    })

    login(data)
})


</script>

<style scoped>

</style>