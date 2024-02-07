import {localAxios} from '@/util/http-commons';

const API_URL = "football"

const local = localAxios();

function requestGetCheersData(success,fail) {
    // 응원 정보 조회
    local.get(`/${API_URL}/cheers`).then(success).catch(fail);
}

function requestPostCheersData(data,success,fail) {
    // 응원 정보 등록
    local.post(`/${API_URL}/cheers`,data).then(success).catch(fail);
}

function requestGetNextMatches(success,fail) {
    // 다음 6 경기 정보 조회
    local.get(`/${API_URL}/fixtures?next=6`).then(success).catch(fail);
}

function requestGetDateMatches(date,success,fail) {
    // 특정 날짜 경기 정보 조회
    // date: YYYY-MM-DD
    local.get(`/${API_URL}/fixtures?date=${date}`).then(success).catch(fail);
}

function requestGetLeagueList(success,fail) {
    // 리그 목록 조회
    local.get(`/${API_URL}/leagues`).then(success).catch(fail);
}

function requestGetLeagueRanking(leagueId,success,fail) {
    // 리그 별 순위 조회
    local.get(`/${API_URL}/leagues/rank/${leagueId}`).then(success).catch(fail);
}

function requestGetTeamDetail(teamId,success,fail) {
    // 팀 상세 정보 조회
    local.get(`/${API_URL}/teams?teamId=${teamId}`).then(success).catch(fail);
}

function requestGetMatchWatchable(startDate,endDate,success,fail) {
    // 시청 가능한 경기 정보 조회
    local.get(`/${API_URL}/fixtures?startDate=${startDate}&endDate=${endDate}`).then(success).catch(fail);
}

function requestGetMatchRealTimeData(fixtureId,success,fail) {
    // 경기 실황 정보 조회
    local.get(`/${API_URL}/fixtures/events?fixtureId=${fixtureId}`).then(success).catch(fail);
}

function requestGetMatchHistory(Id,type,success,fail) {
    // 경기 기록 정보 조회
    // Id: 팀 ID, 리그 ID
    // type: 팀, 리그
    local.get(`/${API_URL}/fixtures?keyword=${Id}&type=${type}`).then(success).catch(fail);
}

function reqeustGetOneCheerData(fixtureId,success,fail) {
    // 특정 경기 응원 정보 조회
    local.get(`/${API_URL}/cheers?fixtureId=${fixtureId}`).then(success).catch(fail);
}

export {
    requestGetCheersData,
    requestPostCheersData,
    requestGetNextMatches,
    requestGetDateMatches,
    requestGetLeagueList,
    requestGetLeagueRanking,
    requestGetTeamDetail,
    requestGetMatchRealTimeData,
    requestGetMatchWatchable,
    requestGetMatchHistory,
    reqeustGetOneCheerData,
}