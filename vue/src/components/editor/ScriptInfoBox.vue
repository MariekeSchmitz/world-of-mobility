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
          Um Skripte sinnvoll zu integrieren, steht eine API bereit, die über die Variable npc genutzt werden kann. 
          Folgende Schnittstellen können genutzt werden:

        </p>
        <br />
        <ul class="list-disc mx-7 text-sm">
          <li>npc.start(): Setzt die Geschwindigkeit des Objekts auf 0.5</li>
          <li>npc.stop(): Setzt die Geschwindigkeit des Objekts auf 0</li>
          <li>npc.getCurrentTile(): Gibt das Kacheln worauf das Objekt drauf steht zurück</li>
          <li>npc.getFrontTile(): Gibt das Kacheln in der frontalen Richtung zurück</li>
          <li>npc.getCurrentVelocity(): Gibt die Geschwindigkeit zurück</li>
          <li>npc.getOrientation(): Gibt die Orientation des Objektes zurück</li>
          <li>npc.isMotorizedObject(): Gibt True zurück wenn es ein MotorizedObject ist</li>
          <li>npc.checkMotorizedObject(moveable): gibt wenn, 
            das ob das übergebendes Objekt ein MotorizedObject ist, true zurück</li>
          <li>npc.checkPassenger(moveable): gibt wenn, das ob das übergebendes Objekt ein Passenger ist, true zurück</li>
          <li>npc.turnLeft(): Dreht das Objekt gegen den Uhrzeigersinn</li>
          <li>npc.turnRight(): Dreht das Objekt im Uhrzeigersinn</li>
          <li>npc.accelerate(): Erhöht die Geschwindigkeit des Objekts</li>
          <li>npc.brake(): Verringert die Geschwindigkeit des Objekts</li>
          <li>npc.surroundingTiles(): Gibt die Kacheln zurück, die das Objekt umgeben</li>
          <li>npc.nearbyRoadUsers(): Gibt die beweglichen Objekte zurück, die das Objekt umgeben</li>
          <li>npc.distanceTo(other): Gibt den Abstand des Objekts zu einem anderen zurück</li>
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
