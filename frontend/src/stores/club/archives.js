import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status"

import {requestArchiveList, requestArchiveDetail, requestCreateArchive, requestUpdateArchive, 
    requestDeleteArchive, requestCreateImage} from '@/api/archive'

export const useArchiveStore = defineStore("archive",() => {
    const archiveList = ref([]);
    const archiveDetail = ref({});
    const imgThumbnailId = ref(null);

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
    
    const createImage = (url) => {
        const data = {
            url: url,
        }
        return new Promise((resolve, reject) => {
            requestCreateImage(
                data,
                (res) => {
                    if (res.status === httpStatusCode.CREATE) {
                        // console.log("********이미지가 아주 잘 저장됨******")
                        // console.log(res.data.data)
                        // {
                        //     "createdTime": "2024-02-13 12:04:31.076883",
                        //     "updatedTime": "2024-02-13 12:04:31.076883",
                        //     "id": 127,
                        //     "type": "image",
                        //     "url": "https://spoparty-bucket.s3.ap-northeast-2.amazonaws.com/1707793471062",
                        //     "thumbnailUrl": "",
                        //     "deleted": false
                        // }
                        imgThumbnailId.value = res.data.data.id;
                        resolve(imgThumbnailId.value);
                    }
                },
                (error) => {
                    console.log(error);
                    reject(error);
                }
            )
        })
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
        createImage,
        archiveList,
        archiveDetail,
        imgThumbnailId,
    }
})