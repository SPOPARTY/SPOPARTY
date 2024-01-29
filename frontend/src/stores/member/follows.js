import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';
import {requestAllTeamList,requestFollowList,requestFollow,requestUnFollow,} from "@/api/follow"
import axios from "axios";

import {httpStatusCode} from "@/util/http-status"

export const useFollowStore = defineStore("follow", () => {
    const router = useRouter();
    const route = useRoute();


    const teamList = ref(null);
    const followList = ref(null);

    const getTeamList = () => {
        requestAllTeamList(
            (res) => {
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("히히 모든 팀 가져오기 발사")
                    teamList.value = res.data
                    console.log(teamList.value)
                    return teamList.value;
                }
            },
            (error) => {
                console.log(error)
                if(error.status === httpStatusCode.NOTFOUND) {
                    console.log(error)

                }
            }
        )
    }


    const getFollowList = (memberId) => {
        requestFollowList(
        memberId,
        (res) => {
            console.log(res)
            if(res.status === httpStatusCode.OK) {
                console.log("히히 팔로우 리스트 발사")
                followList.value = res.data;
                console.log(followList.value)
            }
        }),
        (error) => {
            if(error.status === httpStatusCode.NOTFOUND) {
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
                if(res.status === httpStatusCode.Create) {
                    alert("팔로우 완료!")
                }
            },
            (error) => {
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
                if(res.status === httpStatus.OK) {
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