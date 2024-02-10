<template>
     <RouterView :club-id="clubId"/>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';


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