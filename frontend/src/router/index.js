import { createRouter, createWebHistory } from "vue-router";
import MainPageView from "@/views/MainPageView.vue";
import MatchView from "@/views/MatchView.vue";
import LeagueView from "@/views/LeagueView.vue";
import Signup from "@/components/user/Signup.vue";
import Login from "@/components/user/Login.vue";
import MyPage from "@/components/user/MyPage.vue";

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
      name:"Mypage",
      component: MyPage,
    },

    {
      path: "/club/:clubId",
      name: "ClubView",
      component: () => import("@/views/ClubView.vue")
    }
  ]
});

export default router;
