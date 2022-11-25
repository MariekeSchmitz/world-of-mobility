import { createRouter, createWebHistory } from "vue-router";
import Game from "@/components/Game.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/game",
      name: "GameView",
      component: Game,
    },
  ],
});

export default router;
