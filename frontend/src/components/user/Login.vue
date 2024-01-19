<template>
    <div>
        <h1>로그인 ㄱㄱ</h1>
        <button @click="showAlert">Confirm</button>
        
    </div>
</template>

<script setup>
import Swal from 'sweetalert2'

const showAlert = () => {
  Swal.fire({
    title: "처음 들어봅니다, 처음",
    text : "다 처음 들어봐요",
    imageUrl:"/src/assets/never_heard.jpg",
    imageWidth:400,
    imageHeight:300,
    imageAlt : "안 나옴 ㅠㅠ"
  })
}

</script>

<style scoped>

</style>