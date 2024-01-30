import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status"

import {requestRecentClubs, requestCreateClubs, requestClubInfo, requestUpdateClubName, requestDeleteClub,
    requestClubInviteLink, requestClubMemberList, requestClubInvite, requestUpdateClubLeader, requestDeleteClubMember} from "@/api/club"


export const useClubStore = defineStore("club",() => {

    const myClubs = ref(null);

    const requestClub = () => {
        requestRecentClubs(
            (res) => {
                console.log("히히 최근 활동 그룹 발사")
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    myClubs.value = res.data.data[0];
                    console.log("*******클럽 목록*******")
                    console.log(myClubs.value)
                }
            },
            (error) => {
                console.log("******비상*******")
                console.log(error);
            }
        )
    }
    



    return {
        myClubs,
        requestClub,
    }
})