<template>
    <v-dialog
        v-model="isModalVisible"
        max-width="600px"
        max-height="500px"
        @click:outside="closeModal"
        persistent
    >
        <v-card>
            <v-card-title class="text-center">투표 생성 </v-card-title>
            <v-row>
                <v-col>
                    <v-text-field
                    class="vote-title"
                    v-model="voteTitle"
                    label="투표 제목"
                    ></v-text-field>
                </v-col>
            </v-row>

            <v-row v-for="(choice, index) in choices" :key="index">
                <v-col cols="10">
                    <v-text-field
                    class="choice-text"
                    v-model="choice.text"
                    label="선택지 내용"
                    ></v-text-field>
                </v-col>
                <v-col cols="2">
                    <v-btn class="button" @click="removeChoice(index)" icon>
                        <v-icon>mdi-delete</v-icon>
                    </v-btn>
                </v-col>
            </v-row>

            <v-row >
                <v-col>
                    <v-btn class="button" @click="addChoice">+</v-btn>
                </v-col>
            </v-row>

            <v-row>
                <v-col>
                    <v-select
                    class="select-penalty"
                    v-model="selectedPenalty"
                    :items="penalties"
                    label="벌칙 선택"
                    ></v-select>
                </v-col>
            </v-row>

            <v-row >
                <v-col>
                    <v-btn class="button" @click="submitVote" color="#292646">투표 생성</v-btn>
                </v-col>
                <v-col>
                    <v-btn class="button" @click="closeModal" color="#292646">목록으로</v-btn>
                </v-col>
            </v-row>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref} from 'vue'

const isModalVisible = ref(true);

const emit = defineEmits([
    'create-vote-close'
])

const voteTitle = ref('');
const choices = ref([{ text: '' }]);
const selectedPenalty = ref(null);

const penalties = [
    '음소거',
    '흑백화면',
    '음성변조',
  // 다른 벌칙 항목을 여기에 추가
];

const addChoice = () => {
    choices.value.push({ text: '' });
};

const removeChoice = (index) => {
    if (choices.value.length > 1) {
        choices.value.splice(index, 1);
    }
};

function closeModal() {
    isModalVisible.value = false;
    console.log("CreateVote emit!!!") 
    emit('create-vote-close'); 
}

</script>

<style lang="scss" scoped>
.vote-title, .select-penalty{
    margin-left:50px;
    margin-right:50px;
}

.choice-text{
    margin-left:50px;
}

.button {
    display: flex;
    justify-content: center;
    align-items: center;
    margin:auto;
    box-shadow: none !important;
    background:transparent
}
</style>