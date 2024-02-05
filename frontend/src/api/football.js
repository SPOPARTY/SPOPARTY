import {localAxios} from '@/util/http-commons';

const API_URL = "football"

const local = localAxios();

function requestGetCheersData(success,fail) {
    // 응원 정보 조회
    local.get(`/${API_URL}/cheers`).then(success).catch(fail);
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

export {
    requestGetCheersData,
    requestGetNextMatches,
    requestGetDateMatches,
    requestGetLeagueList,
    requestGetLeagueRanking,
}