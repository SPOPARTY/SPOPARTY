<template>
    <v-dialog 
        v-model="modalVisible"
        max-width="800px"
        max-height="400px"
        class="dialog-container"
        >
        <v-card class=dialog-card>
            <v-card-title class="dialog-title">클럽 창단하기</v-card-title>
            <v-row>
                <v-col cols="4">
                    <v-card-text class="dialog-text"><b>클럽 이름</b></v-card-text>
                </v-col>
                <v-col cols="8">
                    <v-text-field class="dialog-field" v-model="clubName"></v-text-field>
                </v-col>
            </v-row>
            <v-card-actions class="dialog-actions">
                <v-btn color="green darken-1" @click="generateClub">클럽 창단</v-btn>
                <v-btn color="red lighten-1" @click="closeModal">취소</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue';
import {useClubStore} from '@/stores/club/clubs'

const memberId = localStorage.getItem("id");
const clubName = ref("");

const clubStore = useClubStore();

function generateClub() {
    console.log("히히 클럽 창단 발사")
    const data = {
        memberId:memberId,
        name : clubName.value
    }
    console.log(data)
    if(clubName.value === '') {
        console.log("clubName --> ",clubName.value)
        alert("클럽 이름은 빈칸이어서 안됩니다!")
        return;
    }
    if (clubName.value.length > 20) {
        alert("클럽 이름은 20자 이상을 넘기면 안됩니다!")
        clubName.value = clubName.value.substring(0,20);
        return;
    }

    if(confirm(`${clubName.value}로 새 클럽을 창단하시겠습니까?`) === true) {
        clubStore.createClubs(data);
    }
    closeModal();
}

const emits = defineEmits([
    'close-new-club'
])

const modalVisible = ref(true)

function closeModal() {
    modalVisible.value = false
    emits('close-new-club')
}

</script>

<style scoped>
.dialog-container {
    display:flex;
    justify-content: center;
    align-items: center;
}

.dialog-card {
  border: 2px solid black; /* 테두리 두께 조정 */
}

.dialog-title{
    text-align: center;
    margin-bottom:20px;
}
.dialog-text{
    margin-left:100px;
}
.dialog-field{
    margin-right:50px;
}
.dialog-actions {
  margin-left: 300px; /* 마진 설정 */
  padding: 10px; /* 패딩 설정 */
}

</style>