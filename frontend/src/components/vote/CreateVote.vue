<template>
    <v-dialog
        v-model="isModalVisible"
        max-width="480px"
        @click:outside="closeModal"
        persistent
    >
        <v-card class="outer-card pb-4">
            <!-- <v-card-title class="text-center"><h3>투표 생성</h3> </v-card-title> -->
            <v-row class="mt-2">
                <v-col>
                    <v-text-field
                    class="vote-title px-12"
                    v-model="voteTitle"
                    label="투표 제목"
                    bg-color="#E5E8EE"
                    :rules="titleRules"
                    ></v-text-field>
                </v-col>
            </v-row>

            <v-card class="inner-card pt-4">
                <v-row v-for="(option, index) in options" :key="index">
                    <v-col cols="9">
                        <v-text-field
                        class="option-text"
                        v-model="options[index]"
                        label="선택지 내용"
                        :rules="optionRules"
                        bg-color="#E5E8EE"
                        ></v-text-field>
                    </v-col>
                    <v-col cols="2">
                        <v-btn background-color="white" class="button" @click="removeOption(index)" icon>
                            <v-icon>mdi-delete</v-icon>
                        </v-btn>
                    </v-col>
                </v-row>
            </v-card>
            
            <v-row >
                <v-col>
                    <v-btn class="button choice" @click="addOption"><b>선택지 추가</b></v-btn>
                </v-col>
            </v-row>

            <v-row class="justify-center">
                <v-col cols="4">
                    <v-btn class="button custom-penalty-btn" @click="showCustomPenalty = true"><b>커스텀 벌칙</b></v-btn>
                </v-col>
                <v-col cols="7">
                    <v-select
                    class="select-penalty"
                    density="comfortable"
                    v-model="selectedPenalty"
                    :items="penalties"
                    label="벌칙 선택"
                    bg-color="#E5E8EE"
                    ></v-select>
                </v-col>
            </v-row>

            <v-row class="justify-center">
                <v-col cols="4">
                    <v-btn class="bt-button" @click="submitVote" color="primary">투표 생성</v-btn>
                </v-col>
                <v-col cols="3">
                    <v-btn class="bt-button" @click="closeModal" color="red">뒤로</v-btn>
                </v-col>
            </v-row>
        </v-card>
    </v-dialog>

    <!-- 커스텀 벌칙 추가 -->
    <v-dialog 
        v-model="showCustomPenalty"
        max-width="400px"
        max-height="600px"
        @click:outside ="showCustomPenalty = false"
        >
        <v-card class="custom-penalty">
            <v-card-title class="text-center h5">패널티를 직접 정하세요!</v-card-title>
            <v-text-field
                v-model="customPenalty"
                :rules="penaltyRules"
            ></v-text-field>
            <v-row class="my-6 justify-center">
                <v-col cols="5">
                    <v-btn class="custom-button" @click="setCustomPenalty(customPenalty)" color="primary">커스텀 벌칙 생성</v-btn>
                </v-col>
                <v-col cols="3">
                    <v-btn class="custom-button" @click="showCustomPenalty = false" color="#292646" variant="outlined"><b>뒤로</b></v-btn>
                </v-col>
            </v-row>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref,watch,onMounted} from 'vue'
import {useRouter,useRoute} from 'vue-router'
// import Stomp from 'webstomp-client'
// import SockJS from 'sockjs-client'

import {voteConnect, createVote } from '@/api/vote'
import {useVoteStore} from '@/stores/club/party/votes'

const isModalVisible = ref(true);

const props = defineProps({
    nickname : String
})

const emit = defineEmits([
    'create-vote-close'
])


const route = useRoute();
const clubId = route.params.clubId;

const partyId = route.params.partyId;
const memberId = localStorage.getItem("id");
const nickname = ref(props.nickname);
const voteTitle = ref('');
const options = ref(['']);
const selectedPenalty = ref(null);

const voteStore = useVoteStore();



const penalties = ref([
    '음소거',
    '흑백화면',
    '음성변조',
  // 다른 벌칙 항목을 여기에 추가
]);


