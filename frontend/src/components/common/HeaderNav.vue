<template>
  <v-navigation-drawer class="bar-border" v-model="drawer" app permanent color="white">
    <!-- 여기 color 옵션에 따라 자동으로 이후 텍스트 컬러도 결정됨 -->
    <!-- <v-navigation-drawer class="bar-border" v-model="drawer" app permanent color="grey darken-3"> -->
    <!-- 네비게이션 타이틀 굵은 글씨로 -->
    <v-list-item class="py-2 club-title">
      <div>클럽 목록</div>
    </v-list-item>

    <!-- 클럽 목록: 버튼처럼 보이도록 디자인 -->
    <v-list dense class="club-list" v-if="clubs"> 
      <v-list-item v-for="(club, index) in clubs"  :key="index" class="mb-1">
        <div @click="goToOneClubPage(club.clubId)" class="d-flex justify-start align-center club-item"
          style="text-transform: none; padding: 16px; cursor: pointer;">
          <v-list-item-title class="align-start">{{ club.name }}<br>{{ 'ID: ' + club.clubId }}</v-list-item-title>
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
      <v-btn v-if="!isLogined" text to="/signup" class="mx-2" color="primary">
        <v-tooltip activator="parent" location="top" theme="dark">회원가입</v-tooltip>
        <v-icon size="x-large">mdi-account-plus</v-icon>
      </v-btn>
      <v-btn v-if="isLogined" text to="/mypage" class="mx-2" color="primary">
        <v-tooltip activator="parent" location="top" theme="dark">마이페이지</v-tooltip>
        <v-icon size="x-large">mdi-home-edit-outline</v-icon>
      </v-btn>

      <v-btn v-if="!isLogined" text to="/login" class="mx-2" color="primary">
        <v-tooltip activator="parent" location="top" theme="dark">로그인</v-tooltip>
        <v-icon size="x-large">mdi-login</v-icon>
      </v-btn>
      <v-btn v-if="isLogined" @click="logout" class="mx-2" color="primary">
        <v-tooltip activator="parent" location="top" theme="dark">로그아웃</v-tooltip>
        <v-icon size="x-large">mdi-logout</v-icon>
      </v-btn>
    </v-list-item>
  </v-navigation-drawer>

  <v-app-bar class="bar-border" app color="#000000" :elevation="1">
    <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
    <v-divider vertical class="mx-2"></v-divider>

    <!-- 로고 이미지 영역 -->
    <div class="logo-container" @click="goHome" style="cursor: pointer; flex: none; width: 240px;">
      <v-img src="/new-logo-2.png" contain></v-img>
    </div>

    <!-- 로고와 나머지 요소들 사이에 v-spacer 배치 -->
    <v-spacer></v-spacer>

    <!-- 나머지 요소들 -->
    <template v-if="isLogined">
      <v-divider vertical class="mx-2"></v-divider>
      <v-badge :content="countUnread>0? countUnread:null" :color="countUnread>0? '#D3AC2B':'#08042B'">
        <v-btn @click="() => isNotificationListVisible = true" class="mx-2 btn-text">알림</v-btn>
      </v-badge>
    </template>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/match" class="mx-2 btn-text">경기 일정</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn text to="/league" class="mx-2 btn-text">리그 목록</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn v-if="isLogined" text to="/mypage" class="mx-2 btn-text">마이페이지</v-btn>
    <v-btn v-else text to="/signup" class="mx-2 btn-text">회원가입</v-btn>
    <v-divider vertical class="mx-2"></v-divider>
    <v-btn v-if="isLogined" text class="mx-2 btn-text" @click="logout">로그아웃</v-btn>
    <v-btn v-else text to="/login" class="mx-2 btn-text">로그인</v-btn>
  </v-app-bar>
  <NewClub v-if="isNewClubModalVisible" @close-new-club="isNewClubModalVisible = false"/>


  <!-- 알림 목록 모달 -->
  <v-dialog
    v-model="isNotificationListVisible"
    width="600"
    persistent
  >
    <v-card class="justify-center">
      <br>
      <v-card-title class="text-center text-h5">알림 목록</v-card-title>
      <v-card-text>
        <v-table height="300px">
          <thead>
            <tr>
              <th class="text-center">제목</th>
              <th class="text-center">내용</th>
              <th class="text-center">시간</th>
              <th class="text-center">읽음</th>
            </tr>
          </thead>
          <tbody> 
            <tr
              v-for="notification in notificationList"
              :key="notification.id"
              @click="notificationDetail(notification.id)"> 
              <td>{{ notification.title }}</td>
              <td>{{ notification.content }}</td>
              <td>{{ notification.createdTime.split(".")[0] }}</td>
              <td v-if="notification.state"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope-open" viewBox="0 0 16 16"><path d="M8.47 1.318a1 1 0 0 0-.94 0l-6 3.2A1 1 0 0 0 1 5.4v.817l5.75 3.45L8 8.917l1.25.75L15 6.217V5.4a1 1 0 0 0-.53-.882zM15 7.383l-4.778 2.867L15 13.117zm-.035 6.88L8 10.082l-6.965 4.18A1 1 0 0 0 2 15h12a1 1 0 0 0 .965-.738ZM1 13.116l4.778-2.867L1 7.383v5.734ZM7.059.435a2 2 0 0 1 1.882 0l6 3.2A2 2 0 0 1 16 5.4V14a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V5.4a2 2 0 0 1 1.059-1.765z"/></svg></td>
              <td v-else><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope-fill" viewBox="0 0 16 16"><path d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414zM0 4.697v7.104l5.803-3.558zM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586zm3.436-.586L16 11.801V4.697z"/></svg></td>
            </tr>
          </tbody>
        </v-table>
      </v-card-text>
      <v-card-actions>
        <!-- <v-spacer></v-spacer> -->
        <v-btn color="red" class="px-4" @click="deleteAllNotification">전체 삭제</v-btn>
        <v-spacer></v-spacer>
        <v-btn color="green" class="px-4" @click="readAllNotification">전체 읽기</v-btn>
        <v-btn color="primary" class="px-4" @click="isNotificationListVisible = false">확인</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <!-- 알림 상세보기 모달 -->
  <v-dialog
    v-model="isNotificationDetailVisible"
    width="600"
    persistent
  >
    <v-card class="justify-center">
      <v-card-title class="text-center text-h5">알림 상세보기</v-card-title>
      <v-card>
        <v-card-title>{{ notification.title }}</v-card-title>
        <v-card-subtitle>{{ notification.content }}</v-card-subtitle>
        <v-card-text class="text-end">{{ notification.createdTime.split(".")[0] }}</v-card-text>
      </v-card>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="gray" @click="()=>{deleteNotification(notification.id); exitNotificationDetail()}">삭제</v-btn>
        <v-btn color="primary" @click="exitNotificationDetail">확인</v-btn>
      </v-card-actions>
    </v-card>

  </v-dialog>


