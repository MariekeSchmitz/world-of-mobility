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
  <div id="minimap">
    <div class="minimapButtons">
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

  <button id="showMap" @click="toggle">
    <font-awesome-icon
      icon="fa-solid fa-angle-up"
      size="3xl"
      color="#2F8265"
      class="w-8 h-8"
    />
  </button>
</template>

<style scoped>
#minimap {
  position: fixed;
  bottom: 10px;
  right: 10px;
  height: 20%;
  aspect-ratio: 1/1;
  background-color: wheat;
}

.minimapButtons {
  position: fixed;
  bottom: 22%;
  right: 10px;
}

#showMap {
  display: none;
  position: fixed;
  bottom: 2%;
  right: 10px;
}
</style>
