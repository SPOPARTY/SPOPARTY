<template>
     <v-container fluid class="px-4 py-2">
          <!-- 페널티 생성 시 보여질 화면-->
               <v-slide-x-transition>
                    <v-col v-if="showVoteContent" justify="center" align="center" style="position: absolute;">
                         <div style="font-size: 50px; color: white;">
                         {{ voteContent.penalty }} 페널티를 받아라!!!
                    </div>
                    <div style="font-size: 30px; color: white;">
                         {{ voteContent.description }}
                    </div>
               <v-img src="/penalty-soccer.gif" width="25%" height="25%"></v-img>
          </v-col>
          </v-slide-x-transition>
          <!-- 기존 비디오 및 채팅 섹션 -->
          <v-row class="party-section mb-6">
               <v-col class="match-section" cols="9">
                    <v-row class="match-video">
                         <iframe class="coupang-play" v-if="url" width="100%" height="100%" :src=url allow="autoplay"
                              frameborder="0" allowfullscreen title="쿠팡플레이" overflow:hidden></iframe>
                         <v-img v-else src="/soccer-screen.png" aspect-ratio="16/9" contain></v-img>
                    </v-row>
                    <v-row class="selector">
                         <!-- 파티 타이틀 선택 -->
                         <v-col cols="7" class="party pa-2">
                              <!-- <v-card >
                                   <v-card-text> -->
                              <v-text-field v-model="titleModel" class="select-field" :clearable="isTitleEditing"
                                   variant="outlined"
                                   :hint="!isTitleEditing ? 'Click the icon to EDIT' : 'Click the icon to SAVE'"
                                   :readonly="!isTitleEditing" persistent-hint hide-details="auto"
                                   :label="`타이틀  — ${isTitleEditing ? 'Editable' : 'Readonly'}`">
                                   <template v-slot:append>
                                        <v-slide-x-reverse-transition mode="out-in">
                                             <v-icon size="large" :key="`icon-${isTitleEditing}`"
                                                  :color="isTitleEditing ? 'info' : 'success'"
                                                  :icon="isTitleEditing ? 'mdi-lock-open-variant-outline' : 'mdi-lock-outline'"
                                                  @click="[(isTitleEditing = !isTitleEditing), editPartyInfo(isTitleEditing)]"></v-icon>
                                        </v-slide-x-reverse-transition>
                                   </template>
                              </v-text-field>
                              <!-- </v-card-text>
                              </v-card> -->
                         </v-col>
                         <!-- 경기 선택 -->
                         <v-col cols="5" class="match pa-2">
                              <!-- {{ dialog }} -->
                              <v-btn
                                   @click="[dialog = true, isTitleEditing = true, isMatchEditing = true, isUrlEditing = true]"
                                   block variant="outlined" size="x-large">
                                   {{ matchName.find((item) => item.fixtureId === matchModel)?.text || '경기 선택' }}
                              </v-btn>
                              <v-dialog v-model="dialog" max-width="600">
                                   <v-card>
                                        <v-card-title>항목 선택</v-card-title>
                                        <v-card-text>
                                             <v-autocomplete v-model="matchModel" class="select-field"
                                                  :hint="!isMatchEditing ? 'Click the icon to EDIT' : 'Click the icon to SAVE'"
                                                  :items="matches" :item-title="getMatchTitle" item-value="fixtureId"
                                                  :readonly="!isMatchEditing" :clearable="isTitleEditing"
                                                  :label="`경기  — ${isMatchEditing ? 'Editable' : 'Readonly'}`"
                                                  auto-select-first variant="outlined" persistent-hint
                                                  prepend-icon="mdi-soccer" @update:menu="onMatchChange">
                                                  <template v-slot:append>
                                                       <v-slide-x-reverse-transition mode="out-in">
                                                            <v-icon size="large" :key="`icon-${isMatchEditing}`"
                                                                 :color="isMatchEditing ? 'info' : 'success'"
                                                                 :icon="isMatchEditing ? 'mdi-lock-open-variant-outline' : 'mdi-lock-outline'"
                                                                 @click="[(isMatchEditing = !isMatchEditing), editPartyInfo(isMatchEditing)]"></v-icon>
                                                       </v-slide-x-reverse-transition>
                                                  </template>
                                             </v-autocomplete>
                                             <br>
                                             <v-text-field v-model="urlModel" class="select-field" :clearable="isUrlEditing"
                                                  variant="outlined"
                                                  :hint="!isUrlEditing ? 'Click the icon to EDIT' : 'Click the icon to SAVE'"
                                                  :readonly="!isUrlEditing" persistent-hint hide-details="auto"
                                                  :label="`URL  — ${isUrlEditing ? 'Editable' : 'Readonly'}`">
                                                  <template v-slot:append>
                                                       <v-slide-x-reverse-transition mode="out-in">
                                                            <v-icon size="large" :key="`icon-${isUrlEditing}`"
                                                                 :color="isUrlEditing ? 'info' : 'success'"
                                                                 :icon="isUrlEditing ? 'mdi-lock-open-variant-outline' : 'mdi-lock-outline'"
                                                                 @click="[(isUrlEditing = !isUrlEditing), editPartyInfo(isUrlEditing)]"></v-icon>
                                                       </v-slide-x-reverse-transition>
                                                  </template>
                                             </v-text-field>
                                        </v-card-text>
                                        <v-card-actions>
                                             <v-btn color="blue darken-1" text block
                                                  @click="clickCheck(isUrlEditing)">확인</v-btn>
                                        </v-card-actions>
                                   </v-card>
                              </v-dialog>
                         </v-col>
                    </v-row>
               </v-col>
               <v-col class="chatting-section" cols="3">
                    <!-- 여기가 유저들 캠 화면 오는 영역 -->
                    <v-row class="cam-section" id="cam-section">
                         <v-col cols="6" class="cam-video">
                              <UserVideo :stream-manager="publisher"
                                   @click.native="updateMainVideoStreamManager(publisher)" />
                              <v-menu location="center" activator="parent" open-on-hover close-delay="300">
                                   <v-list>
                                        <v-btn class="ma-2" icon variant="outlined" @click="toggleVideoState">
                                             <v-icon v-if="videoState">mdi-video-box</v-icon>
                                             <v-icon v-else>mdi-video-box-off</v-icon>
                                             <v-tooltip activator="parent" location="center">
                                                  <p v-if="videoState">캠 끄기</p>
                                                  <p v-else>캠 켜기</p>
                                             </v-tooltip>
                                        </v-btn>
                                        <v-btn class="ma-2" icon variant="outlined" @click="toggleAudioState">
                                             <v-icon v-if="audioState">mdi-volume-high</v-icon>
                                             <v-icon v-else>mdi-volume-off</v-icon>
                                             <v-tooltip activator="parent" location="center">
                                                  <p v-if="audioState">마이크 끄기</p>
                                                  <p v-else>마이크 켜기</p>
                                             </v-tooltip>
                                        </v-btn>
                                        <!-- <v-list-item
                                        v-for="n in 3"
                                        :key="n"
                                        :title="'Item ' + n"
                                        >
                                             <v-list-item-title>{{ item.title }}</v-list-item-title>
                                        </v-list-item> -->
                                   </v-list>
                              </v-menu>
                         </v-col>
                         <v-col cols="6" class="cam-video" v-for="sub in subscribers"
                              :key="sub.stream.connection.connectionId">

                              <UserVideo :stream-manager="sub" />
                         </v-col>
                         <!-- 파티 초대 -->
                         <v-col cols="6" class="cam-video" v-if="partyMembers.length < maxMembers" @click="inviteToParty"
                              style="cursor: pointer">
                              <v-img src="/maruche.jpg" class="invite-img pt-2" contain></v-img>
                              <span>친구를 초대해 보세요!</span>
                         </v-col>
                         <!-- 채팅창 -->
                         <v-col cols="12" v-show="showChat" class="chat-window" :style="{ height: chatDivHeight }">
                              <div class="chat-content">
                                   <!-- 채팅 내용을 여기에 표시 -->
                                   <!-- {{ chatDivHeightProp }} -->
                                   <Chat :chat-div-height-prop="chatDivHeightProp" :disconnect-prop="chatDisconnectProp" />
                              </div>
                         </v-col>
                         <v-spacer></v-spacer>
                    </v-row>
                    <!-- 캠 영역 끝 -->

                    <v-spacer></v-spacer>
                    <!-- 버튼 영역 -->
                    <v-row class="button-section">
                         <v-col cols="3">
                              <v-btn color="secondary" @click="captureScreen">
                                   <v-tooltip activator="parent" location="top" theme="dark">
                                        사진
                                   </v-tooltip>
                                   <v-icon size="x-large">mdi-camera-outline</v-icon>
                              </v-btn>
                              <!-- 스크린샷 미리보기 오버레이 -->
                              <v-overlay v-model="overlay" class="over">
                                   <v-card class="overlay-card">
                                        <v-img :src="screenshotUrl" contain class="screenshot-preview"></v-img>
                                        <v-card-actions class="justify-end">
                                             <v-expansion-panels>
                                                  <v-btn color="#D3AC2B" @click="uploadScreenshot" size="x-large">
                                                       <v-tooltip activator="parent" location="top" theme="dark">아카이브에 사진 저장</v-tooltip>
                                                       업로드
                                                  </v-btn>
                                                  <v-btn color="green" @click="downloadScreenshot" size="x-large">
                                                       <v-tooltip activator="parent" location="top" theme="dark">내 PC에 사진 저장</v-tooltip>
                                                       다운로드
                                                  </v-btn>
                                                  <v-btn color="red pt-1" @click="overlay = false" size="large">닫기</v-btn>
                                             </v-expansion-panels>
                                             </v-card-actions>
                                   </v-card>
                              </v-overlay>
                              <!-- 스크린샷 오버레이 끝 -->
                         </v-col>
                         <v-col cols="3">
                              <v-btn color="secondary" @click="doVoteContent" :disabled="recordingLoading">
                                   <v-tooltip activator="parent" location="top" theme="dark">
                                        동영상
                                   </v-tooltip>
                                   <v-icon v-if="!recordingState" size="x-large">mdi-video-plus-outline</v-icon>
                                   <div v-else>
                                        {{ timeStr }}
                                   </div>
                              </v-btn>
                              <!-- 동영상 미리보기 오버레이 -->
                              <v-overlay v-model="videoOverlay" persistent class="over">
                                   <v-card class="overlay-card">
                                        <video :src="recordingFile.url" autoplay loop type="video/mp4"
                                             class="video-preview"></video>
                                        <v-card-actions class="justify-end">
                                             <v-btn color="green" @click="registerArchive" size="x-large">아카이브 하기</v-btn>
                                             <v-btn color="red" @click="cancelArchive" size="large">닫기</v-btn>
                                        </v-card-actions>
                                   </v-card>
                              </v-overlay>
                         </v-col>
                         <v-col cols="3">
                              <v-btn color="#D3AC2B" @click="clickSound">
                                   <v-tooltip activator="parent" location="top" theme="dark">
                                        효과음
                                   </v-tooltip>
                                   <v-icon size="x-large" color="#333D51">mdi-bullhorn-outline</v-icon>
                              </v-btn>
                         </v-col>
                         <v-col cols="3">
                              <v-btn @click="showVote = true" color="#CBD0D8">
                                   투표
                              </v-btn>
                              <VoteList v-if="showVote" @vote-close="showVote = false" />
                         </v-col>
                         <!-- <v-col cols="2">
                              <v-btn color="yellow" class="chat-button">
                                   <v-icon color="#08042B">mdi-chat</v-icon>
                              </v-btn>
                         </v-col> -->
                    </v-row>
                    <!-- 다음 줄 -->
                    <v-row class="button-section">
                         <v-col cols="8">
                              <v-btn @click="toggleChat" color="yellow" class="chat-button">채팅창</v-btn>
                         </v-col>
                         <v-col cols="4">
                              <v-btn color="error" @click="exitParty">파티 나가기</v-btn>
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
import { ref, computed, watch, onMounted, onUnmounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router';
import { format, set, parseISO, addDays } from 'date-fns';
import { httpStatusCode } from "@/util/http-status"

import PartyMatch from '@/components/party/PartyMatch.vue'
import Chat from '../components/party/Chat.vue';
import VoteList from "@/components/vote/VoteList.vue"

import { useFootballStore } from '@/stores/football/football'
import { usePartyStore } from '@/stores/club/party/party'
import { useArchiveStore } from '@/stores/club/archives'
import { useFileStore } from '@/stores/member/file'
import { useVoteStore } from '@/stores/club/party/votes'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from '../components/openvidu/UserVideo.vue'

const route = useRoute()
const router = useRouter()

const footballStore = useFootballStore()
const partyStore = usePartyStore()
const archiveStore = useArchiveStore()
const fileStore = useFileStore()
const voteStore = useVoteStore()

const { getMatchWatchable, findTeamIdsByFixtureId } = footballStore
const { getPartyMemberList, putPartyInfo, deletePartyInfo, postPartyMember,
     getPartyParticipant, deletePartyMember, postStartRecording, postStopRecording } = partyStore
const { createArchive } = archiveStore
const { deleteFile } = fileStore

const url = ref("https://www.youtube.com/embed/IMq_dbhxwAY?si=hgpV4dB_yymFN2Uu");
const isUrlExist = ref(false);

const showVoteContent = ref(false)
const voteContent = ref({})

watch(() => voteStore.currentFinishedVote, (newVal) => {
     const targetUser = ""
     subscribers.value.forEach((subscriber) => {
          const data = subscriber.stream.connection.data
          const memberId = data.substr(5, data.length)
          // 페널티 타입 확인
          const penalty = newVal.penalty.content;
          console.log(newVal.penaltyUsers)
          
          newVal.penaltyUsers.forEach(user => {
               if (user.userId === memberId) {
                    targetUser += user.name + ', '
                    console.log(`페널티 대상자 ID : ${user.userId}, 닉네임 : ${user.name}, 페널티 ${penalty}`)
                    sendPenalty(subscriber, penalty, penalty)
               }
          })
          // memberId와 비교하여 페널티 대상자 선택 및 적용
     })
     if (targetUser == "") {
          voteContent.value.penalty = "아무도 페널티를 받지 않았어요!!"
     } else {
          const target = targetUser.substr(0, targetUser.length-2)
          voteContent.value.penalty = target + " 페널티를 받아라!!"
     }
     doVoteContent()
})

const titleModel = ref(null);

watch(() => partyStore.partyInfo, (newVal) => {
     // console.log(newVal)
     if (newVal?.fixtureUrl != null) {
          url.value = newVal.fixtureUrl;
          isUrlExist.value = true;
     } else {
          url.value = "https://www.youtube.com/embed/IMq_dbhxwAY?si=hgpV4dB_yymFN2Uu";
          isUrlExist.value = false;
     }
     titleModel.value = newVal.title;
     // console.log("isUrlExist", isUrlExist.value);
     // console.log("url", url.value);
}, { immediate: true, deep: true })

//// 파티 정보 수정 로직
// 파티 입장 및 퇴장
const clubId = route.params.clubId;
const partyId = route.params.partyId;
const partyMemberList = ref(getPartyMemberList(clubId, partyId));

// console.log("시작멤버리스트", partyMemberList.value);

const showVote = ref(false);

let isInit = false
watch(() => partyStore.partyMemberList, (newPartyMembers) => {
     // console.log("newPartyMembers", newPartyMembers);
     partyMemberList.value = newPartyMembers;
     partyMemberList.value.map((member) => {
          if (member.memberId == localStorage.getItem("id")) {
               joinSession(member.openviduToken, member.nickName)
          }
     })
}, { immediate: true, deep: true });

const myId = ref(null);

watch(() => partyStore.myParticipantId, (newMyId) => {
     // console.warn("myId changed", newMyId);
     myId.value = newMyId;
}, { immediate: true });


// 사용자가 탭을 나갈 때 실행할 함수
function handleBeforeUnload(event) {
     delPartyMem();
     // 사용자에게 경고 메시지를 띄우기
     // event.returnValue를 설정하면 브라우저가 사용자에게 나가기 전에 확인을 요청합니다.
     event.preventDefault();
     event.returnValue = "정말로 페이지를 나가시겠습니까?";
     return message; // 다른 브라우저에서 필요
}

onMounted(() => {
     // const clubId = route.params.clubId;
     // const partyId = route.params.partyId;
     // console.log("onMounted", clubId, partyId);
     // console.log(getPartyMemberList(clubId, partyId));
     postPartyMember(clubId, partyId);
     window.addEventListener('beforeunload', handleBeforeUnload);
})

onBeforeUnmount(() => {
     // const clubId = route.params.clubId;
     // const partyId = route.params.partyId;
     // deletePartyMember(clubId, partyId, myId.value);
     window.removeEventListener('beforeunload', handleBeforeUnload);
})

const answer = window.confirm('쿠팡 플레이 로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?');
if (answer) {
     const url = "https://www.coupangplay.com/login";
     window.open(url, '_blank');
}

const delPartyMem = () => {
     // console.log("delPartyMem", partyMemberList.value);
     myId.value = partyStore.partyMemberList.find((member) => member.userId === partyStore.myUserId);
     if (myId.value !== undefined) {
          // console.warn("delPartyMem", clubId, partyId, myId.value.participantId);
          deletePartyMember(clubId, partyId, myId.value.participantId);
     }
}

onUnmounted(() => {
     myId.value = partyStore.partyMemberList.find(
          (member) => member.userId === partyStore.myUserId
     ).participantId;
     deletePartyMember(clubId, partyId, myId.value);
})

const clickSound = () => {
     alert("기능 준비 중입니다.")
}

// console.log(clubId, partyId);
// console.log(getPartyMemberList(clubId, partyId));

// 채팅창 표시 여부를 제어할 상태 변수
const showChat = ref(false);

// 채팅창 토글 함수
function toggleChat() {
     showChat.value = !showChat.value;
}

// 채팅창 높이 동적 설정
const chatDivHeight = ref('300px'); // 초기값 설정
const chatDivHeightProp = ref(300); // 초기값 설정

const chatDisconnectProp = ref(false)

const updatechatDivHeight = () => {
     const chattingSection = document.querySelector('.chatting-section');
     const buttonSection = document.querySelector('.button-section');

     if (chattingSection && buttonSection) {
          // 버튼 섹션을 제외한 높이 계산
          const height = chattingSection.offsetHeight - 2 * buttonSection.offsetHeight;
          chatDivHeightProp.value = height;
          chatDivHeight.value = `${height}px`;
     }
};

// 0.2초 후에 채팅창 높이를 다시 계산
setTimeout(() => {
     updatechatDivHeight();
}, 200);

// watch(chatDivHeight, (newVal) => {
//      console.log('chatDivHeight changed', newVal);
// });

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
     alert("친구와 함께 파티를 즐겨보세요!");
}

