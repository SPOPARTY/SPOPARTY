<template>
    <v-dialog 
        v-model="modalVisible"
        max-width="300px"
        max-height="600px"
        @click:outside="closeModal"
        persistent
        >
        <v-card v-if="clubMemberList.length !== 1">
            <v-card-title>여기는 그룹 강퇴</v-card-title>
            
            <div v-for="(member, index) in clubMemberList" :key="index">
                <v-card-text class="member" v-if="member.role !== 'host'" @click="selectMember(member)">
                    {{ member.memberNickName }}
                </v-card-text>
            </div>
        </v-card>
        <v-card v-else>
            <br><br>
            <v-card-title>아직 멤버가 없습니다!</v-card-title>
            <br><br>
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

const modalVisible = ref(true)

const clubMemberList = computed(() => {
    return props.clubMemberList
})

function selectMember(member) {

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
.member{
    cursor:pointer
}

</style>