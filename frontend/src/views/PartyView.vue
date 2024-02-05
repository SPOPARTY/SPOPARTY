<template>
     <v-container fluid>
          <!-- 기존 비디오 및 채팅 섹션 -->
          <v-row class="party-section mb-1">
               <v-col class="match-section">
                    <v-row class="match-video">
                         <v-img src="/soccer-screen.png" aspect-ratio="16/9"></v-img>
                    </v-row>
                    <v-row>
                         <v-col cols="7" class="party pa-1 px-4">
                              {{ partyTitle }}
                              <v-icon @click="editParty" size="x-small" class="pa-6">mdi-pencil</v-icon>
                         </v-col>
                         <v-col cols="5" class="match pa-1">
                              {{ matchName }}
                              <v-icon @click="editMatch" size="x-small" class="pa-6">mdi-pencil</v-icon>
                         </v-col>
                    </v-row>
               </v-col>
               <v-col class="chatting-section">
                    <v-row>
                         <v-col cols="6" class="cam-video" v-for="member in partyMembers" :key="member.memberId">
                              {{ member.name }}
                              {{ member.role }}
                              <!-- 여기에 캠 화면 또는 이미지 배치 -->
                         </v-col>
                         <!-- 파티 초대 -->
                         <v-col cols="6" class="cam-video" v-if="partyMembers.length < maxMembers" @click="inviteToParty"
                              style="cursor: pointer">
                              <v-icon class="invite-icon">mdi-account-plus</v-icon>
                              <span>파티 초대</span>
                         </v-col>
                    </v-row>
                    <!-- 버튼 영역 -->
                    <v-row class="button-section">
                         <v-col cols="2" class="mx-1">
                              <v-btn color="secondary">
                                   <v-icon size="x-large">mdi-camera-outline</v-icon>
                              </v-btn>
                         </v-col>
                         <v-col cols="2" class="mx-1">
                              <v-btn color="secondary">
                                   <v-icon size="x-large">mdi-video-plus-outline</v-icon>
                              </v-btn>
                         </v-col>
                         <v-col cols="2" class="mx-1">
                              <v-btn color="#D3AC2B">
                                   <v-icon size="x-large" color="#333D51">mdi-bullhorn-outline</v-icon>
                              </v-btn>
                         </v-col>
                         <v-col cols="2" class="mx-1">
                              <v-btn color="#CBD0D8">
                                   투표
                              </v-btn>
                         </v-col>
                         <v-col cols="2" class="mx-1">
                              <v-btn color="yellow" class="chat-button">
                                   <v-icon color="#08042B">mdi-chat</v-icon>
                              </v-btn>
                         </v-col>
                         <!-- 다음 줄 -->
                         <v-col cols="6">
                              <v-btn color="primary">추가 버튼</v-btn>
                         </v-col>
                         <v-col cols="6">
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
                    <PartyMatch :match-id="matchId"/>
               </v-col>
          </v-row>
     </v-container>
</template>
 
<script setup>
import { ref } from 'vue'

import PartyMatch from '@/components/party/PartyMatch.vue'
import Chat from '../components/party/Chat.vue';

// 임시 경기 id
const matchId = ref("1")

// 파티 최대 인원 수
const maxMembers = 6

const partyMembers = ref([
     { memberId: 1, name: "실버스타", role: "그룹원" },
     { memberId: 2, name: "제라드", role: "그룹장" },
     { memberId: 3, name: "벨타이거", role: "그룹원" },
     { memberId: 4, name: "램파드", role: "그룹원" },
     { memberId: 5, name: "별명별명", role: "그룹원" },
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


let partyTitle = ref('파티 타이틀');
let matchName = ref('경기 이름');

// 파티 정보 수정
const editParty = () => {
     const newPartyTitle = prompt('새 파티 타이틀을 입력하세요', partyTitle.value);
     if (newPartyTitle !== null) partyTitle.value = newPartyTitle;
}

// 경기 정보 수정
const editMatch = () => {
     const newMatchName = prompt('새 경기 이름을 입력하세요', matchName.value);
     if (newMatchName !== null) matchName.value = newMatchName;
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
     background-color: grey;
     /* 화면 크기 960*540 고려 */
     /* 1080p 의 50% 크기 */
     min-width: 960px;
     min-height: 540px;
     max-width: 960px;
     height: 590px;
}

.match-section .party {
     /* background-color: #333D51; */
     height: 45px;
     font-size: 1.5rem;
}

.match-section .match {
     /* background-color: #333D51; */
     padding-top: 5px;
     height: 40px;
     font-size: 1.25rem;
}

.match-video {
     background-color: lightslategray;
     min-height: 500px;
}

.chatting-section {
     background-color: #333D51;
     min-height: 100%;
     min-width: 320px;
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
     padding-top: 20px;
     /* 버튼 영역과 위의 콘텐츠 사이에 간격 추가 */
     text-align: center;
}

.chat-button {
     background-color: yellow;
     /* width: 150px; */
     /* 채팅창 버튼의 너비 조정 */
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
</style>