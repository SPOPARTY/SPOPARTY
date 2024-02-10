<template>
    <v-dialog  
        class="edit-dialog"
        v-model="modalVisible"
        width = "1000px"
        height = "1000px"
        persistent
    >   
    <v-card class="dialog-card">
        <h1>게시글 수정</h1>
        <v-card-text>
            <v-text-field
            v-model = "editedTitle"
            :rules="titleRules"
            outlined
            class="title"
        />
        </v-card-text>
        <v-card-text>
            <TextEditor v-model = "editedContent"/>
        </v-card-text>
        <v-card-text class="file-input-container">
            <v-file-input
                accept="image/*"
                label="파일을 첨부해주세요"
                v-model="editedFile"
                class="file-input"
            >
            </v-file-input>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="#D3AC2B" @click="confirmEdit">수정완료</v-btn>
            <v-btn color="#D3AC2B" @click="closeModal">취소</v-btn>
        </v-card-actions>

        <v-dialog
            v-model="editConfirmVisible"
            persistent
            max-width="500px"
        >
        <v-card>
            <v-card-title>게시글 수정</v-card-title>
            <v-card-text>이 게시글을 수정하시겠습니까?</v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click="editPost">예</v-btn>
                <v-btn color="blue darken-1" text @click="editConfirmVisible = false">아니오</v-btn>
            </v-card-actions>
        </v-card>

        </v-dialog>
    </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import TextEditor from '@/components/board/TextEditor.vue'

import {useBoardStore} from "@/stores/club/boards"

const memberId = localStorage.getItem("id");

const boardStore = useBoardStore();

const modalVisible = ref(true)

const props = defineProps({
    detail : Object, // BoardDetail로부터 받은 게시글 정보
})


const emits = defineEmits([
    'edit-close' // BoardDetail로 이벤트 전달 
])


const titleRules = [
    v => !!v || '제목을 필수입니다!',
    v => (v && v.length <= 40) || '제목은 40자 이하로 작성해주세요'
];

const boardId = props.detail.id
const clubId = props.detail.clubId
const editedTitle = ref(props.detail.title);
const editedContent = ref(props.detail.content);
const editedFile = ref(props.detail.file && props.detail.file.url ? [props.detail.file.url] : []);


const editConfirmVisible = ref(false);

// 수정 확인 Modal on
function confirmEdit(){
    editConfirmVisible.value = true;
}

// 진짜 수정
const editPost = () => {
    const formdata = new FormData(); 
    formdata.append("id",boardId);
    formdata.append("title",editedTitle.value);
    formdata.append("content",editedContent.value);
    formdata.append("file",editedFile.value[0])

    console.log("이건 formData 내용")
    for (let [key, value] of formdata.entries()) {
        console.log(key, value);
    }

    boardStore.updateBoard(formdata)

    const editedPost = {
        id : boardId,
        clubId : clubId,
        title : editedTitle.value,
        content : editedContent.value,
        file : editedFile.value[0]
    }
    
    emits('edit-close',editedPost) 
    editConfirmVisible.value = false; // 수정 확인 모달 off
    modalVisible.value = false; // EditBoard 모달도 off
}

// EditBoard 모달 off
const closeModal = () => {
    modalVisible.value = false;
    emits('edit-close')
}



</script>

<style lang="scss" scoped>
.dialog-card {
    margin-top: 70px; 
    background-color: #292646;
}

h1 {
    text-align: center;
    margin-top:10px;
    margin-bottom:10px;
    color : #D3AC2B;
}

.title {
    border-radius: 10px;
    padding: 6px;
    background-color: #CBD0D8;;

}

.file-input-container {
    display: flex;
    align-items: center;
    background-color: #CBD0D8;
    margin:25px;
    border-radius: 10px;
}

.file-input {
    max-width: 300px; /* 파일 입력 크기 조정 */
    margin-right: 20px; /* 라벨과의 간격 */
}

.left-arrow{
    position:fixed;
    left:0;
    bottom:50%;
}


</style>