import {ref,computed} from 'vue';
import { useRoute,useRouter } from 'vue-router';
import { defineStore } from 'pinia';

import {httpStatusCode} from "@/util/http-status"

import {requestBoardList, requestSearchBoard, requestCreateBoard, requestUpdateBoard,requestDeleteBoard} from '@/api/board'

export const useBoardStore = defineStore("board",() => {




    return {

    }
})