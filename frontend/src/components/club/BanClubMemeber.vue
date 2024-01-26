<template>
    <v-dialog v-model="modalVisible">
        
    </v-dialog>
</template>

<script setup>
import {ref, onMounted} from 'vue'

const modalVisible = ref(true)

const emits = defineEmits([
    'ban-member-close'
])


const clubMembers = ref([
    {memberId : 1, name : "실버스타", role : "그룹원"},
    {memberId : 2, name : "제라드", role : "그룹장"},
    {memberId : 3, name : "벨타이거", role : "그룹원"},
    {memberId : 4, name : "램파드", role : "그룹원"},
    {memberId : 5, name : "별명별명", role : "그룹원"},
    {memberId : 6, name : "글로리맨유", role : "그룹원"},
])


onMounted(() => {
    console.log("히히 멤버 강퇴 발사")
})

</script>

<style scoped>

</style>