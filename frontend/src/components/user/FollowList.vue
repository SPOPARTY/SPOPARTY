<template>
    <v-dialog v-model="isModalVisible" max-width="800px">
      <v-card>
  
        <v-card-title class="justify-space-between">
          <span>팔로우 중인 구단 목록</span>
          <v-btn icon @click="closeModal">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-row>
            <v-col cols="6">
                <v-btn 
                    color="orange" 
                    style="width:100%"
                    @click="showAllClubs">
                    전체
                </v-btn>
            </v-col>   
            <v-col cols="6">
                <v-btn 
                    color="purple" 
                    style="width:100%"
                    @click="showFollwingClubs">
                    내가 팔로우 한 구단
                </v-btn>
            </v-col>
        </v-row>
  
        <v-card-text>
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="검색"
            single-line
            hide-details
          ></v-text-field>
  
          <v-divider></v-divider>
  
          <v-list three-line>
            <v-virtual-scroll
              :items="filterFollowClub"
              height="300"
              item-height="68"
            >
              <template v-slot="{ item }">
                <v-list-item >
                  <v-list-item-avatar>
                    <img :src="item.src" :alt="item.name" style="width:64px; height:64px;">
                  </v-list-item-avatar>
                  
                  <v-list-item-content>
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                    <v-list-item-subtitle>{{ item.subtitle }}</v-list-item-subtitle>
                  </v-list-item-content>
  
                  <v-list-item-action>
                    <v-btn icon v-if="clubData.following.includes(item)" @click="unfollowClub(item)">
                        <v-icon color="red">mdi-delete</v-icon>
                    </v-btn>

                    <v-btn icon v-else @click="followClub(item)">
                      <v-icon color="green">mdi-plus</v-icon>
                    </v-btn>
                  </v-list-item-action>
                </v-list-item>
                <v-divider inset></v-divider>
              </template>
            </v-virtual-scroll>
          </v-list>
        </v-card-text>
      </v-card>
    </v-dialog>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';

const isModalVisible = ref(true);

const emit = defineEmits([
    'FollowListClose',
    'FollowClub',
    'UnfollowClub'
])


// 검색어 찾기
const search = ref('');

// 구단 목록 더미 데이터
const allClubs = [
    { name: '맨체스터 유나이티드', src: '/src/assets/manutd.png', subtitle:'프리미어리그' },
    { name: '첼시', src: '/src/assets/chelsea.png', subtitle:'프리미어리그' },
    { name: '토트넘 홋스퍼', src: '/src/assets/tottenham.png', subtitle:'프리미어리그' },
    { name: '토리노FC', src: '/src/assets/torinoFC.png', subtitle:'세리에A' },
    { name: '토론토FC', src: '/src/assets/torontoFC.png', subtitle:'MLS' },
    { name: '맨체스터 시티', src: '/src/assets/mancity.png', subtitle:'프리미어리그' },
    { name: '아스날', src: '/src/assets/arsenal.png', subtitle:'프리미어리그' },
    { name: '바이에른 뮌헨', src: '/src/assets/bayernMunich.png', subtitle:'분데스리가' },
];

const follwingClubs = [
    { name: '바이에른 뮌헨', src: '/src/assets/bayernMunich.png', subtitle:'분데스리가' },
    { name: '토리노FC', src: '/src/assets/torinoFC.png', subtitle:'세리에A' },
    { name: '아스날', src: '/src/assets/arsenal.png', subtitle:'프리미어리그' },
    { name: '토트넘 홋스퍼', src: '/src/assets/tottenham.png', subtitle:'프리미어리그' },
]

const clubData = reactive({
    current : allClubs, // 모달창이 띄워지면 보여지는 값
    all: allClubs, // 전체 구단 목록
    following: follwingClubs // 팔로우하고 있는 구단목록
})

const showAllClubs = () => {
    clubData.current = clubData.all;
    console.log("clubs ->" , clubData.current)
}

const showFollwingClubs = () => {
    clubData.current = clubData.following;
    console.log("clubs ->" , clubData.current)
}

// 구단 검색
const filterFollowClub = computed(() => {
    if (!search.value) {
        return clubData.current
    }
    return clubData.current.filter((club) => club.name.includes(search.value))
})

// 구단을 팔로우
const followClub = (club) => {
    // 이미 팔로우 중인지 확인
    if(!clubData.following.includes(club)) {
      clubData.following.push(club);
    }
    // 현재 보여지고 있는 목록이 팔로잉 목록일 경우 업데이트
    if (clubData.current === clubData.following){
      clubData.current = [...clubData.following];
    }
    emit('FollowClub',followingCount)
}

// 구단을 언팔로우
const unfollowClub = (club) => {
  const index = clubData.current.indexOf(club);
  if (index > -1) {
    clubData.following.splice(index, 1);
    // 현재 보여지고 있는 목록이 팔로잉 목록일 경우 업데이트
    if (clubData.current === clubData.following) {
      clubData.current = [...clubData.following];
    }
    emit('UnfollowClub',followingCount)
  }
};

// 팔로우 중인 구단 수 
const followingCount = computed(() => {
    return clubData.following.length;
})


// 모달창 닫기
function closeModal() {
    isModalVisible.value = false;
    emit('FollowListClose')
}
</script>

<style scoped>

</style>