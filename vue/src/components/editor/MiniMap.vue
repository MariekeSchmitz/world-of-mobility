<script setup lang="ts">
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faPlus,
  faMinus,
  faAngleDown,
  faAngleUp,
} from "@fortawesome/free-solid-svg-icons";
library.add(faPlus, faMinus, faAngleDown, faAngleUp);

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
    class="fixed bottom-3 right-3 h-1/5 aspect-square bg-orange"
  >
    <div class="fixed bottom-[22%] right-3">
      <button>
        <font-awesome-icon
          icon="fa-solid fa-plus"
          size="3xl"
          color="#2F8265"
          class="w-5 h-5"
        />
      </button>
      <button>
        <font-awesome-icon
          icon="fa-solid fa-minus"
          size="3xl"
          color="#2F8265"
          class="w-5 h-5"
        />
      </button>
      <button @click="toggle">
        <font-awesome-icon
          icon="fa-solid fa-angle-down"
          size="3xl"
          color="#2F8265"
          class="w-8 h-8"
        />
      </button>
    </div>
    <p>
      Minimap, separate camera view from further away, +- Buttons control
      minimap camera
    </p>
  </div>

  <button id="showMap" class="hidden fixed bottom-1 right-3" @click="toggle">
    <font-awesome-icon
      icon="fa-solid fa-angle-up"
      size="3xl"
      color="#2F8265"
      class="w-8 h-8"
    />
  </button>
</template>
