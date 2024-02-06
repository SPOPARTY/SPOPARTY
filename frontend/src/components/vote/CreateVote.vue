<template>
    <v-dialog
        v-model="isModalVisible"
        max-width="600px"
        @click:outside="closeModal"
        persistent
    >
        <v-card>
            <v-card-title class="text-center">투표 생성 </v-card-title>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue'

const isModalVisible = ref(true);

const emit = defineEmits([
    'create-vote-close'
])

function closeModal() {
    isModalVisible.value = false;
    console.log("CreateVote emit!!!") 
    emit('create-vote-close'); 
}

</script>

<style scoped>

</style>