import { createRouter, createWebHistory } from "vue-router";
import Game from "@/components/Game.vue";
import WorldSelect from "@/views/WorldSelect.vue";

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
  ],
});

export default router;
