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
                              <!-- <v-card >
                                   <v-card-text> -->
                                        <v-text-field v-model="titleModel" class="select-field" 
                                             :clearable="isTitleEditing" 
                                             variant="outlined" 
                                             :hint="!isTitleEditing ? 'Click the icon to EDIT' : 'Click the icon to SAVE'"
                                             :readonly="!isTitleEditing" persistent-hint hide-details="auto"
                                             :label="`타이틀  — ${isTitleEditing ? 'Editable' : 'Readonly'}`">
                                             <template v-slot:append>
                                                  <v-slide-x-reverse-transition mode="out-in">
                                                       <v-icon :key="`icon-${isTitleEditing}`"
                                                            :color="isTitleEditing ? 'info' : 'success'"
                                                            :icon="isTitleEditing ? 'mdi-check-outline' : 'mdi-circle-edit-outline'"
                                                            @click="isTitleEditing = !isTitleEditing"></v-icon>
                                                  </v-slide-x-reverse-transition>
                                             </template>
                                        </v-text-field>
                                   <!-- </v-card-text>
                              </v-card> -->
                         </v-col>
                         <!-- 경기 선택 -->
                         <v-col cols="6" class="match pa-2">
                              <!-- <v-card class="select-field">
                                   <v-card-text> -->
                                        <!-- <div class="text-caption pa-3">시청 중인 경기를 선택하세요</div> -->
                                        <!-- {{ matchModel }} -->
                                        <v-autocomplete v-model="matchModel" class="select-field"
                                             :hint="!isMatchEditing ? 'Click the icon to EDIT' : 'Click the icon to SAVE'"
                                             :items="matches" :item-title="getMatchTitle" item-value="fixtureId"
                                             :readonly="!isMatchEditing" :clearable="isTitleEditing"
                                             :label="`경기  — ${isMatchEditing ? 'Editable' : 'Readonly'}`" auto-select-first
                                             variant="outlined" persistent-hint prepend-icon="mdi-soccer"
                                             @update:menu="onMatchChange">
                                             <template v-slot:append>
                                                  <v-slide-x-reverse-transition mode="out-in">
                                                       <v-icon :key="`icon-${isMatchEditing}`"
                                                            :color="isMatchEditing ? 'info' : 'success'"
                                                            :icon="isMatchEditing ? 'mdi-check-outline' : 'mdi-circle-edit-outline'"
                                                            @click="isMatchEditing = !isMatchEditing"></v-icon>
                                                  </v-slide-x-reverse-transition>
                                             </template>
                                        </v-autocomplete>
                                   <!-- </v-card-text>
                              </v-card> -->
                         </v-col>
                    </v-row>
               </v-col>
               <v-col class="chatting-section" cols="3">
                    <!-- 여기가 유저들 캠 화면 오는 영역 -->
                    <v-row class="cam-section">
                         <v-col cols="6" class="cam-video">
                         <UserVideo
                                   :stream-manager="publisher"
                                   @click.native="updateMainVideoStreamManager(publisher)" />
                         </v-col>
                         <v-col cols="6" class="cam-video" v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
                              
                              <UserVideo 
                                   :stream-manager="sub"
                                   />
                         </v-col>
                         <!-- 파티 초대 -->
                         <v-col cols="6" class="cam-video" v-if="partyMembers.length < maxMembers" @click="inviteToParty"
                              style="cursor: pointer">
                              <v-icon class="invite-icon">mdi-human-greeting</v-icon>
                              <span>파티 초대</span>
                         </v-col>
                         <!-- 채팅창 -->
                         <v-col cols="12" v-if="showChat" class="chat-window" :style="{ height: chatDivHeight }">
                              <div class="chat-content">
                                   <!-- 채팅 내용을 여기에 표시 -->
                                   채팅 내용이 여기에 표시됩니다.
                                   <Chat/>
                              </div>
                         </v-col>
                         <v-spacer></v-spacer>
                    </v-row>
                    <!-- 캠 영역 끝 -->

                    <v-spacer></v-spacer>
                    <!-- 버튼 영역 -->
                    <v-row class="button-section">
                         <v-col cols="3">
                              <v-btn color="secondary" @click="dpi">
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
          <!-- <v-row>
               <v-col cols="12">
                    <Chat></Chat>
               </v-col>
          </v-row> -->
          <v-row class="contents-section pa-3">
               <v-col cols="12">
                    <PartyMatch />
               </v-col>
          </v-row>
     </v-container>
