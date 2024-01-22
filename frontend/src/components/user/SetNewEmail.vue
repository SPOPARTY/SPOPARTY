<template>
    <v-dialog 
        v-model="isModalVisible"
        max-width="500px"
        @click:outside="closeModal"
        persistent        >
        <v-card>
            <v-card-title class="text-h4">이메일 수정</v-card-title>
            <v-card-text>
                <v-row>
                    <v-col cols="4" md="4">                        
                        <v-text-field 
                            label="이메일 Id"
                            v-model="newEmailId"
                            outlined
                        />
                    </v-col>
                    <v-col cols="1" md=1>@</v-col>
                    <v-col cols="4" md="4">
                        <v-text-field 
                            label="이메일 도메인"
                            v-model="newEmailDomain"
                            outlined
                        />
                    </v-col>
                    <v-col cols="3">
                        <v-btn color="black" @click="emailSent = true">인증코드 발송</v-btn>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="6" md="5">
                        <v-text-field 
                            label="인증코드 입력"
                            v-model="myVerifyCode"
                            outlined
                        />
                    </v-col>
                    <v-col cols="2">
                        4:59
                    </v-col>
                    <v-col cols="4">
                        <v-btn color="black" @click="checkCode">인증번호 확인</v-btn>
                    </v-col>
                </v-row>
            </v-card-text>
            <v-card-actions>
                <v-spacer/>
                <v-btn color="red" @click="closeModal">닫기</v-btn>
                <v-btn color="black" @click="checkVerified">수정</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 인증코드가 맞았을 때 뜨는 모달 -->
    <v-dialog v-model="codeChecked" max-width="200px">
        <v-card>
            <v-card-text>
                이메일 인증이 완료되었습니다.
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="codeChecked = false">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 인증코드가 틀렸을 때 뜨는 모달 -->
    <v-dialog v-model="codeNotChecked" max-width="200px">
        <v-card>
            <v-card-text>
                코드가 다릅니다.
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="codeNotChecked = false">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 인증코드가 메일로 잘 보내졌을 때 뜨는 모달 -->
    <v-dialog v-model="emailSent" max-width="200px">
        <v-card>
            <v-card-text>
                이메일에 인증코드가 보내졌습니다.
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="emailSent = false">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 이메일 인증 후 이메일 수정이 완료되었음을 알려주는 모달 -->
    <v-dialog v-model="verifyDone" max-width="200px">
        <v-card>
            <v-card-text>
                이메일 수정이 정상적으로 수정되었습니다.
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="closeModal">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 이메일 인증을 하지 않았는데 "수정" 버튼을 눌렀을 때 뜨는 모달 -->
    <v-dialog v-model="verifyNotDone" max-width="200px">
        <v-card>
            <v-card-text>
                이메일을 인증하세요.
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="verifyNotDone = false">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue';
const isModalVisible = ref(true);

const newEmailId = ref(""); // 새 이메일 ID
const newEmailDomain = ref(""); // 새 이메일 도메인
const verifyCode = "12345"; // 이메일에 전송될 인증번호 -> 아직은 임시
const myVerifyCode = ref(""); // 내가 적은 인증번호

const codeChecked = ref(false); // (내가 쓴 인증코드) === (이메일에 전송된 코드)일 때 true
const codeNotChecked = ref(false); // (내가 쓴 인증코드) !== (이메일에 전송된 코드)일 때 true

const emailSent = ref(false); // 이메일에 인증코드가 전송되면 true

const codeVerified = ref(false); //
const verifyDone = ref(false); // 
const verifyNotDone = ref(false); // 

const emit = defineEmits(['setEmail-close','update-email'])

function checkVerified() {
    console.log("codeVerified",codeVerified.value)
    console.log("verifyDone",verifyDone.value)
    console.log("verifyNotDone",verifyNotDone.value)
    if (codeVerified.value) {
        const newEmailData = {
            newEmailId : newEmailId.value,
            newEmailDomain : newEmailDomain.value
        }
        verifyDone.value = true;

        console.log(newEmailData);
        emit('update-email',newEmailData);
    } 
    else {
        verifyNotDone.value = true;
    }
}

function closeModal() {
    isModalVisible.value = false;
    emit('setEmail-close')
}

function checkCode() {
    console.log(verifyCode === myVerifyCode.value)
    if (verifyCode === myVerifyCode.value) {
        codeChecked.value = true;
        codeVerified.value = true;
    } else {
        codeNotChecked.value = true;
    }
}


</script>

<style scoped>

</style>