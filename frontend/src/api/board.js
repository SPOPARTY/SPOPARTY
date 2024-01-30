import {localAxios} from '@/util/http-commons';

const local = localAxios();

const API_URL = "/boards"

function requestBoardList(clubId,success,fail) {
    local.get(`${API_URL}/clubs/${clubId}`).then(success).catch(fail);
}

function requestSearchBoard(boardId, success, fail) {
    local.get(`${API_URL}/${boardId}`).then(success).catch(fail);
}

function requestCreateBoard(data,success,fail) {
    local.post("/boards",data).then(success).catch(fail);
}

function requestUpdateBoard(data,success,fail) {
    local.put("/boards",data).then(success).catch(fail);
}

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