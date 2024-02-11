<template>
    <v-container class="write-board">
        <h1>게시글 작성</h1>
        <v-text-field
            v-model="title"
            :rules="titleRules"
            outlined
            class="title"
        />
        <TextEditor class="file-input" v-model="content"/>
        <v-card-text class="file-input-container">
            <v-file-input
                accept="image/*"
                label="파일을 첨부해주세요"
                v-model="file"
                class="file-input"
            >
            </v-file-input>
        </v-card-text>
        <v-row justify="end" style="margin-top:10px;">
            <v-col cols="auto">
                <v-btn @click="writeBoard">
                    저장하기
                </v-btn>
            </v-col>
        </v-row>
    </v-container>
    <v-btn icon class="left-arrow" @click="goBack">
            <v-icon>mdi-arrow-left</v-icon>
    </v-btn>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import TextEditor from '@/components/board/TextEditor.vue'
import {useBoardStore} from "@/stores/club/boards"

const boardStore = useBoardStore();

const route = useRoute();
const router = useRouter();

const memberId = localStorage.getItem("id");
const clubId = route.params.clubId;
const title = ref('');
const content = ref('');
const file = ref([]); // 업로드 데이터는 배열에 담음

const titleRules = [
    v => !!v || '제목을 필수입니다!',
    v => (v && v.length <= 40) || '제목은 40자 이하로 작성해주세요'
];


const writeBoard = () => {
    // 사진을 비롯한 미디어 파일들은 form형식으로 보내줘야한다.
    const formdata = new FormData(); 
    formdata.append("memberId",memberId);
    formdata.append("clubId",clubId);
    formdata.append("title",title.value);
    formdata.append("content",content.value);
    formdata.append("file",file.value[0])

    if (confirm("게시글을 작성하시겠습니까??") === true) {
        boardStore.createBoard(formdata);
    }
}


const goBack = () => {
    router.push(`/club/${clubId}/board`)
}



</script>

<style lang="scss" scoped>

.write-board {
    background-color: #292646;
    margin-top:50px;
    border-radius: 10px;
    padding:20px;
}

h1 {
    text-align: center;
    margin-top:10px;
    margin-bottom:10px;
    color : #D3AC2B;
}

.title {
    border: 1px solid black;
    border-radius: 10px;
    padding: 6px;
    background-color: #CBD0D8;;
}

.file-input-container {
    display: flex;
    align-items: center;
    background-color: #CBD0D8;
    margin-top:20px;
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