import {localAxios} from '@/util/http-commons';
const local = localAxios();

const API_URL = "/notifications"

// 알림 목록 조회
function requestNotificationList(memberId,success,fail) {
    local.get(`${API_URL}/${memberId}`).then(success).catch(fail);
}

// 알림 생성
function requestCreateNoification(data,success,fail) {
    local.post(`${API_URL}`, data).then(success).catch(fail);
}

// 알림 읽음
function requestUpdateNoification(notificationId,success,fail) {
    local.put(`${API_URL}/${notificationId}`).then(success).catch(fail);
}

// 알림 삭제
function requestDeleteNoification(notificationId,success,fail) {
    local.delete(`${API_URL}/${notificationId}`).then(success).catch(fail);
}

export{
    requestNotificationList,
    requestCreateNoification,
    requestUpdateNoification,
    requestDeleteNoification
}