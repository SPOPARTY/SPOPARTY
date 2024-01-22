<template>
     <v-container fluid class="pa-2 mt-3">
          <v-row align="center" justify="center" class="pb-1 date-section">
               <v-col cols="12" class="text-center">
                    <v-btn icon @click="changeDate(-1)" class="mx-12" :style="{ fontSize: '1.5rem' }">
                         <v-icon size="large">mdi-chevron-left</v-icon>
                    </v-btn>
                    <v-btn v-for="day in displayedDates" :key="day.date" :class="dateClass(day.date)"
                         @click="selectDate(day.date)" :style="{ fontSize: '1.5rem', minWidth: '120px' }">
                         <div class="d-day" v-if="day.dDay">{{ day.dDay }}</div>
                         <div>{{ day.display }}</div>
                    </v-btn>
                    <v-btn icon @click="changeDate(1)" class="mx-12" :style="{ fontSize: '1.5rem' }">
                         <v-icon size="large">mdi-chevron-right</v-icon>
                    </v-btn>
               </v-col>
               <v-col cols="12" class="text-center">
                    <v-btn @click="resetDate" class="mx-2">오늘</v-btn>
                    <v-menu v-model="showDatePicker" :close-on-content-click="true" :nudge-bottom="10" offset-y
                         attach=".date-picker-button">
                         <template v-slot:activator="{ on, attrs }">
                              <v-btn class="date-picker-button mx-2" v-bind="attrs" v-on="on"
                                   @click="showDatePicker = !showDatePicker">날짜 선택</v-btn>
                         </template>
                         <v-date-picker v-model="selectedDate" no-title @input="datePickerSelected"></v-date-picker>
                    </v-menu>
               </v-col>
          </v-row>
          <v-row class="mt-6 match-lists">
               <v-col cols="12" class="text-center">
                    <h2>경기목록</h2>
               </v-col>
               <MatchList :selected-date="selectedDate" />
          </v-row>
     </v-container>
</template>
   
   
<script setup>
import { ref, reactive, computed } from 'vue';
import MatchList from '@/components/match/MatchList.vue';
import { format, subDays, addDays } from 'date-fns';
import { differenceInCalendarDays } from 'date-fns';

const selectedDate = ref(new Date());
const showDatePicker = ref(false);

// 날짜 표시를 위한 계산된 속성
const displayedDates = computed(() => {
     let dates = [];
     const today = new Date();
     for (let i = -2; i <= 2; i++) {
          const date = addDays(selectedDate.value, i);
          const diff = differenceInCalendarDays(date, today);
          let dDay;
          if (diff === 0) {
               dDay = '오늘';
          } else if (diff > 0) {
               dDay = `D-${diff}`;
          } else {
               dDay = `D+${Math.abs(diff)}`;
          }
          dates.push({ date, display: format(date, 'MM/dd'), dDay });
     }
     return dates;
});


function changeDate(direction) {
     selectedDate.value = addDays(selectedDate.value, direction);
}

function dateClass(date) {
     const today = format(new Date(), 'yyyy-MM-dd');
     const isSelected = format(date, 'yyyy-MM-dd') === format(selectedDate.value, 'yyyy-MM-dd');
     return {
          'red--text': format(date, 'yyyy-MM-dd') === today,
          'blue-border': isSelected,
     };
}

function selectDate(date) {
     selectedDate.value = date;
}

function resetDate() {
     selectedDate.value = new Date();
}

function datePickerSelected() {
     showDatePicker.value = false;
}
</script>
   
<style scoped>
.date-section {
     background-color: #E0E0E0;
     /* 날짜 관련 부분의 백그라운드 컬러 */
     white-space: nowrap;
}

.d-day {
     font-size: 0.75rem;
     /* 작은 글씨 크기 */
     color: violet;
     /* 옅은 보라 색 */
     margin-bottom: 4px;
     /* 날짜와의 간격 */
}

.red--text {
     color: red !important;
}

.blue-border {
     border: 2px solid blue !important;
     border-radius: 4px;
}
</style>

   