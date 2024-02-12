import { localAxios } from '@/util/http-commons'

const local = localAxios()

const API_URL = '/files'

function requestDeleteFile(fileId, success, error) {
  local.get(`${API_URL}/${fileId}`).then(success).catch(error)
}

export { requestDeleteFile }