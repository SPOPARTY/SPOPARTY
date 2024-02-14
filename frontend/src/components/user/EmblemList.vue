<template>
    <v-dialog 
        v-model="isModalVisible" 
        max-width="800px" 
        max-height="650px"
        @click:outside="closeModal"
        persistent>
        <v-card class="outer-card">
            <v-row>
                <v-col class="text-left">
                    <v-card-title style="transform: translateX(330px);">
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
            
            <v-row justify="center" align="center">
                <v-col cols="4" class="d-flex justify-center align-center pa-0" style="transform: translateX(100px);">
                    <v-card-text>
                        <h3>현재 엠블럼 : {{currEmblemName}}</h3>
                    </v-card-text>
                </v-col>
                <v-col cols="4" class="d-flex justify-center align-center pa-0" style="transform: translateX(30px);">
                    <v-img :src="currEmblemIcon" :alt="currEmblemName" class="mx-2" style="width: 100px; height: 100px;"></v-img>
                </v-col>
                <v-col cols="4" class="d-flex justify-center align-center pa-0" style="transform: translateX(-10px);">
                    <v-btn color="green" @click="confirmEmblem" style="width:80%">확인</v-btn>
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

            <v-card class="emblem-list">
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
                                <v-img :src="emblem.logo" :alt="emblem.nameKr" style="width:100%; height:100%;"></v-img>
                            </v-avatar> <hr>
                        </v-btn>
                        <h5 style="text-align: center;">{{ emblem.nameKr }}</h5>  
                    </v-col>
                </v-row>
            </v-card>
        </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';

const props = defineProps({
    teamList : Object,
    emblemId : String,
})

console.log("emblemId -> ",props.emblemId)

const currentEmblemId = ref(props.emblemId);
const currEmblemIcon = ref(props.teamList.filter(team => team.id === currentEmblemId.value).map(team => team.logo)[0]);
const currEmblemName = ref( props.teamList.filter(team => team.id === currentEmblemId.value).map(team => team.nameKr)[0]);

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
const emblems = props.teamList;


// 검색어에 따라 엠블럼을 필터링합니다.
const filteredEmblems = computed(() => {
    if (!search.value) {
        return emblems;
    }
    return emblems.filter((emblem) => emblem.nameKr.toLowerCase().includes(search.value.toLowerCase()));
});

const newEmblem = ref({
    emblemId : "",
    emblemIcon : "",
    emblemName : "",
})

function selectEmblem(emblem) {
    currentEmblemId.value = emblem.id;
    currEmblemIcon.value = emblem.logo;
    currEmblemName.value = emblem.nameKr;
    // console.log("선택된 emblemid -> ",currentEmblemId.value)
}

function confirmEmblem() {
    newEmblem.value.emblemId = currentEmblemId.value;
    newEmblem.value.emblemIcon = currEmblemIcon.value;
    newEmblem.value.emblemName = currEmblemName.value;
    // console.log("나중에 바뀔 emblemId -> ", newEmblem.value.emblemId)
    emit('select-emblem',newEmblem.value);
    closeModal();
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

.outer-card{
    overflow-y:hidden
}

.emblem-list {
    height:400px;
    overflow-y:auto;
}

</style>