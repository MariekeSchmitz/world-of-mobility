<script setup lang="ts">
import router from "@/router";
import { ref, computed, watch } from "vue";
import { useGameConfig } from "@/services/useGameConfig";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import Avatar from "@/components/User/Avatar.vue";
import ErrorWarning from "@/components/ErrorWarning.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import { animateHintBox } from "@/components/HintBoxAnimation";
library.add(faArrowLeft);

const { instanceId, createGameInstance } = useGame();
const { sendConfig, valSuccess } = useGameConfig();
const { avatarData } = useLogin();

let name = "";
let playerLimit = ref(1);
const npcs = ref(false);
const errorMessage = ref("")
const props = defineProps<{
  mapName: string;
}>();

async function checkValidation(name: string) {
  console.log(avatarData.avatar)

  if(name == ""){
    errorMessage.value = "Bitte einen Namen eingeben.";
  } else {
    await sendConfig(props.mapName, name, playerLimit.value, npcs.value);
    if (valSuccess.validationSuccess) {
      await createGameInstance(props.mapName, name, playerLimit.value, npcs.value);
      if (instanceId.id != -1) {
        router.push("/joingame/" + instanceId.id);
      } 
    } else {
      errorMessage.value = `Der Name ${name} wurde schon vergeben.`;
    }
  }

}

</script>

<template>
  <div
    class="h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
    >
    <!-- navigation -->
    <div class="grid grid-cols-3 mx-12 pt-12 h-1/6">

      <button @click="$router.go(-1)" class="place-self-start">
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

    <div class="grid grid-cols-8 h-5/6">

      <!-- white box -->
      <div
        class="grid col-start-2 col-end-8 content-center p-20 bg-white h-5/6 mt-8"
        >
        <div class="grid content-center">
          
          <!-- Headline -->
          <div class="grid content-center mb-5 mt-10 text-center">
            <h2 class="">Neues Spiel in der <br/> Welt {{ props.mapName }} erstellen</h2>              
          </div>

          
          <div class="grid grid-cols-[30%_70%]">
            <!-- globe and label -->
            <div class="gird content-center text-center">
              <img src="@/assets/images/globe.png" alt="" class="mx-auto w-1/2 group-hover:border-orange group-hover:border-8 rounded-full"/>
              <h2 class="mt-5 text-orange">{{ props.mapName }}</h2>
            </div>

            <div class="grid grid-cols-[40%_60%] grid-rows-3 items-center pb-10 pr-10">
              
                <label class="text-right mr-12 " >Spielname</label>
                <input class="inline mr-10" id="gamename" v-model="name" placeholder="Spielname eingeben" />
                <label class="text-right mr-12">Spieleranzahl</label>
                <input class="inline w-1/3" id="playerLimit" type="number" :min="1" v-model="playerLimit" />
                <label class="text-right mr-12">NPCs platzieren</label>
                <label class="relative inline-flex items-center cursor-pointer">
                 
                  <input type="checkbox" v-model="npcs" value="" class="sr-only peer">
                  <div class="w-11 h-6 bg-greenLight outline-none peer-focus:outline-none peer-focus:ring-transparent peer-focus:ring-4 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-orange"></div>

                </label>
                

            </div>

          </div>

          <button
            @click="checkValidation(name)"
            class="buttonOrange w-1/6 mx-auto"
          >
            Erstellen
          </button>

          <ErrorWarning :errorMsg="errorMessage">
          </ErrorWarning>
          
        </div>
      </div>
    </div>
  </div>

</template>

