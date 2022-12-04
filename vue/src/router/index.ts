import { createRouter, createWebHistory } from "vue-router";
import Game from "@/components/Game.vue";
import WorldIntroView from "@/views/WorldIntroView.vue";
import GameIntroView from "@/views/GameIntroView.vue";
import JoinGame from "@/components/joinGame/JoinGame.vue";
import CreateWorld from "@/views/CreateWorld.vue";


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
      path: "/joingame",
      name: "JoinGame",
      component: JoinGame,
    },
    {
      path: "/createWorld",
      name: "CreateWorld",
      component: CreateWorld,
    },
  ],
});

export default router;
