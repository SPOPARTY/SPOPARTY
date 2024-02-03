import {ref, computed} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {defineStore} from 'pinia'
import axios from 'axios'

import { requestGetCheersData, requestPostCheersData, requestGetNextMatches, 
    requestGetDateMatches, requestGetLeagueList, requestGetLeagueRanking, 
    requestGetTeamDetail, requestGetMatchWatchable, requestGetMatchRealTimeData } from "@/api/football"

import {httpStatusCode} from "@/util/http-status"

export const useFootballStore = defineStore("football",() => {
    const router = useRouter();
    const route = useRoute();

    const cheersData = ref([]);
    const nextMatches = ref([]);
    const dateMatches = ref([]);
    const leagueList = ref([]);
    const leagueRanking = ref([]);
    const teamDetail = ref([]);
    const matchWatchable = ref([]);
    const matchRealTimeData = ref([]);

    const getCheersData = () => {
        requestGetCheersData(
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 응원 정보 가져오기 발사")
                    cheersData.value = res.data.data;
                    console.log(cheersData.value)
                }
            },
            (error) => {
                console.log("응원 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("응원 정보 가져오기 실패!")
                }
            }
        )
    }

    const postCheersData = async (data) => {
        await requestPostCheersData(
            data,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.CREATE) {
                    console.log("히히 응원 정보 등록하기 발사")
                    console.log(res.data.data)
                    // cheersData.value = [];
                    cheersData.value = res.data.data;
                }
            },
            (error) => {
                console.log("응원 정보 등록하는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("응원 정보 등록하기 실패!")
                }
            }
        )
    }

    const getNextMatches = () => {
        requestGetNextMatches(
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 다음 경기 정보 가져오기 발사")
                    nextMatches.value = res.data.data;
                    console.log(nextMatches.value)
                }
            },
            (error) => {
                console.log("다음 경기 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("다음 경기 정보 가져오기 실패!")
                }
            }
        )
    }

    const getDateMatches = (date) => {
        requestGetDateMatches(
            date,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 특정 날짜 경기 정보 가져오기 발사")
                    dateMatches.value = res.data.data;
                    console.log(dateMatches.value)
                }
            },
            (error) => {
                console.log("특정 날짜 경기 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("특정 날짜 경기 정보 가져오기 실패!")
                }
            }
        )
    }

    const getLeagueList = () => {
        requestGetLeagueList(
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 리그 목록 가져오기 발사")
                    leagueList.value = res.data.data;
                    console.log(leagueList.value)
                }
            },
            (error) => {
                console.log("리그 목록 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("리그 목록 가져오기 실패!")
                }
            }
        )
    }

    const getLeagueRanking = (leagueId) => {
        requestGetLeagueRanking(
            leagueId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 리그 순위 가져오기 발사")
                    leagueRanking.value = res.data.data;
                    console.log(leagueRanking.value)
                }
            },
            (error) => {
                console.log("리그 순위 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("리그 순위 가져오기 실패!")
                }
            }
        )
    }

    const getTeamDetail = (teamId) => {
        requestGetTeamDetail(
            teamId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 팀 상세 정보 가져오기 발사")
                    teamDetail.value = res.data.data;
                    console.log(teamDetail.value)
                }
            },
            (error) => {
                console.log("팀 상세 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("팀 상세 정보 가져오기 실패!")
                }
            }
        )
    }

    const getMatchWatchable = (startDate, endDate) => {
        requestGetMatchWatchable(
            startDate,
            endDate,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 시청 가능한 경기 정보 가져오기 발사")
                    matchWatchable.value = res.data.data;
                    console.log(matchWatchable.value)
                }
            },
            (error) => {
                console.log("시청 가능한 경기 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("시청 가능한 경기 정보 가져오기 실패!")
                }
            }
        )
    }

    const getMatchRealTimeData = (fixtureId) => {
        requestGetMatchRealTimeData(
            fixtureId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 경기 실황 정보 가져오기 발사")
                    matchRealTimeData.value = res.data.data;
                    console.log(matchRealTimeData.value)
                }
            },
            (error) => {
                console.log("경기 실황 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("경기 실황 정보 가져오기 실패!")
                }
            }
        )
    }
    
    return {
        getCheersData,
        postCheersData,
        getNextMatches,
        getDateMatches,
        getLeagueList,
        getLeagueRanking,
        getTeamDetail,
        getMatchWatchable,
        getMatchRealTimeData,
        cheersData,
        nextMatches,
        dateMatches,
        leagueList,
        leagueRanking,
        teamDetail,
        matchWatchable,
        matchRealTimeData,
    }
})
