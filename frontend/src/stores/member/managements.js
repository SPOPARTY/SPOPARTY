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
    const memberId = ref(null);


    const login = async(loginMember) => {
        await memberConfirm(
            loginMember,
            (response) => {
                if(response.status === httpStatusCode.OK) {
                    // console.log(response)
                    let data = response;
                    let accessToken = data["headers"]["accesstoken"];
                    let refreshToken = data["headers"]["refreshtoken"];
                    isLogin.value = true;
                    isLoginError.value = false;
                    isValidToken.value = true;
                    let decodedToken = jwtDecode(accessToken);
                    localStorage.setItem('accessToken',accessToken);
                    localStorage.setItem('refreshToken',refreshToken);
                    localStorage.setItem("id",decodedToken.id);
                    memberId.value = localStorage.getItem("id");
                    window.location.replace("/")
                } 
                else{
                    isLogin.value = false;
                    isLoginError.value = true;
                    isValidToken.value = true;
                }
            },
            (error) => {
                // console.log("*********비상!!!*********")
                console.log(error)
                if (error.response.status === httpStatusCode.UNAUTHORIZED) {
                    alert("관련 회원정보가 존재하지 않습니다!")
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
                    alert("임시 비밀번호가 생성되었습니다. ")
                    return true;
                }
            },
            (error) => {
                console.log(error)
                if (error.response.status === httpStatusCode.BAD_REQUEST) {
                    alert("임시 비밀번호 생성에 실패했습니다!")
                }
                if (error.response.status === httpStatusCode.NOTFOUND) {
                    alert("해당 회원정보가 존재하지 않습니다!")
                }
            }
            
        )
    }

    const logout = async () => {
        // console.log("히히 logout발사")
        await memberLogout(
            (response) => {
                console.log("로그아웃 성공!")
                console.log(response)
                if(response.status === httpStatusCode.OK) {
                    localStorage.removeItem("accessToken");
                    localStorage.removeItem("refreshToken");
                    localStorage.removeItem("id")
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