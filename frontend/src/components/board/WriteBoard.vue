<template>
    <v-container >
        <h1 style="align-items:center">게시글 작성</h1>
        <v-text-field
            v-model = "title"
            :rules="titleRules"
            outlined
            class="title"
        />
        <TextEditor v-model = "content"/>
        <v-row justify="end" style="margin-top:10px;">
            <v-col cols="auto">
                <v-btn @click="saveContent">
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
// API 요청 경로 필요

const title = ref('')
const content = ref('');

const titleRules = [
    v => !!v || '제목을 필수입니다!',
    v => (v && v.length <= 40) || '제목은 40자 이하로 작성해주세요'
];

const routes = useRoute();
const router = useRouter();
// const api = useAPI(); // 실제 api 요청 경로 필요

const saveContent = () => {
    // 저장 로직 -> 추가 작성 필요
}

const clubId = routes.params.clubId;
const goBack = () => {
    router.push(`/club/${clubId}/board`)
}

// 수정일 경우 content 값을 넣어주기 위한 로직
// onMounted(() => {
//   if (/* 수정일 경우의 조건 */) {
//     api.GET_CONTENT_DATA(route.params.id).then((res) => {
//       content.value = res.data.content;
//     });
//   }
// });


</script>

<style lang="scss" scoped>
.title {
    border: 1px solid black;
    border-radius: 10px;
    padding: 6px;
    background-color: white;

    &:hover{
        border-color:black; // hovering시에도 기본 테두리 색상 유지
        background-color:transparent; // 배경색 안 변함
    }

    &.v-focused{
        background-color:white;
    }

    &.v-focused:not(.v-error)::before{
        border-color:black;
    }

}

.left-arrow{
    position:fixed;
    left:0;
    bottom:50%;
}
</style>