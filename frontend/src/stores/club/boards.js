import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status"

import {requestBoardList, requestSearchBoard, requestCreateBoard, requestUpdateBoard,requestDeleteBoard} from '@/api/board'

export const useBoardStore = defineStore("board",() => {

    const boardList = ref([]);
    const boardDetail = ref({});

    const getBoardList = (clubId) => {
        requestBoardList(
            clubId,
            (res) => {
                console.log("히히 게시판 조회 발사")
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    boardList.value = res.data.data;
                    console.log("******* 게시판에 있는 게시물 전체 조회*******")
                    console.log(boardList.value)
                }
            },
            (error) => {
                console.log(error);
            }
        )
    }

    const getBoardDetail = (boardId) => {
        requestSearchBoard(
            boardId,
            (res) => {
                console.log("히히 게시글 상세 조회 발사")
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    boardDetail.value = res.data.data;
                    console.log("*******게시글 단 하나*******")
                    console.log(boardDetail.value)
                    alert(res.data.status)
                }
            },
            (error) => {
                console.log(error);
            }
        )
    }

    const createBoard = (data) => {
        requestCreateBoard(
            data,
            (res) => {
                console.log("히히 게시글 작성 발사")
                console.log(res)
                if(res.data.status === httpStatusCode.CREATE) {
                    console.log("*******게시글이 잘 작성 됨*******")
                    console.log(res.data.data)
                    console.log(res.data.data["updated_time"])
                    const clubId = res.data.data["club_id"] // dataForm이므로 data.get()을 써야
                    getBoardList(clubId);
                    alert("게시글 작성 완료")
                    window.location.replace(`/club/${clubId}/board`)
                }
            },
            (error) => {
                console.log(error);
            }
        )
    }

    const updateBoard = (data) => {
        requestUpdateBoard(
            data,
            (res) => {
                console.log("히히 게시글 수정 발사")
                console.log(res)
                console.log(res.status)
                if(res.status === httpStatusCode.OK) {
                    console.log("*******게시글이 잘 수정 됨*******")
                    console.log(res.data);
                    const clubId = res.data.data["club_id"];
                    console.log("clubId가 잘 뜨나??? ->", clubId)
                    getBoardList(clubId);
                    alert("게시글 수정 완료")
                    window.location.reload(`/club/${clubId}/board`)
                }
            },
            (error) => {
                console.log(error);
                console.log(error.data)
                alert("게시글 수정 실패!")
            }
        )
    }

    const deleteBoard = (boardId,clubId) => {
        requestDeleteBoard(
            boardId,
            (res) => {
                console.log("히히 게시글 삭제 발사")
                console.log(res)
                if(res.status === httpStatusCode.OK) {
                    console.log("*******게시글이 잘 삭제 됨*******")
                    alert("게시글 삭제 완료")
                    getBoardList(clubId);
                }
            },
            (error) => {
                console.log(error);
            }
        )
    }


    return {
        boardList,boardDetail,
        getBoardList ,getBoardDetail,createBoard,updateBoard,deleteBoard
    }
})