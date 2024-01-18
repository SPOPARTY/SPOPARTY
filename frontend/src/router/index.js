import { createRouter,createWebHistory } from "vue-router";
import MainPageView from "../views/MainPageView.vue";
import MatchView from "../views/MatchView.vue";
import LeagueView from "../views/LeagueView.vue";



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
            name:"/",
            component:LeagueView
        },
    ]
})

export default router;