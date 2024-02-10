<template>
  <v-row width="100%">
  <v-container class="pa-0 ma-0 test">
    <v-col cols="auto" class="pa-0 ma-0">
      <v-responsive class="overflow-y-hidden fill-height">
        <v-card flat class="d-flex flex-column">
          <v-card-text class="overflow-y-auto">
            <template v-for="(msg, i) in chats">
              <div :class="{ 'd-flex flex-row-reverse': msg.me }">
                <v-menu >
                  <template v-slot:activator="{ on }">
                      <v-chip
                          :color="msg.me ? 'primary' : ''"
                          dark
                          style="height:auto;white-space: normal;"
                          class="pa-4 mb-2"
                          v-on="on"
                      >
                        {{ msg.message }}
                      </v-chip>
                  </template>
                </v-menu>
              </div>
            </template>
          </v-card-text>
        </v-card>
      </v-responsive>
    </v-col>
  </v-container>
  <v-row rows='2'>
    <v-card-text>
      <v-text-field
        variant="solo"
        label="Search templates"
        :append-icon="mdi-send"
        single-line
        hide-details
        @keyup.enter="sendMessage"
        @click:append="sendMessage"
        @click:clear="clearMessage">
      </v-text-field>
    </v-card-text>
  </v-row>

</v-row>
</template>

<script setup>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { onMounted, reactive, ref } from 'vue'

const serverURL = 'http://localhost:9090/api/ws-stomp'

let stompClient = undefined

const partyStore = usePartyStore()

const chats = reactive([
  {
    "userName" : "test",
    "message" : "test message",
    "me": true
  },
  {
    "userName" : "test",
    "message" : "test message",
    "me": false
  },
  {
    "userName" : "test",
    "message" : "test message",
    "me": false
  },
  {
    "userName" : "test",
    "message" : "test message",
    "me": false
  },
  {
    "userName" : "test",
    "message" : "test message",
    "me": true
  },
  {
    "userName" : "test",
    "message" : "test message",
    "me": false
  },
  {
    "userName" : "test",
    "message" : "test message",
    "me": true
  },
  {
    "userName" : "test",
    "message" : "test message",
    "me": false
  },
  {
    "userName" : "test",
    "message" : "test message",
    "me": true
  }
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
  chats.push(  {
    "userName" : "test",
    "message" : "test message",
    "me": false
  })
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
  connectMessage.value.message = `${connectMessage.value.userName} 님이 퇴장했습니다.`
  send('/sub/chat/out', connectMessage.value, connectMessage.value)
  stompClient.disconnect(() => {
    console.log('stomp client disconnected.')
  })
}
</script>
<style>
.test {
  overflow-y: auto;
}
.chat-input {
  background-color: aliceblue;
}
</style>
