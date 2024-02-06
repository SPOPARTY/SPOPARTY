<template>
    <v-dialog 
        v-model="isModalVisible"
        max-width="300px"
        @click:outside="closeModal"
        persistent        >
        <v-card>
            <v-card-title class="text-h4">비밀번호 재설정</v-card-title>
            <v-card-text>
                <v-form @submit.prevent="changePwd">
                    <v-text-field
                        label="새 비밀번호"
                        type="password"
                        v-model="newPwd"
                        outlined
                        dense
                    />
                    <v-text-field
                        label="새 비밀번호 확인"
                        type="password"
                        v-model="newPwdConfirm"
                        outlined
                        dense
                    />
                    <v-card-actions>
                        <v-spacer/>
                        <v-btn color="black" type="submit">비밀번호 확인</v-btn>
                        <v-btn color="red" text @click="closeModal">이전</v-btn>
                    </v-card-actions>
                </v-form>
            </v-card-text>
        </v-card>
    </v-dialog>

    <!-- 이전에 사용하던 비밀번호 -->
    <v-dialog v-model="isAlreadyUsed" max-width="200px">
        <v-card>
            <v-card-text>
                이전에 사용하던 비밀번호입니다!
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="isAlreadyUsed = false">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 두 비밀번호가 다름 -->
    <v-dialog v-model="pwdNotSame" max-width="200px">
        <v-card>
            <v-card-text>
                두 비밀번호가 다릅니다!
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="pwdNotSame=false">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 비밀번호가 형식에 맞지 않음 -->
    <v-dialog v-model="inValidPwd" max-width="200px">
        <v-card>
            <v-card-text>
                비밀번호는 8자리 이상 특수문자가 포함되어야 합니다!
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="inValidPwd = false">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 옳게 된 비밀번호 -->
    <v-dialog v-model="canChangePwd" max-width="200px">
        <v-card>
            <v-card-text>
                비밀번호 수정이 완료되었습니다.
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="closeModal">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue';
import { jwtDecode } from 'jwt-decode'

const isModalVisible = ref(true);


const props = defineProps({
    currentPwd : String
})

const emit = defineEmits(['set-pwd-close'])

const currentPwd = props.currentPwd;
const newPwd = ref("");
const newPwdConfirm = ref("");

const canChangePwd = ref(false);
const isAlreadyUsed = ref(false);
const pwdNotSame = ref(false);
const inValidPwd = ref(false);

const isPasswordValid = (pwd) => {
    return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
  };


function changePwd(){
    // console.log("지금 비밀번호는 -> ",jwtDecode("Bearer " + currentPwd))
    // if (currentPwd === newPwd.value) {
    //     isAlreadyUsed.value = true;
    //     canChangePwd.value = false;
    // }
    if (newPwd.value === '' || newPwdConfirm === '' || newPwd.value !== newPwdConfirm.value){
        pwdNotSame.value = true;
        canChangePwd.value = false;
    }
    else if (!isPasswordValid(newPwd.value)) {
        inValidPwd.value = true;
        canChangePwd.value = false;
    }
    else {
        canChangePwd.value = true;
    } 
}

function closeModal() {
    isModalVisible.value = false;
    canChangePwd.value = false;
    const newPwds = {
        password : newPwd.value,
        password2 : newPwdConfirm.value
    }
    emit('set-pwd-close',newPwds)
}



</script>

<style>

</style>