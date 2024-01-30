<template>
    <v-dialog 
        v-model="isModalVisible" 
        max-width="800px" 
        max-height="400px"
        @click:outside="closeModal"
        persistent        >
        <v-card>
            <v-row>
                <v-col class="text-left">
                    <v-card-title>
                        엠블럼 목록
                    </v-card-title>
                </v-col>
                <v-spacer></v-spacer>
                <v-col class="text-right">
                    <v-btn icon @click="closeModal" class="no-background-hover">
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-col>
            </v-row>
            
            <v-row>
                <v-col col="8">
                    <v-card-text>
                        현재 엠블럼 : {{currEmblemName}}
                    </v-card-text>
                </v-col>
                <v-col col="4">
                    <v-btn color="green" @click="closeModal" style="width:80%">확인</v-btn>
                </v-col>
            </v-row>
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
                    <v-btn 
                        text 
                        :key="index" 
                        class="d-flex flex-column align-center ma-2" 
                        style="width: 100%; height:150px;"
                        @click="selectEmblem(emblem)">
                        <v-avatar 
                            size="64" 
                            class="ma-2" 
                        >
                            <img :src="emblem.src" :alt="emblem.name" style="width:100%; height:100%;">
                        </v-avatar>
                        <div class="club-name">
                            {{ emblem.name }}  
                        </div>
                    </v-btn>
                </v-col>
            </v-row>
            </v-container>
        </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
    emblemIcon : String,
    emblemName : String
})


console.log("emblemIcon -> ",props.emblemIcon)

const currEmblemIcon = ref(props.emblemIcon);
const currEmblemName = ref(props.emblemName);

const emit = defineEmits([
    'emblem-list-close',
    'select-emblem',
])

const isModalVisible = ref(true);

function closeModal() {
    isModalVisible.value = false;
    emit('emblem-list-close')
}



const search = ref('');
const emblems = ref([
  { name: '맨체스터 유나이티드', src: '/manutd.png' },
  { name: '첼시', src: '/chelsea.png' },
  { name: '토트넘 홋스퍼', src: '/tottenham.png' },
  { name: '토리노FC', src: '/torinoFC.png' },
  { name: '토론토FC', src: '/torontoFC.png' },
  { name: '맨체스터 시티', src: '/mancity.png' },
  { name: '아스날', src: '/arsenal.png' },
  { name: '바이에른 뮌헨', src: '/bayernMunich.png' },
]);

// 검색어에 따라 엠블럼을 필터링합니다.
const filteredEmblems = computed(() => {
    if (!search.value) {
        return emblems.value;
    }
    return emblems.value.filter((emblem) => emblem.name.includes(search.value));
});

function selectEmblem(emblem) {
    currEmblemIcon.value = emblem.src;
    currEmblemName.value = emblem.name;

    const newEmblem = {
        newEmblemIcon : emblem.src,
        newEmblemName : emblem.name
    }
    emit('select-emblem',newEmblem);
    console.log(newEmblem)
}

</script>

<style lang="scss" scoped>
.club-name{
    font-size: 15px; 
    text-align:center; 
    margin-top:8px;
}

.no-background-hover {
  box-shadow: none !important;

  &:hover {
    background-color: transparent !important;
  }
}

</style>