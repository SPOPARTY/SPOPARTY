import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status"

import {requestRecentClubs, requestCreateClubs, requestClubInfo, requestUpdateClubName, requestDeleteClub,
    requestClubInviteLink, requestClubMemberList, requestClubInvite, requestUpdateClubLeader, requestDeleteClubMember} from "@/api/club"


export const useClubStore = defineStore("club",() => {

    const myClubs = ref(null);
    const createdClub = ref(null);
    const clubInfo = ref(null);
    const clubInviteLink = ref(null);
    const clubMemberList = ref(null);

    // 최근 활동 그룹 목록 조회
    const requestClub = () => {
        requestRecentClubs(
            (res) => {
                console.log("히히 최근 활동 그룹 발사")
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    myClubs.value = res.data.data;
                    console.log("*******최근 활동이 있는클럽 목록*******")
                    console.log(myClubs.value)
                    alert(res.data.status)
                }
            },
            (error) => {
                console.log(error);
            }
        )
    }
    
    // 그룹 생성
    const createClubs = (data) => {
        requestCreateClubs(
            data,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("히히 그룹 생성!"); 
                    createdClub.value = res.data.data;
                    console.log("내가 만든 클럽~")
                    console.log(createdClub.value)
                    alert(res.data.status)
                    // 그룹이 생성되면 바로 이동할 수 있게 리다이렉트??
                }
            },
            (error) => {
                alert("그룹 생성 실패!")
            }
        )
    }

    // 그룹 조회
    const getClubInfo = (memberId) => {
        requestClubInfo(
            memberId,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("********히히 그룹 정보!********")
                    clubInfo.value = res.data.data;
                    console.log("지금 내가 있는 클럽~~")
                    console.log(clubInfo.value)
                    alert(res.data.status)
                }
            },
            (error) => {
                console.log(error)
            }
        )
    }

    // 그룹명 수정(그룹장 권한)
    const updateClub = (clubId, data) => {
        requestUpdateClubName(
            clubId,data,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("******히히 그룹 정보 수정~*********")
                    alert(res.data.status)
                    window.location.reload("/");
                }
            },
            (error) => {
                console.log(error)
                alert("그룹 수정 실패!")
            }
        )
    }

    // 그룹 삭제(그룹장 권한)
    const deleteClub = (clubId) => {
        requestDeleteClub(
            clubId,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("******히히 그룹 삭제*********")
                    alert(res.data.status)
                    window.location.replace("/"); // 삭제되면 메인으로 이동
                }
            },
            (error) => {
                console.log(error)
                alert("그룹 삭제 실패!")
            }
        )
    }

    // 그룹 초대 링크
    const getClubInviteLink = (clubId) => {
        requestClubInviteLink(
            clubId,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("******히히 그룹 초대 링크 생성*********")
                    clubInviteLink.value = res.data.data
                    alert(res.data.status)
                }
            },
            (error) => {
                console.log(error)
                alert("그룹 초대 링크 생성 실패!")
            }
        )
    }

    // 그룹원 목록 조회(닉네임, 방장)
    const getClubMemberList = (clubId) => {
        requestClubMemberList(
            clubId,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("******히히 그룹원 목록 조회*********")
                    clubInviteLink.value = res.data.data
                    alert(res.data.status)
                }
            },
            (error) => {
                console.log(error)
                alert("그룹 목록 조회 실패!")
            }
        )
    }

    // 그룹원 초대
    const clubInvite = (data) => {
        requestClubInvite(
            data,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("******히히 그룹원 초대 후 생성*********")
                    alert(res.data.status)
                }
            },
            (error) => {
                console.log(error)
                alert("그룹원 초대 실패!")
            }
        )
    }

    // 그룹장 물려주기(그룹장 권한)
    const updateClubLeader = () => {
        requestUpdateClubLeader(
            clubId,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("******히히 그룹장 물려주기*********")
                    alert(res.data.status)
                }
            },
            (error) => {
                console.log(error)
                alert("그룹원 초대 실패!")
            }
        )
    }

    // 그룹원 탈퇴(그룹장 권한)
    const deleteClubMember = () => {
        requestDeleteClubMember(
            clubId,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("******히히 그룹원 쫒아내기*********")
                    alert(res.data.status)
                }
            },
            (error) => {
                console.log(error)
                alert("그룹원 초대 실패!")
            }
        )
    }


    return {
        myClubs,createdClub, clubInfo, clubInviteLink, clubMemberList,
        requestClub,createClubs,getClubInfo,updateClub, 
        deleteClub,getClubInviteLink,getClubMemberList,clubInvite,
        updateClubLeader,deleteClubMember,
    }
})