// 파티 나가기
const exitParty = () => {
     // 사용자에게 확인을 요청하는 대화상자 표시
     if (confirm("파티를 나가시겠습니까?")) {
          chatDisconnectProp.value = true;
          delPartyMem();
          setTimeout(() => {
               // confirm 후 100ms 지나서 클럽 페이지로 이동
               window.location.href = `/club/${clubId}`;
          }, 100);
     }
     // '아니오'를 선택한 경우 아무 동작도 하지 않음
}


function editPartyInfo(isAsk) {
     isEditing.value = isAsk;
     isMatchEditing.value = isAsk;
     isTitleEditing.value = isAsk;
     isUrlEditing.value = isAsk;

     if (!isAsk) {
          // console.log("editPartyInfo", clubId, partyId, titleModel.value, urlModel.value, matchModel.value);
          putPartyInfo(clubId, partyId, titleModel.value, urlModel.value, matchModel.value);

          session.value.signal({
               data: titleModel.value,
               to: [],
               type: 'titleChanged'
          })
          .then(() => {
               console.log('Message successfully sent');
          })
          .catch(error => {
               console.error(error);
          });
          
          session.value.signal({
               data: matchModel.value,
               to: [],
               type: 'matchChanged'
          })
          .then(() => {
               console.log('Message successfully sent');
          })
          .catch(error => {
               console.error(error);
          });
     }
     
     // console.log("editPartyInfo",clubId, partyId, titleModel.value, urlModel.value, matchModel.value);
     // putPartyInfo(clubId, partyId, titleModel.value, urlModel.value, matchModel.value);
}

