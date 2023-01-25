<script setup lang="ts">
/**
 * view in which the game is displayed
 * @author Astrid Klemmer
 */
import { computed, onMounted, onUnmounted } from "vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import { useInstanceList } from "@/services/useInstanceList";
import Game from "@/components/Game.vue";
import Avatar from "@/components/User/Avatar.vue";
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
const { getGamename, getInstanceList } = useInstanceList();
const { leaveGame } = useGame();
const { loginData, avatarData } = useLogin();

onMounted(async() => {
  await getInstanceList("game");
});

const gamename = computed(() => {
    return getGamename(instanceID);
});

onUnmounted(() => {
  leaveGame(instanceID, loginData.username, "MOTORIZED_OBJECT");
});

</script>

<template>
  <div>
      <button @click="$router.go(-1)" class="fixed top-7 left-7">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          color="white"
          class="bg-greenLight rounded-full p-3 w-6 h-6 inline justify-self-start white hover:bg-greenDark"
        />
      </button>
      <div class="fixed left-1/2 -translate-y-1/2 -translate-x-1/2 top-16">
        <h1>{{gamename}}</h1>
      </div>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="w-16 h-16 fixed top-7 right-7"
      ></Avatar>
    <Game :instanceID="instanceID"></Game>
  </div>
</template>
