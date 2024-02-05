<template>
    <v-dialog 
        v-model="modalVisible"
        max-width="400px"
        max-height="600px"
        @click:outside="closeModal"
        persistent
        >
        <v-card v-if="clubMemberList.length !== 1">
            <br>
            <v-card-title class="member-list-title"><h2>넌 너가라</h2></v-card-title>
            <br>
            <div class="selected" v-if="bannedMember.memberNickName !== ''">
                <b>강퇴 후보</b> : <b><u>{{ bannedMember.memberNickName }}</u></b> <br>
            </div>
            
            <div v-for="(member, index) in clubMemberList" :key="index">
                <v-card-text class="member" v-if="member.role !== 'host'" @click="selectMember(member)">
                    {{ member.memberNickName }}
                </v-card-text>
            </div>
            <v-card-actions class="buttons">
                <v-spacer></v-spacer>
                <v-btn color="red" @click="showBanConfirm">진행시켜</v-btn>
                <v-btn color="green" @click="closeModal"><h3>기회준다 잘해라</h3></v-btn>
            </v-card-actions>
        </v-card>
        <v-card v-else>
            <br><br>
            <v-card-title>아직 멤버가 없습니다!</v-card-title>
            <br><br>
        </v-card>
    </v-dialog>
    
    <!-- 강퇴 직전 재확인 모달 -->
    <v-dialog 
        v-model="isBanConfirm"
        max-width="400px"
        @click:outside="closeModal"
        persistent
    >
        <v-card>
            <br>
            <v-card-title class="member-list-title"><h2>강퇴 재확인</h2></v-card-title>
            <v-card-text class="mention">
                <h2><i><b>{{ bannedMember.memberNickName }}...</b></i></h2> <br>
                이 사람이 그렇게 미웠습니까?<br>
                <b>{{ bannedMember.memberNickName }}</b>에게 더 기회를 주면 안되겠습니까?<br>
                관대함을 베푼다면 <b>{{ bannedMember.memberNickName }}</b>도 바뀔 것입니다.<br>
                자고로 <b>리더</b>는 묵직해야합니다.<br><br>
                <b><h3>다시 생각해주세요!</h3></b>
                <br>
            </v-card-text>
            <v-card-actions class="buttons">
                <v-spacer></v-spacer>
                <v-btn color="red" @click="showBanConfirm">진행시켜</v-btn>
                <v-btn color="green" @click="closeModal"><h3>미워도 다시 한 번</h3></v-btn>
            </v-card-actions>
        </v-card>

    </v-dialog>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue'

const props = defineProps({
    clubMemberList:Array
})


const emits = defineEmits([
    'ban-member-close'
])

// BanClubMember 모달 on/off
const modalVisible = ref(true)

const clubMemberList = computed(() => {
    return props.clubMemberList
})

const bannedMember = ref({
    memberId : '',
    memberNickName : '',
    role : '',
});

function selectMember(member) {
    bannedMember.value.memberId = member.memberId;
    bannedMember.value.memberNickName = member.memberNickName;
    bannedMember.value.role = member.role;
}

// 강퇴하기 직전 재확인 모달
const isBanConfirm = ref(false);

function showBanConfirm() {
    modalVisible.value = false;
    isBanConfirm.value = true;
}


// 강퇴 api
function banMember() {
    
}

function closeModal() {
    modalVisible.value = false
    emits('ban-member-close')
}

onMounted(() => {
    console.log("히히 멤버 강퇴 발사")
})

</script>

<style scoped>


.member-list-title {
    text-align: center !important;
}

.selected {
    text-align: center !important;
}

.member {
    text-align: center !important;
    cursor: pointer;
}

.mention {
    font-size : 1rem;
    text-align : center;
    margin-top : 10px;
    padding:10px;
}


.buttons {
    transform: translateX(-70px);
}
</style>