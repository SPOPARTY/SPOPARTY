import {ref, computed} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {defineStore} from 'pinia'
import axios from 'axios'

import { requestGetCheersData, requestGetNextMatches, requestGetDateMatches, requestGetLeagueList, 
requestGetLeagueRanking } from "@/api/football"

import {httpStatusCode} from "@/util/http-status"

export const useFootballStore = defineStore("football",() => {
    const router = useRouter();
    const route = useRoute();

    const cheersData = ref([]);
    const nextMatches = ref([]);
    const dateMatches = ref([]);
    const leagueList = ref([]);
    const leagueRanking = ref([]);

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
    
    return {
        getCheersData,
        getNextMatches,
        getDateMatches,
        getLeagueList,
        getLeagueRanking,
        cheersData,
        nextMatches,
        dateMatches,
        leagueList,
        leagueRanking,
    }
})
