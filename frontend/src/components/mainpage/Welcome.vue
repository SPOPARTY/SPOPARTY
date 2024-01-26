<template>
    <v-container fluid class="pa-2 fill-height">
        <v-row justify="center">
            <v-col cols="12">
                <v-card color="grey lighten-1" class="d-flex flex-column align-center justify-center pa-2">
                    <v-card-title class="text-h2 py-6">소개 컴포넌트(비로그인)</v-card-title>
                    <v-card-text class="py-6">
                        <div class="intro-text">
                            <span>
                                <v-icon size="100px" class="my-2">mdi-human-greeting-variant</v-icon>
                                <v-icon size="80px" class="mt-2">mdi-soccer</v-icon>
                            </span>
                            <p class="text-center text-h4 mb-5">친구들과 함께 축구 경기를 응원해요!</p>
                            <div class="text-center">
                                <span class="asking">회원이 아니시라면</span>
                                <v-btn text to="/signup" class="mx-2" color="primary">
                                    <v-icon size="x-large">mdi-account-plus</v-icon>
                                </v-btn>
                            </div>
                            <br>
                            <div class="text-center">
                                <span class="asking">회원이시라면</span>
                                <v-btn text to="/login" class="mx-2" color="primary">
                                    <v-icon size="x-large">mdi-login</v-icon>
                                </v-btn>
                            </div>
                        </div>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>
  
    
<script setup>

</script>
    
<style scoped>
.intro-text {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.intro-text p {
    max-width: 100%;
    /* 텍스트의 너비 제한 */
    white-space: normal;
    /* 텍스트 자동 줄바꿈 활성화 */
}

.asking {
    /* 폰트 흐리게 */
    color: rgba(255, 255, 255, 0.7);
}

/* 필요에 따라 추가 스타일링을 적용할 수 있습니다. */
</style>

    