<script setup lang="ts">
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faAngleDown,
  faAngleUp,
} from "@fortawesome/free-solid-svg-icons";
library.add(faAngleDown, faAngleUp);

function scrollingUp() {
  const boxwrapper = document.getElementById("info-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollBy(0, -50);
  }
}

function scrollingDown() {
  const boxwrapper = document.getElementById("info-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollBy(0, 50);
  }
}
</script>


<template>
  <div class="h-full mt-5">
    <button @click="scrollingUp" class="w-full">
        <font-awesome-icon
          icon="fa-solid fa-angle-up"
          color="#2F8265"
          class="w-4 h-4"
        />
      </button>
      <div id="info-wrapper" class="h-60 font-poppins overflow-y-auto">
        <p class=" font-bold">
          Um Skripte sinnvoll zu integrieren, steht eine API bereit, die über die
          Variable npc genutzt werden kann. Folgende Schnittstellen können genutzt
          werden:
        </p>
        <br />
        <ul class="list-disc mx-7 text-sm">
          <li>turnLeft(): Dreht das Objekt gegen den Uhrzeigersinn</li>
          <li>turnRight(): Dreht das Objekt im Uhrzeigersinn</li>
          <li>accelerate(): Erhöht die Geschwindigkeit des Objekts</li>
          <li>brake(): Verringert die Geschwindigkeit des Objekts</li>
          <li>emergencyBrake(): Setzt die Geschwindigkeit des Objekts auf 0</li>
          <li>surroundingTiles(): Gibt die Kacheln zurück, die das Objekt umgeben</li>
          <li>
            nearbyRoadUsers(): Gibt die beweglichen Objekte zurück, die das Objekt
            umgeben
          </li>
          <li>
            distanceTo(other): Gibt den Abstand des Objekts zu einem anderen zurück
          </li>
        </ul>
    </div>
    <button @click="scrollingDown" class="w-full">
        <font-awesome-icon
          icon="fa-solid fa-angle-down"
          color="#2F8265"
          class="w-4 h-4"
        />
      </button>
  </div>
</template>
