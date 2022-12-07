import Game from "@/components/Game.vue";
import WorldIntroView from "@/views/WorldIntroView.vue";
import GameIntroView from "@/views/GameIntroView.vue";
import JoinGame from "@/components/joinGame/JoinGame.vue";
import CreateWorld from "@/views/CreateWorld.vue";

import StartPage from "@/views/StartPage.vue";
import { createRouter, createWebHistory } from "vue-router";
import WorldSelection from "@/components/worldSelection/WorldSelection.vue";
import GameConfig from "@/components/gameConfig/GameConfig.vue";
import EditorView from "@/views/editor/EditorView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/game/:instanceID",
      name: "GameView",
      component: Game,
      props:true
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
      path: "/joingame/:instanceID",
      name: "JoinGame",
      component: JoinGame,
      props:true
    },
    {
      path: "/createWorld",
      name: "CreateWorld",
      component: CreateWorld,
    },

    {
      path: "/login",
      name: "Login",
      component: StartPage,
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
      path: "/editor",
      name: "EditorView",
      component: EditorView,
    }
  ],
});

export default router;
