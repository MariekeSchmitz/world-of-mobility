<script setup lang="ts">
import { useUser } from "@/services/useUser";
import User from "@/components/joinGame/User.vue";
import CarSelection from "@/components/carselect/CarSelection.vue";
import { onMounted } from "vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import router from "@/router";
const { receiveGameUpdate, joinGame } = useGame();
const { userList, getUserList } = useUser();
const { loginData } = useLogin();

const props = defineProps<{
  instanceID: number;
}>();

let moveableType = "";

function join() {
  if (props.instanceID != undefined) {
    joinGame(props.instanceID, loginData.username, moveableType);
    router.push("/gameview/" + props.instanceID);
  }
}

function updateMoveable(type: string) {
  moveableType = type;
}

onMounted(() => {
  getUserList(props.instanceID);
});
</script>

<template>
  <div class="wrapper">
    <RouterLink to="/gameintro">
      <button>
        <img src="../buttons/editor/arrow-left.png" alt="" />
      </button>
    </RouterLink>
    <h1>Fortbewegungsmittel wählen</h1>
    <CarSelection @change-moveable="updateMoveable"></CarSelection>
    <h2>Beigetretene Spieler</h2>
    <div class="userlist">
      <div v-for="user in userList.users">
        <User :name="user"></User>
      </div>
    </div>
    <button @click="join()">Beitreten</button>
  </div>
</template>

<style scoped>
.square {
  width: 12.5rem;
  height: 12.5rem;
  background-color: gray;
}
.wrapper {
  display: grid;
  place-items: center;
}

.userlist {
  overflow-y: scroll;
  overflow: auto;
  height: 200px;
}
</style>
