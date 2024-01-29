import {localAxios} from '@/util/http-commons';

const API_URL = "/follows"

const local = localAxios();

function requestAllTeamList(success,fail) {
    local.get("/members/teams").then(success).catch(fail);
}

function requestFollowList(memberId,success,fail) {
    // local.defaults.header['Authorization'] = localStorage.getItem("accessToken")
    console.log("팔로우 리스트 가져오기")
    local.get(`/members/${memberId}/follows`).then(success).catch(fail);
}

function requestFollow(data,success,fail) {
    // local.default.header['Authorization'] = localStorage.getItem("accessToken")
    local.post("/members/follows",data).then(success).catch(fail)
}

function requestUnFollow(teamId,success,fail) {
    // local.default.header['Authorization'] = localStorage.getItem("accessToken")
    local.delete(`/members/follows/${teamId}`).then(success).catch(fail)
}

export {
    requestAllTeamList,
    requestFollowList,
    requestFollow,
    requestUnFollow,
}