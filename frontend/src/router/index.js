import { createRouter, createWebHistory } from "vue-router";
import MainPageView from "@/views/MainPageView.vue";
import MatchView from "@/views/MatchView.vue";
import LeagueView from "@/views/LeagueView.vue";
import Signup from "@/components/user/Signup.vue";
import Login from "@/components/user/Login.vue";
import MyPage from "@/components/user/Mypage.vue";

// import ClubMain from "@/components/club/ClubMain.vue";

// import ArchieveList from "@/components/archieve/ArchieveList.vue"

// import BoardList from "@/components/board/BoardList.vue"
// import WriteBoard from "@/components/board/WriteBoard.vue"
// import EditBoard from "@/components/board/EditBoard.vue"

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
      component: () => import("@/components/user/Mypage.vue")
    },
    {
      path:"/kakao",
      name:"kakao",
      component: () => import("@/components/user/Kakao.vue")
    },
    {
      path: "/club/:clubId",
      name: "ClubView",
      component: () => import("@/views/ClubView.vue"),
      props:true,
      children : [
        {
          path : "",
          name : "ClubMain",
          component : () => import("@/components/club/ClubMain.vue")
        },
        {
          path : "archieve",
          name : "ArchieveList",
          component : () => import("@/components/archieve/ArchieveList.vue")
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

export default router;
