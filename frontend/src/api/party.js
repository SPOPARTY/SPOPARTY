import {localAxios} from '@/util/http-commons';

const API_URL = "party"

const local = localAxios();

// 파티 정보 조회
function requestGetPartyInfo (clubId,partyId,success,fail) {
    local.get(`clubs/${clubId}/${API_URL}/${partyId}`).then(success).catch(fail);
}

// 파티 정보 생성
async function requestPostPartyInfo (clubId,success,fail) {
    local.post(`clubs/${clubId}/${API_URL}`).then(success).catch(fail);
}

function requestPutPartyInfo (clubId,partyId,data,success,fail) {
    local.put(`clubs/${clubId}/${API_URL}/${partyId}`,data).then(success).catch(fail);
}

// 파티 정보 삭제
function requestDeletePartyInfo (clubId,partyId,success,fail) {
    local.delete(`clubs/${clubId}/${API_URL}/${partyId}`).then(success).catch(fail);
}

// 파티원 목록 조회
function requestGetPartyMemberList (clubId,partyId,success,fail) {
    local.get(`clubs/${clubId}/${API_URL}/${partyId}/participants`).then(success).catch(fail);
}

// 파티원 생성
function requestPostPartyMember (clubId,partyId,success,fail) {
    local.post(`clubs/${clubId}/${API_URL}/${partyId}/participants`).then(success).catch(fail);
}

// 파티원 조회
function requestGetPartyMember (clubId,partyId,participantId,success,fail) {
    local.get(`clubs/${clubId}/${API_URL}/${partyId}/participants/${participantId}`).then(success).catch(fail);
}

// 파티원 삭제
function requestDeletePartyMember (clubId,partyId,participantId,success,fail) {
    local.delete(`clubs/${clubId}/${API_URL}/${partyId}/participants/${participantId}`).then(success).catch(fail);
}

function requestStartRecording(clubId, data, success, fail) {
  local.post(`clubs/${clubId}/${API_URL}/recording/start`, data).then(success).catch(fail)
}

function requestStopRecording(clubId, data, success, fail) {
  local.post(`clubs/${clubId}/${API_URL}/recording/stop`, data).then(success).catch(fail)
}
export {
    requestGetPartyInfo,
    requestPostPartyInfo,
    requestPutPartyInfo,
    requestDeletePartyInfo,
    requestGetPartyMemberList,
    requestPostPartyMember,
    requestGetPartyMember,
    requestDeletePartyMember,
    requestStartRecording,
    requestStopRecording
}