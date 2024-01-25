<template>
  <v-navigation-drawer v-model="drawer" app permanent color="grey darken-3">
    <!-- 네비게이션 타이틀 굵은 글씨로 -->
    <v-list-item class="py-2">
      <div class="text-h5 font-weight-bold">클럽 목록</div>
    </v-list-item>

    <!-- 클럽 목록: 버튼처럼 보이도록 디자인 -->
    <v-list dense class="overflow-auto" style="max-height: 800px;">
      <v-list-item v-for="(club, index) in clubs" :key="index" class="mb-1">
        <div @click="openClubInNewTab(club.id)" class="d-flex justify-start align-center club-item"
          style="text-transform: none; padding: 16px; cursor: pointer;">
          <v-list-item-title class="align-start">{{ club.name }}<br>{{ 'ID: ' + club.id }}</v-list-item-title>
        </div>
      </v-list-item>

      <!-- 새 클럽 추가 버튼 -->
      <v-list-item>
        <div @click="goToNewClubPage" class="d-flex justify-start align-center club-item"
          style="text-transform: none; padding: 16px; cursor: pointer;">
          <v-list-item-title class="align-start">새 클럽</v-list-item-title>
        </div>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>

  <v-app-bar app color="indigo" dark>
    <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
    <v-toolbar-title @click="goHome" style="cursor: pointer;">SPOPARTY</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/match" class="mx-2">경기 일정</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/league" class="mx-2">리그 목록</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/signup" class="mx-2">회원가입</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/login" class="mx-2">로그인</v-btn>
  </v-app-bar>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const drawer = ref(false);

function goHome() {
  router.push('/');
}

// 예시 클럽 데이터
const clubs = ref([
  { id: 1, name: '클럽 A' },
  { id: 2, name: '클럽 B' },
  { id: 3, name: '클럽 C' },
]);

// 클럽 페이지를 새 탭에서 열기
function openClubInNewTab(clubId) {
  const url = router.resolve({ name: 'ClubView', params: { clubId } }).href;
  window.open(url, '_blank');
}


// 새 클럽 만들기 페이지
function goToNewClubPage() {
  router.push('/new-club'); // '새 클럽' 페이지로 라우팅하는 경로를 적절히 조정하세요.
}
</script>

<style scoped>
.club-item {
  width: 100%;
  background-color: grey;
  margin-bottom: 10px;
  border-radius: 4px;
  /* 모서리를 약간 둥글게 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, .1);
  /* 약간의 그림자 효과 */
}

.overflow-auto {
  overflow: auto;
}

.text-h6 {
  font-size: 1.25rem;
}

.font-weight-bold {
  font-weight: bold;
}</style>