// 각종 규칙
const penaltyRules = [
    v => !!v || "빈 칸이어서는 안됩니다",
    v => (v && v.length <= 10) || "벌칙의 길이는 최대 10자입니다!"
]

const titleRules = [
    v => !!v || "빈 칸이어서는 안됩니다",
    v => (v && v.length <= 20) || "제목의 길이는 최대 20자입니다."
]

const optionRules = [
    v => !!v || "빈 칸이어서는 안됩니다!",
    v => (v && v.length <= 20) || "선택지의 길이는 최대 20자입니다."
]

watch(() => penalties,(newPenalties) => {
    penalties.value = newPenalties
})


const addOption = () => {
    if(options.value.length > 4){
        return;
    }
    options.value.push('');
};

const removeOption = (index) => {
    if (options.value.length > 1) {
        options.value.splice(index, 1);
    }
};

// 커스텀 패널티 생성 관련 
const showCustomPenalty = ref(false);

const customPenalty = ref('');
const setCustomPenalty = (penalty) => {
    if (penalty === '' || penalty.length > 20) {
        return;
    }

    penalties.value.push(penalty);
    // console.log("벌칙 리스트! ->",penalties.value)
    alert("커스텀 벌칙이 생성되었습니다!")
    showCustomPenalty.value = false;
    customPenalty.value = ''
}

voteConnect(partyId);

function submitVote() {
    if (voteTitle.value === ""){
        alert("제목이 빈 칸어서는 안됩니다!")
        return;
    }

    if( selectedPenalty.value === ""){
        alert("패널티는 반드시 고르셔야 합니다!")
        return;
    }

    if (options.value.length < 2) {
        console.log("*****선택지의 개수는???***** => ", options.value.length)
        alert("투표 선택지의 옵션은 반드시 2개 이상이어야 합니다!")
        return;
    }

    for (let i = 0; i < options.value.length; i++) {
        if(options.value[i] === '') {
            alert("선택지 내용 중에 빈 칸이 있어서는 안됩니다!")
            return;
        }
        if (options.value[i].length > 20){
            alert("선택지 내용 길이는 최대 20자입니다!")
            return
        } 
    }

    // console.log("partyId -> ",partyId);
    // console.log("memberId -> ",memberId);
    // console.log("title -> ", voteTitle.value);
    // console.log("nickname -> ", props.nickname);
    // console.log("options -> ", options.value);
    // console.log("penalty -> " , selectedPenalty.value);
    const data = {
        partyId : partyId,
        memberId : memberId,
        nickname : props.nickname,
        title : voteTitle.value,
        options : options.value,
        penalty : selectedPenalty.value,
    }
    createVote(data);
    closeModal();
}


// 투표 생성 모달 닫기
function closeModal() {
    isModalVisible.value = false;
    // console.log("CreateVote emit!!!") 
    emit('create-vote-close'); 
}

</script>

<style lang="scss" scoped>
.v-card-title {
    margin-top:10px;
    margin-bottom : 10px;
}

// .vote-title, .select-penalty{
//     margin-left:50px;
//     margin-right:50px;
// }

.outer-card {
    border : 5px solid royalblue;
    border-radius: 6px;

}

.inner-card {
    text-align: center;
    margin : 20px;
    height:300px;
    border: 1px solid #D3AC2B;
    overflow-y:auto;
}

.option-text{
    margin-left:50px;
}



.button {
    display: flex;
    justify-content: center;
    align-items: center;
    margin:auto;
    box-shadow: none !important;
    background:#E4E3DD;
}
.custom-button {
    background-color: #c3cad5;
}
.bt-button {
    font-size: 1.1rem;
    box-shadow: none !important;
    // background:#CBD0D8;
}
.custom-penalty{
    padding:20px;
}

.custom-penalty-btn{
    height: 3rem;
}
.v-btn:hover {
    transform: scale(1.15);
    /* 호버 시 버튼 y축 방향으로 이동 */
}
</style>