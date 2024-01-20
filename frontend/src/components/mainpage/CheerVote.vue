<template>
    <div class="cheer-vote">
        <h1>응원 투표</h1>
        <div class="cheer-item">
            <p>응원</p>
            <div class="item-vote">
                <button>&lt;</button>
                <button>팀1</button>
                <button>팀2</button>
                <button>&gt;</button>
            </div>
        </div>
    </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
// .cheer-vote {
//     background-color: #007d04;
// }

// .cheer-item {
//     width: 100%;
//     height: 200px;
//     align-content: center;
//     justify-content: center;
//     background-color: grey;
// }
.cheer-vote {
    background-color: #007d04;
}

.cheer-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 200px;
    background-color: grey;
}

//버튼으로 해둔 것은 임시일 뿐.
.item-vote {
    display: flex;
    height: 150px;
    width: 100%;
    justify-content: space-around;
    align-items: center;
}
</style>