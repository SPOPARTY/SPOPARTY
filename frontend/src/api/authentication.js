import {localAxios} from '@/util/http-commons';

const local = localAxios();

const API_URL = "/authentication"

function idCheck(loginId,success,error) {
    local.get(`${API_URL}/id-check/${loginId}`).then(success).catch(error);
}

function emailCheck(email, success, error) {
    local.get(`${API_URL}/email-check/${email}`).then(success).catch(error);
}

function requestTempPassword(data, success, error) {
    local.post(`${API_URL}/password`,data).then(success).catch(error);
}

function verifyCodeCheck(data,success,error) {
    local.post(`${API_URL}/email-check`,data).then(success).catch(error);
}

async function tokenRegeneration(refreshToken, success,fail) {
    await local.post(`${API_URL}/regenerate`,refreshToken).then(success).catch(fail);
}

export {
    idCheck,
    emailCheck,
    requestTempPassword,
    verifyCodeCheck
}   