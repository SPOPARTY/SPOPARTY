<template>
    <h1 style="color:white">그룹 가입 진행중.....</h1>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRouter, useRoute} from 'vue-router';
import {useClubStore} from '@/stores/club/clubs'
import {useManagementStore} from "@/stores/member/managements"
const {VITE_REST_API} = import.meta.env;

const route = useRoute();
const router = useRouter();
const clubStore = useClubStore();
const managementStore = useManagementStore();

onMounted(() => {
    alert("너, 동료가 되어라!!!!!!")
    const memberId = sessionStorage.getItem("id"); // 로그인 여부 판단
    // const inviteUrl = route.path; // 로컬에서 시험이 안됨 ㅠ
    const param = route.params.inviteUrl;
    const inviteUrl = `https://i10a802.p.ssafy.io/invite/${param}`;
    console.log(inviteUrl)

    const data = JSON.stringify({
        "inviteUrl" : inviteUrl
    })


    clubStore.clubInvite(data,memberId)

})


</script>

<style scoped>

</style>