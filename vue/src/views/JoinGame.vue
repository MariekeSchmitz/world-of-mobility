<script setup lang="ts">
import { useUser } from "@/services/useUser";
import { computed, ref,watch,onMounted } from "vue";
import User from "@/components/joinGame/User.vue";
import CarSelection from "@/components/carselect/CarSelection.vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import router from "@/router";
import SpawnPoint from "@/components/spawnpoint/SpawnPoint.vue";
import { useSpawnPoint } from "@/components/spawnpoint/useSpawnPoint";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faArrowLeft, faChevronRight, faChevronLeft } from "@fortawesome/free-solid-svg-icons";
import Avatar from "@/components/User/Avatar.vue";
import ErrorWarning from "@/components/ErrorWarning.vue";
import { useUserFeedback } from "@/services/editor/useUserFeedback";
import { animateHintBox } from "@/components/HintBoxAnimation";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { useInstanceList } from "@/services/useInstanceList";

library.add(faPlus, faArrowLeft, faChevronRight, faChevronLeft);
const { spawnState, setMoveableObject, setInstanceId } = useSpawnPoint();
const { joinGame } = useGame();
const { userList, getUserList } = useUser();
const { loginData, avatarData } = useLogin();

const joinSuccessfull = ref(false);

const {feedbackMessage} = useUserFeedback();

const props = defineProps<{
  instanceID: string;
}>();

const instanceID = Number(props.instanceID);
let moveableType = "";
const { instanceState, getInstanceList, getUserlist } = useInstanceList();

const spawnPointSet = ref(false)

const errorMessage = ref("")

async function join() {

  if(!spawnPointSet.value) {
    errorMessage.value = "Du musst erst einen Startpunkt wählen."
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
    else   errorMessage.value = "Du musst erst einen Startpunkt wählen.";
;
  }
}

function updateMoveable(type: string) {
  moveableType = type;
  setMoveableObject(type);
  spawnPointSet.value = false;
  errorMessage.value = "";

}

onMounted(async() => {
  getUserList(instanceID);
  setInstanceId(instanceID);
  await getInstanceList("game");
});

function toggleButton() {
  spawnPointSet.value = true
  errorMessage.value = "";
}


watch(errorMessage, (neu, alt) => {
  const errorBox = document.getElementById("errorBox");
  animateHintBox(errorMessage.value != "", errorBox);
});

watch(feedbackMessage, (neu, alt) => {
  errorMessage.value = feedbackMessage.value

});

watch(spawnState, (neu, alt) => {
  if (spawnState.xPos == -1) {
    spawnPointSet.value = false
  } else {
    spawnPointSet.value = true
  }
});

const userlist = computed(() => {
  return getUserlist(instanceID)
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
              <SpawnPoint :instance-id="instanceID" class=""/>
            </div>
          </div>

          <button :class="{
            'buttonOrange w-3/12 mx-auto mt-8':spawnPointSet,
            'buttonOrange w-3/12 mx-auto bg-black bg-opacity-10 hover:bg-black hover:bg-opacity-10 mt-8': !spawnPointSet
          }" @click="join()" >Spiel starten</button>
        </div>

        <div v-if="userlist.length != 0" class=" grid bg-greenLight bg-opacity-60 p-11 h-96">
          <h3 class="text-greenDark text-center h-3/12">Beigetretene <br/>Spieler</h3>
          <div class="overflow-y-scroll h-9/12">
            <div>
              <div v-for="user in userlist">
                <User :name="user"></User>
              </div>
            </div>
          </div> 
        </div>
      </div>

    </div>
    <ErrorWarning :errorMsg="errorMessage"></ErrorWarning>
    
 
  </div>
</template>