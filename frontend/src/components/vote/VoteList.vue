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
            <v-card class="inner-card" >
                <div v-for="(vote, index) in currentVotes" :key="index">
                    <v-card-text class="vote-title" 
                        v-if="voteType == 'ongoing' && vote.user.userId !== memberId" 
                        @click="showDetailVote(vote)">{{ vote.title }}</v-card-text>
                    <v-card-text class="vote-title" 
                        v-if="voteType == 'myVotes' && vote.ongoing === 1" 
                        @click="showMyVote(vote)">{{ vote.title }}</v-card-text>
                    <v-card-text class="vote-title" 
                        v-if="voteType == 'finished'" 
                        @click="showFinishedVote(vote)">{{ vote.title }}</v-card-text>
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
        <CreateVote v-if="isCreateVoteVisible" @create-vote-close="isCreateVoteVisible = false" :nickname="nickname"/>
    </v-dialog>

    <!-- 투표 참여 모달 -->
    <v-dialog
        v-model="isDetailVoteVisible"
        max-width="500px"
    >
        <v-card class="vote-detail">
            <v-card-title class="text-center">진행 중인 투표</v-card-title>
            <v-card-text>Q : {{ voteDetail.title }}</v-card-text>
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

    <!-- 투표 마감 ==> 내가 만든 투표 상세  -->
    <v-dialog 
        v-model="isMyVoteVisible"
        max-width="500px"
        >
        <v-card>
            <!-- {{ myVoteDetail }} -->
            <v-card-title class="text-center"> <h2>{{ myVoteDetail.title }}</h2> </v-card-title>
            <v-card-text v-for="(option,index) in myVoteDetail.options" :key="index" class="text-center" @click="finalizeAnswer(option)">
                {{ option.content }}
            </v-card-text>
            <v-card-text v-if="myAnswer.answerOption !== ''" class="text-center">
                당신의 선택 : <b>{{ myAnswer.answerOption }}</b>
            </v-card-text>


            <v-card-actions class="buttons" style="transform:translateX(-180px)">
                <v-spacer></v-spacer>
                <v-btn color="green" @click="() => {confirmFinish = true; isMyVoteVisible = false;}"><h4>마감</h4></v-btn>
                <v-btn color="blue" @click="isMyVoteVisible = false"><h4>취소</h4></v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>

    <v-dialog v-model="confirmFinish" max-width="300px">
        <v-card class="text-center">
            <v-card-title class="close-vote">투표를 마감하시겠습니까?</v-card-title>
            <v-card-text><h2>최종 답안</h2></v-card-text>
            <v-card-text><h3>{{ myAnswer.answerOption }}</h3></v-card-text>
            <v-card-actions class="buttons" style="transform:translateX(-80px)">
                <v-spacer></v-spacer>
                <v-btn color="green" @click="doneVote"><h4>확인</h4></v-btn>
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
            <!-- {{ finishedVoteDetail }} -->
            <v-card-title class="text-center">완료된 투표</v-card-title>
            <!-- {{ finishedVoteDetail.options }} -->
            <v-card-text class="text-center">
                정답
                <h3>
                    {{finishedVoteDetail.options.filter((option) => option.optionId ===finishedVoteDetail.answerOptionId)[0].content }}
                </h3>
            </v-card-text>
            <v-card-text v-for="(option, index) in finishedVoteDetail.options" :key="index" class="text-center">
                {{ option.content }} - <span>{{ option.users.length }}</span>
            </v-card-text>
            <v-card class="inner-card" >
                <!-- <v-card-text><h3>벌칙 당첨자</h3></v-card-text> -->
                <v-card-text class="text-center">벌칙 <h4>{{ finishedVoteDetail.penalty.content }}</h4></v-card-text>
                <div v-for="(users,index) in finishedVoteDetail.options.filter((option) => option.optionId !== finishedVoteDetail.answerOptionId)" :key="index">
                    <v-card-text v-for="(user, idx) in users.users" :key="idx">
                        {{ user.name }}
                    </v-card-text>
                </div>
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

import {getMember} from '@/api/member'
import CreateVote from '@/components/vote/CreateVote.vue'
import {voteConnect, createVote,doVote,finishVote } from '@/api/vote'
import {useVoteStore} from '@/stores/club/party/votes'


const emit = defineEmits([
    'vote-close'
])



const voteStore = useVoteStore();
const route = useRoute();

const memberId = localStorage.getItem("id");
const nickname = ref('');
const clubId = route.params.clubId;
const partyId = route.params.partyId;

const isModalVisible = ref(true);

const getMemberInfo = () => {
    getMember(
        memberId,
        ({data,status}) => {
        console.log("data ==> ",data);
        nickname.value = data.data.nickname;
        },
        (error) => {
        console.log("살려줘")
        console.log(error)
        }
    )
}


voteConnect(partyId);

// 투표 참여 프로세스
const voteDetail = ref({
    partyId : '',
    voteId : '',
    content : '',
    ongoing : null,
    partyMemberId : '',
    options : [],
})

const onGoingVoteList = ref([]);
const finishedVoteList = ref([]);
const myVoteList = ref([]);

