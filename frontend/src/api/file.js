import { localAxios, localAxios2 } from '@/util/http-commons'

const local = localAxios()

const local2 = localAxios2()

const API_URL = '/files'

function requestDeleteFile(fileId, success, error) {
  local.get(`${API_URL}/${fileId}`).then(success).catch(error)
}

async function requestUploadFile(data, success, error) {
  local2.post(API_URL, data).then(success).catch(error)
}

export { requestDeleteFile, requestUploadFile }