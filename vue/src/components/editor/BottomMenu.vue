<script setup lang="ts">
import { TileName } from "@/services/editor/TileNameEnum";
import TileSelection from "@/components/editor/bottomElements/TileSelection.vue";
import { usePlaceState } from "@/services/editor/usePlaceState";
import { NaturObjectEnum } from "@/services/NaturObjectEnum";
import { ObjectEnum } from "@/services/ObjectEnum";
import { ControlEnum } from "@/services/ControlEnum";
import BottomSelectionLoop from "@/components/editor/BottomSelectionLoop.vue";
import { NpcPassengerType } from "@/services/editor/NpcPassengerType";
import { NpcVehicleType } from "@/services/editor/NpcVehicleType";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faAngleRight,
  faAngleLeft,
  faAngleDown,
  faAngleUp,
} from "@fortawesome/free-solid-svg-icons";
library.add(faAngleRight, faAngleLeft, faAngleDown, faAngleUp);

const { setPlaceState } = usePlaceState();

function scrollingLeft() {
  const boxwrapper = document.getElementById("box-wrapper");
  const boxwrappernpc = document.getElementById("box-wrapper-npc");

  if (boxwrapper != null) {
    boxwrapper.scrollLeft -= 200;
  }
  if (boxwrappernpc != null) {
    boxwrappernpc.scrollLeft -= 200;
  }
}

function scrollingRight() {
  const boxwrapper = document.getElementById("box-wrapper");
  const boxwrappernpc = document.getElementById("box-wrapper-npc");
  if (boxwrapper != null) {
    boxwrapper.scrollLeft += 200;
  }
  if (boxwrappernpc != null) {
    boxwrappernpc.scrollLeft += 200;
  }
}

function resetScrolling() {
  const boxwrapper = document.getElementById("box-wrapper");
  const boxwrappernpc = document.getElementById("box-wrapper-npc");

  if (boxwrapper != null) {
    boxwrapper.scrollLeft = 0;
  }
  if (boxwrappernpc != null) {
    boxwrappernpc.scrollLeft = 0;
  }
}

function toggle() {
  const bottomMenu = document.getElementById("bottomMenu");
  const showElement = document.getElementById("showElement");

  if (bottomMenu != null && showElement != null) {
    if (bottomMenu.style.display == "none") {
      bottomMenu.style.display = "grid";
      showElement.style.display = "none";
    } else {
      bottomMenu.style.display = "none";
      showElement.style.display = "block";
    }
  }
}

function switchItems(element: string) {
  const streetItems = document.getElementById("streetItems");
  const componentItems = document.getElementById("componentItems");
  const otherItems = document.getElementById("otherItems");
  const passengerItems = document.getElementById("passenger");
  const motorizedItems = document.getElementById("motorized");

  resetScrolling();

  if (
    streetItems != null &&
    componentItems != null &&
    otherItems != null &&
    passengerItems != null &&
    motorizedItems != null
  ) {
    switch (element) {
      case "componentItems":
        streetItems.style.display = "none";
        componentItems.style.display = "block";
        otherItems.style.display = "none";
        passengerItems.style.display = "none";
        motorizedItems.style.display = "block";
        break;
      case "otherItems":
        streetItems.style.display = "none";
        componentItems.style.display = "none";
        otherItems.style.display = "block";
        passengerItems.style.display = "none";
        motorizedItems.style.display = "block";
        break;
      case "passenger":
        motorizedItems.style.display = "none";
        passengerItems.style.display = "block";
        break;
      case "motorized":
        motorizedItems.style.display = "block";
        passengerItems.style.display = "none";
        break;
      default:
        streetItems.style.display = "block";
        componentItems.style.display = "none";
        otherItems.style.display = "none";
        passengerItems.style.display = "none";
        motorizedItems.style.display = "block";
        break;
    }
  }
}

function switchContent(element: string) {
  const landscapeMenu = document.getElementById("landscapeMenu");
  const npcMenu = document.getElementById("npcMenu");

  if (landscapeMenu != null && npcMenu != null) {
    switch (element) {
      case "npcMenu":
        landscapeMenu.style.display = "none";
        npcMenu.style.display = "grid";
        break;
      default:
        landscapeMenu.style.display = "grid";
        npcMenu.style.display = "none";
        break;
    }
  }
}
</script>

