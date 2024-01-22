<template>
    <v-container class="mypage-container">
        <v-card class="pa-4" outlined>
            <v-card-title class="text-h4 mb-6">마이페이지</v-card-title>
        
            <v-row>
                <v-col cols="12" md="8">
                    <v-text-field label="아이디" value="kbukm123" outlined dense readonly></v-text-field>
                </v-col>
                <v-col cols="12" md="4">
                    <v-btn color="#393646" style="margin-top:10px" @click="showChangePwdModal" block>비밀번호 수정</v-btn>
                </v-col>
            </v-row>
            <SetNewPwd v-if="isPwdModalVisible" @setpwd-close="isPwdModalVisible = false"/>
            
            <v-row>
                <v-col cols="12">
                    <v-text-field label="닉네임" value="버스터" outlined dense></v-text-field>
                </v-col>
            </v-row>
            
            <v-row>
                <v-col cols="4" md="4">
                    <v-text-field label="이메일 아이디" v-model="emailId" outlined dense></v-text-field>
                </v-col>
                <v-col cols="1" md="1" class="text-center">@</v-col>
                <v-col cols="4" md="4">
                    <v-text-field label="도메인" v-model="emailDomain" outlined dense></v-text-field>
                </v-col>
                <v-col cols="3" md="3">
                    <v-btn color="#123421" style="margin-top:10px;" @click="showChangeEmailModal">이메일 수정</v-btn>
                </v-col>
            </v-row>
            <SetNewEmail 
                v-if="isEmailModalVisible" 
                @set-email-close="isEmailModalVisible = false"
                @update-email="updateEmail($event)"
                />
            
            <v-row>
            </v-row>
            
            <v-row>
                <v-col cols="8">
                <v-select
                    label="대표 앰블럼"
                    :items="emblems"
                    outlined dense
                ></v-select>
                </v-col>
                <v-col cols="4" md="4">
                    <v-btn color="#4F4557" style="margin-top:10px;" @click="showEmblemModal">엠블럼 목록</v-btn>
                </v-col>
            </v-row>
            <EmblemList v-if="isEmblemModalVisible" @emblem-list-close="isEmblemModalVisible = false"/>

            <v-card-actions class="text-center">
                <v-spacer></v-spacer>
                <v-btn color="grey" @click="goBack">이전</v-btn>
                <v-btn color="primary">저장</v-btn>
            </v-card-actions>
        </v-card>
    </v-container>
</template>

<script setup>
import {useRouter} from 'vue-router'
import { ref } from 'vue';
import SetNewPwd from '@/components/user/SetNewPwd.vue';
import SetNewEmail from './SetNewEmail.vue';
import EmblemList from './EmblemList.vue';

const router = useRouter();

const emailId = ref("kimbumki");
const emailDomain = ref("naver.com")

// 앰블럼 선택을 위한 아이템 배열
const emblems = [
    '토트넘 FC',
    '리버풀 FC',
    '토론토 FC'
];

const isPwdModalVisible = ref(false) // 비밀번호 수정 모달 보일까 말까

function showChangePwdModal() {
    isPwdModalVisible.value = true
}

const isEmailModalVisible = ref(false) // 이메일 수정 모달 보일까 말까

function showChangeEmailModal() {
    isEmailModalVisible.value = true
}

function updateEmail(newEmailData) {
    emailId.value = newEmailData.newEmailId;
    emailDomain.value = newEmailData.newEmailDomain;
}

const isEmblemModalVisible = ref(false) // 엠블럼 수정 모달 보일까 말까
function showEmblemModal() {
    isEmblemModalVisible.value = true;
}

function goBack() {
    router.back()
}

</script>

<style scoped>
.mypage-container{
    max-width: 600px;
}

</style>