<template>
     <v-container fluid>
          <v-row align="center" justify="center" class="pb-1 mb-2 contents-box title-section">
               <v-col cols="12" class="text-center">
                    <v-btn icon @click="changeDate(-1)" class="mx-12" :style="{ fontSize: '1.5rem' }">
                         <v-icon size="large">mdi-chevron-left</v-icon>
                    </v-btn>
                    <v-btn v-for="day in displayedDates" :key="day.date" :class="dateClass(day.date)" class="ma-1"
                         @click="selectDate(day.date)" :style="{ fontSize: '1.5rem', minWidth: '120px', minHeight: '45px'}">
                         <div class="d-day" v-if="day.dDay">{{ day.dDay }}</div>
                         <div>{{ day.display }}</div>
                    </v-btn>
                    <v-btn icon @click="changeDate(1)" class="mx-12" :style="{ fontSize: '1.5rem' }">
                         <v-icon size="large">mdi-chevron-right</v-icon>
                    </v-btn>
               </v-col>
               <v-col cols="12" class="text-center">
                    <v-btn @click="resetDate" class="mx-2" 
                    :style="{ fontSize: '1.2rem', minWidth: '100px', minHeight: '40px'}">오늘</v-btn>
                    <v-menu v-model="showDatePicker" :close-on-content-click="false" :nudge-bottom="10" offset-y
                         attach=".date-picker-button">
                         <template v-slot:activator="{ on, attrs }">
                              <v-btn class="date-picker-button mx-2" v-bind="attrs" v-on="on" @mousedown="showDatePicker = true" 
                              :style="{ fontSize: '1.2rem', minWidth: '100px', minHeight: '40px'}">날짜 선택</v-btn>
                         </template>
                         <v-date-picker v-model="selectedDate" show-adjacent-months no-title></v-date-picker>
                    </v-menu>
               </v-col>
          </v-row>
          <v-row class="mt-6 mx-12 match-lists">
               <MatchList :selected-date="selectedDate" />
          </v-row>
     </v-container>
</template>
   
   
<script setup>
import { ref, computed, watch } from 'vue';
import MatchList from '@/components/match/MatchList.vue';
import { format, addDays, differenceInCalendarDays } from 'date-fns';

const selectedDate = ref(new Date());
const showDatePicker = ref(false);

watch(showDatePicker, () => {
     console.log(showDatePicker.value);
});

watch(selectedDate, () => {
     console.log("날짜변경")
     console.log(selectedDate.value);
     showDatePicker.value = false;
});

// console.log(selectedDate.value);

// console.log(selectedDate.value);
// "2024-01-31T05:46:09.320Z"

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
          'sel-height': isSelected,
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
.title-section {
     /* background-color: #292646; */
     /* 날짜 관련 부분의 백그라운드 컬러 */
     white-space: nowrap;
}

.d-day {
     font-size: 0.8rem;
     /* 작은 글씨 크기 */
     color: plum;
     /* 옅은 보라 색 */
     margin-bottom: 4px;
     /* 날짜와의 간격 */
}

.red--text {
     color: tomato !important;
}

.blue-border {
     border: 4px solid #D3AC2B !important;
     border-radius: 8px;
}

.sel-height {
     height: 55px;
     /* 오늘 날짜의 높이 */
}
</style>

   