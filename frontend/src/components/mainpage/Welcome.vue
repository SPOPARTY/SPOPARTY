<template>
    <v-container fluid class="pa-2 fill-height part-section">
        <v-row justify="center">
            <v-col cols="12" class="d-flex flex-column align-center justify-center pa-2">
                <h2 class="card-title text-h2 py-6">소개 컴포넌트(비로그인)</h2>
                <div class="card-text py-6">
                    <div class="intro-text">
                        <span>
                            <v-icon size="100px" class="my-2">mdi-human-greeting-variant</v-icon>
                            <v-icon size="80px" class="mt-2">mdi-soccer</v-icon>
                        </span>
                        <p class="text-center text-h4 mb-5">친구들과 함께 축구 경기를 응원해요!</p>
                        <div class="text-center">
                            <span class="asking">회원이 아니시라면</span>
                            <v-btn text to="/signup" class="mx-2" color="primary">
                                <v-tooltip activator="parent" location="top" theme="dark">
                                    회원가입
                                </v-tooltip>
                                <v-icon size="x-large">mdi-account-plus</v-icon>
                            </v-btn>
                        </div>
                        <br>
                        <div class="text-center">
                            <span class="asking">회원이시라면</span>
                            <v-btn text to="/login" class="mx-2" color="primary">
                                <v-tooltip activator="parent" location="top" theme="dark">
                                    로그인
                                </v-tooltip>
                                <v-icon size="x-large">mdi-login</v-icon>
                            </v-btn>
                        </div>
                    </div>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

  
    
<script setup>

</script>
    
<style scoped>
.card-title {
    margin-bottom: 16px;
    /* 제목 아래 여백 */
    font-weight: bold;
}

.card-text {
    margin-bottom: 16px;
    /* 텍스트 아래 여백 */
}

.intro-text {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.intro-text p {
    max-width: 100%;
    white-space: normal;
}

.asking {
    color: rgba(0, 0, 0, 0.5);
}
</style>

    