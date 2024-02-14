import { defineStore } from 'pinia'
import { requestDeleteFile } from '@/api/file'

import { httpStatusCode } from '@/util/http-status'

export const useFileStore = defineStore('file', () => {

  const deleteFile = (fileId) => {
    requestDeleteFile(fileId,
      (res) => {
        if (res.status === httpStatusCode.OK) {
          // console.log("delete file success")
        }
      },
      (error) => {
        console.error(error)
      },
    )
  }

  return {
    deleteFile
  }
})