import { createApp } from "vue";
import { createPinia } from "pinia";
import router from "./router";
import Game from "@/components/Game.vue";

const app = createApp(Game);

app.use(createPinia());
app.use(router);

app.mount("#app");
