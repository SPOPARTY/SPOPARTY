<template>
     <!-- <h1>클럽뷰</h1> -->
     <RouterView/>
     <!-- <ClubMain/> -->
</template>

<script setup>
import { ref } from 'vue';
// import { ClubMain } from '@/components/club/ClubMain.vue';

const props = defineProps({
    clubId: {
     // 타입: 스트링으로 해야 잘 받아옴.
        type: String,
        required: true
    }
})

// const clubId = ref(props.clubId)

</script>

<style>
h1 {
     color: white;
}

</style>