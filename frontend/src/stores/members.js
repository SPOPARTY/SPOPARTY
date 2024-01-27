import {ref, computed} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {defineStore} from 'pinia'
import { jwtDecode } from 'jwt-decode'
import axios from 'axios'

import {memberConfirm, registMember} from "@/api/members"
import {httpStatusCode} from "@/util/http-status"

export const useMemberStore = defineStore("memberStore",() => {
    const router = useRouter();
    const route = useRoute();

    const isLogin = ref(false);
    const memberInfo = ref(null);
    const isLoginError = ref(false);
    const isValidToken = ref(false);
    const memberId = ref(null)

    const login = async(loginMember) => {
        await memberConfirm(
            loginMember,
            (response) => {
                console.log("여기까지 잘 왔나")
                if(response.status === httpStatusCode.OK) {
                    let data = response;
                    let accessToken = data["headers"]["accesstoken"];
                    let refreshToken = data["headers"]["refreshtoken"];
                    console.log("히히 access-token 발사 -> ", accessToken);
                    console.log("히히 refresh-token 발사 -> ", refreshToken)
                    isLogin.value = true;
                    isLoginError.value = false;
                    isValidToken.value = true;
                    let decodedToken = jwtDecode(accessToken);
                    console.log("히히 decoded-token 발사 -> ",decodedToken);
                    sessionStorage.setItem('accessToken',accessToken);
                    sessionStorage.setItem('refreshToken',refreshToken);
                    sessionStorage.setItem("id",decodedToken.id);
                    memberId.value = sessionStorage.getItem("id");
                    router.push("/");
                    // getMemberInfo(accessToken)
                } else{
                    console.log("히히 로그인 실패 발사")
                    isLogin.value = false;
                    isLoginError.value = true;
                    isValidToken.value = true;
                }
            },
            (error) => {
                console.log("*********비상!!!*********")
                console.log(error);
            }
        )
    }

    const getMemberInfo = () => {
        
    }

    const logout = () => {
        axios.delete(
            "http://localhost:9090/members/logout",{
            headers : {
                "Authorization" : sessionStorage.getItem("accessToken")
            }
        })
        .then((res) => {
            console.log(res);
            sessionStorage.removeItem("accessToken");
            sessionStorage.removeItem("refreshToken");
            sessionStorage.removeItem("id")
            isLogin.value = false;
            memberInfo.value = null;
            isValidToken.value = null;
            memberId.value = null;
        })
        .catch((err) => {
            console.error(err);
            console.log("*******비상*******")
        })
    }

    // const doLogout = async () => {
    //     console.log("히히 logout발사")
    //     await logout(
    //         (response) => {
    //             if(response.status === httpStatusCode.OK) {
    //                 sessionStorage.removeItem("accessToken");
    //                 sessionStorage.removeItem("refreshToken");
    //                 sessionStorage.removeItem("id")
    //                 isLogin.value = false;
    //                 memberInfo.value = null;
    //                 isValidToken.value = null;
    //                 memberId.value = null;
    //             } else {
    //                 console.error("유저 정보 X")
    //             }
    //         } , 
    //         (error) => {
    //             console.log(error);
    //         }
    //     )
    // }

    return {
        isLogin,
        isLoginError,
        memberInfo,
        isValidToken,
        login,
        getMemberInfo,
        logout,

    }
})