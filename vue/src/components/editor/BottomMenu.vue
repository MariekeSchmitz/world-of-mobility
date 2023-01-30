<script setup lang="ts">
import { TileName } from "@/services/editor/TileNameEnum";
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
  faAngleUp,
  faXmark,
  faRoad,
  faCar,
  faTree,
  faTrafficLight,
  faPersonWalking,
} from "@fortawesome/free-solid-svg-icons";
library.add(
  faAngleRight,
  faAngleLeft,
  faAngleUp,
  faXmark,
  faRoad,
  faCar,
  faTree,
  faTrafficLight,
  faPersonWalking
);

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
    class="bg-whiteOverlay grid grid-cols-[25%_70%_5%] w-3/5 h-1/5 py-3 px-6 fixed bottom-0 left-[20%] font-poppins"
  >
    <fieldset class="grid grid-rows-2 editorLabel text-greenDark">
      <label class="group grid grid-cols-[30%_70%] items-center">
        <input
          type="radio"
          name="selectMenu"
          checked
          @click="switchContent('landscapeMenu')"
          class="hidden peer"
        />
        <font-awesome-icon
          icon="fa-solid fa-road"
          color="#2F8265 "
          class="w-5 h-5 bg-white rounded-full p-2 mr-1 justify-self-end peer-checked:text-orange group-hover:bg-orangeLight"
        />
        <div class="pl-2 text-left peer-checked:text-orange">
          Infrastruktur,<br />
          Landschaft
        </div>
      </label>
      <label class="group grid grid-cols-[30%_70%] items-center">
        <input
          type="radio"
          name="selectMenu"
          @click="switchContent('npcMenu')"
          class="hidden peer"
        />
        <font-awesome-icon
          icon="fa-solid fa-car"
          color="#2F8265"
          class="w-5 h-5 bg-white rounded-full p-2 mr-1 justify-self-end peer-checked:text-orange group-hover:bg-greenLight"
        />
        <div class="pl-2 text-left peer-checked:text-orange">NPCs</div>
      </label>
    </fieldset>

    <div class="bg-white pr-5 py-5">
      <div id="landscapeMenu" class="grid grid-cols-[15%_85%] h-full">
        <fieldset class="grid grid-rows-3">
          <label class="group grid items-center">
            <input
              type="radio"
              name="selectObject"
              checked
              class="hidden peer"
              @click="switchItems('streetItems')"
            />
            <font-awesome-icon
              icon="fa-solid fa-road"
              color="#2F826575"
              class="w-5 h-5 justify-self-center peer-checked:text-orange group-hover:text-orange"
            />
          </label>
          <label class="group grid items-center">
            <input
              type="radio"
              name="selectObject"
              class="hidden peer"
              @click="switchItems('componentItems')"
            />
            <font-awesome-icon
              icon="fa-solid fa-tree"
              color="#2F826575"
              class="w-5 h-5 justify-self-center peer-checked:text-orange group-hover:text-orange"
            />
          </label>
          <label class="group grid items-center">
            <input
              type="radio"
              name="selectObject"
              class="hidden peer"
              @click="switchItems('otherItems')"
            />
            <font-awesome-icon
              icon="fa-solid fa-traffic-light"
              color="#2F826575"
              class="w-5 h-5 justify-self-center peer-checked:text-orange group-hover:text-orange"
            />
          </label>
        </fieldset>

        <div class="grid grid-cols-[10%_80%_10%] my-auto">
          <button id="scrollLeft" @mousedown="scrollingLeft">
            <font-awesome-icon
              icon="fa-solid fa-angle-left"
              color="#2F8265"
              class="w-8 h-8"
            />
          </button>

          <ul
            id="box-wrapper"
            class="h-full whitespace-nowrap overflow-hidden"
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
              color="#2F8265"
              class="w-8 h-8 hover:text-greenLight"
            />
          </button>
        </div>
      </div>

      <div id="npcMenu" class="h-full hidden grid-cols-[15%_85%]">
        <fieldset class="h-full grid grid-rows-2">
          <label class="group grid items-center">
            <input
              type="radio"
              name="selectNpc"
              checked
              class="hidden peer"
              @click="switchItems('motorized')"
            />
            <font-awesome-icon
              icon="fa-solid fa-car"
              color="#2F826575"
              class="w-5 h-5 justify-self-center peer-checked:text-orange group-hover:text-orange"
            />
          </label>
          <label class="group grid items-center">
            <input
              type="radio"
              name="selectNpc"
              class="hidden peer"
              @click="switchItems('passenger')"
            />
            <font-awesome-icon
              icon="fa-solid fa-person-walking"
              color="#2F826575"
              class="w-6 h-6 justify-self-center peer-checked:text-orange group-hover:text-orange"
            />
          </label>
        </fieldset>

        <div class="grid grid-cols-[10%_80%_10%] my-auto">
          <button id="scrollLeft" @mousedown="scrollingLeft">
            <font-awesome-icon
              icon="fa-solid fa-angle-left"
              color="#2F8265"
              class="w-8 h-8 hover:text-greenLight"
            />
          </button>

          <ul
            id="box-wrapper-npc"
            class="h-full whitespace-nowrap overflow-hidden"
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

          <button id="scrollRight" @click="scrollingRight" class="group">
            <font-awesome-icon
              icon="fa-solid fa-angle-right"
              color="#2F8265"
              class="w-8 h-8 group-hover:text-greenLight"
            />
          </button>
        </div>
      </div>
    </div>

    <button id="hideElement" @click="toggle" class="group grid items-start">
      <font-awesome-icon
        icon="fa-solid fa-xmark"
        color="#2F8265"
        class="w-6 h-6 justify-self-end group-hover:text-greenLight"
      />
    </button>
  </div>

  <button
    id="showElement"
    class="editorLabel hidden fixed bottom-8 right-[20%] text-greenDark"
    @click="toggle"
  >
    <font-awesome-icon
      icon="fa-solid fa-angle-up"
      color="#2f8265"
      class="w-5 h-5"
    /><br />
    Welt bearbeiten
  </button>
</template>
