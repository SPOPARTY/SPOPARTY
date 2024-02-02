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
            v-model="message.message"
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
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { onMounted, reactive, ref } from 'vue';

const serverURL = "http://localhost:9090/api/ws-stomp"

let stompClient = undefined;

const chats = reactive([
  { text : "a", icon : 'mdi-clock' },
  { text : "b", icon : 'mdi-clock' },
  { text : "c", icon : 'mdi-clock' },
  { text : "d", icon : 'mdi-clock' },
])

const memberInfo = ref({
  id : "123",
  loginId : "",
  loginPwd : "",
  nickname : "maru",
  email : "",
  team : {
      id : "123",
      logo : "123",
  },
  status : "",
})

const message = ref({ 
          userName: memberInfo.value.nickname,
          teamLogo: memberInfo.value.team.logo,
          message: ""
        });

onMounted(() => {
    connect()
})

const clearMessage = () => {
    message.value.message = ''
}

const sendMessage =  () => {
  if(userName !== '' && message.value !== ''){
    send("/chat/send", message.value, message.value)
    message.value.message = ''
  }
}

const send = (destination, body, header) => {
  
  if (stompClient &&stompClient.connected) {
    console.log(`send message destination : ${destination}, body : ${body}, header : ${header}`);
    stompClient.send(destination, JSON.stringify(body) ,JSON.stringify(header))
  } else {
    console.error(`failed to send message destination : ${destination}, body : ${body}, header : ${header}`)
  }
} 

const connect = () => {
  const socket = new SockJS(serverURL);
  stompClient = Stomp.over(socket);
  
  stompClient.connect({},
    frame => {
      console.log("stomp client connected.")
      const connectMessage = { 
        userName: memberInfo.value.nickname,
        teamLogo: memberInfo.value.logo,
        teamId: memberInfo.value.team.id,
        groupId: memberInfo.value.id,
        teamLogo: memberInfo.value.team.logo,
      };

      send("/chat/enter", connectMessage, connectMessage)

      stompClient.subscribe(
        `/sub/chat`,
        response => {
          console.log(response)
        });
      stompClient.subscribe(
        `/user/${memberInfo.value.nickname}/sub/chat`, 
        response => {
          console.log(response)
        });
    },
    error => {
      console.error(`stomp client connect error : ${error}`)
    }
  );        
}

const disconnect = () => {
  stompClient.disconnect(() => {
    console.log("stomp client disconnected.")
  })
}

</script>
<style>

.chat-input {
    background-color: aliceblue;
}
</style>