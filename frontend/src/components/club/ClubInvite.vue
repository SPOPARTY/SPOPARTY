<template>
    <h1>그룹 가입 진행중.....</h1>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRouter, useRoute} from 'vue-router';
import {useClubStore} from '@/stores/club/clubs'
const {VITE_REST_API} = import.meta.env;

const route = useRoute();
const clubStore = useClubStore()

onMounted(() => {
    alert("너, 동료가 되어라!!!!!!")
    const memberId = sessionStorage.getItem("id");
    // const inviteUrl = route.path;
    const param = route.params.inviteUrl;
    const inviteUrl = `${VITE_REST_API}/${param}`;
    const clubId = route.params.inviteUrl.split("_")[0];
    console.log(inviteUrl)

    const data = JSON.stringify({
        "memberId" : memberId,
        "inviteUrl" : inviteUrl
    })

    const isInvited = clubStore.clubInvite(data)
    if (isInvited){
        alert("너, 동료가 되었다!")
        window.location.replace(`/club/${clubId}`)
    }
})


</script>

<style scoped>

</style>