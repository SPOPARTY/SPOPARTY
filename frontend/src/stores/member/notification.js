import {ref,computed} from 'vue';
import { defineStore } from 'pinia';
import {requestNotificationList, requestCreateNoification, requestUpdateNoification, requestDeleteNoification} from "@/api/notification"

export const useNotificationStore = defineStore("notification", () => {
    
    const notificationList = ref([])
    
    const getNotificationList = (memberId) => {
        requestNotificationList(
            memberId,
            ({data}) => {
                console.log("useNotificationStore.requestNotificationList: ",data);
                notificationList.value = data.data;
            },
            (error) => {
                console.log(error);
            }
        )
    }
        
    const readNotification = (notificationId) => {
        requestUpdateNoification(
            notificationId,
            ({data}) => {
                console.log(data);
                notificationList.value = notificationList.value.filter(item => item.id != notificationId)
                notificationList.value.push(data.data);
            },
            (error) => {
                console.log(error);
            }
        )
    }

    const deleteNotification = (notificationId) => {
        requestDeleteNoification(
            notificationId,
            ({data}) => {
                console.log(data);
                notificationList.value = notificationList.value.filter(item => item.id != notificationId)
            },
            (error) => {
                console.log(error);
            }
        )
    }

    const createNotification = (notification) => {
        requestCreateNoification(
            JSON.stringify(notification),
            ({data}) => {
                console.log(data);
            },
            (error) => {
                console.log(error);
            }
        )
    }

    const connect = (memberId) => {
        console.log("SSE Connecting");
        const data = new EventSource(`https://i10a802.p.ssafy.io/api/notifications/connect/${memberId}`);
        data.addEventListener('connect', (emit) => {
            console.log('SSE Connet Success: ',emit);
        })
        data.addEventListener('notification', (emit) => {
        console.log('SSE notification: ',emit);
            getNotificationList(memberId);
        })
    }
    
    
    
    return {
        notificationList,
        getNotificationList, readNotification, deleteNotification, connect, createNotification
    }
})