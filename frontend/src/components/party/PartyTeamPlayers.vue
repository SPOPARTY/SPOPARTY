<template>
    <v-container fluid class="vc">
        <p>선수 목록 페이지. fixtureId = {{ fixtureId? fixtureId : 'null'}}</p>
        <p v-if="fixtureId==null" class="alert-msg">경기를 선택해주세요.</p>
        <v-row v-if="fixtureId!=null">
            <v-col cols="6">
                <p class="team-name text-center">Home {{ teamIds['homeName'] }}</p>
                <!-- {{ teamIds.home }} -->
                <TeamInfoMembers :key="'home-' + teamIds.home" :team-id="teamIds.home" />
                <!-- <TeamInfoMembers v-else :team-id="teamIds.home" /> -->
            </v-col>
            <v-col cols="6">
                <p class="team-name text-center">Away {{ teamIds['awayName'] }}</p>
                <!-- {{ teamIds.away }} -->
                <TeamInfoMembers :key="'away-' + teamIds.away" :team-id="teamIds.away" />
                <!-- <TeamInfoMembers v-else :team-id="teamIds.away" /> -->
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
    fixtureId.value = newFixtureId;
    if (newFixtureId !== null) {
        // 새로운 객체를 생성하여 teamIds를 업데이트합니다.
        teamIds.value = {...footballStore.findTeamIdsByFixtureId(newFixtureId)};
    }
    // count.value = !count.value; // 이 부분은 더 이상 필요하지 않습니다.
    console.warn("###", teamIds.value);
}, { immediate: true, deep: true });


// watch (() => footballStore.fixtureIdForParty, (newFixtureId) => {
//     fixtureId.value = newFixtureId
//     if (newFixtureId !== null) {
//         teamIds.value = footballStore.findTeamIdsByFixtureId(newFixtureId)
//     }
//     count.value = !count.value
//     console.warn("###",teamIds.value)
// }, { immediate: true }, { deep: true })

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
.alert-msg {
    text-align: center;
    margin: 30px;
    font-size: 2rem;
    color: #292646;
}
</style>