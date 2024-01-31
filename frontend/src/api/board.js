import {localAxios} from '@/util/http-commons';

const local = localAxios();

const API_URL = "/boards"

// 게시판에 있는 게시물 전체 조회
function requestBoardList(clubId,success,fail) {
    local.get(`${API_URL}/clubs/${clubId}`).then(success).catch(fail);
}

// 게시글 상세 조회
function requestSearchBoard(boardId, success, fail) {
    local.get(`${API_URL}/${boardId}`).then(success).catch(fail);
}

// 게시글 작성
function requestCreateBoard(data,success,fail) {
    local.defaults.headers["Content-type"] = "multipart/form-data"
    local.post("/boards",data).then(success).catch(fail);
}

// 게시글 수정
function requestUpdateBoard(data,success,fail) {
    local.put("/boards",data).then(success).catch(fail);
}

// 게시글 삭제
function requestDeleteBoard(boardId,success, fail) {
    local.delete(`${API_URL}/${boardId}`).then(success).catch(fail);
}


export{
    requestBoardList,
    requestSearchBoard,
    requestCreateBoard,
    requestUpdateBoard,
    requestDeleteBoard,
}