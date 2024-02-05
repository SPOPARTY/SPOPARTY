<template>
  <div
    id="main-container"
    class="container">
    <div
      id="join"
      v-if="!session">
      <div id="img-div">
        <!-- <img src="resources/images/openvidu_grey_bg_transp_cropped.png" /> -->
      </div>
      <div
        id="join-dialog"
        class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required />
          </p>
          <p>
            <label>Session</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required />
          </p>
          <p class="text-center">
            <button
              class="btn btn-lg btn-success"
              @click="joinSession">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div
      id="session"
      v-if="session">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session" />
      </div>
      <div
        id="main-video"
        class="col-md-6">
        <UserVideo :stream-manager="mainStreamManager" />
      </div>
      <div
        id="video-container"
        class="col-md-6">
        <UserVideo
          :stream-manager="publisher"
          @click.native="updateMainVideoStreamManager(publisher)" />
        <UserVideo
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click.native="updateMainVideoStreamManager(sub)" />
      </div>
      <button @click="sendMessage">asdasdasdadsdas</button>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from '../components/openvidu/UserVideo.vue'
import { ref, reactive } from 'vue'
axios.defaults.headers.post['Content-Type'] = 'application/json'

const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production' ? '' : 'https://i10a802.p.ssafy.io/'
// Move the OpenVidu initialization outside of the component

const OV = ref(undefined)
const session = ref(undefined)
const mainStreamManager = ref(undefined)
const publisher = ref(undefined)
const subscribers = ref([])

let mySessionId = 'SessionA'
let myUserName = 'Participant' + Math.floor(Math.random() * 100)

const joinSession = () => {
  OV.value = new OpenVidu()
  // --- 2) Init a session ---
  session.value = OV.value.initSession()

  // --- 3) Specify the actions when events take place in the session ---

  // On every new Stream received...
  session.value.on('streamCreated', ({ stream }) => {
    const subscriber = session.value.subscribe(stream)
    subscribers.value.push(subscriber)
  })

  // On every Stream destroyed...
  session.value.on('streamDestroyed', ({ stream }) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0)
    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
  })

  // On every asynchronous exception...
  session.value.on('exception', ({ exception }) => {
    console.warn(exception)
  })

  session.value.on('signal', (event) => {
    console.log(event.data) // Message
    console.log(event.from) // Connection object of the sender
    console.log(event.type) // The type of message
  })

  // --- 4) Connect to the session with a valid user token ---

  // Get a token from the OpenVidu deployment
  getToken(mySessionId).then((token) => {
    // First param is the token. Second param can be retrieved by every user on event
    // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
    session.value
      .connect(token, { clientData: myUserName })
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
          mirror: false, // Whether to mirror your local video or not
        })

        // Set the main video in the page to display our webcam and store our Publisher
        mainStreamManager.value = initPublisher
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
  })

  window.addEventListener('beforeunload', leaveSession)
}

const leaveSession = () => {
  // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
  if (session.value) session.value.disconnect()

  // Empty all properties...
  session.value = undefined
  mainStreamManager.value = undefined
  publisher.value = undefined
  subscribers.value = []
  OV.value = undefined

  // Remove beforeunload listener
  window.removeEventListener('beforeunload', leaveSession)
}

const updateMainVideoStreamManager = (stream) => {
  if (mainStreamManager.value === stream) return
  mainStreamManager.value = stream
}

const getToken = async (mySessionId) => {
  const sessionId = await createSession(mySessionId)
  return await createToken(sessionId)
}

const createSession = async (sessionId) => {
  const response = await axios.post(
    APPLICATION_SERVER_URL + 'api/openvidu/sessions',
    { customSessionId: sessionId },
    {
      headers: { 'Content-Type': 'application/json' },
    },
  )
  return response.data.data // The sessionId
}

const createToken = async (sessionId) => {
  console.log(sessionId)
  const response = await axios.post(
    APPLICATION_SERVER_URL +
      'api/openvidu/sessions/' +
      sessionId +
      '/connections',
    {},
    {
      headers: { 'Content-Type': 'application/json' },
    },
  )
  return response.data.data // The token
}

// Rest of the code remains unchanged...
</script>
