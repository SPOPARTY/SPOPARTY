<template>
    <v-container fluid class="pa-1">
        <v-list lines="one">
            <v-list-item
        v-for="(item, i) in chats"
        :key="i"
        :value="item"
        color="primary"
      >
        <template v-slot:prepend>
          <v-icon :icon="item.icon"></v-icon>
        </template>

        <v-list-item-title v-text="item.text"></v-list-item-title>
      </v-list-item>
</v-list>
    
    </v-container>
    <v-form>
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-text-field class="chat-input"
            v-model="message"
            :append-icon="message ? 'mdi-send' : 'mdi-microphone'"
            variant="filled"
            clear-icon="mdi-close-circle"
            clearable
            label="Message"
            type="text"
            @click:append="sendMessage"
            @click:clear="clearMessage"
          ></v-text-field>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
</template>

<script setup>
import axios from 'axios'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { onMounted, reactive, ref } from 'vue';

let stompClient = undefined;
let connected = false;
let userName = "qwer"

const chats = reactive([
{ text : "a", icon : 'mdi-clock' },
{ text : "b", icon : 'mdi-clock' },
{ text : "c", icon : 'mdi-clock' },
{ text : "d", icon : 'mdi-clock' },
])

const message = ref("")

const memberInfo = ref({
    id : "qwer",
    loginId : "",
    loginPwd : "",
    nickname : "maru",
    email : "",
    team : {
        id : "",
        logo : "",
    },
    status : "",
})

onMounted(() => {
    connect()
})

const clearMessage = () => {
    message.value = ''
}

const sendMessage =  (e) => {
      if(userName !== '' && message.value !== ''){
        send()
        message.value = ''
      }
    }
    const send = () => {
      console.log("Send message:" + message);
      if (stompClient &&stompClient.connected) {
        const msg = { 
          userName: memberInfo.value.nickname,
          teamLogo: memberInfo.value.team.logo,
          message: message.value
        };
        stompClient.send("/chat/send", JSON.stringify(msg) ,JSON.stringify(msg))
      }
    } 
    const connect = () => {
      const serverURL = "http://localhost:9090/api/ws-stomp"
      let socket = new SockJS(serverURL);
      stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)

      stompClient.connect({},
        frame => {
          // 소켓 연결 성공
          connected = true;
          console.log('소켓 연결 성공', frame);
          const msg = { 
          userName: memberInfo.value.nickname,
          teamLogo: memberInfo.value.team.logo,
          message: message.value
        };
          stompClient.send("/chat/enter", JSON.stringify(msg) ,JSON.stringify(msg))
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          stompClient.subscribe("/sub/chat", res => {
            console.log(res)
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            // recvList.push(JSON.parse(res.body))
          });
          stompClient.subscribe("user/maru/sub/chat", res => {
            console.log(res)
            console.log('구독으로 받은 유저 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            // recvList.push(JSON.parse(res.body))
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          connected = false;
        }
      );        
    }

</script>
<style>

.chat-input {
    background-color: aliceblue;
}
</style>