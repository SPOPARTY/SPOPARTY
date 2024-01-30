import {ref, computed} from 'vue'
import {useRouter, useRoute} from 'vue-router'
import {defineStore} from 'pinia'
import axios from 'axios'


import {httpStatusCode} from "@/util/http-status"

export const useSoccerStore = defineStore("soccer",() => {
    const router = useRouter();
    const route = useRoute();

    const realTimeData = () => {
        
    }
    return {

    }
})
