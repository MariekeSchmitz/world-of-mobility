import App from "@/App.vue";
import Game from "@/components/Game.vue";
import { createPinia } from "pinia";
import { createApp } from "vue";
import router from "./router";
import "./tailwind.css";

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount("#app");
