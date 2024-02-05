<template>
     <v-container fluid>
          <!-- 기존 비디오 및 채팅 섹션 -->
          <v-row class="party-section mb-1">
               <v-col class="match-section" cols="9">
                    <v-row class="match-video">
                         <v-img src="/soccer-screen.png" aspect-ratio="16/9" contain></v-img>
                    </v-row>
                    <v-row class="selector">
                         <!-- 파티 타이틀 선택 -->
                         <v-col cols="6" class="party pa-2">
                              <v-card>
                                   <v-card-text>
                                        <v-text-field v-model="titleModel" :clearable="isTitleEditing"
                                             :hint="!isMatchEditing ? 'Click the icon to edit' : 'Click the icon to save'"
                                             :readonly="!isTitleEditing" persistent-hint hide-details="auto"
                                             :label="`타이틀  — ${isMatchEditing ? 'Editable' : 'Readonly'}`">
                                             <template v-slot:append>
                                                  <v-slide-x-reverse-transition mode="out-in">
                                                       <v-icon :key="`icon-${isTitleEditing}`"
                                                            :color="isTitleEditing ? 'success' : 'info'"
                                                            :icon="isTitleEditing ? 'mdi-check-outline' : 'mdi-circle-edit-outline'"
                                                            @click="isTitleEditing = !isTitleEditing"></v-icon>
                                                  </v-slide-x-reverse-transition>
                                             </template>
                                        </v-text-field>
                                   </v-card-text>
                              </v-card>
                         </v-col>
                         <!-- 경기 선택 -->
                         <v-col cols="6" class="match pa-2">
                              <v-card>
                                   <!-- <v-card-title class="text-h5 font-weight-regular bg-blue-grey">
                                   Profile
                              </v-card-title> -->
                                   <v-card-text>
                                        <!-- <div class="text-caption pa-3">시청 중인 경기를 선택하세요</div> -->
                                        <!-- {{ matchModel }} -->
                                        <v-autocomplete v-model="matchModel"
                                             :hint="!isMatchEditing ? 'Click the icon to edit' : 'Click the icon to save'"
                                             :items="matches" :item-title="getMatchTitle" item-value="fixtureId"
                                             :readonly="!isMatchEditing"
                                             :label="`경기  — ${isMatchEditing ? 'Editable' : 'Readonly'}`" auto-select-first
                                             clearable variant="outlined" persistent-hint prepend-icon="mdi-soccer"
                                             @update:menu="onMatchChange">
                                             <template v-slot:append>
                                                  <v-slide-x-reverse-transition mode="out-in">
                                                       <v-icon :key="`icon-${isMatchEditing}`"
                                                            :color="isMatchEditing ? 'success' : 'info'"
                                                            :icon="isMatchEditing ? 'mdi-check-outline' : 'mdi-circle-edit-outline'"
                                                            @click="isMatchEditing = !isMatchEditing"></v-icon>
                                                  </v-slide-x-reverse-transition>
                                             </template>
                                        </v-autocomplete>
                                   </v-card-text>
                              </v-card>
                         </v-col>
                    </v-row>
               </v-col>
               <v-col class="chatting-section" cols="3">
                    <!-- 여기가 유저들 캠 화면 오는 영역 -->
                    <v-row>
                         <v-col cols="6" class="cam-video" v-for="member in partyMembers" :key="member.memberId">
                              {{ member.name }}
                              <!-- 여기에 캠 화면 또는 이미지 배치 -->
                         </v-col>
                         <!-- 파티 초대 -->
                         <v-col cols="6" class="cam-video" v-if="partyMembers.length < maxMembers" @click="inviteToParty"
                              style="cursor: pointer">
                              <v-icon class="invite-icon">mdi-human-greeting</v-icon>
                              <span>파티 초대</span>
                         </v-col>
                         <!-- 채팅창 -->
                         <v-col cols="12" v-if="showChat" class="chat-window" :style="{ height: camVideoHeight }">
                              <div class="chat-content">
                                   <!-- 채팅 내용을 여기에 표시 -->
                                   채팅 내용이 여기에 표시됩니다.
                              </div>
                         </v-col>
                    </v-row>
                    <!-- 캠 영역 끝 -->

                    <v-spacer></v-spacer>
                    <!-- 버튼 영역 -->
                    <v-row class="button-section">
                         <v-col cols="3">
                              <v-btn color="secondary">
                                   <v-icon size="x-large">mdi-camera-outline</v-icon>
                              </v-btn>
                         </v-col>
                         <v-col cols="3">
                              <v-btn color="secondary">
                                   <v-icon size="x-large">mdi-video-plus-outline</v-icon>
                              </v-btn>
                         </v-col>
                         <v-col cols="3">
                              <v-btn color="#D3AC2B">
                                   <v-icon size="x-large" color="#333D51">mdi-bullhorn-outline</v-icon>
                              </v-btn>
                         </v-col>
                         <v-col cols="3">
                              <v-btn color="#CBD0D8">
                                   투표
                              </v-btn>
                         </v-col>
                         <!-- <v-col cols="2">
                              <v-btn color="yellow" class="chat-button">
                                   <v-icon color="#08042B">mdi-chat</v-icon>
                              </v-btn>
                         </v-col> -->
                    </v-row>
                    <!-- 다음 줄 -->
                    <v-row class="button-section">
                         <v-col cols="9">
                              <v-btn @click="toggleChat" color="yellow" class="chat-button">채팅창</v-btn>
                         </v-col>
                         <v-col cols="3">
                              <v-btn color="error" @click="closeTab">파티 나가기</v-btn>
                         </v-col>
                    </v-row>
               </v-col>
          </v-row>
          <!-- 경기정보영역 -->
          <v-row>
               <v-col cols="12">
                    <Chat></Chat>
               </v-col>
          </v-row>
          <v-row class="contents-section pa-3">
               <v-col cols="12">
                    <PartyMatch />
               </v-col>
          </v-row>
     </v-container>
