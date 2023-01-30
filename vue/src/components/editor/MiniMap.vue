<script setup lang="ts">
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faPlus,
  faMinus,
  faAngleUp,
  faXmark,
} from "@fortawesome/free-solid-svg-icons";
library.add(faPlus, faMinus, faXmark, faAngleUp);

function toggle() {
  const minimap = document.getElementById("minimap");
  const showMap = document.getElementById("showMap");

  if (minimap != null && showMap != null) {
    if (minimap.style.display == "none") {
      minimap.style.display = "grid";
      showMap.style.display = "none";
    } else {
      minimap.style.display = "none";
      showMap.style.display = "block";
    }
  }
}
</script>

<template>
  <div
    id="minimap"
    class="bg-white fixed bottom-5 left-3 h-[27%] aspect-square grid items-center pb-4"
  >
    <div class="h-1/6 inline place-self-end self-start pt-2 pr-2">
      <button>
        <font-awesome-icon
          icon="fa-solid fa-plus"
          color="#2F8265"
          class="w-2 h-2"
        />
      </button>
      <button>
        <font-awesome-icon
          icon="fa-solid fa-minus"
          color="#2F8265"
          class="w-2 h-2"
        />
      </button>
      <button @click="toggle">
        <font-awesome-icon
          icon="fa-solid fa-xmark"
          color="#2F8265"
          class="w-5 h-5"
        />
      </button>
    </div>
    <p
      class="bg-orangeLight h-4/5 aspect-square justify-self-center relative bottom-5"
    >
      Minimap, separate camera view from further away, +- Buttons control
      minimap camera
    </p>
  </div>

  <button
    id="showMap"
    class="editorLabel hidden fixed bottom-2 left-[13%]"
    @click="toggle"
  >
    <font-awesome-icon
      icon="fa-solid fa-angle-up"
      color="white"
      class="w-5 h-5"
    /><br />
    Minimap
  </button>
</template>
