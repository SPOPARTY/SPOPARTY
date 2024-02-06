import {localAxios} from '@/util/http-commons';

const API_URL = "party"

const local = localAxios();

function requestGetPartyInfo (clubId,partyId,success,fail) {
    // 파티 정보 조회
    local.get(`clubs/${clubId}/${API_URL}/${partyId}`).then(success).catch(fail);
}

async function requestPostPartyInfo (clubId,success,fail) {
    // 파티 정보 생성
    local.post(`clubs/${clubId}/${API_URL}`).then(success).catch(fail);
}

function requestPutPartyInfo (clubId,partyId,data,success,fail) {
    // 파티 정보 수정
    // const data = {
    //     memberId = sessionStorage.getItem("id"),
    //     title: title,
    //     fixtureUrl: fixtureUrl,
    //     fixtureId: fixtureId,
    // }
    local.put(`clubs/${clubId}/${API_URL}/${partyId}`,data).then(success).catch(fail);
}

function requestDeletePartyInfo (clubId,partyId,success,fail) {
    // 파티 정보 삭제
    local.delete(`clubs/${clubId}/${API_URL}/${partyId}`).then(success).catch(fail);
}

function requestGetPartyMemberList (clubId,partyId,success,fail) {
    // 파티원 목록 조회
    local.get(`clubs/${clubId}/${API_URL}/${partyId}/participants`).then(success).catch(fail);
}

function requestPostPartyMember (clubId,partyId,success,fail) {
    // 파티원 생성
    local.post(`clubs/${clubId}/${API_URL}/${partyId}/participants`).then(success).catch(fail);
}

function requestGetPartyMember (clubId,partyId,participantId,success,fail) {
    // 파티원 조회
    local.get(`clubs/${clubId}/${API_URL}/${partyId}/participants/${participantId}`).then(success).catch(fail);
}

function requestDeletePartyMember (clubId,partyId,participantId,success,fail) {
    // 파티원 삭제
    local.delete(`clubs/${clubId}/${API_URL}/${partyId}/participants/${participantId}`).then(success).catch(fail);
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
}