function clickCheck(isAsk) {
     dialog.value = false;
     if (isAsk) {
          isAsk = false;
          editPartyInfo(isAsk);
     }
}

// 수정 여부 전체 관리
const isEditing = ref(false);

// 파티 정보 수정
const isTitleEditing = ref(false);


// 영상 주소 수정
const isUrlEditing = ref(false);
const urlModel = ref(null);

// 경기 정보 수정
const isMatchEditing = ref(false);
const matchModel = ref(null);

const dialog = ref(false);
const matchName = ref([]);


const matches = ref([]);

watch(() => footballStore.matchWatchable, (newVal) => {
     // console.log(newVal);
     matches.value = newVal;
}, { immediate: true }), { deep: true };

// 시청 가능 경기 목록
const today = ref(new Date());
const startDate = ref(format(addDays(today.value, -1), 'yyyy-MM-dd'));
const endDate = ref(format(addDays(today.value, 1), 'yyyy-MM-dd'));

// console.log(today.value)
// console.log(format(addDays(today.value, -1), 'yyyy-MM-dd'))
// console.log(format(addDays(today.value, 7), 'yyyy-MM-dd'))
const getMatchTitle = (item) => {
     const status = ref("");
     // 소문자화
     const statusEng = item.status.toLowerCase();
     if (statusEng === "not started") {
          status.value = "예정";
     } else if (statusEng === "match finished") {
          status.value = "경기 종료";
     } else if (statusEng === "time to be defined") {
          status.value = "시간 미정";
     } else {
          status.value = "진행중";
     }

     matchName.value.push({
          fixtureId: item.fixtureId,
          text: `${item.homeTeam.nameKr} vs ${item.awayTeam.nameKr}`
          // text:`${item.league.nameKr} ${item.round} / ${item.homeTeam.nameKr} vs ${item.awayTeam.nameKr} / ${status.value}`
     });
     return `${item.league.nameKr} ${item.round} / ${item.homeTeam.nameKr} vs ${item.awayTeam.nameKr} / ${status.value}`;
};

