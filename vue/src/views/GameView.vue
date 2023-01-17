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
      <button id="exitButton" @click="leave()">
        <img src="@/buttons/editor/close.png" alt="" />
      </button>
    </div>
    <Game :instanceID="instanceID"></Game>
  </div>
</template>
