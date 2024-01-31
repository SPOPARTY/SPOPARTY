import {ref, computed} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {defineStore} from 'pinia'
import { jwtDecode } from 'jwt-decode'
import axios from 'axios'

import {memberConfirm, memberLogout} from "@/api/member"
import { requestTempPassword } from '@/api/authentication'
import {httpStatusCode} from "@/util/http-status"
const {VITE_REST_API} = import.meta.env;


export const useManagementStore = defineStore("management",() => {
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
                if(response.status === httpStatusCode.OK) {
                    console.log("여기까지 잘 왔나")
                    console.log(response)
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
                    localStorage.setItem('accessToken',accessToken);
                    sessionStorage.setItem('refreshToken',refreshToken);
                    sessionStorage.setItem("id",decodedToken.id);
                    memberId.value = sessionStorage.getItem("id");
                    window.location.replace("/")
                    // getMemberInfo(accessToken)
                } 
                else{
                    console.log("히히 로그인 실패 발사")
                    isLogin.value = false;
                    isLoginError.value = true;
                    isValidToken.value = true;
                }
            },
            (error) => {
                console.log("*********비상!!!*********")
                console.log(error)
                console.log(error.response.status);
                if (error.response.status === httpStatusCode.UNAUTHORIZED) {
                    alert("등록되지 않은 회원입니다!")
                }
            }
        )
    }


    const getMemberInfo = () => {
        
    }

    const tempPassword = (data) => {
        requestTempPassword(
            data,
            (res) => {
                if (res.status === httpStatusCode.OK) {
                    console.log("히히 비밀번호 재발급 발사");
                    alert("임시 비밀번호가 생성되었습니다. ")
                    return true;
                }
            },
            (error) => {
                console.log(error)
                if (error.response.status === 400) {
                    alert("임시 비밀번호 생성에 실패했습니다!")
                }
            }
            
        )
    }

    const logout = async () => {
        console.log("히히 logout발사")
        await memberLogout(
            (response) => {
                if(response.status === httpStatusCode.OK) {
                    localStorage.removeItem("accessToken");
                    sessionStorage.removeItem("refreshToken");
                    sessionStorage.removeItem("id")
                    isLogin.value = false;
                    memberInfo.value = null;
                    isValidToken.value = null;
                    memberId.value = null;
                    window.location.replace("/")
                } else {
                    console.error("유저 정보 X")
                }
            } , 
            (error) => {
                console.log("*******비상*******")
                console.log(error);
            }
        )
    }

    return {
        isLogin,
        isLoginError,
        memberInfo,
        isValidToken,
        login,
        tempPassword,
        getMemberInfo,
        logout,

    }
})