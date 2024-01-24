<template>
    <v-card class="container" align="center">
        <h3>최근 전적</h3>
        <v-simple-table>
            <template v-slot:default>
                <thead>
                    <tr>
                        <th class="text-left">일시</th>
                        <th class="text-left">우리 팀</th>
                        <th class="text-left">스코어</th>
                        <th class="text-left">상대 팀</th>
                        <th class="text-left">결과</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="record in sortedRecentRecords" :key="record.date">
                        <td>{{ record.date }}</td>
                        <td>{{ teamData.name }}</td>
                        <td>{{ record.score }}</td>
                        <td>{{ record.opponent }}</td>
                        <td
                            :class="{ 'win': record.result === '승', 'loss': record.result === '패', 'draw': record.result === '무' }">
                            {{ record.result }}
                        </td>
                    </tr>
                </tbody>
            </template>
        </v-simple-table>
    </v-card>
</template>
  
<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
    teamId: {
        type: String,
        required: true,
    },
});

const teams = ref([
    { teamId: '1', leagueId: 1, logo: 'arsenal.png', rank: 1, name: '아스날 FC', points: 30, wins: 9, losses: 3, draws: 3, goalsFor: 25, goalsAgainst: 10, goalDifference: 15, recentForm: 'W-W-D-L-W', followed: true },
    { teamId: '2', leagueId: 2, logo: 'teamB.png', rank: 2, name: '팀 B', points: 28, wins: 8, losses: 4, draws: 4, goalsFor: 22, goalsAgainst: 12, goalDifference: 10, recentForm: 'L-W-W-W-D', followed: false },
]);

const teamData = computed(() => teams.value.find((team) => team.teamId === props.teamId));

const recentRecords = ref([
    { date: '2024-01-24', opponent: '팀 H', result: '승', score: '2:0' },
    { date: '2024-01-22', opponent: '팀 I', result: '승', score: '1:0' },
    { date: '2024-01-18', opponent: '팀 J', result: '무', score: '1:1' },
    { date: '2024-01-15', opponent: '싸피 FC', result: '패', score: '1:4' },
    { date: '2024-01-12', opponent: '팀 L', result: '승', score: '2:0' },
    { date: '2024-01-03', opponent: '팀 C', result: '승', score: '2:0' },
    { date: '2024-01-07', opponent: '팀 D', result: '패', score: '0:1' },
    { date: '2024-01-10', opponent: '팀 E', result: '무', score: '0:0' },
    { date: '2024-01-15', opponent: '팀 F', result: '승', score: '1:0' },
    { date: '2024-01-20', opponent: '팀 G', result: '승', score: '3:1' },
]);

const sortedRecentRecords = computed(() => {
    return [...recentRecords.value].sort((a, b) => new Date(b.date) - new Date(a.date));
});
</script>
  
<style scoped>
.container {
    max-width: 500px;
    padding: 20px;
    box-shadow: 0px 0px 15px rgba(0,0,0,0.2);
    border-radius: 8px;
}

.win {
    color: #4CAF50;
    /* 승리 */
}

.loss {
    color: #F44336;
    /* 패배 */
}

.draw {
    color: #FFC107;
    /* 무승부 */
}

th,td {
    padding: 8px 12px;
}

thead tr {
    background-color: #E0E0E0;
}

tbody tr {
    &:nth-child(odd) {
        background-color: #F5F5F5;
    }

    &:hover {
        background-color: #B3E5FC;
    }
}

h3 {
    margin-bottom: 15px;
}
</style>
  