import {ref, computed} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {defineStore} from 'pinia'
import axios from 'axios'

import { requestGetCheersData, requestPostCheersData, requestGetNextMatches, 
    requestGetDateMatches, requestGetLeagueList, requestGetLeagueRanking, 
    requestGetTeamDetail, requestGetMatchWatchable, requestGetMatchRealTimeData, 
    requestGetMatchHistory, reqeustGetOneCheerData } from "@/api/football"

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
    const fixtureIdForParty = ref(null);
    const teamIdsForParty = ref({home: null, homeName:null, away: null, awayName: null});
    const oneCheerData = ref([]);
    const matchHistory = ref(
        {
            home: [],
            away: [],
        }
    );

    const getCheersData = () => {
        return new Promise((resolve, reject) => {
            requestGetCheersData(
                (res) => {
                    // console.log(res)
                    if(res.status === httpStatusCode.OK) {
                        // console.log("히히 응원 정보 가져오기 발사")
                        cheersData.value = res.data.data;
                        // console.log(cheersData.value)
                        resolve(res.data.data);
                    } else {
                        reject(new Error("응답 상태가 OK가 아님"));
                    }   
                },
                (error) => {
                    // console.log("응원 정보 가져오는데 에러")
                    if(error.response.status === httpStatusCode.NOTFOUND) {
                        // console.log("***********비상***********")
                        console.error(error)
                        // alert("응원 정보 가져오기 실패!")
                    }
                    reject(error);
                }
            )
        }
    )}

    const postCheersData = async (data) => {
        return new Promise((resolve, reject) => {
            requestPostCheersData(
                data,
                (res) => {
                    // console.log(res);
                    if (res.status === httpStatusCode.CREATE) {
                        // console.log("히히 응원 정보 등록하기 발사");
                        // console.log(res.data.data);
                        // cheersData.value = res.data.data;
                        resolve(res.data.data); // 성공한 경우, 결과 데이터를 resolve로 반환
                    } else {
                        reject(new Error("응답 상태가 CREATE가 아님")); // CREATE 상태 코드가 아니면 reject
                    }
                },
                (error) => {
                    // console.log("응원 정보 등록하는데 에러");
                    if (error.response.status === httpStatusCode.NOTFOUND) {
                        // console.log("***********비상***********");
                        console.error(error);
                        // alert("응원 정보 등록하기 실패!");
                    }
                    reject(error); // 에러 발생 시 reject로 에러 객체 반환
                }
            );
        });
    };

    const getNextMatches = () => {
        requestGetNextMatches(
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    // console.log("히히 다음 경기 정보 가져오기 발사")
                    nextMatches.value = res.data.data;
                    // console.log(nextMatches.value)
                }
            },
            (error) => {
                // console.log("다음 경기 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    // console.log("***********비상***********")
                    console.error(error)
                    // alert("다음 경기 정보 가져오기 실패!")
                }
            }
        )
    }

    const getDateMatches = (date) => {
        requestGetDateMatches(
            date,
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    // console.log("히히 특정 날짜 경기 정보 가져오기 발사")
                    dateMatches.value = res.data.data;
                    // console.log(dateMatches.value)
                }
            },
            (error) => {
                // console.log("특정 날짜 경기 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    // console.log("***********비상***********")
                    console.error(error)
                    // alert("특정 날짜 경기 정보 가져오기 실패!")
                }
            }
        )
    }

    const getLeagueList = () => {
        requestGetLeagueList(
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    // console.log("히히 리그 목록 가져오기 발사")
                    leagueList.value = res.data.data;
                    // console.log(leagueList.value)
                }
            },
            (error) => {
                // console.log("리그 목록 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    // console.log("***********비상***********")
                    console.error(error)
                    // alert("리그 목록 가져오기 실패!")
                }
            }
        )
    }

    const getLeagueRanking = (leagueId) => {
        requestGetLeagueRanking(
            leagueId,
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    // console.log("히히 리그 순위 가져오기 발사")
                    leagueRanking.value = res.data.data;
                    // console.log(leagueRanking.value)
                }
            },
            (error) => {
                // console.log("리그 순위 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    // console.log("***********비상***********")
                    console.error(error)
                    // alert("리그 순위 가져오기 실패!")
                }
            }
        )
    }

    const getTeamDetail = (teamId) => {
        return new Promise((resolve, reject) => {
            requestGetTeamDetail(
                teamId,
                (res) => {
                    // console.log(res)
                    if(res.status === httpStatusCode.OK) {
                        // console.log("히히 팀 상세 정보 가져오기 발사")
                        teamDetail.value = res.data.data;
                        // console.log(teamDetail.value)
                        resolve(res.data.data);
                    } else {
                        reject(new Error("응답 상태가 OK가 아님"));
                    }
                },
                (error) => {
                    // console.log("팀 상세 정보 가져오는데 에러")
                    if(error.response.status === httpStatusCode.NOTFOUND) {
                        // console.log("***********비상***********")
                        console.error(error)
                        // alert("팀 상세 정보 가져오기 실패!")
                    }
                    reject(error);
                }
            )
        }
    )}

    const getMatchWatchable = (startDate, endDate) => {
        requestGetMatchWatchable(
            startDate,
            endDate,
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    // console.log("히히 시청 가능한 경기 정보 가져오기 발사")
                    matchWatchable.value = res.data.data;
                    // console.log(matchWatchable.value)
                }
            },
            (error) => {
                // console.log("시청 가능한 경기 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    // console.log("***********비상***********")
                    console.error(error)
                    // alert("시청 가능한 경기 정보 가져오기 실패!")
                }
            }
        )
    }

    const getMatchRealTimeData = (fixtureId) => {
        return new Promise((resolve, reject) => {
        requestGetMatchRealTimeData(
            fixtureId,
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    // console.log("히히 경기 실황 정보 가져오기 발사")
                    matchRealTimeData.value = res.data.body.data;
                    // console.log(matchRealTimeData.value)
                    resolve(res.data.data);
                } else {
                    reject(new Error("응답 상태가 OK가 아님"));
                }
            },
            (error) => {
                // console.log("경기 실황 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    // console.log("***********비상***********")
                    console.error(error)
                    // alert("경기 실황 정보 가져오기 실패!")
                }
                reject(error);
            }
        )
    })}

    const findTeamIdsByFixtureId = (fixtureId) => {
        const matches = matchWatchable.value;
        for(let i=0; i<matches.length; i++) {
            if(matches[i].fixtureId === fixtureId) {
                const match = matches[i];
                teamIdsForParty.value.home = match.homeTeam.seasonLeagueTeamId;
                teamIdsForParty.value.homeName = match.homeTeam.nameKr;
                teamIdsForParty.value.away = match.awayTeam.seasonLeagueTeamId;
                teamIdsForParty.value.awayName = match.awayTeam.nameKr;
                return teamIdsForParty.value;
            }
        } return "not found";
    }
    
    const getMatchHistory = (name,home='home',type="팀") => {
        requestGetMatchHistory(
            name,type,
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    // console.log("히히 경기 기록 정보 가져오기 발사")
                    // console.log(res.data.data)
                    if (home === 'home') {
                        matchHistory.value.home = res.data.data;
                    } else {
                        matchHistory.value.away = res.data.data;
                    }
                }
            },
            (error) => {
                // console.log("경기 기록 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    // console.log("***********비상***********")
                    console.error(error)
                    // alert("경기 기록 정보 가져오기 실패!")
                    return null;
                }
            }
        )
    }

    const getCheerData = async (fixtureId) => {
        // 단일 경기 응원 정보 가져오기
        reqeustGetOneCheerData(
            fixtureId,
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    // console.log("히히 특정 경기 응원 정보 가져오기 발사")
                    // console.log(res.data.data)
                    oneCheerData.value = res.data.data;
                }
            },
            (error) => {
                // console.log("특정 경기 응원 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    // console.log("***********비상***********")
                    console.error(error)
                    // alert("특정 경기 응원 정보 가져오기 실패!")
                    oneCheerData.value = [];
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
        findTeamIdsByFixtureId,
        getMatchHistory,
        getCheerData,
        cheersData,
        nextMatches,
        dateMatches,
        leagueList,
        leagueRanking,
        teamDetail,
        matchWatchable,
        matchRealTimeData,
        fixtureIdForParty,
        teamIdsForParty,
        oneCheerData,
        matchHistory,
    }
})
