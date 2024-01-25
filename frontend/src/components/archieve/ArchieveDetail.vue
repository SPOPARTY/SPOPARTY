<template>
    <v-dialog 
        v-model="modalVisible" 
        persistent 
        max-width="600px"
        max-height="500px"
        >
        <v-card>
            <v-row>
                <v-col cols="10" style="margin-left:0px; padding-left:230px;">
                    <v-card-title>추억 한 조각</v-card-title>
                </v-col>
                <v-col cols="2" style="margin-top:5px;">
                    <v-btn :ripple="false" @click="closeModal" class="no-background-hover">
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                </v-col>
            </v-row>
            <v-card-item class="text-center">
                <v-img :src="props.detail.img" :alt="props.detail.title"/>
            </v-card-item>
            <v-card-text class="text-right">
                {{ props.detail.title }}
            </v-card-text>
            <v-card-subtitle class="text-right">
                {{ props.detail.created_time }}
            </v-card-subtitle>
            <v-card-actions>
                <v-spacer/>
                <v-btn color="blue darken-1" text @click="closeModal">다운로드</v-btn>
                <v-btn color="green darken-2" text @click="closeModal">취소</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup>
import {ref, onMounted} from 'vue'

// Detail on/off
const modalVisible = ref(true);

const props = defineProps({
    detail : Object
})

const emits = defineEmits([
    'detail-close'
])

// 다운로드 하기
// 추후 작성

// Detail off
function closeModal() {
    modalVisible.value = false;
    emits('detail-close')
}

onMounted(()=>{
    console.log("히히 모달 발사")
})

</script>

<style lang="scss" scoped>
.no-background-hover {
  box-shadow: none !important;

  &:hover {
    background-color: transparent !important;
  }
}

</style>