<template>
    <v-container class="list-match">
        <v-row>
            <v-col cols="12" v-for="match in filteredMatches" :key="match.id">
                <v-card class="card-match">
                    <v-card-subtitle>{{ match.league }}</v-card-subtitle>
                    <span>{{ match.startTime }} {{ getMatchStatus(match.startTime) }}</span>
                    <v-card-title class="pb-6">
                        <v-row class="card-content" align="center" justify="center">
                            <v-col cols="4" class="text-end">
                                {{ match.teamA }}
                            </v-col>
                            <v-col cols="1" class="d-flex justify-center align-center">
                                <v-img :src="match.teamALogo" contain class="team-logo">AA</v-img>
                            </v-col>
                            <v-col cols="1" class="d-flex justify-center align-center">
                                VS
                            </v-col>
                            <v-col cols="1" class="d-flex justify-center align-center">
                                <v-img :src="match.teamBLogo" contain class="team-logo">BB</v-img>
                            </v-col>
                            <v-col cols="4" class="text-start">
                                {{ match.teamB }}
                            </v-col>
                        </v-row>
                    </v-card-title>
                    <v-card-text>
                        <div v-if="match.finished">
                            결과: {{ match.winner }} 승리, 최종 스코어: {{ match.score }}
                        </div>
                        <div v-if="playingNow">
                            {{ getMatchStatus(match.startTime) }}
                        </div>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
        <div v-if="filteredMatches.length === 0">
            <v-card class="card-match mt-6">
                예정된 경기가 없습니다!
            </v-card>
        </div>
    </v-container>
</template>
  
<script setup>
import { ref, computed } from 'vue';
import { format, set, parseISO, differenceInHours, isToday, differenceInMinutes, isTomorrow, isSameDay } from 'date-fns';

// selectedDate가 부모 컴포넌트로부터 props로 넘어온다고 가정
const props = defineProps({
    selectedDate: {
        type: Date,
        required: true,
    },
});
const selectedDate = computed(() => {
    return props.selectedDate;
});

console.log(selectedDate.value);

// 예시 데이터
// 원래 오늘 날짜에 해당하는 경기가 있어야함
const now = new Date();
const matches = ref([
    {
        id: 1,
        teamA: 'FC 바르셀로나',
        teamALogo: 'path/to/barcelona-logo.png',
        teamB: '레알 마드리드',
        teamBLogo: 'path/to/real-madrid-logo.png',
        // 현재 날짜에 시간을 설정
        startTime: format(set(now, { hours: 12, minutes: 0 }), 'yyyy-MM-dd HH:mm'),
        league: '라 리가',
        finished: true,
        winner: 'FC 바르셀로나',
        score: '3-1',
    },
    {
        id: 2,
        teamA: 'AC 밀란',
        teamALogo: 'path/to/ac-milan-logo.png',
        teamB: '인터 밀란',
        teamBLogo: 'path/to/inter-milan-logo.png',
        // 현재 날짜에 시간을 설정
        startTime: format(set(now, { hours: 20, minutes: 0 }), 'yyyy-MM-dd HH:mm'),
        league: '세리에 A',
        finished: false,
    }
]);
/////
// 경기 데이터 중 선택된 날짜와 동일한 날짜의 경기만 필터링
const filteredMatches = computed(() => {
    return matches.value.filter(match => {
        const matchDate = parseISO(match.startTime);
        return isSameDay(matchDate, selectedDate.value);
    });
});

function getMatchStatus(startTime) {
    const now = new Date();
    const start = parseISO(startTime);
    const diffHours = differenceInHours(start, now);
    const playingNow = ref(false);

    if (isToday(start) || isTomorrow(start)) {
        if (24 > diffHours && diffHours > 0) {
            // 경기까지 남은 시간을 hh:mm 분 남았다고 표시
            const diffMinutes = differenceInMinutes(start, now);
            // 시간과 분으로 변환
            const hoursLeft = Math.floor(diffMinutes / 60);
            const minutesLeft = diffMinutes % 60;
            return `${hoursLeft}시간 ${minutesLeft}분 남았습니다`;
        } else if (diffHours <= 0 && diffHours > -2) {
            // 진행 중인 경우 현재 스코어 표시 필요
            playingNow.value = true;
            return "진행 중";
        } else {
            return "경기 종료";
        }
    } else {
        if (diffHours > 0) {
            return "예정";
        } else {
            return "경기 종료";
        }
    }
}
</script>
  
<style scoped>
.list-match {
    background-color: beige;
    overflow-x: auto;
    /* 내용이 넘칠 경우 수평 스크롤 가능 */
    white-space: nowrap;
    /* 내용을 한 줄로 유지 */
    min-width: max-content;
    /* 컨테이너의 최소 너비를 내용에 맞게 조정 */
}

.team-logo {
    width: 50px;
    height: 50px;
    min-width: 50px;
}

.card-match {
    white-space: nowrap;
    border-color: black;
    border-radius: 8px;
    padding: 16px;
    display: inline-block;
    /* 카드를 인라인 블록 요소로 설정 */
    white-space: normal;
    /* 카드 내부의 텍스트는 정상적으로 줄바꿈 */
    width: 100%;
    /* 카드의 너비를 고정하지 않고 유연하게 조정 */
    min-width: 300px;
    /* 카드의 최소 너비 설정 */
    margin-right: 16px;
    /* 카드 간의 간격 조정 */
}

.card-content {
    white-space: nowrap;
}
</style>
  