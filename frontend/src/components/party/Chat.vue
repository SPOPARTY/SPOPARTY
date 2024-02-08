<template>
  <v-container fluid class="pa-0 chat-container" ref="chatContainer" @scroll="handleScroll"
    :style="{ height: `${chatDivHeightProp - 10}px` }">
    <div class="chat-messages" ref="chatMessages">
      <v-list lines="one">
        <v-list-item v-for="(item, i) in chats" :key="i" :value="item" color="primary">
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title v-text="item.userName"></v-list-item-title>
            <v-list-item-subtitle v-text="item.message"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </div>
    <!-- 새 메시지 알림 (사용자가 스크롤을 맨 아래에 두지 않았을 때 표시) -->
    <div v-if="showNewMessageAlert" class="new-message-alert" @click="scrollToBottom">
      새 메시지가 도착했습니다.
    </div>

    <!-- 입력창 -->
    <v-form @submit.prevent class="chat-form">
      <v-text-field class="chat-input" v-model="myMessage.message" append-icon="mdi-send" variant="outlined"
        clear-icon="mdi-close-circle" clearable label="Message" type="text" @keyup.enter="sendMessage"
        @click:append="sendMessage" @click:clear="clearMessage"></v-text-field>
    </v-form>
  </v-container>
</template>


<script setup>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { usePartyStore } from '@/stores/club/party/party'

const serverURL = 'https://i10a802.p.ssafy.io/api/ws-stomp'

let stompClient = undefined

const route = useRoute()
const router = useRouter()

const partyStore = usePartyStore()

const chats = reactive([])

const message = ref('')

onMounted(() => { })

//// 파티 정보 수정 로직
// 파티 입장 및 퇴장
const clubId = route.params.clubId
const partyId = route.params.partyId
const partyMemberList = ref(partyStore.partyMemberList)

const myMessage = ref({
  userName: '',
  teamLogo: '',
  partyId: '',
  clubId: '',
  message: '',
})

const connect = () => {
  const socket = new SockJS(serverURL)
  stompClient = Stomp.over(socket)

  stompClient.connect(
    {},
    (frame) => {
      console.log('stomp client connected.')
      myMessage.value.message = `${myMessage.value.userName} 님이 입장했습니다.`
      send('/chat/enter', myMessage.value, myMessage.value)
      myMessage.value.message = ''
      stompClient.subscribe(`/sub/chat`, (response) => {
        console.log(response)
        chats.push(JSON.parse(response.body))
      })
      stompClient.subscribe(
        `/user/${myMessage.value.userName}/sub/chat`,
        (response) => {
          chats.push(...JSON.parse(response.body))
          console.log(chats)
        },
      )
    },
    (error) => {
      console.error(`stomp client connect error : ${error}`)
    },
  )
}

watch(
  () => partyStore.partyMemberList,
  (newPartyMembers) => {
    partyMemberList.value = newPartyMembers
    partyMemberList.value.map((member) => {
      console.log(member)
      console.log(localStorage.getItem('id'))
      if (member.memberId == localStorage.getItem('id')) {
        myMessage.value.userName = member.memberNickname
        myMessage.value.teamLogo = 'qwer'
        myMessage.value.clubId = clubId
        myMessage.value.partyId = partyId
        connect()
      }
    })
  },
  { immediate: true, deep: true },
)

const clearMessage = () => {
  myMessage.value.message = ''
}

const sendMessage = () => {
  console.log('sendMessage')
  if (myMessage.value.userName !== '' && myMessage.value.message !== '') {
    console.log('sendMessage rrrr')
    send('/chat/send', myMessage.value, myMessage.value)
    myMessage.value.message = ''
  }
}

const send = (destination, body, header) => {
  if (stompClient) {
    console.log(
      `send message destination : ${destination}, body : ${body}, header : ${header}`,
    )
    stompClient.send(destination, JSON.stringify(body), JSON.stringify(header))
  } else {
    console.error(
      `failed to send message destination : ${destination}, body : ${body}, header : ${header}`,
    )
  }
}

const disconnect = () => {
  myMessage.value.message = `${myMessage.value.userName} 님이 퇴장했습니다.`
  send('/sub/chat/out', myMessage.value, myMessage.value)
  stompClient.disconnect(() => {
    console.log('stomp client disconnected.')
  })
}

////// 그 외 로직
const props = defineProps({
  chatDivHeightProp: {
    type: String,
    required: true,
    default: '300'
  }
})

// 채팅창 스크롤 관련 로직 // 테스트 필요 (에러 시 주석 처리할 것)
const chatContainer = ref(null);
const showNewMessageAlert = ref(false);

const handleScroll = () => {
  const isAtBottom = isScrolledToBottom();
  showNewMessageAlert.value = !isAtBottom;
};

const scrollToBottom = () => {
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
    showNewMessageAlert.value = false;
  }
};

const isScrolledToBottom = () => {
  if (!chatContainer.value) return false;
  const { scrollTop, scrollHeight, clientHeight } = chatContainer.value;
  return scrollTop + clientHeight === scrollHeight;
};

watch(chats, () => {
  if (isScrolledToBottom()) {
    scrollToBottom();
  }
});

</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  /* height: {chatDivHeightProp}; */
}

.chat-messages {
  overflow-y: auto;
  flex-grow: 1;
  padding: 10px;
  margin-bottom: 10px;
  /* 입력창과의 간격 */
  background-color: #F4F3EA;
}

.chat-form {
  padding: 10px 10px 0px 10px;
  /* 입력창과의 간격 */
  background-color: #f4f4f4;
  /* 채팅 입력창 배경색 */
  width: 100%;
}

.chat-input {
  background-color: #ffffff;
  /* 입력창 배경색 */
  padding: 0;
}
</style>
