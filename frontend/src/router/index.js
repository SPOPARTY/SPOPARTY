import { createRouter, createWebHistory } from "vue-router";
import MainPageView from "@/views/MainPageView.vue";
import MatchView from "@/views/MatchView.vue";
import LeagueView from "@/views/LeagueView.vue";
import ClubView from "@/views/ClubView.vue";
// import Signup from "@/components/user/Signup.vue";
// import Login from "@/components/user/Login.vue";
// import MyPage from "@/components/user/Mypage.vue";


const router = createRouter({
  history: createWebHistory(),
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  },
  routes: [
    {
      path: '/',
      name: 'MainPage',
      component: MainPageView
    },
    {
      path: "/match",
      name: "MatchView",
      component: MatchView
    },
    {
      path: "/league",
      name: "LeagueView",
      component: LeagueView
    },
    {
      path: "/league/:leagueId",
      name: "LeagueDetailView",
      component: () => import("@/views/LeagueDetailView.vue")
    },
    {
      path: "/team/:teamId",
      name: "TeamDetailView",
      component: () => import("@/views/TeamDetailView.vue")
    },
    {
      path: "/signup",
      name: "Signup",
      component: () => import("@/components/user/Signup.vue")
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("@/components/user/Login.vue")
    },
    {
      path:"/mypage",
      name:"Mypage",
      component: () => import("@/components/user/Mypage.vue"),
      meta: { requiresAuth: true },
    },
    {
      path:"/kakao",
      name:"kakao",
      component: () => import("@/components/user/Kakao.vue")
    },
    {
      path:"/invite/:inviteUrl",
      name:"invite",
      component: () => import("@/components/club/InviteClub.vue")
    },
    {
      path: "/club/:clubId",
      // name: "ClubView",
      component: ClubView,
      // path : "",
      // name : "ClubMain",
      // component : () => import("@/components/club/ClubMain.vue"),
      props:true,
      meta: { requiresAuth: true },
      children : [
        {
          path : "",
          name : "ClubMain",
          component : () => import("@/components/club/ClubMain.vue")
        },
        {
          path : "archive",
          name : "ArchiveList",
          component : () => import("@/components/archive/ArchiveList.vue")
        },
        {
          path : "board",
          name : "BoardList",
          component : () => import("@/components/board/BoardList.vue")
        },
        {
          path : "board/write",
          name : "WriteBoard",
          component : () => import("@/components/board/WriteBoard.vue")
        },
        {
          path : "board/edit",
          name : "EditBoard",
          component : () => import("@/components/board/EditBoard.vue")
        },
        {
          path : "party/:partyId",
          name : "PartyView",
          component : () => import("@/views/PartyView.vue")
        }
      ]
    }
  ]
});

// 라우트 정의에 meta 필드 추가 예시
// {
//   path: "/mypage",
//   name: "Mypage",
//   component: () => import("@/components/user/Mypage.vue"),
//   meta: { requiresAuth: true } // 로그인한 사용자만 접근 가능
// },
// {
//   path: "/club/:clubId/party/:partyId",
//   name: "PartyView",
//   component: () => import("@/views/PartyView.vue"),
//   meta: { requiresAuth: true, requiresClubMembership: true } // 특정 클럽 멤버만 접근 가능
// },


router.beforeEach(async (to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const isLoggedIn = !!localStorage.getItem("id"); // 바로 boolean 값으로 변환

  if (requiresAuth && !isLoggedIn) {
    // await alert("로그인이 필요한 서비스입니다.");
    next({ name: "Login" });
  } else {
    next();
  }
});



export default router;
