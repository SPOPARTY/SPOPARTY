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
            v-model="connectMessage.message"
            :append-icon="connectMessage.message ? 'mdi-send' : 'mdi-microphone'"
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
import { onMounted, reactive, ref } from 'vue'

const serverURL = 'https://i10a802.p.ssafy.io/api/ws-stomp'

let stompClient = undefined

const chats = reactive([
{userName: "test",
  teamLogo: "test",
  message: "잘하네"},
  {userName: "maru",
  teamLogo: "test",
  message: "달려라 마루쎄"},
  {userName: "제라드",
  teamLogo: "test",
  message: "훔바훔바링"},

])

const message = ref('')

const connectMessage = ref({
  userName: "maru",
  teamLogo: 'test',
  partyId: '125',
  clubId: '125',
  message: "",
})

onMounted(() => {
  connect()
})

const clearMessage = () => {
  connectMessage.value.message = ''
}

const sendMessage = () => {
  console.log("sendMessage")
  if (connectMessage.value.userName !== '' && connectMessage.value.message !== '') {
    console.log("sendMessage rrrr")
    send('/chat/send', connectMessage.value, connectMessage.value)
    connectMessage.value.message = ''
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
  const socket = new SockJS(serverURL)
  stompClient = Stomp.over(socket)

  stompClient.connect(
    {},
    (frame) => {
      console.log('stomp client connected.')
      connectMessage.value.message = `${connectMessage.value.userName} 님이 입장했습니다.`
      send('/chat/enter', connectMessage.value, connectMessage.value)
      connectMessage.value.message = ""
      stompClient.subscribe(`/sub/chat`, (response) => {
        console.log(response)
        chats.push(JSON.parse(response.body))
      })
      stompClient.subscribe(
        `/user/${connectMessage.value.userName}/sub/chat`,
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

const disconnect = () => {
  connectMessage.value.message = `${connectMessage.value.userName} 님이 퇴장했습니다.`
  send('/sub/chat/out', connectMessage.value, connectMessage.value)
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
