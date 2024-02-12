<template>
    <v-dialog
        class="vote-list"
        v-model="isModalVisible"
        max-width="400px"
        @click:outside="closeModal"
        persistent
    >
        <v-card>
            <v-card-title class="modal-title text-center">투표합시다</v-card-title>
            <v-row class="votes">
                <v-col cols="12" lg="4" md="4" sm="4">
                    <v-btn @click="chooseVoteType('ongoing')">진행 중인 투표</v-btn>
                </v-col>
                <v-col cols="12" lg="4" md="4" sm="4">
                    <v-btn @click="chooseVoteType('myVotes')">내가 만든 투표</v-btn>
                </v-col>
                <v-col cols="12" lg="4" md="4" sm="4">
                    <v-btn @click="chooseVoteType('finished')">완료된 투표</v-btn>
                </v-col>   
            </v-row>
            <!-- 투표 리스트 --> 
            <v-card class="inner-card" >
                <div v-for="(vote, index) in currentVotes" :key="index">
                    <v-card-text class="vote-title" v-if="voteType == 'ongoing'" @click="showDetailVote(vote)">{{vote}}</v-card-text>
                    <v-card-text class="vote-title" v-if="voteType == 'myVotes'" @click="showMyVote(vote)">{{vote}}</v-card-text>
                    <v-card-text class="vote-title" v-if="voteType == 'finished'" @click="showFinishedVote(vote)">{{vote}}</v-card-text>
                </div>
            </v-card>
            <v-row class="buttons">
                <v-col cols="8">
                    <v-btn @click="isCreateVoteVisible = true">투표 생성</v-btn>
                </v-col>
                <v-col cols="4">
                    <v-btn @click="closeModal">나가기</v-btn>
                </v-col>
            </v-row>
        </v-card>
        <CreateVote v-if="isCreateVoteVisible" @create-vote-close="isCreateVoteVisible = false"/>
    </v-dialog>

    <!-- 투표 상세 모달 -->
    <v-dialog
        v-model="isDetailVoteVisible"
        max-width="500px"
    >
        <v-card class="vote-detail">
            <v-card-title class="text-center">진행 중인 투표</v-card-title>
            <v-card-text>Q : {{ voteDetail.content }}</v-card-text>
            <v-card-text class="option" v-for="(option, index) in voteDetail.options" :key="index"
                        @click="selectAnswer(option)">
                {{ option.content }}
            </v-card-text>
            <v-card-text v-if="selectedAnswer.content !== ''">
                당신의 선택 : <b>{{ selectedAnswer.content }}</b>
            </v-card-text>
            <v-card-actions class="buttons" style="transform:translateX(-180px)">
                <v-spacer></v-spacer>
                <v-btn color="green" @click="submitAnswer(selectedAnswer)"><h4>제출</h4></v-btn>
                <v-btn color="blue" @click="isDetailVoteVisible = false"><h4>취소</h4></v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <v-dialog v-model="confirmSubmit" max-width="300px">
        <v-card class="submit-vote">
            <v-card-title class="text-center">투표가 제출되었습니다!</v-card-title>
        </v-card>
    </v-dialog>

    <!-- 내가 만든 투표 -->
    <v-dialog 
        v-model="isMyVoteVisible"
        max-width="500px"
        >
        <v-card>
            <v-card-title class="text-center">내가 만든 투표</v-card-title>
            <v-card-actions class="buttons" style="transform:translateX(-180px)">
                <v-spacer></v-spacer>
                <v-btn color="green" @click="() => {confirmFinish = true; isMyVoteVisible = false;}"><h4>마감</h4></v-btn>
                <v-btn color="blue" @click="isMyVoteVisible = false"><h4>취소</h4></v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <v-dialog v-model="confirmFinish" max-width="300px">
        <v-card>
            <v-card-title class="close-vote text-center">투표를 마감하시겠습니까?</v-card-title>
            <v-card-actions class="buttons" style="transform:translateX(-70px)">
                <v-spacer></v-spacer>
                <v-btn color="green" @click="doneVote(selectAnswer)"><h4>확인</h4></v-btn>
                <v-btn color="blue" @click="confirmFinish = false"><h4>취소</h4></v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <!-- 마감된 투표 -->
    <v-dialog
        v-model="isFinishVoteVisible"
        max-width="500px"
        >
        <v-card>
            <v-card-title class="text-center">완료된 투표 content</v-card-title>
            <v-card-text class=text-center>정답</v-card-text>
            <v-card-text class=text-center>벌칙 당첨자</v-card-text>
            <v-card class="inner-card" >
                <v-card-text>벌칙</v-card-text>
                <v-card-text>당첨자 1</v-card-text>
                <v-card-text>당첨자 2</v-card-text>
            </v-card>
            <v-card-actions class="buttons" style="transform:translateX(-200px)">
                <v-spacer></v-spacer>
                <v-btn color="blue" @click="isFinishVoteVisible = false"><h4>목록으로</h4></v-btn>
            </v-card-actions>
        </v-card>

    </v-dialog>

    
</template>

<script setup>
import {ref, computed, watch, onMounted} from 'vue'
import {useRoute, useRouter} from 'vue-router';
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

import CreateVote from '@/components/vote/CreateVote.vue'
import {useVoteStore} from '@/stores/club/party/votes'
// import {voteConnect, voteDisconnect,doVote, finishVote } from '@/api/vote'



