import {ref, computed} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {defineStore} from 'pinia'
import axios from 'axios'

import { requestGetPartyInfo, requestPostPartyInfo, requestPutPartyInfo, requestDeletePartyInfo,
    requestGetPartyMemberList, requestPostPartyMember, requestGetPartyMember, requestDeletePartyMember, } from "@/api/party"

import {httpStatusCode} from "@/util/http-status"

export const usePartyStore = defineStore("party",() => {
    const router = useRouter();
    const route = useRoute();

    const partyInfo = ref([]);
    const openViduSession = ref({});
    const partyMemberList = ref([]);
    const myParticipantId = ref(null);

    const getPartyInfo = async (clubId,partyId) => {
        requestGetPartyInfo(
            clubId,partyId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 파티 정보 가져오기 발사")
                    partyInfo.value = res.data.data;
                    console.log(partyInfo.value)
                    return partyInfo.value;
                }
            },
            (error) => {
                console.log("파티 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    partyInfo.value = [];
                    // alert("파티 정보 가져오기 실패!")
                    return [];
                }
            }
        )
    }

    const postPartyInfo = async (clubId) => {
        requestPostPartyInfo(
            clubId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.CREATE) {
                    console.log("히히 파티 정보 등록하기 발사")
                    console.log(res.data.data)
                    partyInfo.value = res.data.data;
                    return partyInfo.value.partyId;
                }
            },
            (error) => {
                console.log("파티 정보 등록하는데 에러")
                console.log(error)
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("파티 정보 등록하기 실패!")
                }
            }
        )
    }

    const putPartyInfo = (clubId,partyId,title,fixtureUrl,fixtureId) => {
        const data = {
            memberId: localStorage.getItem("id"),
            title: title,
            fixtureUrl: fixtureUrl,
            fixtureId: fixtureId,
        }
        requestPutPartyInfo(
            clubId,partyId,data,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 파티 정보 수정하기 발사")
                    console.log(res.data.data)
                    partyInfo.value = res.data.data;
                    return partyInfo.value;
                }
            },
            (error) => {
                console.log("파티 정보 수정하는데 에러")
                console.log(error)
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("파티 정보 수정하기 실패!")
                }
            }
        )
    }

    const deletePartyInfo = (clubId,partyId) => {
        requestDeletePartyInfo(
            clubId,partyId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 파티 정보 삭제하기 발사")
                    console.log(res.data.data)
                    partyInfo.value = [];
                    return partyInfo.value;
                }
            },
            (error) => {
                console.log("파티 정보 삭제하는데 에러")
                console.log(error)
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("파티 정보 삭제하기 실패!")
                }
            }
        )
    }

    const getPartyMemberList = (clubId,partyId) => {
        requestGetPartyMemberList(
            clubId,partyId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 파티 멤버 리스트 가져오기 발사")
                    partyMemberList.value = res.data.data;
                    return partyMemberList.value;
                }
            },
            (error) => {
                console.log("파티 멤버 리스트 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("파티 멤버 리스트 가져오기 실패!")
                }
            }
        )
    }

    const postPartyMember = (clubId,partyId) => {
        requestPostPartyMember(
            clubId,partyId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.CREATE) {
                    console.log("히히 파티 멤버 추가하기 발사")
                    console.log(res.data.data)
                    getPartyInfo(clubId,partyId);
                    console.log(res.data.data.participantId)
                    myParticipantId.value = res.data.data.participantId;
                }
            },
            (error) => {
                console.log("파티 멤버 추가하는데 에러")
                console.log(error)
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("파티 멤버 추가하기 실패!")
                }
            }
        )
    }

    const getPartyParticipant = (clubId,partyId,participantId) => {
        // 파티원 1명을 조회함
        requestGetPartyMember(
            clubId,partyId,participantId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 파티 참가자 정보 가져오기 발사")
                    partyMemberList.value = res.data.data;
                    return partyMemberList.value;
                }
            },
            (error) => {
                console.log("파티 참가자 정보 가져오는데 에러")
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("파티 참가자 정보 가져오기 실패!")
                }
            }
        )
    }

    const deletePartyMember = (clubId,partyId,participantId) => {
        requestDeletePartyMember(
            clubId,partyId,participantId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 파티 멤버 삭제하기 발사")
                    console.log(res.data.data)
                    partyMemberList.value = res.data.data;
                    partyInfo.value = getPartyInfo(clubId,partyId);
                    return partyMemberList.value;
                }
            },
            (error) => {
                console.log("파티 멤버 삭제하는데 에러")
                console.log(error)
                if(error.response.status === httpStatusCode.NOTFOUND) {
                    console.log("***********비상***********")
                    console.error(error)
                    alert("파티 멤버 삭제하기 실패!")
                }
            }
        )
    }
    

    return {
        partyInfo,
        openViduSession,
        partyMemberList,
        myParticipantId,
        getPartyInfo,
        postPartyInfo,
        putPartyInfo,
        deletePartyInfo,
        getPartyMemberList,
        postPartyMember,
        getPartyParticipant,
        deletePartyMember,
    }
})