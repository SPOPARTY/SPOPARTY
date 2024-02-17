<template>
    <v-dialog 
        v-model="modalVisible"
        max-width="600px"
        max-height="600px"
        @click:outside="closeModal"
        persistent    
    >
        <v-card class="board-detail" >
            <v-card-title class="text-center">{{ props.post.title }}</v-card-title>
            <v-card-subtitle class="text-right">{{ props.post.member.nickname }}</v-card-subtitle>
            <v-card-text v-if="post.file">{{ formatDateTime(post.file.updatedTime) }}</v-card-text>
            <v-card-item v-if="props.post.file">
                <v-img v-if="props.post.file.type == 'image'" :src="props.post.file.url" class="img" cover width="100%"></v-img>
                <video v-if="props.post.file.type == 'video'" :src="props.post.file.url" controls type="video/mp4" cover width="100%"></video>
            </v-card-item>
            <v-card-text>
                <div>
                    <div v-html="props.post.content"></div>
                </div>
            </v-card-text>
            <v-card-actions>
                <v-spacer/>
                <v-btn v-if="currentUserId == props.post.member.id" color="success" @click="showEditModal">수정하기</v-btn>
                <v-btn v-if="currentUserId == props.post.member.id" color="error" @click="confirmDelete">삭제하기</v-btn>
                <v-btn color="red" @click="closeModal">닫기</v-btn>
            </v-card-actions>
            
            <v-dialog v-model="deleteConfirmVisible" persistent max-width="500px">
                <v-card>
                    <v-card-title class="text-center text-h5">게시글 삭제</v-card-title>
                    <v-card-text class=text-center>정말로 이 게시글을 삭제하시겠습니까?</v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="blue darken-1" text @click="deleteConfirmVisible = false">취소</v-btn>
                        <v-btn color="red darken-1" text @click="deletePost">삭제</v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
        </v-card>
    </v-dialog>
    <EditBoard 
        v-if="isEditModalVisible" 
        :detail="post" 
        @edit-close="editClose($event)"/>
</template>

<script setup>
import  {ref, onMounted} from 'vue';
import EditBoard from '@/components/board/EditBoard.vue';
import {useBoardStore} from "@/stores/club/boards";

import {formatDateTime} from "@/util/tools.js"

const boardStore = useBoardStore();

const currentUserId = localStorage.getItem("id"); // 로그인 된 id
const modalVisible = ref(true); // BoardDetail on/off 관장

const props = defineProps({
    post : Object
})

const emits = defineEmits(['detail-close', 'delete-post'])

const isEditModalVisible = ref(false)

// EditBoard모달 on
function showEditModal() {
    isEditModalVisible.value = true; // EditBoard on
    modalVisible.value = false; // BoardDetail off
}

// EditBoard모달 off
function editClose(editedPost) {
    if(editedPost) {
        props.post = editedPost
    }
    isEditModalVisible.value = false // EditBoard off
    modalVisible.value = true// BoardDetail on
    closeModal()
}

// 삭제 확인 모달
const deleteConfirmVisible = ref(false);
const confirmDelete = () => {
    deleteConfirmVisible.value = true;
}

// 진짜 삭제
function deletePost() {
    boardStore.deleteBoard(props.post.id,props.post.club_id);
    emits('delete-post',props.post.id)
    closeModal()
    deleteConfirmVisible.value = false;
}

// BoardDetail 모달 off
function closeModal() {
    modalVisible.value = false; // 1. modalVisible off
    emits('detail-close') // 2. emit
}


</script>

<style lang="scss" scoped>
h1{
    color:black
}
.img{
    border:solid;
    border-radius: 20px;
    width:100%;
    height:100%;
}

.board-detail {
    background-color: #F4F3EA ;
}
</style>