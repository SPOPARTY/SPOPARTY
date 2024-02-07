<template>
    <v-dialog
        v-model="modalVisible"        
        max-width="400px"
        @click:outside="closeModal"
        persistent
    >
        <v-container>
            <v-card>
                <br>
                <v-card-title>그룹명 바꾸기</v-card-title>
                <v-card-text>변경하고자 하는 그룹 이름을 작성하세요!</v-card-text>
                <v-text-field
                    v-model="clubName"

                >   
                    <!-- 연필 아이콘 -> 필요? -->
                    <!-- <template v-slot:append>
                        <v-btn icon class="no-background-hover">
                            <v-icon>mdi-pencil</v-icon>
                        </v-btn>
                    </template> -->
                </v-text-field>
                <v-card-actions>
                    <v-btn class="button" color="#292646" text @click="submitName">변경</v-btn>
                    <v-btn class="button" color="red darken -1" text @click="closeModal">취소</v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
    </v-dialog>
    <v-dialog 
        v-model="confirm"
        max-width="400px"
        max-height="600px"
        @click:outside="closeModal"
        persistent
    >
        <v-card class="confirm-change">
            <v-card-text>변경이 완료되었습니다!</v-card-text>
            <v-btn color="blue lighten-1" text @click="closeModal" width="200px" style="margin-top:20px; margin-bottom:20px;">확인</v-btn>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {useRoute} from 'vue-router'
import {useClubStore} from '@/stores/club/clubs'

const route = useRoute();

const emits = defineEmits([
    'change-club-name-close'
])

const clubStore = useClubStore();

const {updateClub} = clubStore;

const clubId = route.params.clubId;
const memberId = sessionStorage.getItem("id"); // 언제까지나 sessionStorage에 넣어야하나?
const clubName = ref(null);

const modalVisible = ref(true)

const submitName = () =>  {
    if(clubName.value === null) {
        alert("그룹명이 공란이어서는 안됩니다!")
        return;
    }

    let data = {
        memberId : memberId,
        name : clubName.value
    }

    console.log(data);

    // 예외처리 세밀하게
    try{
        const success = updateClub(clubId,data);
        if (success) {
            console.log("그룹 명 수정, 성공적!")
            modalVisible.value = false;
            confirm.value = true
        } else {
            console.error("그룹 정보 수정 실패!")
        }
    } catch(error) {
        console.log("비상!!!!!" + error)
    }
        
    }



const confirm = ref(false)

function closeModal() {
    modalVisible.value = false
    emits('change-club-name-close')
}

onMounted(() => {
    console.log("히히 그룹명 변경 발사")
})

</script>

<style scoped>
.v-dialog .v-overlay{
    background-color: transparent;

}

.v-container{
    padding:0px;
    text-align: center;
}

.v-card-title{
    margin-top : 10px;
    margin-bottom : 10px;
}

.v-card-text {
    font-weight: bold;
}

.v-text-field{
    background-color: transparent; /* 배경색 변경 없음 */
    border-color:initial; /* 테두리 색 변경 없음 */
    width:80%;
    margin:auto;
}

.v-card-actions {
    margin-right:20px;
    margin-bottom : 10px;
}

.confirm-change {
    display: flex; 
    flex-direction: column; 
    align-items: center; 
    justify-content: center; 
    height: 100%; /* v-card의 높이를 부모 컨테이너(여기서는 v-dialog)에 맞춤 */
    text-align: center;
}

.button {
    display: flex;
    justify-content: center;
    align-items: center;
    margin:auto;
    box-shadow: none !important;
    background:transparent
}

</style>