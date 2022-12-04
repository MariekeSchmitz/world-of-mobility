import Game from "@/components/Game.vue";
import WorldIntroView from "@/views/WorldIntroView.vue";
import GameIntroView from "@/views/GameIntroView.vue";
import WorldSelection from "@/components/worldSelection/WorldSelection.vue";
import GameConfig from "@/components/gameConfig/GameConfig.vue";
import EditorView from "@/views/editor/EditorView.vue";
import StartPage from "@/views/StartPage.vue";
import { createRouter, createWebHistory } from "vue-router";

import JoinGame from "@/components/joinGame/JoinGame.vue";

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
      path: "/gameConfig/:mapName",
      name: "GameConfig",
      component: GameConfig,
      props: true
    },

    {
      path: "/login",
      name: "Login",
      component: StartPage,
    },
    {
      path: "/joingame/:instanceID",
      name: "JoinGame",
      component: JoinGame,
      props: true
    },
    {
      path: "/editor",
      name: "EditorView",
      component: EditorView,
    }
  ],
});

export default router;
