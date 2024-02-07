<template>
    <v-container fluid class="vc">
        <p>경기 이력 페이지. fixtureId = {{ fixtureId? fixtureId : 'null'}}</p>
    </v-container>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useFootballStore } from '@/stores/football/football'

const footballStore = useFootballStore()

const { matchWatchable, getMatchHistory } = footballStore

const matches = ref(matchWatchable)
const match = ref(null)
const homeHistory = ref(null)
const awayHistory = ref(null)
const fixtureId = ref(null)

watch (() => footballStore.fixtureIdForParty, (newFixtureId) => {
    fixtureId.value = newFixtureId
    if (fixtureId.value) {
        match.value = matches.value.find(match => match.fixtureId === fixtureId.value)
        homeHistory.value = getMatchHistory(match.value.homeTeam.seasonLeagueTeamId)
        awayHistory.value = getMatchHistory(match.value.awayTeam.seasonLeagueTeamId)
        console.log('homeHistory', homeHistory.value)
        console.log('awayHistory', awayHistory.value)
    }
}, { immediate: true })


</script>

<style lang="scss" scoped>
.vc {
    color : #121212;
}

</style>