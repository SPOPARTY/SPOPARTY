import axios from 'axios'
import { httpStatusCode } from '@/util/http-status'

const {API_URL} = import.meta.env;

function localAxios() {
    console.log("히히 API_URL 발사 -> ", API_URL)
    const instance = axios.create({
        baseURL:"http://localhost:9090/",
        headers: {
            "Content-Type" : "application/json;charset=utf-8",
        },
    });
    return instance;
}

export {localAxios}