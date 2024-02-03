<template>
    <h1>그룹 가입 진행중.....</h1>
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
    const memberId = sessionStorage.getItem("id");
    // const inviteUrl = route.path; // 로컬에서 시험이 안됨 ㅠ
    const param = route.params.inviteUrl;
    const inviteUrl = `${VITE_REST_API}/${param}`;
    const clubId = route.params.inviteUrl.split("_")[0];
    console.log(inviteUrl)

    const data = JSON.stringify({
        "memberId" : memberId,
        "inviteUrl" : inviteUrl
    })

    if (memberId === null) {
        alert("동료가 되기 전 로그인부터 해라!")
        window.location.replace("/login") // 로그인으로 보낸 다음에 다시 원래 요청으로 돌아오고 싶다.
    }
    else {
        const isInvited = clubStore.clubInvite(data)
        if (isInvited){
            alert("너, 동료가 되었다!")
        }
        window.location.replace(`/club/${clubId}`)
    }
})


</script>

<style scoped>

</style>