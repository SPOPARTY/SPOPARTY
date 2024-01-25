<template>
    <v-card class= "ma-5" outlined>
        <v-row class="pa-4" align="center" justify="start" no-gutters style="background-color: #E0E0E0;">
            <v-col cols="10">
                <div class="headline">그룹 이름</div>
                <div>정원 - {{clubMembers.length}}/6</div>
            </v-col>
            <v-col cols="2">
                <v-icon large @click="showClubMemberFunc">mdi-cog</v-icon>
                <ClubLeader 
                    v-if="isClubMemberFuncVisible && loginUser.role === '그룹장'"
                    @club-leader-close="closeClubMemberFunc"
                    />
                <ClubMember 
                    v-if="isClubMemberFuncVisible && loginUser.role === '그룹원'"
                    @club-member-close="closeClubMemberFunc"
                    />
            </v-col>
        </v-row>
        
        <v-btn block color="pink lighten-3" @click="showClubMembers">그룹원 보기</v-btn>

        <v-btn block color="blue" style="margin-top:30px;" @click="showInvite">그룹으로 초대</v-btn>

        <v-btn block color="red" dark class="mt-4" style="height:100px;">
            파티를 열어보세요!
        </v-btn>
    </v-card>

    <!-- 그룹원 보기 -->
    <v-dialog 
        v-model="isClubMemberVisible"
        max-width="400px"
        max-height="400px"
        >
        <v-card class="member-list">
            <v-card-title>그룹원 목록</v-card-title>
            <div v-for="(member, index) in clubMembers" :key="index" class="member">
                <v-card-text>
                    {{ member.name}}
                    <v-icon v-if="member.role === '그룹장'" class="star">mdi-star</v-icon>
                </v-card-text>
            </div>
        </v-card>
    </v-dialog>

    <!-- 그룹으로 초대 -->
    <v-dialog
        v-model="isInviteVisible"
        max-width="400px"
        max-height="200px"
    >   
        <v-card class="align-items-center">
            <v-card-title>초대 링크를 복사하세요!</v-card-title>
            <div class="text-to-copy">
                <v-card-text class="invite">
                    URL : {{ textToCopy }}
                    <v-btn @click="copyText" class="copy-btn">
                        <v-icon>mdi-content-copy</v-icon>
                    </v-btn>
                </v-card-text>
            </div>
        </v-card>

    </v-dialog>
  </template>

<script setup>
import {ref} from 'vue'
import ClubLeader from '@/components/club/ClubLeader.vue';
import ClubMember from '@/components/club/ClubMember.vue';


const clubMembers = ref([
    {memberId : 1, name : "실버스타", role : "그룹원"},
    {memberId : 2, name : "제라드", role : "그룹장"},
    {memberId : 3, name : "벨타이거", role : "그룹원"},
    {memberId : 4, name : "램파드", role : "그룹원"},
    {memberId : 5, name : "별명별명", role : "그룹원"},
    {memberId : 6, name : "글로리맨유", role : "그룹원"},
])

const loginUser = {
    memberId : 2, name : "제라드", role : "그룹장"
}

// 그룹원 보기 on/off
const isClubMemberVisible = ref(false)

function showClubMembers() {
    isClubMemberVisible.value = true;
}

// 그룹원 초대 on/off
const isInviteVisible = ref(false)
function showInvite() {
    isInviteVisible.value = true;
}

// 복사할 초대 링크
const textToCopy = ref('www.naver.com')

// 그룹 초대 링크 복사
const copyText = async() => {
    try {
        await navigator.clipboard.writeText(textToCopy.value);
        alert("텍스트가 클립보드에 복사되었습니다.")
    } catch (err) {
        console.error('복사 실패 : ',err);
        alert('텍스트 복사에 실패했습니다.')
    }
}


// 그룹원 or 그룹장 세부기능 on/off
const isClubMemberFuncVisible = ref(false);

const showClubMemberFunc = () => {
    console.log("히히 그룹원/그룹장 세부기능 발사")
    isClubMemberFuncVisible.value = true;
}

function closeClubMemberFunc() {
    isClubMemberFuncVisible.value = false
}

</script>


<style lang="scss" scoped>


.member-list{
    border:solid;
    border-radius: 40px;
    & .member {
        transform:translateX(150px)  
    }
    & .star {
        transform:translateY(-3px) translateX(-80px) !important
    }
}

.v-card-title {
    text-align: center;
}

div.text-to-copy {
    text-align: center;
    margin-left:10px;
    transform:translateY(-10px) translateX(10px);

}

.copy-btn {
    box-shadow: none !important;
    background:none;
    & hover {
        background-color : transparent !important
    }
}

</style>