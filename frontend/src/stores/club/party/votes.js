import {ref, computed} from 'vue';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status";

import {requestCreateVote, requestCastVote, requestFinishVote, requestVoteDetail,
    requestOngoingVoteList, requestFinishedVoteList, requestMyVoteList} from '@/api/vote';

export const useVoteStore = defineStore("vote", () => {

    const voteDetail = ref({});
    const ongoingVoteList = ref([]);
    const finishtedVoteList = ref([]);
    const myVoteList = ref([]);

    // 진행 중인 투표 조회
    const getOngoingVoteList = (partyId) => {
        requestOngoingVoteList(partyId,
            (res) => {
                if (res.data.status === httpStatusCode.OK) {
                    console.log("*******진행 중인 투표 조회******")
                    ongoingVoteList.value = res.data.data;
                }
            },
            (error) => {
                console.log("*******진행 중인 투표 조회 실패*******")
                console.error(error);
            }
        ) 
    }

    // 종료된 투표 조회
    const getFinishedVoteList = (partyId) => {
        requestFinishedVoteList(partyId,
            (res) => {
                if (res.data.status === httpStatusCode.OK) {
                    console.log("*******종료된 투표 조회*******")
                    finishtedVoteList.value = res.data.data;
                }
            },
            (error) => {
                console.log("*********종료된 투표 조회 실패**********")
                console.error(error);
            }
        )
    }

    // 내가 만든 투표 조회
    const getMyVoteList = (partyId, memberId) => {
        requestMyVoteList(partyId, memberId,
            (res) => {
                if (res.data.status === httpStatusCode.OK) {
                    console.log("*********내가 만든 투표 조회*********");
                    myVoteList.value = res.data.data;
                }
            },
            (error) => {
                console.log("**********내가 만든 투표 조회*************")
                console.error(error);
            }
        )
    }


    // 투표 상세 조회
    const getVoteDetail = (partyId, voteId) => {
        requestVoteDetail(partyId, voteId,
        (res) => {
            if(res.data.status === httpStatusCode.OK) {
                console.log("*******투표 상세 조회 성공*******");
                console.log(res.data.data)
                voteDetail.value = res.data.data;
            }
        }, 
        (error) => {
            console.log("*****투표 상세 조회 실패*******")
            console.error(error);
        }
        )
    }

    // 투표 생성
    const createVote = (partyId, data) => {
        requestCreateVote(
            partyId,data,
            (res) => {
                if(res.data.status === httpStatusCode.CREATE) {
                    console.log("******투표 생성*****")
                    alert(res.data.message);

                }
            },
            (error) => {
                console.log(error);
                alert("투표 생성 실패!")
            }
        )
    }

    // 투표 하기
    const castVote = (partyId, voteId, data) => {
        requestCastVote(
            partyId, voteId, data,
            (res) => {
                if(res.data.status === httpStatusCode.OK) {
                    console.error("******투표 완료******")
                    alert("투표 완료");

                }
            },
            (error) => {
                console.error(error);
                alert("투표 실패!")
            }
        )
    }

    // 투표 종료
    const finishVote = (partyId, voteId, data) => {
        requestFinishVote(partyId, voteId, data,
            (res) => {
                if(res.data.status === httpStatusCode.OK) {
                    console.log("******투표 종료*******")
                    alert(res.data.message);

                }
            },
            (error) => {
                console.error(error);
                alert("투표 종료 실패!")
            }
        ) 
    }


    return {
        voteDetail, ongoingVoteList, finishtedVoteList, myVoteList, 
        getOngoingVoteList, getFinishedVoteList, getMyVoteList, getVoteDetail, createVote, castVote, finishVote
    }
})