const currentVotes = computed(() => {
    switch (voteType.value) {
        case 'ongoing':
            return onGoingVoteList.value;
        case 'myVotes':
            return myVoteList.value;
        case 'finished':
            return finishedVoteList.value;
        default:
            return [];
    }
});
const voteType = ref('ongoing');

watch(() => voteStore.ongoingVoteList,(newOngoingList) => {
    onGoingVoteList.value = newOngoingList
},{immediate:true})

watch(() => voteStore.myVoteList,(newMyVoteList) => {
    myVoteList.value = newMyVoteList
},{immediate:true})

watch(() => voteStore.finishedVoteList,(newFinishedVoteList) => {
    finishedVoteList.value = newFinishedVoteList
},{immediate:true})


function chooseVoteType(type) {
    voteType.value = type
    if (type === 'ongoing') {
        currentVotes.value = onGoingVoteList.value;
        console.log(currentVotes.value)
    } else if (type === 'myVotes') {
        currentVotes.value = myVoteList.value;
    } else if (type === 'finished') {
        currentVotes.value = finishedVoteList.value;
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
        partyId : partyId,
        voteId : '',
        memberId : memberId,
        nickname : nickname.value,
        optionId : '',
        content:'',
    })
    isDetailVoteVisible.value = true;
    voteDetail.value = vote;
}

// 투표 해버리기
function selectAnswer(option) {
    selectedAnswer.value.optionId = option.optionId;
    selectedAnswer.value.voteId = voteDetail.value.voteId;
    selectedAnswer.value.content = option.content;
    console.log("내가 고를 선지")
    console.log(selectedAnswer.value)
}

// 투표 제출
const confirmSubmit = ref(false); 

function submitAnswer(selected) {
    console.log(selected)
    if(selected.content === '') {
        alert("반드시 고르셔야 합니다!")
        return;
    }
    doVote(selected)
    confirmSubmit.value = true;
    isDetailVoteVisible.value = false;
}



// 투표 종료 - 내가 만든 투표
const isMyVoteVisible = ref(false);

const myVoteDetail = ref({
    voteId : '',
    title : '',
    voteCount : '', 
    penalty : {
        content : '',
        penaltyId : '',    
    },
    user : {
        userId : '',
        name : '',
    },
    options : [],
})

function showMyVote(vote) {
    console.log("*****내가 만든 투표 상세*****")
    console.log(vote)
    isMyVoteVisible.value = true;
    myVoteDetail.value.voteId = vote.voteId;
    myVoteDetail.value.title = vote.title;
    myVoteDetail.value.voteCount = vote.voteCount;
    myVoteDetail.value.penalty = vote.penalty;
    myVoteDetail.value.user = vote.user
    myVoteDetail.value.options = vote.options;
}

// 정답 확정 하기
let myAnswer = ref({
    memberId : '',
    nickname : '',
    answerOptionId : '',
    answerOption : '',
})

function finalizeAnswer(answer) {
    if (answer.optionId === '') {
        return;
    }
    myAnswer.value.memberId = myVoteDetail.value.user.userId;
    myAnswer.value.nickname = myVoteDetail.value.user.name;
    myAnswer.value.answerOptionId = answer.optionId;
    myAnswer.value.answerOption = answer.content;
    console.log("당신의 최종 답안 => ", myAnswer.value)
}

const confirmFinish = ref(false)

// 내가 만든 투표 마감
function doneVote() {
    let data = {
        partyId : partyId,
        voteId : myVoteDetail.value.voteId,
        memberId : myVoteDetail.value.user.userId,
        nickname : myVoteDetail.value.user.name,
        answerOptionId : myAnswer.value.answerOptionId
    }
    if (data.answerOptionId === '') {
        alert("정답은 반드시 고르셔야 합니다!")
        return;
    }
    console.log(data)
    finishVote(data);
    confirmFinish.value = false;
    isMyVoteVisible.value = false;
    // alert("투표 마감 완료!")
}



// 투표 결과 모달 -> 나여야 함
const isFinishVoteVisible = ref(false);

const finishedVoteDetail = ref({
    answerOptionId : '',
    answerOptionContent : '',
    penalty : {
        content : '',
        penaltyId : '',
    },
    user : {
        userId : '',
        name : '',
    },
    options : [],
    penaltyUsers : [],
})





function showFinishedVote(vote) {
    finishedVoteDetail.value.answerOptionId = vote.answerOptionId;
    // finishedVoteDeetail.value.answerOptionContent = vote.answerOptionContent;
    finishedVoteDetail.value.penalty = vote.penalty
    finishedVoteDetail.value.user = vote.user
    finishedVoteDetail.value.options = vote.options
    // finishedVoteDetail.value.penaltyUsers = vote.penaltyUsers;
    isFinishVoteVisible.value = true;
}

function closeModal() {
    isModalVisible.value = false; 
    console.log("*****투표 나가기*****")
    emit('vote-close'); 
}

onMounted(() => {
    getMemberInfo()
    voteStore.getOngoingVoteList(partyId);
    voteStore.getFinishedVoteList(partyId);
    voteStore.getMyVoteList(partyId, memberId);
    console.log("VoteList ONMOUNTED!!!!!!!");
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