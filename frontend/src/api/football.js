import {localAxios} from '@/util/http-commons';

const API_URL = "football"

const local = localAxios();

function requestRealTimeData(success,fail) {
    local.get(`/${API_URL}/realtime`).then(success).catch(fail);
}