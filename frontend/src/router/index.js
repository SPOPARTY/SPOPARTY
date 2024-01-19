import { createRouter,createWebHistory } from "vue-router";
import MainPageView from "@/views/MainPageView.vue";
import MatchView from "@/views/MatchView.vue";
import LeagueView from "@/views/LeagueView.vue";
import Signup from "@/components/user/Signup.vue";
import Login from "@/components/user/Login.vue";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'MainPage',
            component: MainPageView
        },
        {
            path:"/match",
            name:"/MatchView",
            component:MatchView
        },
        {
            path:"/league",
            name:"LeagueView",
            component:LeagueView
        },
        {
            path:"/signup",
            name:"Signup",
            component:Signup
        },
        {
            path:"/login",
            name:"Login",
            component:Login
        },
    ]
})

export default router;