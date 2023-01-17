<script setup lang="ts">
import router from "@/router";
import { ref, computed } from "vue";
import { useGameConfig } from "@/services/useGameConfig";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";

const { instanceId, createGameInstance } = useGame();
const { sendConfig, valSuccess } = useGameConfig();

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
  <div class="wrapper">
    <RouterLink to="/worldSelection">
      <img src="../buttons/editor/arrow-left.png" alt="" />
    </RouterLink>
    <h1>Neues Spiel in der Welt {{ props.mapName }}</h1>
    <div class="square"></div>
    <p>Spielname</p>
    <input id="gamename" v-model="name" placeholder="Spielname eingeben" />
    <p>Spieleranzahl</p>
    <input id="playerLimit" type="number" :min="1" v-model="playerLimit" />
    <p>NPCs platzieren</p>
    <label class="switch">
      <input if="npcSwitch" type="checkbox" v-model="npcs" />
      <span class="slider round"></span>
    </label>

    <button @click="checkValidation(name)">Erstellen</button>
    <p v-if="showError">
      Der Name {{ name }} wurde schon vergeben.
    </p>
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

button {
  padding: 1em;
  border-radius: 1em;
  background-color: antiquewhite;
  width: 10rem;
  margin: 10px;
}

/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: 0.4s;
  transition: 0.4s;
}

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
