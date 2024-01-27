<template>
  <v-navigation-drawer class="bar-border" v-model="drawer" app permanent color="white">
    <!-- 여기 color 옵션에 따라 자동으로 이후 텍스트 컬러도 결정됨 -->
    <!-- <v-navigation-drawer class="bar-border" v-model="drawer" app permanent color="grey darken-3"> -->
    <!-- 네비게이션 타이틀 굵은 글씨로 -->
    <v-list-item class="py-2 club-title">
      <div>클럽 목록</div>
    </v-list-item>

    <!-- 클럽 목록: 버튼처럼 보이도록 디자인 -->
    <v-list dense class="club-list">
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

    <!-- 로그인 관련 footer -->
    <v-list-item class="sidebar-footer" align="center">
      <v-btn text to="/signup" class="mx-2" color="primary">
        <v-icon size="x-large">mdi-account-plus</v-icon>
      </v-btn>

      <v-btn text to="/login" class="mx-2" color="primary">
        <v-icon size="x-large">mdi-login</v-icon>
      </v-btn>
    </v-list-item>
  </v-navigation-drawer>

  <v-app-bar class="bar-border" app color="#000000" :elevation="1">
    <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
    <v-divider vertical class="mx-2"></v-divider>

    <!-- 로고 이미지 영역 -->
    <div class="logo-container" @click="goHome" style="cursor: pointer; flex: none; width: 240px;">
      <v-img src="src/assets/new-logo-2.png" contain></v-img>
    </div>

    <!-- 로고와 나머지 요소들 사이에 v-spacer 배치 -->
    <v-spacer></v-spacer>

    <!-- 나머지 요소들 -->
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/match" class="mx-2 btn-text">경기 일정</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/league" class="mx-2 btn-text">리그 목록</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/signup" class="mx-2 btn-text">회원가입</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/login" class="mx-2 btn-text">로그인</v-btn>
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
  { id: 4, name: '클럽 D' },
  { id: 5, name: '클럽 AA' },
  { id: 6, name: '클럽 BB' },
  { id: 7, name: '클럽 CC' },
  { id: 8, name: '클럽 DD' },
  { id: 9, name: '클럽 E' },
  { id: 10, name: '클럽 F' },
  { id: 11, name: '클럽 G' },
  { id: 12, name: '클럽 H' },
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
.logo-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 240px;
  /* 로고 컨테이너의 너비 고정 */
}

.club-title {
  background-color: #08042B;
  border: 2px solid #292071;
  height: 64px;
  font-size: 2rem;
  color: white;
  padding: 2rem;
}

span.v-btn__content {
  font-size: 2rem;
}

@media (max-width: 600px) {
  .logo-container {
    width: 150px;
    /* 더 작은 화면에서 로고 크기 조정 */
  }
}

.v-img {
  /* 로고 이미지의 최소 및 최대 크기 설정 */
  min-width: 240px;
  max-width: 100%;
  height: 48px;
}

.bar-border {
  border: 2px solid #292071;
}

.club-list {
  overflow: auto;
  /* max-height: calc(100% + 360px); */
  height: calc(100% - 128px);
}

.club-item {
  width: 100%;
  background-color: #CBD0D8;
  margin-bottom: 10px;
  border-radius: 8px;
  /* 모서리를 약간 둥글게 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, .1);
  /* 약간의 그림자 효과 */
}

.btn-text {
  font-size: 1rem;
}

.sidebar-footer {
  height: 64px;
  background-color: #333D51;
}

/* Chrome, Edge, Safari */
.club-list::-webkit-scrollbar {
  display: none; /* 스크롤바 영역을 숨깁니다 */
}

/* Firefox */
.club-list {
  scrollbar-width: none; /* Firefox에서 스크롤바를 숨깁니다 */
}

/* IE and Edge */
.club-list {
  -ms-overflow-style: none; /* Internet Explorer 및 Edge에서 스크롤바를 숨깁니다 */
}

</style>