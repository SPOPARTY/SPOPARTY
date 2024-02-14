import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status"

import {requestArchiveList, requestArchiveDetail, requestCreateArchive, requestUpdateArchive, 
    requestDeleteArchive} from '@/api/archive'

export const useArchiveStore = defineStore("archive",() => {
    const archiveList = ref([]);
    const archiveDetail = ref({});

    // 추억 목록 조회
    const getArchiveList = (clubId) => {
        requestArchiveList(
            clubId,
            (res) => {
                // console.log("**********추억 목록 res**********")
                // console.log(res);
                if (res.status === httpStatusCode.OK) {
                    archiveList.value = res.data.data;
                    // console.log("********추억 목록 실제 값******")
                    // console.log(archiveList.value)
                }
            },
            (error) => {
                console.log(error);
            }
            )
    } 

    // 추억 상세 조회
    const getArchiveDetail = (archiveId) => {
        requestArchiveDetail(
            archiveId,
            (res) => {
                // console.log("**********추억 상세 조회 res**********")
                // console.log(res);
                if (res.status === httpStatusCode.OK) {
                    archiveDetail.value = res.data.data;
                    // console.log("********추억 상세 조회 실제 값******")
                    // console.log(archiveDetail.value)
                }
            },
            (error) => {
                console.log(error);
            }
        )
    } 

    // 추억 생성
    const createArchive = (data) => {
        // console.log("******생성될 추억 정보******")
        // console.log(data)
        requestCreateArchive(
            data,
            (res) => {
                if (res.status === httpStatusCode.CREATE) {
                    // console.log("********추억이 아주 잘 저장됨******")
                    alert("추억이 잘 간직되었습니다!")
                }
            },
            (error) => {
                console.log(error);
            }
        )
    }

    // 추억 수정
    const updateArchive = (data) => {
        // console.log("*******수정될 추억 정보********")
        // console.log(data)
        requestUpdateArchive(
            data,
            (res) => {
                // console.log("*******추억 수정 res********")
                // console.log(res)
                if (res.status === httpStatusCode.OK) {
                    // console.log("********추억이 아주 잘 수정됨******")
                    alert("추억정보가 잘 수정되었습니다!")
                }
            },
            (error) => {
                console.log(error);
            }
        )
    }

    // 추억 삭제
    const deleteArchive = (archiveId, clubId) => {
        requestDeleteArchive(
            archiveId,
            (res) => {
                // console.log("*******추억 삭제 res********")
                // console.log(res)
                if (res.status === httpStatusCode.OK) {
                    // console.log("********추억이 아주 잘 삭제됨******")
                    alert("추억이 사라졌습니다!")
                    getArchiveList(clubId)
                }
            },
            (error) => {
                console.log(error);
            }

        )
    }

    return {
        getArchiveList,
        getArchiveDetail,
        createArchive,
        updateArchive,
        deleteArchive,
        archiveList,
        archiveDetail,
    }
})