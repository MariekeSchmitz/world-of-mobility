import { createRouter, createWebHistory } from "vue-router";
import Game from "@/components/Game.vue";
import WorldIntroView from "@/views/WorldIntroView.vue";
import GameIntroView from "@/views/GameIntroView.vue";
import WorldSelection from "@/components/worldSelection/WorldSelection.vue";
import GameConfig from "@/components/gameConfig/GameConfig.vue";
import EditorView from "@/views/editor/EditorView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/game",
      name: "GameView",
      component: Game,
    },
    {
      path: "/worldintro",
      name: "worldSelect",
      component: WorldIntroView,
    },
    {
      path: "/gameintro",
      name: "GameSelect",
      component: GameIntroView,
    },
    {
      path: "/worldSelection",
      name: "WorldSelection",
      component: WorldSelection,
    },
    {
      path: "/gameConfig",
      name: "GameConfig",
      component: GameConfig,
    }

    {
      path: "/editor",
      name: "EditorView",
      component: EditorView,
    }
  ],
});

export default router;
