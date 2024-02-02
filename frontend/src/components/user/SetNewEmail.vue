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
                        <v-btn color="black" @click="sendEmail">인증코드 발송</v-btn>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="6" md="5">
                        <v-text-field 
                            label="인증코드 입력"
                            v-model="verifyCode"
                            outlined
                        />
                    </v-col>
                    <v-col cols="2">
                        4:59
                    </v-col>
                    <v-col cols="4">
                        <v-btn color="black" @click="checkVerifyCode">인증번호 확인</v-btn>
                    </v-col>
                </v-row>
            </v-card-text>
            <v-card-actions>
                <v-spacer/>
                <v-btn color="red" @click="closeModal">닫기</v-btn>
                <v-btn color="black" @click="confirmChange">수정</v-btn>
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
import {ref, computed} from 'vue';
import {emailCheck,verifyCodeCheck} from '@/api/authentication'
import { httpStatusCode } from '@/util/http-status';

const isModalVisible = ref(true);

const newEmailId = ref(""); // 새 이메일 ID
const newEmailDomain = ref(""); // 새 이메일 도메인
const newEmail = computed(() => {
    return `${newEmailId.value}@${newEmailDomain.value}`
  } )

const emailVerifiedConfirm = ref(false); // 이메일 수정 최종 승인

const emailSent = ref(false); // 이메일에 인증코드가 전송되면 true

const verifyDone = ref(false); // 이메일 인증 완료
const verifyNotDone = ref(false); // 아직 이메일 인증이 완료되지 않음

const emit = defineEmits(['setEmail-close','update-email'])


function sendEmail () {
    if(newEmailId.value === '' || newEmailDomain.value === '' ) {
      alert("올바른 이메일을 입력해주세요!")
      return;
    }

    console.log("새로운 이메일 잘 왔나 ->", newEmail.value)

    emailCheck(
      newEmail.value,
      (res) => {
        console.log(res)
        if(res.status === httpStatusCode.OK) {
          console.log("잘 발송 되었음")
          console.log(res.status)
          emailSent.value = true;
        }
      },
      (error) => {
        console.log("비상!")
        console.log(error)
        if (error.response.status === httpStatusCode.CONFLICT) {
          console.log(error)
          alert("이미 사용 중인 이메일입니다!")
        }
      }
    ) 
}

const verifyCode = ref(""); // 내가 적은 인증번호

const codeChecked = ref(false); // (내가 쓴 인증코드) === (이메일에 전송된 코드)일 때 true
const codeNotChecked = ref(false); // (내가 쓴 인증코드) !== (이메일에 전송된 코드)일 때 true


function checkVerifyCode () {
    const data = {
      email:newEmail.value,
      code:verifyCode.value,
    }
    console.log(data)
    verifyCodeCheck(
      data,
      (res)=>{
        if(res.status === httpStatusCode.OK){
            console.log("")
            codeChecked.value = true // 인증코드가 알맞고
            emailVerifiedConfirm.value = true; // 그로 인해 이메일 인증도 되었음
            //   verifyDone.value = true; // 인증완료 ==> 얘는 최후의 수정버튼을 클릭했을 때만!
            }
      },
      (error)=>{
        console.log(error)
        if(error.response.status === httpStatusCode.BAD_REQUEST){
            codeNotChecked.value = true;
        }
      }
    )
}

function confirmChange() {
    if(!emailVerifiedConfirm.value) { // 이메일이 인증되지 않았으면
        verifyNotDone.value = true; // 인증 X 모달을 띄움
        return;
    }

    if (emailVerifiedConfirm.value) { // 이메일 인증이 되었다면
        verifyDone.value = true; // 인증 O 모달을 띄움
        // console.log("바뀐 이메일??")
        // console.log(newEmail.value)
        emit('update-email',newEmail)
    }
    else {
        verifyNotDone.value = true;
    }
}



function closeModal() {
    isModalVisible.value = false;
    emit('setEmail-close')
}

</script>

<style scoped>

</style>