<template>
    <v-container fluid class="vc">
        <p>선수 목록 페이지. fixtureId = {{ fixtureId? fixtureId : 'null'}}</p>
        <v-row>
            <v-col cols="6">
                <p class="team-name text-center">Home {{ teamIds['homeName'] }}</p>
                <TeamInfoMembers v-if="count" :team-id="teamIds['home']" />
                <TeamInfoMembers v-else :team-id="teamIds['home']" />
            </v-col>
            <v-col cols="6">
                <p class="team-name text-center">Away {{ teamIds['awayName'] }}</p>
                <TeamInfoMembers v-if="count" :team-id="teamIds['away']" />
                <TeamInfoMembers v-else :team-id="teamIds['away']" />
            </v-col>
        </v-row>
    </v-container>
</template>

<script setup>
import { ref, watch } from 'vue'
import TeamInfoMembers from '@/components/league/TeamInfoMembers.vue';
import { useFootballStore } from '@/stores/football/football'

const footballStore = useFootballStore()

const fixtureId = ref(null)
const teamIds = footballStore.teamIdsForParty
const count = ref(true)

watch (() => footballStore.fixtureIdForParty, (newFixtureId) => {
    fixtureId.value = newFixtureId
    if (newFixtureId !== null) {
        teamIds.value = footballStore.findTeamIdsByFixtureId(newFixtureId)
    }
    count.value = !count.value
    // console.log(teamIds.value)
}, { immediate: true }, { deep: true })

// console.log(teamIds.value)

</script>

<style lang="scss" scoped>
.vc {
    color : #121212;
}
.team-name {
    height: 25px;
    font-size: 1.5rem;
    font-weight: bold;
    margin: 10px;
}
</style>