</template>

<script setup>
import { ref,computed, watch,onMounted } from 'vue';
import { useRouter } from 'vue-router';

import { useManagementStore} from "@/stores/member/managements"
import { useClubStore} from "@/stores/club/clubs"
import { useNotificationStore } from "@/stores/member/notification"
import { storeToRefs } from "pinia";

import NewClub from '@/components/club/NewClub.vue';
import { set } from 'date-fns';

const {myClubs} = useClubStore();

const managemetStore = useManagementStore();
const clubStore = useClubStore();

const clubs = ref([]);
watch(() => clubStore.myClubs,(newClubs) => {
  clubs.value = newClubs;
},{immediate:true})


// 로그인 여부 감지
const isLogined = ref(localStorage.getItem("accessToken") !== null);
// console.log("로그인 됨?")
// console.log(localStorage.getItem("accessToken") !== null);

// 로그아웃
const logout = () => {
  managemetStore.logout();
}

const router = useRouter();
const drawer = ref(false);

function goHome() {
  // router.push('/');
  // // 이동 후 새로고침
  // setTimeout(() => {
  //   router.go(0);
  // }, 20);
  window.location.href = '/';
}

// 예시 클럽 데이터


// 클럽 페이지를 새 탭에서 열기
function goToOneClubPage(clubId) {
  // const url = router.resolve({ name: 'ClubView', params: { clubId } }).href;
  // window.open(url, '_blank');
  console.log("클럽 페이지로 이동")
  drawer.value = false;
  router.push({ name: 'ClubMain', params: { clubId } });
}


// 새 클럽 만들기 모달
const isNewClubModalVisible = ref(false)

function goToNewClubPage() {
  if (localStorage.getItem("accessToken")== null) {
    console.log()
    if(confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?") === true) {
      window.location.replace("/login")
      return;
    }
    return;
  }
  isNewClubModalVisible.value = true;
  return;
}




// 알림 모달
const isNotificationListVisible = ref(false);
const isNotificationDetailVisible = ref(false);
const notification = ref({});
const notificationStore = useNotificationStore()
const {getNotificationList, readNotification, deleteNotification, connect} = notificationStore;
const {notificationList} = storeToRefs(notificationStore);

const exitNotificationDetail = () => {
  isNotificationDetailVisible.value = false; 
  isNotificationListVisible.value = true
}

onMounted(async () => {
  if (localStorage.getItem("accessToken") != null) {
    await clubStore.requestClub();
  }
  const memberId = localStorage.getItem("id");
  if(memberId) {
    await getNotificationList(memberId);
    await connect(memberId);
  }
})

const notificationDetail = async (id) => {
  isNotificationListVisible.value = false;
  notification.value = notificationList.value.find(args => args.id == id);
  if(notification.value.state == 0) await readNotification(id);
  isNotificationDetailVisible.value = true;
}

const readAllNotification = async () => {
  // Prompt the user for confirmation
  if (confirm("모든 알림을 읽음으로 처리하시겠습니까?")) {
    // User clicked "OK"
    for (let i = 0; i < notificationList.value.length; i++) {
      if (notificationList.value[i].state == 0) {
        // Call the function to mark the notification as read
        await readNotification(notificationList.value[i].id);
      }
    }
  } else {
    // User clicked "Cancel", do nothing
    console.log("모두 읽기 취소!");
  }
}

const deleteAllNotification = async () => {
  // Prompt the user for confirmation
  if (confirm("정말 모든 알림을 삭제하시겠습니까?")) {
    // User clicked "OK"
    for (let i = 0; i < notificationList.value.length; i++) {
      // Call the function to delete the notification
      await deleteNotification(notificationList.value[i].id);
    }
  } else {
    // User clicked "Cancel", do nothing
    console.log("모두 삭제 취소!");
  }
}

const countUnread = computed(() => {
  return notificationList.value.filter(args => args.state == 0).length;
})

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
  overscroll-behavior: contain;
}

</style>