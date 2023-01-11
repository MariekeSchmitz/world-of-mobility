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
    instanceID: number;
  }>(),
  { instanceID: 1 }
);

const { leaveGame } = useGame();
const { loginData } = useLogin();

onUnmounted(() => {
  leaveGame(props.instanceID, loginData.username, "MOTORIZED_OBJECT");
});

function leave() {
  router.push("/joingame/" + props.instanceID);
}
</script>

<template>
  <div>
    <div>
      <button id="exitButton" @click="leave()">
        <img src="../buttons/editor/close.png" alt="" />
      </button>
    </div>
    <Game :instanceID="props.instanceID"></Game>
  </div>
</template>
