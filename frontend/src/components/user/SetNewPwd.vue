<template>
    <v-dialog v-model="isModalVisible" persistent max-width="300px">
        <v-card>
            <v-card-title class="text-h4">비밀번호 재설정</v-card-title>
            <v-card-text>
                <v-form @submit.prevent="changePwd">
                    <v-text-field
                        label="새 비밀번호"
                        v-model="newPwd"
                        outlined
                        dense
                    />
                    <v-text-field
                        label="새 비밀번호 확인"
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
    <v-dialog v-model="CanChangePwd" max-width="200px">
        <v-card>
            <v-card-text>
                비밀번호 수정이 완료되었습니다.
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="closeModal">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
    <v-dialog v-model="needChangePwd" max-width="200px">
        <v-card>
            <v-card-text>
                다시해라
            </v-card-text>
            <v-card-actions>
                <v-btn color="black" @click="needChangePwd=false">확인</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue';
const isModalVisible = ref(true);

const newPwd = ref("");
const newPwdConfirm = ref("");

const emit = defineEmits(['setpwd-close'])

const CanChangePwd = ref(false);
const needChangePwd = ref(false);
function changePwd(){
    if (newPwd.value === '' || newPwdConfirm === '' || newPwd.value !== newPwdConfirm.value){
        needChangePwd.value = true;
    }
    else {
        CanChangePwd.value = true;
    } 
}

function closeModal() {
    isModalVisible.value = false;
    emit('setpwd-close')
}



</script>

<style>

</style>