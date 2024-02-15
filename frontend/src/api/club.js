import {localAxios} from '@/util/http-commons';

const local = localAxios();

const API_URL = "/clubs"

// 최근 활동 그룹 목록 조회
function requestRecentClubs(success,fail) {
    local.get("/clubs/recent").then(success).catch(fail);
}

// 그룹 생성
async function requestCreateClubs(data,success,fail) {
    local.post("/clubs",data).then(success).catch(fail);
}

// 그룹 조회
async function requestClubInfo(clubId,success,fail) {
    local.get(`${API_URL}/${clubId}`).then(success).catch(fail);
}

// 그룹명 수정(그룹장 권한)
function requestUpdateClubName(clubId,data,success,fail) {
    local.put(`${API_URL}/${clubId}`,data).then(success).catch(fail);
}

// 그룹 삭제(그룹장 권한)
function requestDeleteClub(clubId, success, fail) {
    local.delete(`${API_URL}/${clubId}`).then(success).catch(fail);
}

// 그룹 초대 링크 
function requestClubInviteLink(clubId, success, fail) {
    local.get(`${API_URL}/invite/${clubId}`).then(success).catch(fail);
}

// 그룹원 목록 조회(닉네임, 방장)
async function requestClubMemberList(clubId, success, fail) {
    local.get(`${API_URL}/${clubId}/clubmembers`).then(success).catch(fail);
}

// 그룹원 초대
function requestClubInvite(data, success,fail) {
    local.post(`${API_URL}/invite`,data).then(success).catch(fail);
}

// 그룹장 수정(그룹장 권한)
function requestUpdateClubLeader(clubId, clubMemberId,success,fail) {
    local.put(`${API_URL}/${clubId}/host/${clubMemberId}`).then(success).catch(fail);
}

// 그룹 떠나기
function requestDeleteClubMember(clubId, success, fail) {
    local.delete(`${API_URL}/${clubId}/clubmembers`).then(success).catch(fail);
}

// 그룹원 강퇴
function requestBanClubMember(clubId,clubMemberId,success,fail) {
    local.delete(`${API_URL}/${clubId}/clubmembers/${clubMemberId}`).then(success).catch(fail);
}



export {
    requestRecentClubs,
    requestCreateClubs,
    requestClubInfo,
    requestUpdateClubName,
    requestDeleteClub,
    requestClubInviteLink,
    requestClubMemberList,
    requestClubInvite,
    requestUpdateClubLeader,
    requestDeleteClubMember,
    requestBanClubMember,
}