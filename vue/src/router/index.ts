import Game from "@/components/Game.vue";
import WorldIntroView from "@/views/WorldIntroView.vue";
import GameIntroView from "@/views/GameIntroView.vue";
import JoinGame from "@/views/JoinGame.vue";
import CreateWorld from "@/views/CreateWorld.vue";

import StartPage from "@/views/StartPage.vue";
import { createRouter, createWebHistory } from "vue-router";
import WorldSelection from "@/views/WorldSelection.vue";
import GameConfig from "@/views/GameConfig.vue";
import EditorView from "@/views/editor/EditorView.vue";
import { useLogin } from "@/services/login/useLogin";

const { loginData } = useLogin();

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/game/:instanceID",
      name: "GameView",
      component: Game,
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

router.beforeEach(async (to, from) => {
  // wenn z.B. ein 'berechtigt' nicht wahr ist,
  // alle Nicht-/login-Navigationen auf /login leiten
  if (!loginData.loggedIn && to.name !== "Login") {
    return { name: "Login" };
  }
});

export default router;
