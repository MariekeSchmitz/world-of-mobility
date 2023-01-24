<script setup lang="ts">
import { useUser } from "@/services/useUser";
import { ref,watch } from "vue";
import User from "@/components/joinGame/User.vue";
import CarSelection from "@/components/carselect/CarSelection.vue";
import { onMounted } from "vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import router from "@/router";
import SpawnPoint from "@/components/spawnpoint/SpawnPoint.vue";
import { useSpawnPoint } from "@/components/spawnpoint/useSpawnPoint";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faArrowLeft, faChevronRight, faChevronLeft } from "@fortawesome/free-solid-svg-icons";
import Avatar from "@/components/User/Avatar.vue";
import ErrorWarning from "@/components/ErrorWarning.vue";
import { animateErrorWarning } from "@/components/ErrorAnimation";


library.add(faPlus, faArrowLeft, faChevronRight, faChevronLeft);
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

const spawnPointSet = ref(false)

async function join() {

  if(!spawnPointSet.value) {
    showError.value = true
  }
  
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
  spawnPointSet.value = false

}

onMounted(() => {
  getUserList(instanceID);
  setInstanceId(instanceID);
});

function toggleButton() {
  spawnPointSet.value = true
  showError.value = false
}

watch(showError, (neu, alt) => {
  const errorBox = document.getElementById("errorBox");
  if (neu) {
        if (errorBox != null) {
            errorBox.classList.toggle("opacity-0");
            errorBox.classList.toggle("opacity-100");
            errorBox.classList.toggle("-right-60");
            errorBox.classList.toggle("right-28");
        }
    } else {
        if (errorBox != null) {
        errorBox.classList.toggle("opacity-100");
        errorBox.classList.toggle("opacity-0");
        errorBox.classList.toggle("right-28");
        errorBox.classList.toggle("-right-60");
        }
    }
});

</script>

<template>
  <div class="h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
>

    <!-- navigation -->
    <div class="grid grid-cols-3 mx-12 pt-12 h-1/6">
      <button class="place-self-start" @click="$router.go(-1)">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          size="xl"
          color="white"
          class="bg-greenLight rounded-full p-3 w-6 h-6 inline justify-self-start white hover:bg-greenDark"
        />
      </button>
      
      <div class="text-center">
        <h1>Spielmodus</h1>
      </div>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end w-16"
      ></Avatar>
    </div>


    <div :class="{
      'grid grid-cols-[1fr,10fr,1fr] h-5/6' : userList.users.length != 0,
      'grid grid-cols-8 h-5/6' : userList.users.length == 0,
      }">

      <!-- white box -->
      <div
        :class="{
          'grid col-start-2 grid-cols-[75%_25%] content-center p-20 bg-white h-5/6 mt-8': userList.users.length != 0,
          'grid col-start-2 col-end-8 content-center p-20 bg-white h-5/6 mt-8': userList.users.length == 0,
          }"
        >

        <div grid="grid content-center">
          <!-- Settings -->
          <div class="grid grid-cols-2 mt-10">

            <!-- Choose Moveable -->
            <div class="grid text-center content-start ">
              <div class="grid content-start">
                <h2>Fortbewegungsmittel<br/>wählen</h2>
              </div>
              <CarSelection @change-moveable="updateMoveable" class="grid items-center h-2/3"></CarSelection>
            </div>

            <!-- Choose Spawnpoint -->
            <div class="text-center">
              <h2 class="">Startpunkt<br v-if="userList.users.length != 0"/>wählen</h2>
              <SpawnPoint @set-spawn-point="toggleButton" :instance-id="instanceID" class=""/>
            </div>
          </div>

          <button :class="{
            'buttonOrange w-3/12 mx-auto mt-8':spawnPointSet,
            'buttonOrange w-3/12 mx-auto bg-black bg-opacity-10 hover:bg-black hover:bg-opacity-10 mt-8': !spawnPointSet
          }" @click="join()" >Spiel starten</button>
        </div>

        <div v-if="userList.users.length != 0" class=" grid bg-greenLight bg-opacity-60 p-11 h-96">
          <h3 class="text-greenDark text-center h-3/12">Beigetretene <br/>Spieler</h3>
          <div class="overflow-y-scroll h-9/12">
            <div >
              <div v-for="user in userList.users">
                <User :name="user"></User>
              </div>

            </div>
          </div> 
        </div>
      </div>

    </div>
    <ErrorWarning :errorMsg="'Du musst erst einen Startpunkt wählen.'"></ErrorWarning>
 
    <!-- <p v-if="showError">Spielerlimit ausgeschöpft.</p> -->
  </div>
</template>