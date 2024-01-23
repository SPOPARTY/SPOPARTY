<template>
    <v-dialog 
        v-model="modalVisible"
        max-width="1000px"
        max-height="600px"
        @click:outside="closeModal"
        persistent    
    >
        <v-card class="board-detail">
            <v-card-title>히히 boardDetail발사</v-card-title>
            <v-card-text>
                <p>내용내용</p>
                <p>내용내용</p>
            </v-card-text>
            <v-card-actions>
                <v-spacer/>
                <v-btn color="success" @click="closeModal">수정</v-btn>
                <v-btn color="red" @click="closeModal">닫기</v-btn>
            </v-card-actions>
        </v-card>

    </v-dialog>
</template>

<script setup>
import  {ref} from 'vue';

const modalVisible = ref(true);

const props = defineProps({
    post : Object
})
const emits = defineEmits(['detail-close'])

function closeModal() {
    modalVisible.value = false;
    emits('detail-close')
    console.log("BoardDetail닫음")
    console.log("modalVisible --> ",modalVisible.value)
}

console.log(props.post)

</script>

<style lang="scss" scoped>

</style>