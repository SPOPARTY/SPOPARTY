import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';
import {requestAllTeamList,requestFollowList,requestFollow,requestUnFollow,} from "@/api/follow"
import axios from "axios";

import {httpStatusCode} from "@/util/http-status"

export const useFollowStore = defineStore("follow", () => {
    const router = useRouter();
    const route = useRoute();

    const memberId = localStorage.getItem("id") // 나중에 입력받은 memberId로 바꿔야함
    const teamList = ref(null);
    const followList = ref(null);

    const getTeamList = () => {
        requestAllTeamList(
            ({data,status}) => {
                // console.log("***********히히 모든 구단 목록 조회********")
                // console.log(data.data)
                if(status === httpStatusCode.OK) {
                    teamList.value = data.data
                    // console.log(teamList.value)
                    return teamList.value;
                }
            },
            (error) => {
                console.error(error)
                if(error.status === httpStatusCode.NOTFOUND) {
                    console.log(error)

                }
            }
        )
    }


    const getFollowList = (memberId) => {
        requestFollowList(
        memberId,
        ({data,status}) => {
            console.log(data)
            if(status === httpStatusCode.OK) {
                followList.value = data.data;
                // console.log(followList.value)
            }
        }),
        (error) => {
            if(error.response.status === httpStatusCode.NOTFOUND) {
                console.err(err);
                alert("팔로우 리스트 가져오기 실패!")
            }
        }

    }
    // teamId를 받아서 memberId와 함께 팔로우 요청을 보내는 함수
    const doFollow = (teamId) => {
        const data = {
            memberId: localStorage.getItem("id"),
            teamId: teamId
        }
        requestFollow(
            data,
            (res) => {
                // console.log(res)
                if(res.status === httpStatusCode.CREATE) {
                    getFollowList(memberId);
                    alert("팔로우 완료!")
                }
            },
            (error) => {
                // console.log(error)
                if(error.status === httpStatusCode.NOTFOUND) {
                    // console.log(err);
                    alert("팔로우 실패!")
                }
            }

        )
    }
    // teamId를 받아서 followTeamId를 찾아서 팔로우 취소 요청을 보내는 함수
    const doUnFollow = (teamId) => {
        getFollowList(localStorage.getItem("id"));

        const followTeamId = followList.value
        .filter((club) => club.teamId === teamId)
        .map((club) => club.id)[0];

        requestUnFollow(
            followTeamId,
            (res) => {
                if(res.status === httpStatusCode.OK) {
                    getFollowList(memberId);
                    alert("팔로우 취소 완료")
                    
                }
            },
            (error) => {
                if(error.status === 400) {
                    // console.log(error)
                    alert("팔로우 취소 실패!")
                }
            }
        )
    }

    return {
        getTeamList,
        getFollowList,
        doFollow,
        doUnFollow,
        teamList,
        followList,
    }

})