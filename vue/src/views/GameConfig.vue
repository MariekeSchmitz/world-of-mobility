<script setup lang="ts">
import router from "@/router";
import { ref, computed } from "vue";
import { useGameConfig } from "@/services/useGameConfig";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import Avatar from "@/components/User/Avatar.vue";
import ErrorWarning from "@/components/ErrorWarning.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
library.add(faArrowLeft);

const { instanceId, createGameInstance } = useGame();
const { sendConfig, valSuccess } = useGameConfig();
const { avatarData } = useLogin();

let name = "";
let playerLimit = 1;
const npcs = ref(false);
const showError = ref(false);
const props = defineProps<{
  mapName: string;
}>();

async function checkValidation(name: string) {
  await sendConfig(props.mapName, name, playerLimit, npcs.value);
  if (valSuccess.validationSuccess) {
    await createGameInstance(props.mapName, name, playerLimit, npcs.value);
    if (instanceId.id != -1) {
      router.push("/joingame/" + instanceId.id);
    }
  } else {
    showError.value = true;
  }
}
</script>

<template>
  <div
    class="grid grid-cols-8 grid-rows-6 h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
  >
    <!-- navigation -->
    <div class="grid col-span-8 row-span-1 grid-cols-2 mx-12 mt-12">
      <RouterLink to="/worldSelection" class="">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          size="3xl"
          color="white"
          class="bg-greenLight rounded-full p-2 w-8 h-8 inline justify-self-start"
        />
      </RouterLink>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end w-16"
      ></Avatar>
    </div>

    <!-- white box -->
    <div
      class="grid content-center col-start-2 col-end-8 row-span-4 p-20 bg-white"
    >
      <div class="mb-20 inline-flex items-center">
        <h2 class="ml-6 mb-0 inline">Neues Spiel in der Welt:</h2>
        <h2 class="ml-6 mb-0 inline text-orange">
          {{ props.mapName }}
        </h2>
      </div>
      <div class="w-40 h-40 bg-greenDark"></div>
      <p>Spielname</p>
      <input id="gamename" v-model="name" placeholder="Spielname eingeben" />
      <p>Spieleranzahl</p>
      <input id="playerLimit" type="number" :min="1" v-model="playerLimit" />
      <p>NPCs platzieren</p>
      <label class="relative inline-block w-16 h-8">
        <input
          if="npcSwitch"
          type="checkbox"
          v-model="npcs"
          class="opacity-0"
        />
        <span
          class="slider round rounded-full bg-greenLight transition duration-300 absolute inset-0 checked:bg-greenDark"
        ></span>
      </label>

      <button
        @click="checkValidation(name)"
        class="rounded-full font-poppins bg-orange text-white p-3"
      >
        Erstellen
      </button>
      <div v-if="showError">
        <ErrorWarning errorMsg="Der Name {{ name }} wurde schon vergeben.">
        </ErrorWarning>
      </div>
    </div>
  </div>
</template>
<style scoped>
/* The slider */
.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: 0.4s;
  transition: 0.4s;
}

input:checked + .slider {
  background-color: #2196f3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196f3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>
