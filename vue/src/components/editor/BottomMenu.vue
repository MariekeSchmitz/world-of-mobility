<script setup lang="ts">
import { TileName } from "@/services/editor/TileNameEnum";
import { NaturObjectEnum } from "@/services/NaturObjectEnum";
import { ObjectEnum } from "@/services/ObjectEnum";

function scrollingLeft() {
  const boxwrapper = document.getElementById("box-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollLeft -= 200;
  }
}

function scrollingRight() {
  const boxwrapper = document.getElementById("box-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollLeft += 200;
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

  if (streetItems != null && componentItems != null && otherItems != null) {
    switch (element) {
      case "componentItems":
        streetItems.style.display = "none";
        componentItems.style.display = "block";
        otherItems.style.display = "none";
        break;
      case "otherItems":
        streetItems.style.display = "none";
        componentItems.style.display = "none";
        otherItems.style.display = "block";
        break;
      default:
        streetItems.style.display = "block";
        componentItems.style.display = "none";
        otherItems.style.display = "none";
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
  <div id="bottomMenu" class="menuOff">
    <div class="typeSelector">
      <button @click="switchContent('landscapeMenu')">
        Infrastruktur, Landschaft
      </button>
      <button @click="switchContent('npcMenu')">NPCs</button>
    </div>

    <div id="landscapeMenu">
      <div class="itemSelector">
        <button @click="switchItems('streetItems')">
          <img src="@/textures/editor/STREET_STRAIGHT.jpg" />
        </button>
        <button @click="switchItems('componentItems')">
          Natur/<br />Tiere
        </button>
        <button @click="switchItems('otherItems')">
          Gegenstände/<br />Tankstelle
        </button>
      </div>

      <!--
          für beliebige menge an tiles auswahl selbst erstellen lassen - src klappt nicht, wie angeben
        <div class="items" v-for="tileType in TileName">
          <p>{{tileType}}</p>
            <button @click="$emit('selectTile', tileType)"><img :props="{src: 'src/textures/editor/'+tileType+'.jpg'}"/></button>
        </div>
        -->

      <div id="itemList">
        <button id="scrollLeft" @mousedown="scrollingLeft">
          <img src="@/buttons/editor/arrow-left.png" />
        </button>

        <ul id="box-wrapper">
          <div id="streetItems">
            <li>
              <button
                class="itemButton"
                @click="$emit('selectTile', TileName.STREET_STRAIGHT)"
              >
                <img src="@/textures/editor/STREET_STRAIGHT.jpg" />
              </button>
            </li>
            <li>
              <button
                class="itemButton"
                @click="$emit('selectTile', TileName.STREET_CURVE)"
              >
                <img src="@/textures/editor/STREET_CURVE.jpg" />
              </button>
            </li>
            <li>
              <button
                class="itemButton"
                @click="$emit('selectTile', TileName.STREET_T_CROSS)"
              >
                <img src="@/textures/editor/STREET_T_CROSS.jpg" />
              </button>
            </li>
            <li>
              <button
                class="itemButton"
                @click="$emit('selectTile', TileName.STREET_CROSS)"
              >
                <img src="@/textures/editor/STREET_CROSS.jpg" />
              </button>
            </li>
            <li>
              <button
                class="itemButton"
                @click="$emit('selectTile', TileName.SIDEWAY)"
              >
                <img src="@/textures/editor/SIDEWAY.jpg" />
              </button>
            </li>
          </div>
          <!--
          <div
            id="componentItems"
            v-for="(valueEnum, naturEnum) in NaturObjectEnum"
            :key="`${valueEnum}`"
          >
            <li>
              <img
                :src="'@/assets/objekte/natur/' + valueEnum + '.png'"
                :alt="'@/assets/objekte/natur/' + valueEnum + '.png'"
              />
            </li>
          </div>
-->

          <div id="componentItems">
            <li>
              <button
                class="itemButton"
                @click="$emit('selectObject', NaturObjectEnum.TREE)"
              >
                <img src="@/assets/objekte/natur/baum.png" />
              </button>
            </li>
            <li>
              <button
                class="itemButton"
                @click="$emit('selectObject', NaturObjectEnum.BUSH)"
              >
                <img src="@/assets/objekte/natur/busch.png" />
              </button>
            </li>
            <li>
              <button
                class="itemButton"
                @click="$emit('selectObject', NaturObjectEnum.HORSE)"
              >
                <img src="@/assets/objekte/natur/pferd.png" />
              </button>
            </li>
            <li>
              <button
                class="itemButton"
                @click="$emit('selectObject', NaturObjectEnum.COW)"
              >
                <img src="@/assets/objekte/natur/kuh.png" />
              </button>
            </li>
          </div>

          <div id="otherItems">
            <li>
              <button
                class="itemButton"
                @click="$emit('selectObject', ObjectEnum)"
              >
                <img src="@/assets/objekte/gegenstaende/tankstelle.png" />
              </button>
            </li>
          </div>
        </ul>

        <button id="scrollRight" @click="scrollingRight">
          <img src="@/buttons/editor/arrow-right.png" />
        </button>
      </div>
    </div>

    <button id="hideElement" @click="toggle">
      <img src="@/buttons/editor/arrow-down.png" />
    </button>

    <div id="npcMenu"></div>
  </div>
  <button id="showElement" @click="toggle">
    <img src="@/buttons/editor/arrow-up.png" />
  </button>
</template>

<style>
#bottomMenu {
  display: grid;
  grid-template-columns: 20% 75% 5%;
  position: fixed;
  width: 50%;
  height: 14%;
  background-color: rgb(221, 221, 221);
  padding: 10px;
  bottom: 0px;
  left: 10%;
}

#landscapeMenu {
  display: grid;
  grid-template-columns: 20% 80%;
}

#npcMenu {
  display: grid;
  display: grid;
  grid-template-columns: 20% 80%;
}

#showElement {
  display: none;
  position: fixed;
  bottom: 2%;
  left: 58%;
}

.typeSelector {
  display: grid;
  grid-template-rows: 1fr 1fr;
}

.itemSelector {
  display: grid;
  grid-template-rows: 1fr 1fr 1fr;
}

#itemList {
  display: grid;
  grid-template-columns: 10% 80% 10%;
  margin: auto 0;
}

.itemButton > img {
  width: 80px;
  height: 80px;
}

#componentItems,
#otherItems {
  display: none;
}

ul {
  height: 100%;
  width: 85%;
  overflow-y: hidden;
  overflow-x: hidden;
  white-space: nowrap;
}

li {
  list-style-type: none;
  display: inline-block;
  background-color: lightblue;
  margin: 0 5px;
}
</style>
