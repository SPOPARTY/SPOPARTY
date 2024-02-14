import {ref, computed} from 'vue';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status";

import {requestVoteDetail, requestOngoingVoteList, requestFinishedVoteList, requestMyVoteList} from '@/api/vote';

export const useVoteStore = defineStore("vote", () => {

    const voteDetail = ref({});
    const ongoingVoteList = ref([]);
    const finishedVoteList = ref([]);
    const myVoteList = ref([]);

    const currentFinishedVote = ref({})
    // 진행 중인 투표 조회
    const getOngoingVoteList = (partyId) => {
        requestOngoingVoteList(partyId,
            (res) => {
                if (res.data.status === httpStatusCode.OK) {
                    // console.log("*******진행 중인 투표 조회******")
                    // console.log(res)
                    ongoingVoteList.value = res.data.data;
                }
            },
            (error) => {
                // console.log("*******진행 중인 투표 조회 실패*******")
                console.error(error);
            }
        ) 
    }

    // 종료된 투표 조회
    const getFinishedVoteList = (partyId) => {
        requestFinishedVoteList(partyId,
            (res) => {
                if (res.data.status === httpStatusCode.OK) {
                    // console.log("*******종료된 투표 조회*******")
                    // console.log(res)
                    finishedVoteList.value = res.data.data;
                }
            },
            (error) => {
                // console.log("*********종료된 투표 조회 실패**********")
                console.error(error);
            }
        )
    }

    // 내가 만든 투표 조회
    const getMyVoteList = (partyId, memberId) => {
        requestMyVoteList(partyId, memberId,
            (res) => {
                if (res.data.status === httpStatusCode.OK) {
                    // console.log("*********내가 만든 투표 조회*********");
                    // console.log(res)
                    myVoteList.value = res.data.data;
                }
            },
            (error) => {
                // console.log("**********내가 만든 투표 조회*************")
                console.error(error);
            }
        )
    }


    // 투표 상세 조회
    const getVoteDetail = (partyId, voteId) => {
        requestVoteDetail(partyId, voteId,
        (res) => {
            if(res.data.status === httpStatusCode.OK) {
                // console.log("*******투표 상세 조회 성공*******");
                // console.log(res)
                voteDetail.value = res.data.data;
            }
        }, 
        (error) => {
            // console.log("*****투표 상세 조회 실패*******")
            console.error(error);
        }
        )
    }



    return {
        voteDetail, ongoingVoteList, finishedVoteList, myVoteList, 
        getOngoingVoteList, getFinishedVoteList, getMyVoteList, getVoteDetail
    }
})