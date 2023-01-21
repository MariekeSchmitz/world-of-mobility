<script setup lang="ts">
import { useUser } from "@/services/useUser";
import { ref } from "vue";
import User from "@/components/joinGame/User.vue";
import CarSelection from "@/components/carselect/CarSelection.vue";
import { onMounted } from "vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import router from "@/router";
import SpawnPoint from "@/components/spawnpoint/SpawnPoint.vue";
import { useSpawnPoint } from "@/components/spawnpoint/useSpawnPoint";
const { spawnState, setMoveableObject, setInstanceId } = useSpawnPoint();
const { joinGame } = useGame();
const { userList, getUserList } = useUser();
const { loginData, avatarData } = useLogin();

const joinSuccessfull = ref(false);
const showError = ref(false);

const props = defineProps<{
  instanceID: string;
}>();

const instanceID = Number(props.instanceID);
let moveableType = "";

async function join() {
  if (
    instanceID != undefined &&
    spawnState.xPos != -1 &&
    spawnState.yPos != -1 &&
    spawnState.tileNumber != -1
  ) {
    joinSuccessfull.value = await joinGame(
      instanceID,
      loginData.username,
      moveableType,
      spawnState.xPos,
      spawnState.yPos
    );
    if (joinSuccessfull.value) router.push("/gameview/" + instanceID);
    else showError.value = true;
  }
}

function updateMoveable(type: string) {
  moveableType = type;
  setMoveableObject(type);
}

onMounted(() => {
  getUserList(instanceID);
  setInstanceId(instanceID);
});
</script>

<template>
  <div class="h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
>

    <!-- navigation -->
    <div class="grid grid-cols-3 mx-12 pt-12 h-1/6">
      <RouterLink to="/gameintro" class="">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          size="xl"
          color="white"
          class="bg-greenLight rounded-full p-3 w-6 h-6 inline justify-self-start white hover:bg-greenDark"
        />
      </RouterLink>
      <div class="text-center">
        <h1>Baumodus</h1>
      </div>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end w-16"
      ></Avatar>
    </div>


    <div class="grid grid-cols-8 h-5/6">

      <!-- white box -->
      <div
        class="grid col-start-2 col-end-8 content-center p-20 bg-white h-5/6 mt-8"
      >
      </div>

    </div>

    
    <div id="personal-config-container">
      <div id="car-select">
        <h1>Fortbewegungsmittel wählen</h1>
        <CarSelection @change-moveable="updateMoveable"></CarSelection>
      </div>
      <div id="place-select">
        <h1>Spawnpoint wählen</h1>
        <SpawnPoint :instance-id="instanceID" />
      </div>
    </div>
    <h2>Beigetretene Spieler</h2>
    <div class="userlist">
      <div v-for="user in userList.users">
        <User :name="user"></User>
      </div>
    </div>
    <button @click="join()">Beitreten</button>
    <p v-if="showError">Spielerlimit ausgeschöpft.</p>
  </div>
</template>

<!-- <style scoped>
#personal-config-container {
  display: flex;
  box-shadow: 0px 0px 20px 10px rgba(0, 0, 0, 0.47);
  margin-top: 2%;
}

#car-select,
#place-select {
  padding: 20px;
  padding-top: 0;
}

#car-select {
  border-right: 3px solid rgba(0, 0, 0, 0.455);
}

#car-select h1,
#place-select h1 {
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
</style> -->
