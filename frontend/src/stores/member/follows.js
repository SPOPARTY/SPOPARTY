import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';
import {requestAllTeamList,requestFollowList,requestFollow,requestUnFollow,} from "@/api/follow"
import axios from "axios";

import {httpStatusCode} from "@/util/http-status"

export const useFollowStore = defineStore("follow", () => {
    const router = useRouter();
    const route = useRoute();

    const memberId = sessionStorage.getItem("id") // 나중에 입력받은 memberId로 바꿔야함
    const teamList = ref(null);
    const followList = ref(null);

    const getTeamList = () => {
        requestAllTeamList(
            ({data,status}) => {
                console.log("히히 모든 팀 가져오기 발사")
                // console.log(data.data)
                if(status === httpStatusCode.OK) {
                    teamList.value = data.data
                    console.log(teamList.value)
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
                console.log("히히 팔로우 리스트 발사")
                followList.value = data.data;
                console.log(followList.value)
            }
        }),
        (error) => {
            console.log("팔로우 리스트 가져오는데 에러")
            if(error.response.status === httpStatusCode.NOTFOUND) {
                console.log("***********비상***********")
                console.err(err);
                alert("팔로우 리스트 가져오기 실패!")
            }
        }

    }

    const doFollow = (data) => {
        requestFollow(
            data,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.CREATE) {
                    getFollowList(memberId);
                    alert("팔로우 완료!")
                }
            },
            (error) => {
                console.log(error)
                if(error.status === httpStatusCode.NOTFOUND) {
                    console.log("비상!!!!")
                    console.log(err);
                    alert("팔로우 실패!")
                }
            }

        )
    }

    const doUnFollow = (teamId) => {
        requestUnFollow(
            teamId,
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    getFollowList(memberId);
                    alert("팔로우 취소 완료")
                    
                }
            },
            (error) => {
                if(error.status === 400) {
                    console.log(error)
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