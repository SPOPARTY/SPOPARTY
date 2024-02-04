import {localAxios} from '@/util/http-commons';

const local = localAxios();

const API_URL = "/archives"

// 추억 목록 조회
function requestArchiveList(clubId,success,fail) {
    local.get(`${API_URL}/clubs/${clubId}`).then(success).catch(fail)
}

// 추억 조회
function requestArchiveDetail(archiveId, success,fail) {
    local.get(`${API_URL}/${archiveId}`).then(success).catch(fail)
}

// 추억 생성
function requestCreateArchive(data,success,fail) {
    local.post(`${API_URL}`,data).then(success).catch(fail);
}

// 추억 수정
function requestUpdateArchive(data,success,fail) {
    local.put(`${API_URL}`,data).then(success).catch(fail);
}

// 추억 삭제
function requestDeleteArchive(archiveId,success,fail) {
    local.delete(`${API_URL}/${archiveId}`).then(success).catch(fail);
}

export{
    requestArchiveList,
    requestArchiveDetail,
    requestCreateArchive,
    requestUpdateArchive,
    requestDeleteArchive,
}