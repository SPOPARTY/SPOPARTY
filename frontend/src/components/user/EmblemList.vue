<template>
<v-dialog v-model="isModalVisible" max-width="800px" max-height="400px">
    <v-card>
    <v-card-title>
        엠블럼 목록
        <v-btn icon @click="closeModal">
        <!-- <v-icon>mdi-close</v-icon> -->
        </v-btn>
    </v-card-title>

    <v-card-text>
        <v-text-field
        v-model="search"
        label="원하는 엠블럼을 검색하세요"
        single-line
        hide-details
        ></v-text-field>

        <v-divider></v-divider>

        <v-container>
        <v-row style="align-items: center;">
            <v-col v-for="(emblem, index) in filteredEmblems" :key="index" cols="4" >
            <v-hover>
                <template v-slot:default="{ hover }">
                <v-avatar size="64" class="ma-2" :class="{ 'elevation-6': hover }">
                    <img :src="emblem.src" :alt="emblem.name" style="width:64px; height:64px;">
                </v-avatar>
                <div class="club-name" style="font-size: 15px;">
                    <v-text :class="{ 'text-decoration-underline': hover }" >
                        {{ emblem.name }}
                    </v-text>
                </div>
                </template>
            </v-hover>
            </v-col>
        </v-row>
        </v-container>

        <v-row>
        <v-col>
            <v-btn block @click="confirmSelection">선택하기</v-btn>
        </v-col>
        <v-col>
            <v-btn color="red" @click="closeModal">닫기</v-btn>
        </v-col>
        </v-row>
    </v-card-text>
    </v-card>
</v-dialog>
</template>

<script setup>
import { ref, computed } from 'vue';

const emit = defineEmits([
    'emblem-list-close'
])

const isModalVisible = ref(true);

function closeModal() {
    isModalVisible.value = false;
    emit('emblem-list-close')
}

const search = ref('');
const emblems = ref([
  // 엠블럼 데이터를 여기에 추가하세요
  { name: '맨체스터 유나이티드', src: '/src/assets/manutd.png' },
  { name: '첼시', src: '/src/assets/chelsea.png' },
  { name: '토트넘 홋스퍼', src: '/src/assets/tottenham.png' },
  { name: '토리노FC', src: '/src/assets/torinoFC.png' },
  { name: '토론토FC', src: '/src/assets/torontoFC.png' },
  { name: '맨체스터 시티', src: '/src/assets/mancity.png' },
  { name: '아스날', src: '/src/assets/arsenal.png' },
  { name: '바이에른 뮌헨', src: '/src/assets/bayernMunich.png' },

  // ... 나머지 엠블럼들
]);

// 검색어에 따라 엠블럼을 필터링합니다.
const filteredEmblems = computed(() => {
    if (!search.value) {
        return emblems.value;
    }
    return emblems.value.filter((emblem) => emblem.name.includes(search.value));
});

function confirmSelection() {

}

</script>

<style scoped>

</style>