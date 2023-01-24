<script setup lang="ts">
/**
 * view in which the game is displayed
 * @author Astrid Klemmer
 */
import { onUnmounted } from "vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import Game from "@/components/Game.vue";
import router from "@/router";
import { library } from "@fortawesome/fontawesome-svg-core";
  import {
    faArrowLeft
  } from "@fortawesome/free-solid-svg-icons";
  library.add(faArrowLeft);

const props = withDefaults(
  defineProps<{
    instanceID: string;
  }>(),
  { instanceID: "1" }
);

const instanceID = Number(props.instanceID);
const { leaveGame } = useGame();
const { loginData } = useLogin();

onUnmounted(() => {
  leaveGame(instanceID, loginData.username, "MOTORIZED_OBJECT");
});

function leave() {
  router.push("/gameintro");
}
</script>

<template>
  <div>
    <div>
      <button class="fixed top-7 left-7" @click="leave()">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          color="#2F8265"
          class="p-3 w-10 h-10 rounded-full bg-white"
        />
      </button>
    </div>
    <Game :instanceID="instanceID"></Game>
  </div>
</template>
