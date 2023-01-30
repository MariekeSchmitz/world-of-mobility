<script setup lang="ts">
/**
 * view in which the game is displayed
 * @author Astrid Klemmer
 */
import { computed, onMounted, onUnmounted } from "vue";
import { useGame } from "@/services/game/useGame";
import { useLogin } from "@/services/login/useLogin";
import { useInstanceList } from "@/services/useInstanceList";
import Game from "@/components/game/Game.vue";
import Avatar from "@/components/User/Avatar.vue";
import ServerChat from "@/components/ServerChat.vue";
import UserListMenu from "@/components/editor/UserListMenu.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import MiniMap from "@/components/miniMap/MiniMap.vue";
  library.add(faArrowLeft);

const props = withDefaults(
  defineProps<{
    instanceID: string;
  }>(),
  { instanceID: "1" }
);

const instanceID = Number(props.instanceID);
const { getGamename, getInstanceList, endReceiveInstanceUpdates } = useInstanceList();
const { leaveGame } = useGame();
const { loginData, avatarData } = useLogin();

onMounted(async() => {
  await getInstanceList("game");
});

const gamename = computed(() => {
    return getGamename(instanceID);
});

onUnmounted(() => {
  endReceiveInstanceUpdates();
  leaveGame(instanceID, loginData.username, "MOTORIZED_OBJECT");
});

</script>

<template>
  <div>
    <RouterLink to="/gameintro" class="fixed top-7 left-7">
      <font-awesome-icon
        icon="fa-solid fa-arrow-left"
        color="white"
        class="bg-greenLight rounded-full p-3 w-6 h-6 inline justify-self-start white hover:bg-greenDark"
      />
    </RouterLink>
    <div class="fixed left-1/2 -translate-y-1/2 -translate-x-1/2 top-16">
      <h1 class="text-greenLight text-4xl mt-8">{{gamename}}</h1>
    </div>
    <div id="miniMap-container">
      <MiniMap :instanceId="instanceID" :user="loginData.username"/>
    </div>
    <Avatar
      :avatarPicture="avatarData.avatar"
      class="w-16 h-16 fixed top-7 right-7"
    ></Avatar>

    <ServerChat :instanceId="instanceID" :type="'game'" :username="loginData.username"></ServerChat>

    <Game :instanceID="instanceID"></Game>
    <UserListMenu :instanceId="instanceID" :type="'game'" ></UserListMenu>
  </div>
</template>

<style scoped>
#miniMap-container {
  position: absolute;
  bottom: calc(15vw + 20px);
  left: 20px;
}
</style>