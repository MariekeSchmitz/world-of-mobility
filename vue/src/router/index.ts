import Game from "@/components/Game.vue";
import CreateWorld from "@/views/CreateWorld.vue";
import GameIntroView from "@/views/GameIntroView.vue";
import JoinGame from "@/views/JoinGame.vue";
import WorldIntroView from "@/views/WorldIntroView.vue";

import StartPage from "@/views/StartPage.vue";

import { createRouter, createWebHistory } from "vue-router";
import WorldSelection from "@/views/WorldSelection.vue";
import GameConfig from "@/views/GameConfig.vue";
import EditorView from "@/views/EditorView.vue";
import GameView from "@/views/GameView.vue";
import { useLogin } from "@/services/login/useLogin";

const { loginData } = useLogin();

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/game/:instanceID",
      name: "Game",
      component: Game,
      props: true,
    },
    {
      path: "/gameview/:instanceID",
      name: "GameView",
      component: GameView,
      props: true,
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
      props: true,
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
      props: true,
    },
    {
      path: "/editor/:editorID",
      name: "EditorView",
      component: EditorView,
      props: true,
    },
  ],
});

/**
 * if user is not logged in, always root to login page
 */
router.beforeEach(async (to, from) => {
  if (!loginData.loggedIn && to.name !== "Login") {
    return { name: "Login" };
  }
});

export default router;
