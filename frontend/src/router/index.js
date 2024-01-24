import { createRouter, createWebHistory } from "vue-router";
import MainPageView from "@/views/MainPageView.vue";
import MatchView from "@/views/MatchView.vue";
import LeagueView from "@/views/LeagueView.vue";
import Signup from "@/components/user/Signup.vue";
import Login from "@/components/user/Login.vue";
import MyPage from "@/components/user/Mypage.vue";

import ClubMain from "@/components/club/ClubMain.vue";

import ArchieveList from "@/components/archieve/ArchieveList.vue"

import BoardList from "@/components/board/BoardList.vue"
import WriteBoard from "@/components/board/WriteBoard.vue"
import EditBoard from "@/components/board/EditBoard.vue"

const router = createRouter({
  history: createWebHistory(),
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
      component: Signup
    },
    {
      path: "/login",
      name: "Login",
      component: Login
    },
    {
      path:"/mypage",
      name:"MyPage",
      component: MyPage,
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
          component : ClubMain,
        },
        {
          path : "archieve",
          name : "ArchieveList",
          component : ArchieveList
        },
        {
          path : "board",
          name : "BoardList",
          component : BoardList
        },
        {
          path : "board/write",
          name : "WriteBoard",
          component : WriteBoard
        },
        {
          path : "board/edit",
          name : "EditBoard",
          component : EditBoard
        }
      ]
    }
  ]
});

export default router;