const teamIds = ref({ home: null, homeName: null, away: null, awayName: null });
const onMatchChange = () => {
     // console.log("onMatchChange")
     fixtureId.value = matchModel.value;
     footballStore.fixtureIdForParty = matchModel.value;
     if (matchModel.value !== null) {
          teamIds.value = findTeamIdsByFixtureId(matchModel.value);
          // console.log(teamIds.value);
     }
}

getMatchWatchable(startDate.value, endDate.value);

/////// 스크린샷 캡처 로직 
import html2canvas from 'html2canvas';

const overlay = ref(false);
const screenshotUrl = ref('');
const file = ref(null);

// 스크린샷 캡처 함수
function captureScreen() {
     const element = document.getElementById('cam-section')
     if (element) {
          html2canvas(element, { scale: 1 }).then(canvas => {
               // canvas를 이미지 URL로 변환
               screenshotUrl.value = canvas.toDataURL('image/png');
               overlay.value = true;
               canvas.toBlob(blob => {
                    file.value = new File([blob], "screenshot.png", { type: "image/png" });
                    
                    // 이제 'file'을 사용하여 웹에서 다루거나 서버로 업로드할 수 있습니다.
                    // uploadFile(file);
               }, 'image/png');
          });
     }
}

// 다운로드 함수
function downloadScreenshot() {
     const a = document.createElement('a');
     a.href = screenshotUrl.value;
     a.download = 'screenshot.png';
     document.body.appendChild(a);
     a.click();
     document.body.removeChild(a);
}

