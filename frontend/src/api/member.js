import {localAxios} from '@/util/http-commons';

const local = localAxios();

const memberUrl = "/members";

function getMember(memberId, success, fail) {
    local.get(`/members/${memberId}`).then(success).catch(fail);
}

function registMember(member, success, fail) {
    local.post(`/members/register`,member).then(success).catch(fail);
}

function updateMember(member, success, fail) {
    local.put(`${memberUrl}`,member).then(success).catch(fail);
}

function deleteMember(memberId, success,fail) {
    local.delete(`${memberUrl}/${memberId}`).then(success).catch(fail);
}

async function memberConfirm(params,success,fail) {
    await local.post("/members/login",params).then(success).catch(fail);
}

async function findById(memberId, success, fail) {
    await local.get(`/members/${memberId}`).then(success).catch(fail);
}


function memberLogout(success, fail) {
    local.delete(`/members/logout`).then(success).catch(fail);
}


export {
    getMember,
    registMember,
    updateMember,
    deleteMember,
    memberConfirm,
    findById,
    memberLogout,
};