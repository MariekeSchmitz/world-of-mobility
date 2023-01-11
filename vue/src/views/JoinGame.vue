<script setup lang="ts">
import { useUser } from "@/services/useUser";
import User from "@/components/joinGame/User.vue";
import CarSelection from "@/components/carselect/CarSelection.vue";
import { onMounted } from "vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import router from "@/router";
import SpawnPoint from "@/components/spawnpoint/SpawnPoint.vue";
import { useSpawnPoint } from '@/components/spawnpoint/useSpawnPoint';
const { spawnState, setMoveableObject, setInstanceId } = useSpawnPoint();
const { joinGame } = useGame();
const { userList, getUserList } = useUser();
const { loginData } = useLogin();

const props = defineProps<{
  instanceID: number;
}>();

let moveableType = "";

function join() {
  if (props.instanceID != undefined && spawnState.xPos != -1 && spawnState.yPos != -1 && spawnState.tileNumber != -1) {
    joinGame(props.instanceID, loginData.username, moveableType, spawnState.xPos, spawnState.yPos);
    router.push("/game/" + props.instanceID);
  }
}

function updateMoveable(type: string) {
  moveableType = type;
  setMoveableObject(type);
}

onMounted(() => {
  getUserList(props.instanceID);
  setInstanceId(props.instanceID);
});
</script>

<template>
  <div class="wrapper">
    <RouterLink to="/gameintro">
      <button>
        <img src="../buttons/editor/arrow-left.png" alt="" />
      </button>
    </RouterLink>
    <div id="personal-config-container">
      <div id="car-select">
        <h1>Fortbewegungsmittel wählen</h1>
        <CarSelection @change-moveable="updateMoveable"></CarSelection>
      </div>
      <div id="place-select">
        <h1>Spawnpoint wählen</h1>
        <SpawnPoint :instance-id="props.instanceID"/>
      </div>
    </div>
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

#personal-config-container {
  display: flex;
  box-shadow: 0px 0px 20px 10px rgba(0, 0, 0, 0.47);
  margin-top: 2%;
}

#car-select, #place-select {
  padding: 20px;
  padding-top: 0;
}

#car-select {
  border-right: 3px solid rgba(0, 0, 0, 0.455);
}

#car-select h1, #place-select h1 {
  width: 50%;
}

#car-select h1 {
  margin-bottom: 20%;
}

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
  height: 20vh;
}
</style>
