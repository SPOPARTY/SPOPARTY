import { defineStore } from 'pinia'
import { requestDeleteFile, requestUploadFile } from '@/api/file'

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

  const uploadFile = (data) => {
    return new Promise((resolve, reject) => {
      requestUploadFile(data,
        (res) => {
          if (res.status === httpStatusCode.CREATE) {
            console.log("upload file success")
            console.log(res)
            resolve(res)
          }
        },
        (error) => {
          console.error(error)
          reject(error)
        },
      )
    })
  }


  return {
    deleteFile,
    uploadFile,
  }
})