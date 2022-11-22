<template>
  <!-- Algorythm to build the map -->
  <div v-for="(subTile, index) in obj.tiles" :key="`row-${index}`">
    <div v-for="tile in subTile" :key="tile.type">
      <MapTile
        :width="squareSize"
        :heigth="squareSize"
        :posX="tempWidth"
        :posY="tempHeigth"
        :posZ="0"
        :rotationX="0"
        :rotationY="0"
        :rotationZ="computeRotationZ(tile.orientation)"
        :type="tile.type"
      >
      </MapTile>
      {{ (tempWidth += squareSize) }}
    </div>
    {{ (tempWidth = 0) }}
    {{ (tempHeigth -= squareSize) }}
  </div>
</template>

<script setup lang="ts">
import MapTile from "./MapTile.vue";

let squareSize = 10;
let tempWidth = 0;
let tempHeigth = 0;
/**
 * translates the given orientation to a number
 * @param orientation the orientation of the object to rotate
 */
function computeRotationZ(orientation: string): number {
  const quarterTurn = Math.PI / 2;
  switch (orientation) {
    case "NORTH":
      return 0 * quarterTurn;
    case "EAST":
      return 1 * quarterTurn;
    case "SOUTH":
      return 2 * quarterTurn;
    case "WEST":
      return 3 * quarterTurn;
    default:
      return 0;
  }
}

const obj = {
  tiles: [
    [
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
    ],
    [
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "STREET_CURVE",
        orientation: "EAST",
        placedObjects: [],
      },
      {
        type: "STREET_CURVE",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
    ],
    [
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "STREET_CURVE",
        orientation: "SOUTH",
        placedObjects: [],
      },
      {
        type: "STREET_CURVE",
        orientation: "WEST",
        placedObjects: [],
      },
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
    ],
    [
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
      {
        type: "SIDEWAY",
        orientation: "NORTH",
        placedObjects: [],
      },
    ],
  ],
  NPCs: [
    {
      user: "NPC1",
      xPos: 0,
      yPos: 0,
      classname: "car",
    },
  ],
};
</script>
