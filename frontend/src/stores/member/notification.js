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
    
    
    
    return {
        notificationList,
        getNotificationList, readNotification, deleteNotification
    }
})