// 업로드 함수
async function uploadScreenshot() {
     // console.log("##########", file.value)
     const formData = new FormData();
     formData.append('files', file.value);
     console.log("#######", formData.get('file'));
     const imgData = await fileStore.uploadFile(formData);
     console.log(imgData.data.data);
     console.log(imgData.data.data[0].id);
     createArchive(
          {
               memberId: localStorage.getItem("id"),
               clubId: clubId,
               partyTitle: titleModel.value ? titleModel.value : "",
               fixtureTitle: matchModel.value ? matchModel.value : "",
               fileId: null,
               thumbnailId: imgData.data.data[0].id,
          }
     )
}


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
     // console.log("joinSession Called!!!!!!!!!!!!!!!!!!!")
     // console.log(openviduToken)
     // console.log(nickName)
     if (!isInit) {
          isInit = true
          OV.value = new OpenVidu()
          // --- 2) Init a session ---
          session.value = OV.value.initSession()

          // --- 3) Specify the actions when events take place in the session ---

          // On every new Stream received...
          session.value.on('streamCreated', ({ stream }) => {
               const subscriber = session.value.subscribe(stream)
               subscribers.value.push(subscriber)
               console.log("subscriber added")
               // console.log(subscribers.value)
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
               if (event.type == "titleChanged") {
                    titleModel.value = event.data
               } else if (event.type == "matchChanged") {
                    // console.log(matchModel.value)
                    matchModel.value = event.data
               }
               // console.log(event.data) // Message
               // console.log(event.from) // Connection object of the sender
               // console.log(event.type) // The type of message
          })

          // --- 4) Connect to the session with a valid user token ---

          // Get a token from the OpenVidu deployment
          // First param is the token. Second param can be retrieved by every user on event
          // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
          // console.log(session.value)
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

const videoOverlay = ref(false);

const recordingSession = ref({})
const recordingFile = ref({})

const recordingState = ref(false)
const recordingLoading = ref(false)
const recordingTime = ref(0)

let intervalId = undefined
const timeStr = ref("00:00")
let min, sec

const toggleRecording = () => {
     if (!recordingState.value) {
          console.log("start recording call")
          startRecording()
     }
     else {
          console.log("stop recording call")
          stopRecording()
     }
     recordingState.value = !recordingState.value
}

const startRecording = () => {
     if (clubId !== undefined) {
          recordingLoading.value = true
          postStartRecording(
               clubId,
               {
                    "session": clubId,
                    "outputMode": "COMPOSED",
                    "hasAudio": true,
                    "hasVideo": true
               },
               (res) => {
                    if (res.status === httpStatusCode.OK) {
                         // console.log(res.data.data)
                         recordingSession.value = res.data.data
                         recordingLoading.value = false
                         intervalId = setInterval(() => {
                              recordingTime.value++
                              timeStr.value = getTimeFormatString()
                              if (recordingTime.value >= 60) {
                                   recordingTime.value = 0
                                   recordingState.value = !recordingState.value
                                   timeStr.value = "00:00"
                                   clearInterval(intervalId)
                              }
                         }, 1000)
                    }
               },
               (error) => {
                    console.error(error)
                    if (error.response.status === httpStatusCode.NOTFOUND) {
                         // console.error(error)
                    }
               }
          )
     }
}

const stopRecording = () => {
     if (clubId !== undefined && recordingSession.value.id !== undefined) {
          recordingLoading.value = true
          console.log("stop recording")
          postStopRecording(
               clubId,
               {
                    "recording": recordingSession.value.id
               },
               (res) => {
                    // console.log(res)
                    if (res.status === httpStatusCode.OK) {
                         recordingFile.value = res.data.data
                         // console.log(res.data.data)
                         recordingLoading.value = false
                         clearInterval(intervalId)
                         recordingTime.value = 0
                         timeStr.value = "00:00"
                         downloadFile(recordingFile.value.url)
                         recordingSession.value = {}
                         videoOverlay.value = true
                    }
               },
               (error) => {
                    console.error(error)
                    if (error.response.status === httpStatusCode.NOTFOUND) {
                         // console.error(error)
                    }
               },
          )
     }
}

const downloadFile = async (url) => {
     const res = await fetch(url)
     const blob = await res.blob()

     const downloadUrl = window.URL.createObjectURL(blob)

     const link = document.createElement('a')
     link.href = downloadUrl
     link.download = 'video.mp4'
     link.click()
}

const getTimeFormatString = () => {
     min = parseInt(String(recordingTime.value / 60));
     sec = recordingTime.value % 60;

     return String(min).padStart(2, '0') + ":" + String(sec).padStart(2, '0');
}

const registerArchive = () => {
     createArchive(
          {
               memberId: localStorage.getItem("id"),
               clubId: clubId,
               partyTitle: titleModel.value ? titleModel.value : "",
               fixtureTitle: matchModel.value ? matchModel.value : "",
               fileId: recordingFile.value.id,
               thumbnailId: recordingFile.value.thumbnailId
          }
     )
     videoOverlay.value = false
}

const cancelArchive = () => {
     videoOverlay.value = false
     deleteFile(recordingFile.value.id)
}

const doVoteContent = () => {
     voteContent.value.penalty = "테스트"
     voteContent.value.description = "테스트 입니다!!!"
     console.log(showVoteContent)
     showVoteContent.value = !showVoteContent.value
     if (showVoteContent.value) {
          const id = setInterval(() => {
          showVoteContent.value = false
          clearInterval(id)
          console.log("toggle state")
     }, 2000)
     }

}

const sendPenalty = (subscriber, penalty) => {
     voteContent.value.content = penalty
     if (penalty == "음소거") {
          subscriber.subscribeToAudio(false)
          voteContent.value.description = "5초간 음소거 됩니다!!!"
          const id = setInterval(() => {
               subscriber.subscribeToAudio(true)
          }, 5000)
     } else if (penalty == "음성변조") {
          voteContent.value.description = "5초간 음성변조 됩니다!!!"
     } else if (penalty == "흑백화면") {
          subscriber.subscribeToVideo(false)
          voteContent.value.description = "5초간 화면이 보이지 않습니다!!!"
          const id = setInterval(() => {
               subscriber.subscribeToVideo(true)
               clearInterval(id)
          }, 5000)
     } else {
          voteContent.value.description = `${penalty}에 당첨되었어요!!!`
     }
}

</script>

<style>
.party-section {
     background-color: #08042B;
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
     min-height: 110px;
     height: 15vh;
     max-height: 200px;
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
     height: 75vh;
     /* aspect-ratio: 16/9; */
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
     height: 88vh;
     min-width: 320px;
     max-width: 30%;
     width: 30vw;
     /* 채팅 섹션의 높이를 브라우저 창의 높이와 맞춤 */
     /* height: 100vh;  */
     border: 1px solid #CBD0D8;
}

.cam-section {
     height: 75%;
     /* justify-self: start; */
     align-content: start;
     background-color: #333D51;
}

.cam-video {
     background-color: blueviolet;
     border: 1px solid white;
     min-height: 100px;
     height: 33.3%;
     /* height: 300px; */
     text-align: center;
     /* 중앙 정렬 */
     display: flex;
     flex-direction: column;
     justify-content: center;
     align-items: center;
     /* aspect-ratio: 16/16; */
     padding: 2px 0;
}

.button-section {
     /* 나머지 콘텐츠 위에 버튼 섹션을 밀어 올림 */
     /* margin-top: auto;  */
     max-height: 60px;
     width: 105%;
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
     /* overflow-y: auto; */
     /* 내용이 많을 경우 스크롤 */
     /* z-index: 10;  */
     /* 다른 요소 위에 채팅창이 나타나도록 z-index 설정 */
     padding: 0;
}

.chat-content {
     font-size: 2rem;
     color: #333D51;
}

.invite-img {
     height: 80%;
     width: 80%;
     margin-bottom: 10px;
     /* 텍스트와의 간격 */
     color: #CBD0D8;
}

.contents-section {
     /* margin: 2%; */
     /* min-height: 100%; */
     min-width: 1200px;
     /* color: #121212; */
     /* 내용물 가운데 정렬하기 */
     text-align: center;
}


.button-section button {
     width: 400px;
}

.select-field>.v-card-text {
     color: #333D51;
     height: 90px;
     padding: 0;
}

/* Chrome, Edge, Safari */
.match-video::-webkit-scrollbar {
     display: none;
     /* 스크롤바 영역을 숨깁니다 */
}

/* Firefox */
.match-video {
     scrollbar-width: none;
     /* Firefox에서 스크롤바를 숨깁니다 */
}

/* IE and Edge */
.match-video {
     -ms-overflow-style: none;
     /* Internet Explorer 및 Edge에서 스크롤바를 숨깁니다 */
     overscroll-behavior: contain;
}

.overlay-card {
     margin: auto;
     width: 32vw;
     /* max-width: 600px; */
     overflow: hidden;
     /* 내용이 넘칠 경우 숨김 처리 */
     min-width: 300px;
     min-height: 40px;
}

.over {
     display: flex;
     align-items: center;
     justify-content: center;
}

.screenshot-preview {
     /* max-height: 80vh; */
     margin: 20px;
     /* padding: 30px; */
}

.video-preview {
     /* max-height: 80vh; */
     width: 90%;
     object-fit: cover;
     margin: 20px;
     /* padding: 30px; */
}
</style> 