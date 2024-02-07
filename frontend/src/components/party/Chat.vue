<template>
  <v-container
    fluid
    class="pa-1">
    <v-list lines="one">
      <v-list-item
        v-for="(item, i) in chats"
        :key="i"
        :value="item"
        color="primary">
        <template v-slot:prepend>
          <v-icon :icon="item.icon"></v-icon>
        </template>
        <v-list-item-title v-text="item.userName"></v-list-item-title>
        <v-list-item-title v-text="item.message"></v-list-item-title>
      </v-list-item>
    </v-list>
  </v-container>
  <v-form>
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-text-field
            class="chat-input"
            v-model="myMessage.message"
            :append-icon="myMessage.message ? 'mdi-send' : 'mdi-microphone'"
            variant="filled"
            clear-icon="mdi-close-circle"
            clearable
            label="Message"
            type="text"
            @click:append="sendMessage"
            @click:clear="clearMessage"></v-text-field>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script setup>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { usePartyStore } from '@/stores/club/party/party'

const serverURL = 'http://localhost:9090/api/ws-stomp'

let stompClient = undefined

const route = useRoute()
const router = useRouter()

const partyStore = usePartyStore()

const chats = reactive([])

const message = ref('')

onMounted(() => {})

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
</script>
<style>
.chat-input {
  background-color: aliceblue;
}
</style>
