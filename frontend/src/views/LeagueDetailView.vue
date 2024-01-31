<template>
    <v-container fluid class="pa-2 mt-3">
        <v-row align="center" justify="center" class="py-4 mb-1 title-section">
            <h1>리그 랭킹</h1>
        </v-row>
        <v-row justify="center" class="py-4 mx-12 mt-12 contents-section">
            <v-col cols="11">
                <LeagueTable :league-id="leagueId"/>
            </v-col>
        </v-row>
        <br>
</v-container>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router';
import LeagueTable from '@/components/league/LeagueTable.vue';


const route = useRoute();
const leagueId = route.params.leagueId; // URL에서 leagueId 값을 얻습니다.

</script>

<style>
h1 {
    color: white;
}

.title-section, .contents-section {
     /* background-color: #E0E0E0; */
     /* 타이틀 부분의 백그라운드 컬러 */
     white-space: nowrap;
     min-width: 1100px;
}

</style>