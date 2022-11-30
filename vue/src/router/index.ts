import Game from "@/components/Game.vue";
import GameSelect from "@/views/GameSelect.vue";
import StartPage from "@/views/Login/StartPage.vue";
import WorldSelect from "@/views/WorldSelect.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/game",
      name: "GameView",
      component: Game,
    },
    {
      path: "/worldselect",
      name: "worldSelect",
      component: WorldSelect,
    },
    {
      path: "/gameselect",
      name: "GameSelect",
      component: GameSelect,
    },

    {
      path: "/login",
      name: "Login",
      component: StartPage,
    },
  ],
});

export default router;
