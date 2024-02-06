<template>
    <v-dialog
        class="vote-list"
        v-model="isModalVisible"
        max-width="400px"
        @click:outside="closeModal"
        persistent
    >
        <v-card class>
            <v-card-title class="text-center">투표합시다</v-card-title>
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
                    <v-card-text class="vote-title" @click="showDetailVote(vote)">{{vote.content}}</v-card-text>
                </div>
                <div v-if="currentVotes.length === 0">
                    <br>
                    <v-card-text class="vote-title">해당 투표가 없습니다.</v-card-text>
                    <br>
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
            <v-card-title class="text-center">투표 상세</v-card-title>
            <v-card-text>Q : {{ voteDetail.content }}</v-card-text>
            <v-card-text class="choice" v-for="(choice, index) in voteDetail.choices" :key="index"
                        @click="selectAnswer(choice)">
                {{ choice.content }}
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
        <v-card>
            <br><br><br>
            <v-card-title>투표가 제출되었습니다!</v-card-title>
            <br><br><br>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref, computed, watch} from 'vue'

import CreateVote from '@/components/vote/CreateVote.vue'

const emit = defineEmits([
    'vote-close'
])

const memberId = sessionStorage.getItem("id");

const isModalVisible = ref(true);

const votes = ref([
    {voteId : 1, content : "다음 중 잉글랜드 최고 미드필더는?", ongoing : true, partyMemberId : 12 },
    {voteId : 2, content : "내일 메뉴 추천 좀", ongoing : true, partyMemberId : 15 },
    {voteId : 3, content : "내일 승리 팀은?", ongoing : true, partyMemberId : 15 },
    {voteId : 4, content : "미안하다 이거 보여줄려고 어그로 끌었다", ongoing : false, partyMemberId : 12 }
])



const currentVotes = ref(votes.value.filter(vote => vote.ongoing === true));

function chooseVoteType(type) {
    if (type === 'ongoing') {
        currentVotes.value = votes.value.filter(vote => vote.ongoing === true);
    } else if (type === 'myVotes') {
        currentVotes.value = votes.value.filter(vote => vote.partyMemberId === memberId);
    } else if (type === 'finished') {
        currentVotes.value = votes.value.filter(vote => vote.ongoing === false);
    }
}
// 투표 생성 프로세스 컴포넌트 모달 on/off
const isCreateVoteVisible = ref(false);

// 투표 참여 프로세스 모달 -> 나만 아니면 됨
const choices = ref([
    {choiceId : 1, content:"제라드", count : 0, voteId : 1, isAnswer : true},
    {choiceId : 2, content:"램파드", count : 0, voteId : 1, isAnswer : true},
    {choiceId : 3, content:"스콜스", count : 0, voteId : 1, isAnswer : true},
    {choiceId : 4, content:"피자", count : 0, voteId : 2, isAnswer : true},
    {choiceId : 5, content:"치킨", count : 0, voteId : 2, isAnswer : true},
    {choiceId : 6, content:"파스타", count : 0, voteId : 2, isAnswer : true},
    {choiceId : 7, content:"한국", count : 0, voteId : 3, isAnswer : true},
    {choiceId : 8, content:"요르단", count : 0, voteId : 3, isAnswer : true},
    {choiceId : 9, content:"히히", count : 0, voteId : 4, isAnswer : true},
    {choiceId : 10, content:"투표", count : 0, voteId : 4, isAnswer : true},
    {choiceId : 11, content:"발사", count : 0, voteId : 4, isAnswer : true},
])
// 선택된 답변 - 원래 아래 있었음
let selectedAnswer = ref({
    choiceId : '',
    content : '',
    voteId : '',
})

 
const isDetailVoteVisible = ref(false);
const voteDetail = ref({
    voteId : '',
    content : '',
    ongoing : null,
    partyMemberId : '',
    choices : [],
})


function showDetailVote(vote) {
    selectedAnswer = ref({
    choiceId : '',
    content : '',
    voteId : '',
    })
    isDetailVoteVisible.value = true;
    voteDetail.value = vote;
    voteDetail.value.choices = choices.value.filter(choice => choice.voteId === vote.voteId)
}

// 투표 고르기
function selectAnswer(choice) {
    selectedAnswer.value.choiceId = choice.choiceId;
    selectedAnswer.value.content = choice.content;
    selectedAnswer.value.voteId = choice.voteId;
}

const confirmSubmit = ref(false);

function submitAnswer(selected) {
    console.log(selected)
    if(selected.content === '') {
        alert("반드시 고르셔야 합니다!")
        return;
    }

    confirmSubmit.value = true;
    isDetailVoteVisible.value = true;
}

// 투표 마감 프로세스 모달 -> 나여야 함
const isFinishVoteVisible = ref(false);



function closeModal() {
    isModalVisible.value = false; 
    emit('vote-close'); 
}

</script>

<style scoped>
.vote-list {
    max-height : 500p;
}

.vote-detail {
    text-align: center;
}

.votes {
    padding:10px;
}

.inner-card {
    text-align: center;
    margin : 20px;
    border: 1px solid black;
    max-height:300px;
    overflow-y:auto; /* 내용이 넘칠 경우 스크롤 허용 */
}

.vote-title {
    cursor: pointer;
}

.choice {
    cursor:pointer;
}

.buttons {
    margin-left : 10px;
    margin-bottom : 10px;
}

</style>