</template>
 
<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue'
import { useRoute, useRouter } from 'vue-router';
import { format, set, parseISO, addDays } from 'date-fns';

import PartyMatch from '@/components/party/PartyMatch.vue'
import Chat from '../components/party/Chat.vue';

import { useFootballStore } from '@/stores/football/football'
import { usePartyStore } from '@/stores/club/party/party'

import { OpenVidu } from 'openvidu-browser'
import UserVideo from '../components/openvidu/uservideo.vue'

const route = useRoute()
const router = useRouter()

const footballStore = useFootballStore()
const partyStore = usePartyStore()

const { getMatchWatchable, findTeamIdsByFixtureId } = footballStore
const { getPartyMemberList, putPartyInfo, deletePartyInfo, postPartyMember, 
     getPartyParticipant, deletePartyMember } = partyStore


//// 파티 정보 수정 로직
// 파티 입장 및 퇴장
const clubId = route.params.clubId;
const partyId = route.params.partyId;
const partyMemberList = ref(partyStore.partyMemberList);

watch(() => partyStore.partyMemberList, (newPartyMembers) => {
     partyMemberList.value = newPartyMembers;
     partyMemberList.value.map((member) => {
          console.log(member)
          console.log(sessionStorage.getItem("id"))
          if (member.memberId == sessionStorage.getItem("id")) {
               joinSession(member.openviduToken, member.nickName)
          }
     })
}, { immediate: true, deep: true });

onMounted(() => {
     // const clubId = route.params.clubId;
     // const partyId = route.params.partyId;
     console.log("onMounted",clubId, partyId);
     console.log(getPartyMemberList(clubId, partyId));
     postPartyMember(clubId, partyId);
})

onUnmounted(() => {
     // const clubId = route.params.clubId;
     // const partyId = route.params.partyId;
     deletePartyMember(clubId, partyId);
})

const dpm = () => {
     console.log("dpm", partyMemberList.value);
     deletePartyMember(clubId, partyId);
}

const dpi = () => {
     console.log("dpi", partyMemberList.value);
     deletePartyInfo(clubId, partyId);
}

console.log(clubId, partyId);
console.log(getPartyMemberList(clubId, partyId));

// 채팅창 표시 여부를 제어할 상태 변수
const showChat = ref(false);

// 채팅창 토글 함수
function toggleChat() {
     showChat.value = !showChat.value;
}

// 채팅창 높이 동적 설정
const chatDivHeight = ref('300px'); // 초기값 설정

const updatechatDivHeight = () => {
  const chattingSection = document.querySelector('.chatting-section');
  const buttonSection = document.querySelector('.button-section');
  
  if (chattingSection && buttonSection) {
    // 버튼 섹션을 제외한 높이 계산
    const height = chattingSection.offsetHeight - 2*buttonSection.offsetHeight;
    chatDivHeight.value = `${height}px`;
  }
};

// 0.2초 후에 채팅창 높이를 다시 계산
setTimeout(() => {
     updatechatDivHeight();
}, 200);

watch(chatDivHeight, (newVal) => {
     console.log('chatDivHeight changed', newVal);
});

onMounted(() => {
     updatechatDivHeight();
     window.addEventListener('resize', updatechatDivHeight);
});