</template>
 
<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { format, set, parseISO, addDays } from 'date-fns';

import PartyMatch from '@/components/party/PartyMatch.vue'
import Chat from '../components/party/Chat.vue';

import { useFootballStore } from '@/stores/football/football'

const footballStore = useFootballStore()

const { getMatchWatchable, findTeamIdsByFixtureId } = footballStore

// 채팅창 표시 여부를 제어할 상태 변수
const showChat = ref(false);

// 채팅창 토글 함수
function toggleChat() {
     showChat.value = !showChat.value;
}

// 채팅창 높이 동적 설정
const camVideoHeight = ref('450px'); // 초기값 설정

const updateCamVideoHeight = () => {
  const chattingSection = document.querySelector('.chatting-section');
  const buttonSection = document.querySelector('.button-section');
  
  if (chattingSection && buttonSection) {
    // 버튼 섹션을 제외한 높이 계산
    const height = chattingSection.offsetHeight - 2*buttonSection.offsetHeight;
    camVideoHeight.value = `${height}px`;
  }
};

// 0.2초 후에 채팅창 높이를 다시 계산
setTimeout(() => {
     updateCamVideoHeight();
}, 200);

watch(camVideoHeight, (newVal) => {
     console.log('camVideoHeight changed', newVal);
});

onMounted(() => {
     updateCamVideoHeight();
     window.addEventListener('resize', updateCamVideoHeight);
});

onUnmounted(() => {
     window.removeEventListener('resize', updateCamVideoHeight);
});


// 임시 경기 id
const fixtureId = ref(null);

// 파티 최대 인원 수
const maxMembers = 6

const partyMembers = ref([
     { memberId: 1, name: "실버스타" },
     { memberId: 2, name: "제라드" },
     { memberId: 3, name: "벨타이거" },
     { memberId: 4, name: "램파드" },
     { memberId: 5, name: "별명별명" },
])

// 파티 초대
const inviteToParty = () => {
     alert("파티 초대하기");
}

// 파티 나가기
const closeTab = () => {
     // 사용자에게 확인을 요청하는 대화상자 표시
     if (confirm("파티를 나가시겠습니까?")) {
          window.close(); // 사용자가 '예'를 선택한 경우 탭 닫기
     }
     // '아니오'를 선택한 경우 아무 동작도 하지 않음
}


// let partyTitle = ref('파티 타이틀');
// let matchName = ref('경기 이름');

// 파티 정보 수정
const isTitleEditing = ref(false);
const titleModel = ref(null);


// const editParty = () => {
//      const newPartyTitle = prompt('새 파티 타이틀을 입력하세요', partyTitle.value);
//      if (newPartyTitle !== null) partyTitle.value = newPartyTitle;
// }

// 경기 정보 수정
const isMatchEditing = ref(false);
const matchModel = ref(null);
const matches = ref([]);

watch(() => footballStore.matchWatchable, (newVal) => {
     console.log(newVal);
     matches.value = newVal;
}, { immediate: true }), { deep: true };

// 시청 가능 경기 목록
const today = ref(new Date());
const startDate = ref(format(addDays(today.value, -1), 'yyyy-MM-dd'));
const endDate = ref(format(addDays(today.value, 7), 'yyyy-MM-dd'));

// console.log(today.value)
// console.log(format(addDays(today.value, -1), 'yyyy-MM-dd'))
// console.log(format(addDays(today.value, 7), 'yyyy-MM-dd'))
const getMatchTitle = (item) => {
     const status = ref("");
     if (item.status === "not start") {
          status.value = "예정";
     } else {
          status.value = "경기 중";
     }
     return `${item.league.nameKr} ${item.round} / ${item.homeTeam.nameKr} vs ${item.awayTeam.nameKr} / ${status.value}`;
};

