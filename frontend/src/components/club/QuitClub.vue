<template>
    <v-dialog
        v-model="modalVisible"
        max-width="400px"
        max-height="600px"
        @click:outside="closeModal"
        persistent
    >
        <v-container v-if="role === '그룹장'">
            <v-card>
                <v-card-text class="mention">
                    <br>
                    <h1>잠깐!</h1> <br>
                    당신은 <b><u>그룹장</u></b> 아닙니까?<br>
                    어떻게 그룹을 <b><i>헌신짝</i></b><br>처럼
                    내칠 수가 있습니까?<br>
                    당신만을 바라보고 있는 그룹원이 많습니다.<br><br>
                    <b><h3>다시 생각해주세요!</h3></b><br>
                    <br>
                </v-card-text>
                <v-card-actions style="transform:translateX(-75px)">
                    <v-spacer></v-spacer>
                    <v-btn color="red" @click="showMemberList">진행시켜</v-btn>
                    <v-btn color="green" @click="closeModal"><h3>다시 생각해보자</h3></v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
        <v-container v-else>
            <v-card>
                <v-card-text class="mention">
                    <h2>정말로 탈퇴하시겠습니까?</h2><br>
                    우리의 <b>추억</b>이 고작 그 정도 입니까?<br>
                    <b>추억</b>을 되돌아본다면 <br>
                    생각이 바뀔 수도 있습니다.<br>
                    부디 다시 생각해주세요!<br>
                </v-card-text>
                <v-card-actions style="transform:translateX(-75px)">
                    <v-spacer></v-spacer>
                    <v-btn color="red" @click="showTakeOver">진행시켜</v-btn>
                    <v-btn color="green" @click="closeModal"><h3>다시 생각해보자</h3></v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
    </v-dialog>

    <!-- 그룹원 명단 => 그룹장으로 넘김 -->
    <v-dialog
        v-model="isMemberListVisible"
        max-width="400px"
    >  
        <v-container>
            <v-card>
                <v-card-text class="member-list-title">
                    <h1>그룹원 명단</h1> <br>
                    <div v-if="nextLeader.name !== ''">
                        <b>차세대 리더</b> : <b><u>{{ nextLeader.name }}</u></b> <br>
                    </div>
                </v-card-text>
                <v-card-text 
                    class="member"
                    v-for="(member, index) in clubMembers" 
                    :key="index" 
                    @click="selectLeader(member)"
                    >
                    {{ member.name}}
                <v-icon v-if="member.role === '그룹장'" class="star">mdi-star</v-icon>
                </v-card-text>
                <v-card-actions style="transform:translateX(-40px)">
                    <v-spacer></v-spacer>
                    <v-btn color="red" @click="showTakeOver">진행시켜</v-btn>
                    <v-btn color="green" @click="closeModal"><h3>진짜 다시 생각해보자</h3></v-btn>
                </v-card-actions>
            </v-card>
        </v-container>
    </v-dialog>
    <v-dialog
        v-model="isTakeOverVisible"
        max-width="400px"
        @click:outside="closeModal"
    >
        <v-card>
            <v-card-text>
                <h1>마지막 인사</h1><br>
                별 하나의 추억과...<br>
                별 하나의 사랑과...<br>
                별 하나의 그룹...<br>
                헤아릴 수 없는 별들 만큼이나<br>
                많은 그룹이 있습니다.<br>
                그 안에서 만들어진 추억은 귀중한 것입니다.<br>
                이대로 홀연히 떠나시겠습니까?<br>
                
            </v-card-text>
            <v-card-actions style="transform:translateX(-40px)">
                    <v-spacer></v-spacer>
                    <v-btn color="red" @click="goodBye = true">진행시켜</v-btn>
                    <v-btn color="green" @click="closeModal"><h3>돌이킬 수 있는 마지막 기회</h3></v-btn>
                </v-card-actions>
        </v-card>

    </v-dialog>
    <v-dialog v-model="goodBye" max-width="600px" persistent>
        <v-container>
            <v-card align-items="center" class="text-center">
                <br>
                <v-card-title>
                    <h2>함께해서 더러웠고</h2><br><br>
                    <h2>다시는 만나지 말자</h2>
                </v-card-title>
                <br>
                <v-card-actions class="justify-center">
                    <v-btn color="blue lighten-1" text @click="leaveForever" width="200px"><h1>확인</h1></v-btn>
                </v-card-actions>
                <br>
            </v-card>
        </v-container>

    </v-dialog>

</template>

<script setup>
import {ref, onMounted} from 'vue'
import {useRouter} from 'vue-router'

const router = useRouter();

const role = "그룹장"; // 나중에 스토어로 불러올 것임.

const emits = defineEmits([
    'quit-club-close'
])


const modalVisible = ref(true)


// 그룹장 넘기기 위한 그룹원 명단 보이기
const isMemberListVisible = ref(false)

function showMemberList() {
    isTakeOverVisible.value = false;
    isMemberListVisible.value = true;
}

const nextLeader = ref({
    memberId : null,
    name : '',
    role : '',
});


function selectLeader(member) {
    if (member.role === '그룹장'){
        return
    }
    nextLeader.value.memberId = member.memberId;
    nextLeader.value.name = member.name;
    nextLeader.value.role = "그룹장"
}

// 그룹장 넘기기
const isTakeOverVisible = ref(false)
function showTakeOver() {
    if (role === "그룹장" && nextLeader.value.name === '') {
        return
    }
    isTakeOverVisible.value = true
    isMemberListVisible.value = false
}

const goodBye = ref(false)

function leaveForever() {
    closeModal();
    router.push("/")
}


// 대충 store에서 그룹원들의 명단을 가져옴
const clubMembers = ref([
    {memberId : 1, name : "실버스타", role : "그룹원"},
    {memberId : 2, name : "제라드", role : "그룹장"},
    {memberId : 3, name : "벨타이거", role : "그룹원"},
    {memberId : 4, name : "램파드", role : "그룹원"},
    {memberId : 5, name : "별명별명", role : "그룹원"},
    {memberId : 6, name : "글로리맨유", role : "그룹원"},
])

// QuitClub.vue 닫음
function closeModal() {
    modalVisible.value = false
    emits('quit-club-close')
}

onMounted(() => {
    console.log("히히 그룹 탈퇴 발사")
})

</script>


<style scoped lang="scss">
.v-container {
    padding:0px;
}



.v-card-title {
    font-weight : bold;
    font-size : 2rem;
    text-align : center;
    margin-top : 10px;
}

.mention {
    font-size : 1rem;
    text-align : center;
    margin-top : 10px;
    padding:10px;
}

.member-list-title {
    text-align: center !important;
}

.member {
    margin-left : 150px;
    cursor: pointer;
}
.star{
    transform:translateY(-5px) translateX(-80px) !important 
} 

.text-center {
    text-align: center;
}

.justify-center {
    display: flex;
    justify-content: center;
}
</style>