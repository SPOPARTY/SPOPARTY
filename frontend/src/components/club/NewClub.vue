<template>
    <v-dialog 
        v-model="modalVisible"
        max-width="800px"
        max-height="400px"
        class="dialog-container"
        >
        <v-card class=dialog-card>
            <v-card-title class="dialog-title">클럽 창단하기</v-card-title>
            <v-card-text class="dialog-text">클럽 이름</v-card-text>
            <v-text-field class="dialog-field"></v-text-field>
            <v-card-actions class="dialog-actions">
                <v-btn color="green darken-1">클럽 창단</v-btn>
                <v-btn color="red lighten-1" @click="closeModal">취소</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue';

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

.dialog-title,
.dialog-text,
.dialog-input,
.dialog-actions {
  margin: 10px; /* 마진 설정 */
  padding: 10px; /* 패딩 설정 */
}

</style>