onUnmounted(() => {
     window.removeEventListener('resize', updatechatDivHeight);
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


const OV = ref(undefined)
const session = ref(undefined)
// const mainStreamManager = ref(undefined) // 우리는 main Streamer가 없음.
const publisher = ref(undefined)
const subscribers = ref([])

const videoState = ref(true)
const audioState = ref(true)

const toggleVideoState = () => {
     console.log("call toggle video state")
     videoState.value = !videoState.value
     publisher.value.publishVideo(videoState.value);
}

const toggleAudioState = () => {
     console.log("call toggle audio state")
     audioState.value = !audioState.value
     publisher.value.publishAudio(audioState.value)
}


const joinSession = (openviduToken, nickName) => {
  OV.value = new OpenVidu()
  // --- 2) Init a session ---
  session.value = OV.value.initSession()

  // --- 3) Specify the actions when events take place in the session ---

  // On every new Stream received...
  session.value.on('streamCreated', ({ stream }) => {
    const subscriber = session.value.subscribe(stream)
    subscribers.value.push(subscriber) 
    console.log("subscriber added")
    console.log(subscribers.value)
  })

  // On every Stream destroyed...
  session.value.on('streamDestroyed', ({ stream }) => {
     console.log("subscriber removed")
    const index = subscribers.value.indexOf(stream.streamManager, 0)
    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
  })

  // On every asynchronous exception...
  session.value.on('exception', ({ exception }) => {
     console.log("exception occured")
    console.warn(exception)
  })

  session.value.on('signal', (event) => {
    console.log(event.data) // Message
    console.log(event.from) // Connection object of the sender
    console.log(event.type) // The type of message
  })

  // --- 4) Connect to the session with a valid user token ---

  // Get a token from the OpenVidu deployment
    // First param is the token. Second param can be retrieved by every user on event
    // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
    session.value
      .connect(openviduToken, { clientData: nickName })
      .then(() => {
        // --- 5) Get your own camera stream with the desired properties ---
          
        // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
        // element: we will manage it on our own) and with the desired properties
        let initPublisher = OV.value.initPublisher(undefined, {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: undefined, // The source of video. If undefined default webcam, screen 선택 시 화면 공유 가능
          publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: true, // Whether you want to start publishing with your video enabled or not
          resolution: '640x480', // The resolution of your video
          frameRate: 30, // The frame rate of your video
          insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
          mirror: true, // Whether to mirror your local video or not
        })

        // Set the main video in the page to display our webcam and store our Publisher
     //    mainStreamManager.value = initPublisher
        publisher.value = initPublisher

        // --- 6) Publish your stream ---

        session.value.publish(publisher.value)
      })
      .catch((error) => {
        console.log(
          'There was an error connecting to the session:',
          error.code,
          error.message,
        )
      })
  
  window.addEventListener('beforeunload', leaveSession)
}

const leaveSession = () => {
  // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
  if (session.value) session.value.disconnect()

  // Empty all properties...
  session.value = undefined
//   mainStreamManager.value = undefined
  publisher.value = undefined
  subscribers.value = []
  OV.value = undefined

  // Remove beforeunload listener
  window.removeEventListener('beforeunload', leaveSession)
}

</script>

<style>
.party-section {
     /* background-color: #333D51; */
     color: white;
     margin-top: 0px;
     min-width: 1280px;
}

.match-section {
     /* background-color: #333D51; */
     min-width: 800px;
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
     margin-top: 10px;
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
     background-color: lightgrey;
     min-height: 400px;
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
     border : 1px solid #CBD0D8;
}
.cam-section {
     height: 65%;
     /* justify-self: start; */
     align-content: start;
}

.cam-video {
     background-color: blueviolet;
     border: 1px solid white;
     min-height: 100px;
     height: 30%;
     /* height: 300px; */
     text-align: center;
     /* 중앙 정렬 */
     display: flex;
     flex-direction: column;
     justify-content: center;
     align-items: center;
     /* aspect-ratio: 16/16; */
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
.select-field > .v-card-text {
     color: #333D51;
     height: 90px;
     padding: 0;
}

</style> 