import {localAxios} from '@/util/http-commons';

const local = localAxios();

// 투표 생성
function requestCreateVote(partyId, data, success,fail) {
    local.post(`/party/${partyId}/votes`,data).then(success).catch(fail);
}

// 투표 하기
function requestCastVote(partyId, voteId, data, success,fail) {
    local.put(`/party/${partyId}/votes/${voteId}`,data).then(success).catch(fail);
}

// 투표 종료(정답, 집계)
function requestFinishVote(partyId, voteId, data, success,fail) {
    local.put(`/party/${partyId}/votes/${voteId}`,data).then(success).catch(fail);
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
    requestCreateVote, 
    requestCastVote, 
    requestFinishVote, 
    requestVoteDetail,
    requestOngoingVoteList, 
    requestFinishedVoteList, 
    requestMyVoteList
}