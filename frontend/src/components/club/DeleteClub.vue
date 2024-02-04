<template>
    <v-dialog
        v-model="modalVisible"
        max-width="400px"
        max-height="600px"
        @click:outside="closeModal"
        persistent
        >
        <v-card>
            <v-card-text class="mention">
                <br>
                <h1 >잠깐!</h1> <br>
                당신은 <b><u>그룹장</u></b> 아닙니까?<br>
                그룹을 <b><i>산산조각</i></b>내시겠습니까?<br>
                그러기 위해 당신 그룹의 리더가 되신겁니까?<br>
                <u>모름지기 그룹의 리더는 묵직해야 합니다.</u><br>
                순간의 선택이 그룹의 운명을 좌우합니다.<br><br>
                <b><h3>다시 생각해주세요!</h3></b><br>
                <br>
            </v-card-text>
            <v-card-actions style="transform:translateX(-75px)">
                <v-spacer></v-spacer>
                <v-btn color="red" @click="deleteClub">진행시켜</v-btn>
                <v-btn color="green" @click="closeModal"><h3>다시 생각해보자</h3></v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
    <v-dialog
        v-model="finalDelete"
        max-width="500px"
        persistent
    >
        <v-card>
            <v-card-text class="mention">
                <br>
                <h1>배신자!</h1>
                <br>
                <h1>그룹의 원수!</h1>
                <br>
                <h1>당신 때문에</h1>
                <h1>이 그룹이 폭파되었어!</h1>
            </v-card-text>
            <v-card-actions style="transform:translateX(-210px)">
                <v-spacer></v-spacer>
                <v-btn color="red" @click="goMain"><h3>홈으로</h3></v-btn><br>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue'
import {useRoute} from 'vue-router'
import {useClubStore} from '@/stores/club/clubs'

const route = useRoute();

const emits = defineEmits([
    'delete-club-close'
])

const clubStore = useClubStore();

const clubId = route.params.clubId;

// DeleteClub.vue on/off
const modalVisible = ref(true)

// delete 최종 결정 모달 on/off
const finalDelete = ref(false)
const deleteClub = () => {
    try{
        const success = clubStore.deleteClub(clubId);
        if (success) {
            console.log("*****성공적으로 그룹 폭파*****")
            modalVisible.value = false;
            finalDelete.value = true;
        }
    } catch {
        alert("그룹 폭파 실패")
    }
}

// 그룹 삭제 이후 메인으로
const goMain = () => {
    finalDelete.value = false;
    window.location.replace("/") 
}

// DeleteClub.vue 닫기
const closeModal = () => {
    modalVisible.value = false;
    emits('delete-club-close');
}

</script>

<style lang="scss" scoped>
.v-container {
    padding:0px;
}


.v-card-title {
    font-weight : bold;
    font-size : 2rem;
    text-align : center;
    margin-top : 10px;
}

.mention {
    font-size : 1rem;
    text-align : center;
    margin-top : 10px;
    padding:10px;
}
</style>