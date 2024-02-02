<template>
    <v-container fluid class="pa-1">
        <v-card>
            <v-tabs v-model="tab" bg-color="#CBD0D8" show-arrows slider-color="primary" class="tabs">
                <v-tab value="one">실시간 정보</v-tab>
                <v-tab value="two">경기 내역</v-tab>
                <v-tab value="three">팀 플레이어들</v-tab>
                <!-- 새로고침 버튼 -->
                <v-spacer></v-spacer>
                <v-btn :loading="loading" icon @click="refreshData" variant="text">
                    <v-icon>mdi-refresh</v-icon>
                </v-btn>
            </v-tabs>
            <v-card-text>
                <v-window v-model="tab">
                    <v-window-item value="one">
                        <PartyRealTimeInfo :matchId="matchId" />
                    </v-window-item>
                    <v-window-item value="two">
                        <PartyMatchHistory :matchId="matchId" />
                    </v-window-item>
                    <v-window-item value="three">
                        <PartyTeamPlayers :matchId="matchId" />
                    </v-window-item>
                </v-window>
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script setup>
import { ref } from 'vue'
import PartyMatchHistory from '@/components/party/PartyMatchHistory.vue'
import PartyRealTimeInfo from '@/components/party/PartyRealTimeInfo.vue'
import PartyTeamPlayers from '@/components/party/PartyTeamPlayers.vue'

const props = defineProps({
    matchId: String
})

const matchId = ref(props.matchId)
const tab = ref(null) // 현재 선택된 탭을 관리

const loading = ref(false) // 새로고침 버튼 로딩 상태

const refreshData = () => {
    // 여기에 데이터 새로고침 로직 구현
    loading.value = true
    setTimeout(() => (loading.value = false), 2000)
}
</script>

<style scoped>
.tabs {
    /* 탭 스타일 */
    
}

.v-tab {
    /* 개별 탭 스타일 */
    width: 20%;
    text-transform: none;
    font-size: 1.1em;
}

.v-btn {
    /* 버튼 스타일 */
    margin-right: 10px;
}
</style>
