<template>
  <v-container fluid class="pa-0 chat-container"
    :style="{ height: `${chatDivHeightProp - 10}px` }">
    <div class="chat-messages" ref="chatMessages">
      <v-list lines="one">
        <!-- chatsMock 은 ui 테스트용 -->
        <!-- <v-list-item v-for="(item, i) in chatsMock" :key="i" :value="item" color="primary"> -->
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

    <!-- 입력창 -->
    <v-form @submit.prevent class="chat-form">
      <v-text-field class="chat-input" v-model="myMessage.message" append-icon="mdi-send" variant="outlined"
        clear-icon="mdi-close-circle" clearable label="Message" type="text" @keyup.enter="sendMessage" 
        @click:append="sendMessage" @click:clear="clearMessage"></v-text-field>
      <!-- <v-text-field class="chat-input" v-model="myMessage.message" append-icon="mdi-send" variant="outlined"
        clear-icon="mdi-close-circle" clearable label="Message" type="text" @keyup.enter="sendMessage"
        @click:append="sendMessage" @click:clear="clearMessage" @click="addChatMock"></v-text-field> -->
    </v-form>
  </v-container>
</template>


<script setup>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { onBeforeUnmount, onMounted, reactive, ref, watch, nextTick } from 'vue'
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

const connect = () => {
  if (stompClient === undefined) {
    const socket = new SockJS(serverURL)
    stompClient = Stomp.over(socket)

    stompClient.connect(
      {},
      (frame) => {
        console.log('stomp client connected.')
        myMessage.value.message = `${myMessage.value.userName} 님이 입장했습니다.`
        send('/chat/enter', myMessage.value, myMessage.value)
        myMessage.value.message = ''
        stompClient.subscribe(`/sub/chat${clubId}-${partyId}`, (response) => {
          console.log(response)
          chats.push(JSON.parse(response.body))
        })
        stompClient.subscribe(
          `/user/${myMessage.value.userName}/sub/chat${clubId}-${partyId}`,
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

// 스크롤을 채팅창의 맨 아래로 이동시키는 함수
const chatMessages = ref(null); // chat-messages div에 대한 참조

const scrollToBottom = () => {
  console.log('scrollToBottom')
  console.log(chatMessages.value)
  nextTick(() => {
    if (chatMessages.value) {
      chatMessages.value.scrollTop = chatMessages.value.scrollHeight;
    }
  });
};

// chats 배열에 변화가 있을 때마다 스크롤을 맨 아래로 이동
watch(chats, () => {
  // console.log('chats changed')
  scrollToBottom();
}, { immediate: true, deep: true });



/// /// 로컬에서 테스트할 때 쓸 코드 (테스트할 때만 true로 변경) 
const chatsMock = ref([
  { userName: 'Alice', message: '안녕하세요!' },
  { userName: 'Bob', message: '오늘 날씨가 좋네요.' },
  { userName: 'Son', message: '저도 그렇게 생각해요.' },
  { userName: 'Alice', message: '그럼 다음에 또 봐요!' },
  { userName: 'Son', message: '네, 안녕히가세요!' }
]);

// chatsMock 배열에 변화가 있을 때마다 스크롤을 맨 아래로 이동
watch(chatsMock, () => {
  console.log('chatsMock changed')
  scrollToBottom();
}, { immediate: true, deep: true });

// 채팅 추가 함수
// 채팅 메시지 추가 시 스크롤 처리
const addChatMock = () => {
  chatsMock.value.push({ userName: 'TEST', message: '새 채팅 추가' });
};


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
