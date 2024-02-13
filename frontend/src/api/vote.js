import { localAxios } from '@/util/http-commons'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import {useVoteStore} from '@/stores/club/party/votes'


const local = localAxios()
const serverURL = 'http://localhost:9090/api/ws-stomp'
// const serverURL = "https://i10a802.p.ssafy.io/api/ws-stomp"
let stompClient = undefined
const voteStore = useVoteStore();

const memberId = localStorage.getItem("id")

// 웹소켓 연결
const voteConnect = (partyId) => {
  if (stompClient === undefined) {
    const socket = new SockJS(serverURL)
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function () {
      stompClient.subscribe(`/sub/vote/create/${partyId}`, function (response) {
        // 1. 투표 생성
        console.log('*******CreateVote********')
        console.log(response)
        alert("투표 만들기 성공!")
        voteStore.getOngoingVoteList(partyId);
        voteStore.getMyVoteList(partyId,memberId);
   
      })

      // 2. 투표 진행
      // voteCount : 투표에 참여했습니다.
      // 총인원수 == voteCount : 투표 종료 할 수 있으니까 -> 생성한 사람한테 보내주기
      stompClient.subscribe(`/sub/vote/do/${partyId}`, function (response) {
        console.log('*******do********')
        alert("투표 참여 성공!")
        console.log(response)
        voteStore.getOngoingVoteList(partyId);
        voteStore.getMyVoteList(partyId,memberId);
        voteStore.getFinishedVoteList(partyId);

      })
      // 3. 투표 종료
      stompClient.subscribe(
        `/sub/vote/counting/${partyId}`,
        function (response) {
          alert("투표 마감 성공!")
          console.log('*******counting********')
          console.log(response)
          voteStore.getOngoingVoteList(partyId);
          voteStore.getMyVoteList(partyId,memberId);
          voteStore.getFinishedVoteList(partyId);

        },
      )
    })
  }
}


function createVote(data) {
  // 투표 생성 호출 메서드
  console.log(stompClient)
  stompClient.send(
    '/vote/create',
    JSON.stringify(data), // body
    {}, // headers
  )
}

// 투표 하기
function doVote(data) {
  // 투표 진행 호출 메서드
  stompClient.send(
    '/vote/do',
    JSON.stringify(data), // body
    {}, // header
  )
}

// 투표 종료(정답, 집계)
function finishVote(data) {
  // 투표 종료 호출 메서드
  stompClient.send(
    '/vote/counting',
    JSON.stringify(data), // body
    {}, // header
  )
}
// 연결 해제
const voteDisconnect = () => {
  stompClient.disconnect(() => {
    console.log('stomp client disconnected.')
  })

}

// 투표 상세 조회
function requestVoteDetail(partyId, voteId, success, fail) {
  local.get(`/party/${partyId}/votes/${voteId}`).then(success).catch(fail)
}

// 진행 중인 투표 조회
function requestOngoingVoteList(partyId, success, fail) {
  local.get(`/party/${partyId}/votes/ongoing`).then(success).catch(fail)
}


// 종료된 투표 조회
function requestFinishedVoteList(partyId, success, fail) {
  local.get(`/party/${partyId}/votes/finished`).then(success).catch(fail)
}

// 내가 만든 투표 조회
function requestMyVoteList(partyId, memberId, success, fail) {
  local
    .get(`/party/${partyId}/votes/members/${memberId}`)
    .then(success)
    .catch(fail)
}

export {
  voteConnect,
  createVote,
  doVote,
  finishVote,
  voteDisconnect,
  requestVoteDetail,
  requestOngoingVoteList,
  requestFinishedVoteList,
  requestMyVoteList,
}

