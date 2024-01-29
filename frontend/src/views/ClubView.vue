<template>
     <RouterView/>
     <!-- <ClubMain :club-id=clunId/> -->
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
    clubId: {
     // 타입: 스트링으로 해야 잘 받아옴.
        type: String,
        required: true
    }
})

</script>

<style>
h1 {
     color: white;
}

</style>