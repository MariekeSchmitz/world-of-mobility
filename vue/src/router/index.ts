import { createRouter, createWebHistory } from "vue-router";
import Game from "@/components/Game.vue";
import WorldSelect from "@/views/WorldSelect.vue";
import GameSelect from "@/views/GameSelect.vue";
import GameConfig from "@/components/gameConfig/GameConfig.vue";

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
      path: "/gameConfig",
      name: "GameConfig",
      component: GameConfig,
    },
  ],
});

export default router;
