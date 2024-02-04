import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status"

import {requestRecentClubs, requestCreateClubs, requestClubInfo, requestUpdateClubName, requestDeleteClub,
    requestClubInviteLink, requestClubMemberList, requestClubInvite, requestUpdateClubLeader, requestDeleteClubMember} from "@/api/club"


export const useClubStore = defineStore("club",() => {

    const myClubs = ref({});
    const createdClub = ref(null);
    const clubInfo = ref({});
    const clubInviteLink = ref(null);
    const clubMemberList = ref([]);
    const memberId = ref(sessionStorage.getItem("id"));

    // 최근 활동 그룹 목록 조회
    const requestClub = () => {
        requestRecentClubs(
            (res) => {
                // console.log("히히 최근 활동 그룹 발사")
                // console.log(res)
                if(res.status === httpStatusCode.OK) {
                    myClubs.value = res.data.data;
                    // console.log("*******최근 활동이 있는클럽 목록*******")
                    // console.log(myClubs.value)
                    // console.log(myClubs.value);
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
                if (res.data.status === httpStatusCode.CREATE) {
                    console.log("히히 그룹 생성!"); 
                    createdClub.value = res.data.data;
                    console.log("내가 만든 클럽~")
                    console.log(createdClub.value)
                    requestClub();
                    window.location.replace("/");
                }
            },
            (error) => {
                alert("그룹 생성 실패!")
            }
        )
    }

    // 그룹 조회
    const getClubInfo = (clubId) => {
        requestClubInfo(
            clubId,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("********히히 그룹 정보!********")
                    clubInfo.value = res.data.data;
                    console.log("지금 내가 있는 클럽~~")
                    console.log(clubInfo.value)
                }
            },
            (error) => {
                console.log(error)
            }
        )
    }

    // 그룹명 수정
    const updateClub = (clubId, data) => {
        return new Promise((resolve, reject) => { // 함수 내부에서 값을 반환하지 않고 콜백함수에서 값을 처리해야한다.
            requestUpdateClubName(
                clubId, data,
                (res) => {
                    console.log(res);
                    if (res.data.status === httpStatusCode.OK) {
                        console.log("******히히 그룹 정보 수정~*********");
                        resolve(true); // 성공 시 true를 리턴 -> 비동기 콜백함수에서 값을 처리하도록...
                        getClubInfo(clubId);
                    } else {
                        reject("그룹 정보 수정 실패"); 
                    }
                },
                (error) => {
                    console.log(error);
                    reject("그룹 정보 수정 실패"); 
                }
            );
        });
    };

    // 그룹 삭제(그룹장 권한)
    const deleteClub = (clubId) => {
        return new Promise((resolve,reject) => {

            requestDeleteClub(
                clubId,
                (res) => {
                    console.log(res)
                    if (res.data.status === httpStatusCode.OK) {
                        console.log("******히히 그룹 삭제*********")
                        resolve(true)
                        // window.location.replace("/"); // 삭제되면 메인으로 이동
                    }
                },
                (error) => {
                    console.log(error)
                    reject("그룹 삭제 실패!")
                }
            )
        }) 
    }

    // 그룹 초대 링크
    const getClubInviteLink = (clubId) => {
        requestClubInviteLink(
            clubId,
            (res) => {
                console.log(res)
                if (res.data.status === httpStatusCode.OK) {
                    console.log("******히히 그룹 초대 링크 생성*********")
                    console.log(res.data.data.inviteUrl);
                    clubInviteLink.value = res.data.data.inviteUrl
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
                    clubMemberList.value = res.data.data
                    console.log(clubMemberList.value)
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
        return new Promise((resolve,reject) => {
            requestClubInvite(
                data,
                (res) => {
                    console.log(res)
                    if (res.data.status === httpStatusCode.OK) {
                        console.log("******히히 그룹원 초대 후 생성*********")
                        resolve(true)
                    }
                },
                (error) => {
                    if (error.response.status === httpStatusCode.BAD_REQUEST){
                        console.log(error)
                        alert("")
                        reject("그룹원 초대 실패!")
                    }
                }
            )
        }) 
        
    }

    // 그룹장 물려주기(그룹장 권한)
    const updateClubLeader = (clubId, data) => {
        return new Promise((success,fail) => {
            requestUpdateClubLeader(
                clubId,
                data,
                (res) => {
                    console.log("******히히 그룹장 물려주기*********")
                    console.log(res)
                    if (res.data.status === httpStatusCode.OK) {
                        console.log("그룹장 물려주기 성공!!")
                        success(true)
                    }
                },
                (error) => {
                    console.log(error)
                    alert("그룹장 물려주기 실패!")
                }
            )
        })
    }

    // 그룹원 탈퇴(그룹장 권한)
    const leaveClub = (clubId) => {
        return new Promise((resolve,reject) => {
            requestDeleteClubMember(
                clubId,
                (res) => {
                    console.log(res)
                    if (res.data.status === httpStatusCode.OK) {
                        console.log("******히히 그룹 떠나기*********")
                        resolve(true);
                    }
                },
                (error) => {
                    console.log(error)
                    reject("그룹 떠나기 실패!")
                }
            )
        })
    }


    return {
        myClubs,createdClub, clubInfo, clubInviteLink, clubMemberList,
        requestClub,createClubs,getClubInfo,updateClub, 
        deleteClub,getClubInviteLink,getClubMemberList,clubInvite,
        updateClubLeader,leaveClub,
    }
})