<template>
  <div
    id="bottomMenu"
    class="grid grid-cols-[20%_75%_5%] w-1/2 h-1/5 fixed bottom-0 left-1/4 font-poppins"
  >
    <div class="grid grid-rows-2">
      <button @click="switchContent('landscapeMenu')">
        Infrastruktur, Landschaft
      </button>
      <button @click="switchContent('npcMenu')">NPCs</button>
    </div>
    <div>
      <div id="landscapeMenu" class="grid grid-cols-[20%_80%] h-full">
        <div class="grid grid-rows-3">
          <button @click="switchItems('streetItems')">
            <img
              src="@/textures/editor/STREET_STRAIGHT.jpg"
              class="w-10 h-10 inline justify-center"
            />
          </button>
          <button @click="switchItems('componentItems')">
            Natur/<br />Tiere
          </button>
          <button @click="switchItems('otherItems')">
            Gegenstände/<br />Tankstelle
          </button>
        </div>

        <div class="grid grid-cols-[10%_80%_10%] my-auto">
          <button id="scrollLeft" @mousedown="scrollingLeft">
            <font-awesome-icon
              icon="fa-solid fa-angle-left"
              size="3xl"
              color="#2F8265"
              class="w-8 h-8"
            />
          </button>

          <ul
            id="box-wrapper"
            class="h-full w-4/5 whitespace-nowrap overflow-hidden"
          >
            <div id="streetItems">
              <BottomSelectionLoop
                :enum="Object.keys(TileName)"
                :boolTile="true"
                :boolNpc="false"
                :boolPlaceable="false"
              ></BottomSelectionLoop>
            </div>

            <div id="componentItems" class="hidden">
              <li class="bottomMenuListStyle">
                <button
                  class="w-16 h-16"
                  @click="setPlaceState(ControlEnum.REMOVE, false, false, true)"
                >
                  <img src="@/assets/objects/REMOVE.png" />
                  löschen
                </button>
              </li>
              <BottomSelectionLoop
                :enum="Object.keys(NaturObjectEnum)"
                :boolTile="false"
                :boolNpc="false"
                :boolPlaceable="true"
              ></BottomSelectionLoop>
            </div>

            <div id="otherItems" class="hidden">
              <li class="bottomMenuListStyle">
                <button
                  class="w-16 h-16"
                  @click="setPlaceState(ControlEnum.REMOVE, false, false, true)"
                >
                  <img src="@/assets/objects/REMOVE.png" />
                  löschen
                </button>
              </li>
              <BottomSelectionLoop
                :enum="Object.keys(ObjectEnum)"
                :boolTile="false"
                :boolNpc="false"
                :boolPlaceable="true"
              ></BottomSelectionLoop>
            </div>
          </ul>

          <button id="scrollRight" @click="scrollingRight">
            <font-awesome-icon
              icon="fa-solid fa-angle-right"
              size="3xl"
              color="#2F8265"
              class="w-8 h-8"
            />
          </button>
        </div>
      </div>

      <div id="npcMenu" class="h-full hidden grid-cols-[20%_80%]">
        <div class="h-full grid grid-rows-2">
          <button @click="switchItems('motorized')">Fahrzeuge</button>
          <button @click="switchItems('passenger')">Fußgänger</button>
        </div>

        <div class="grid grid-cols-[10%_80%_10%] my-auto">
          <button id="scrollLeft" @mousedown="scrollingLeft">
            <font-awesome-icon
              icon="fa-solid fa-angle-left"
              size="3xl"
              color="#2F8265"
              class="w-8 h-8"
            />
          </button>

          <ul
            id="box-wrapper-npc"
            class="h-full w-4/5 whitespace-nowrap overflow-hidden"
          >
            <div id="passenger" class="hidden">
              <li class="bottomMenuListStyle">
                <button
                  class="w-16 h-16"
                  @click="
                    setPlaceState(ControlEnum.REMOVE_NPC, false, false, false)
                  "
                >
                  <img src="@/assets/objects/REMOVE.png" />
                  löschen
                </button>
              </li>
              <BottomSelectionLoop
                :enum="Object.keys(NpcPassengerType)"
                :boolTile="false"
                :boolNpc="true"
                :boolPlaceable="false"
              ></BottomSelectionLoop>
            </div>

            <div id="motorized">
              <li class="bottomMenuListStyle">
                <button
                  class="w-16 h-16"
                  @click="
                    setPlaceState(ControlEnum.REMOVE_NPC, false, false, false)
                  "
                >
                  <img src="@/assets/objects/REMOVE.png" />
                  löschen
                </button>
              </li>
              <BottomSelectionLoop
                :enum="Object.keys(NpcVehicleType)"
                :boolTile="false"
                :boolNpc="true"
                :boolPlaceable="false"
              ></BottomSelectionLoop>
            </div>
          </ul>

          <button id="scrollRight" @click="scrollingRight">
            <font-awesome-icon
              icon="fa-solid fa-angle-right"
              size="3xl"
              color="#2F8265"
              class="w-8 h-8"
            />
          </button>
        </div>
      </div>
    </div>

    <button id="hideElement" @click="toggle">
      <font-awesome-icon
        icon="fa-solid fa-angle-down"
        size="3xl"
        color="#2F8265"
        class="w-8 h-8"
      />
    </button>
  </div>

  <button
    id="showElement"
    class="hidden fixed bottom-2 right-1/4"
    @click="toggle"
  >
    <font-awesome-icon
      icon="fa-solid fa-angle-up"
      size="3xl"
      color="#2F8265"
      class="w-8 h-8"
    />
  </button>
</template>
