import {localAxios} from '@/util/http-commons';
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

const local = localAxios();
const serverURL = 'https://i10a802.p.ssafy.io/api/ws-stomp'
let stompClient = undefined;
// 웹소켓 연결
const voteConnect = (partyId) => {
    if (stompClient === undefined) {
      const socket = new SockJS(serverURL)
      stompClient = Stomp.over(socket)
      stompClient.connect({}, function () {
        stompClient.subscribe(`/sub/vote/${partyId}`, function (response) { // 콜백함수 정의
          console.log(response) // subscriber에게 보내진 응답이 여기로 모두 들어옵니다.
        })
      })
    }
  }
  
// 투표 생성
function createVote(data) { // 투표 생성 호출 메서드
    stompClient.send(
      '/vote/create',
      JSON.stringify(data), // body
      {} // headers
    )
  }
  
// 투표 하기
function doVote(data) { // 투표 진행 호출 메서드
    stompClient.send(
      '/vote/do',
      JSON.stringify(data), // body
      {} // header
    )
  }
  

// 투표 종료(정답, 집계)
function finishVote() { // 투표 종료 호출 메서드
    stompClient
      .send(
        '/vote/counting',
        JSON.stringify(data), // body
        {} // header
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
    local.get(`/party/${partyId}/votes/${voteId}`).then(success).catch(fail);
}

// 진행 중인 투표 조회
function requestOngoingVoteList(partyId, success, fail) {
    local.get(`/party/${partyId}/votes/ongoing`).then(success).catch(fail);
} 

// 종료된 투표 조회
function requestFinishedVoteList(partyId, success, fail) {
    local.get(`/party/${partyId}/votes/finished`).then(success).catch(fail);
}

// 내가 만든 투표 조회
function requestMyVoteList(partyId, memberId, success, fail) {
    local.get(`/party/${partyId}/votes/members/${memberId}`).then(success).catch(fail);
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
    requestMyVoteList
}