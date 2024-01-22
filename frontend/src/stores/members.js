import {ref, computed} from 'vue'
import {defineStore} from 'pnia'
import axios from 'axios'
import {useRouter, useRoute} from 'vue-router'

const REST_API = "http://localhost:8080/members"

export const useMemberStore = defineStore("members",() => {
    const router = useRouter();
    const route = useRoute();
    const currUser = ref(null);

    const login = (user) => {
        axios.post({
            
        })
    }

    return {

    }
})