const serverURL = "http://localhost:9090/api/ws-stomp"
let stompClient = undefined;

const connect = () => {
  if (stompClient === undefined) {
    const socket = new SockJS(serverURL)
    stompClient = Stomp.over(socket)
    stompClient.connect({}, function () {
      stompClient.subscribe(`/sub/vote/${partyId}`, function (response) {
        console.log("******VOTELIST*********")
        console.log(response);
        // 1. 투표 생성
        // 
        // 2. 투표 진행
        // voteCount : 투표에 참여했습니다.
        // 총인원수 == voteCount : 투표 종료 할 수 있으니까 -> 생성한 사람한테 보내주기

        // 3. 투표 종료
      })
    })
  }
}


const emit = defineEmits([
    'vote-close'
])

const voteStore = useVoteStore();

const route = useRoute();

const memberId = localStorage.getItem("id");
const partyId = route.params.partyId;

const isModalVisible = ref(true);

const votes = ref([

])

const options = ref([

])

// 투표 참여 프로세스
const voteDetail = ref({
    voteId : '',
    content : '',
    ongoing : null,
    partyMemberId : '',
    options : [],
})

const onGoingVoteList = ref([]);
const finishedVoteList = ref([]);
const myVoteList = ref([]);

watch(() => voteStore.ongoingVoteList,(newOngoingList) => {
    onGoingVoteList.value = newOngoingList
},{immediate:true, deep:true})

watch(() => voteStore.finishedVoteList,(newFinishedVoteList) => {
    finishedVoteList.value = newFinishedVoteList
},{immediate:true, deep:true})

watch(() => voteStore.myVoteList,(newMyVoteList) => {
    myVoteList.value = newMyVoteList
},{immediate:true, deep:true})


const currentVotes = ref([]);
const voteType = ref('ongoing');

function chooseVoteType(type) {
    voteType.value = type
    if (type === 'ongoing') {
        currentVotes.value = onGoingVoteList.value;
        console.log(currentVotes.value)
    } else if (type === 'myVotes') {
        currentVotes.value = finishedVoteList.value;
        console.log(currentVotes.value)
    } else if (type === 'finished') {
        currentVotes.value = myVoteList.value;
        console.log(currentVotes.value)
    }
}
// 투표 생성 프로세스 컴포넌트 모달 on/off
const isCreateVoteVisible = ref(false);


// 투표 참여 프로세스 모달 -> 나만 아니면 됨
const isDetailVoteVisible = ref(false);

// 선택된 답변 - 원래 아래 있었음
let selectedAnswer;


function showDetailVote(vote) {
    selectedAnswer = ref({
    optionId : '',
    content : '',
    voteId : '',
    })
    isDetailVoteVisible.value = true;
    voteDetail.value = vote;
    voteDetail.value.options = options.value.filter(option => option.voteId === vote.voteId)
}

// 투표 고르기
function selectAnswer(option) {
    selectedAnswer.value.optionId = option.optionId;
    selectedAnswer.value.content = option.content;
    selectedAnswer.value.voteId = option.voteId;
}

// 제출
const confirmSubmit = ref(false); 

function submitAnswer(selected) {
    console.log(selected)
    if(selected.content === '') {
        alert("반드시 고르셔야 합니다!")
        return;
    }

    confirmSubmit.value = true;
    isDetailVoteVisible.value = false;
}

// 내가 만든 투표
const isMyVoteVisible = ref(false);

const myVoteDetail = ref({
    voteId : '',
    content : '',
    ongoing : null,
    partyMemberId : '',
    options : [],
})

function showMyVote(vote) {
    isMyVoteVisible.value = true;
}

const confirmFinish = ref(false)

// 내가 만든 투표 마감
function doneVote(selectAnswer) {
    confirmFinish.value = false;
    isMyVoteVisible.value = false;
    alert("투표 마감 완료!")
}



// 투표 결과 모달 -> 나여야 함
const isFinishVoteVisible = ref(false);

function showFinishedVote() {
    isFinishVoteVisible.value = true;
}

function closeModal() {
    isModalVisible.value = false; 
    console.log("*****투표 나가기*****")
    emit('vote-close'); 
}

onMounted(() => {
    voteStore.getOngoingVoteList(partyId);
    voteStore.getFinishedVoteList(partyId);
    voteStore.getMyVoteList(partyId, memberId);
    console.log("VoteList ONMOUNTED!!!!!!!");
    connect();
})

</script>

<style scoped>


.inner-card {
    text-align: center;
    margin : 20px;
    border: 1px solid black;
    height:150px;
    overflow-y:auto; /* 내용이 넘칠 경우 스크롤 허용 */
}

.submit-vote {
    height:200px;
    display: flex;
    justify-content: center;
}

.close-vote{
    height:200px;
    display: flex;
    justify-content: center;
}

.vote-list {
    max-height : 500p;
}

.vote-detail {
    text-align: center;
}

.votes {
    padding:10px;
}


.vote-title {
    cursor: pointer;
}

.vote-types {
    height:500px;
    overflow-y:auto;
}

.option {
    cursor:pointer;
}

.buttons {
    margin-left : 10px;
    margin-bottom : 10px;
}

</style>