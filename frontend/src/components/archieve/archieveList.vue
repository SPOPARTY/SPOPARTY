<template>
    <div>
        <h1>여기는 추억 저장소</h1>
        <h2>경로 : {{ routes.params.clubId }}</h2>
    </div>
</template>

<script setup>
import {useRoute} from 'vue-router'
const routes = useRoute();
console.log(routes.params.clubId)

</script>

<style lang="scss" scoped>

</style>