const teamIds = ref({ home: null, homeName: null, away: null, awayName: null });
const onMatchChange = () => {
     // console.log("onMatchChange")
     fixtureId.value = matchModel.value;
     footballStore.fixtureIdForParty = matchModel.value;
     if (matchModel.value !== null) {
          teamIds.value = findTeamIdsByFixtureId(matchModel.value);
          console.log(teamIds.value);
     }
}

getMatchWatchable(startDate.value, endDate.value);

// matches
// {
//     "fixtureId": 15,
//     "startTime": "2024-02-02T08:00:00",
//     "round": "5차전",
//     "status": "not start",
//     "homeTeamGoal": 0,
//     "awayTeamGoal": 0,
//     "league": {
//         "leagueId": 1,
//         "nameKr": "챔피언십",
//         "logo": "https://media.api-sports.io/football/leagues/40.png"
//     },
//     "homeTeam": {
//         "seasonLeagueTeamId": 4,
//         "teamId": 4,
//         "nameKr": "멍뭉",
//         "nameEng": "cccc",
//         "logo": "https://source.unsplash.com/random/300x300?emblem"
//     },
//     "awayTeam": {
//         "seasonLeagueTeamId": 1,
//         "teamId": 1,
//         "nameKr": "마루쉐",
//         "nameEng": "maroche",
//         "logo": "https://i1.sndcdn.com/avatars-000953353822-6fbf5r-t240x240.jpg"
//     }
// }



// const editMatch = () => {
//      const newMatchName = prompt('새 경기 이름을 입력하세요', matchName.value);
//      if (newMatchName !== null) matchName.value = newMatchName;
// }

</script>

<style>
.party-section {
     /* background-color: #333D51; */
     color: white;
     margin-top: 0px;
     min-width: 1280px;
}

.match-section {
     background-color: grey;
     min-width: 960px;
     /* 최소 너비 유지 */
     width: 60vw;
     /* 화면 너비에 따라 조정 */
     max-width: 70%;
     /* 최대 너비 제한, 필요에 따라 조정 */
     min-height: 540px;
     margin: auto;
     /* 중앙 정렬 */
}

.selector {
     min-height: 45px;
     height: 12vh;

}

.party {
     /* background-color: #333D51; */
     height: 45px;
     font-size: 1.5rem;
}

.match {
     /* background-color: #333D51; */
     padding: auto;
     height: 40px;
     font-size: 1.25rem;
}

.match-video {
     background-color: lightslategray;
     min-height: 500px;
}

.chatting-section {
     position: relative;
     display: flex;
     flex: 0 0 30%;
     flex-direction: column;
     justify-content: space-between;
     /* 콘텐츠를 위아래로 분산시킴 */
     background-color: #333D51;
     min-height: 100%;
     min-width: 320px;
     max-width: 30%;
     width: 30vw;
     /* 채팅 섹션의 높이를 브라우저 창의 높이와 맞춤 */
     /* height: 100vh;  */
}

.cam-video {
     background-color: blueviolet;
     border: 1px solid white;
     min-height: 150px;
     text-align: center;
     /* 중앙 정렬 */
     display: flex;
     flex-direction: column;
     justify-content: center;
     align-items: center;

}

.button-section {
     /* 나머지 콘텐츠 위에 버튼 섹션을 밀어 올림 */
     /* margin-top: auto;  */
     max-height: 60px;
     width: 100%;
     text-align: center;
     /* 버튼을 가운데 정렬 */
     justify-content: space-between;
}

.chat-button {
     background-color: yellow;
     /* width: 150px; */
     /* 채팅창 버튼의 너비 조정 */
}

.chat-window {
     border: 2px solid black;
     position: absolute;
     /* bottom: 0;  */
     /* 채팅창을 하단에 위치시킵니다. */
     left: 0;
     /* 채팅창을 왼쪽에 위치시킵니다. */
     width: 100%;
     /* 채팅창 너비를 chatting-section에 맞춥니다. */
     /* height: 50%; */
     /* 채팅창 높이를 조정합니다. */
     /* min-height: 450px; */
     background-color: #CBD0D8;
     /* 채팅창 배경색 */
     overflow-y: auto;
     /* 내용이 많을 경우 스크롤 */
     /* z-index: 10;  */
     /* 다른 요소 위에 채팅창이 나타나도록 z-index 설정 */
}

.chat-content {
     font-size: 2rem;
     color: #333D51;
}

.invite-icon {
     font-size: 3rem;
     /* 아이콘 크기 조정 */
     margin-bottom: 10px;
     /* 텍스트와의 간격 */
     color: #CBD0D8;
}

.contents-section {
     margin-top: 0px;
     /* min-height: 100%; */
     min-width: 1280px;
     /* color: #121212; */
}

.button-section button {
     width: 400px;
}
</style> 