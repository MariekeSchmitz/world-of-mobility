import GamingMap from "@/components/GamingMap.vue";
import App from "@/TestCompVickyKevin.vue";
import StartPage from "@/views/Login/StartPage.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: StartPage,
    },
    {
      path: "/app",
      name: "App",
      component: App,
    },
  ],
});

export default router;
