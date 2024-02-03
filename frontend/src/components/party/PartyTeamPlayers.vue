<template>
    <v-container fluid class="vc">
        <p>선수 목록 페이지. fixtureId = {{ fixtureId? fixtureId : 'null'}}</p>
    </v-container>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useFootballStore } from '@/stores/football/football'

const footballStore = useFootballStore()

const fixtureId = ref(null)

watch (() => footballStore.fixtureIdForParty, (newFixtureId) => {
    fixtureId.value = newFixtureId
}, { immediate: true })

</script>

<style lang="scss" scoped>
.vc {
    color : #121212;
}

</style>