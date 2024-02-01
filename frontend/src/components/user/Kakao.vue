<template>
    <v-card>
        <h1>여기는 카카오 리다이렉트</h1>
    </v-card>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRouter, useRoute} from 'vue-router';
import {useManagementStore} from '@/stores/member/managements'

const login = useManagementStore.login();

onMounted(() => {
    alert("카카오 로그인!!!!!!")
    const path = new URL(document.location).searchParams.get("path");
    const loginId = path;
    const loginPwd = path.split("_")[1];

    data = JSON.stringify({
        "loginId" : loginId,
        "loginPwd" : loginPwd
    })

    login(data)
})


</script>

<style scoped>

</style>