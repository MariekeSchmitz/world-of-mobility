<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import Tile from "@/components/Tile.vue";
import type { IMap } from "@/interfaces/IMap";
import type { ITile } from "@/interfaces/ITile";

const squareSize = 10;

const obj: IMap = {
  //@ts-ignore
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
/**
 * translates the orientation as string into an acutal vector.
 * @param orientation the orientation of a tile
 */
function computeVector3(orientation: string): THREE.Vector3 {
  const quarterTurn = Math.PI / 2;
  let vector3 = new THREE.Vector3(0, 0, 0);
  switch (orientation) {
    case "NORTH":
      vector3.set(-quarterTurn, 0 * quarterTurn, 0);
      break;
    case "EAST":
      vector3.set(-quarterTurn, 0, 1 * quarterTurn);
      break;
    case "SOUTH":
      vector3.set(-quarterTurn, 0, 2 * quarterTurn);
      break;
    case "WEST":
      vector3.set(-quarterTurn, 0, 3 * quarterTurn);
      break;
    default:
      return vector3;
  }
  return vector3;
}
/**
 * prepares the map for our causes.
 * generates and sets an x and y position for every Tile.
 * @param map the fetched map
 */
function modifyMap(map: IMap) {
  let tempZ = 0;
  map.tiles.forEach((subArray: ITile[]) => {
    let tempX = 0;
    subArray.forEach((tile: ITile) => {
      tile.positionX = tempX;
      tile.positionZ = tempZ;
      tempX += squareSize;
    });
    tempZ += squareSize;
  });
}

modifyMap(obj);
</script>
<template>
  <!-- Loop to build the map -->
  <div v-for="(subTile, index) in obj.tiles" :key="`row-${index}`">
    <div v-for="tile in subTile" :key="tile.type">
      <Tile
        :width="squareSize"
        :height="squareSize"
        :position="new THREE.Vector3(tile.positionX, 0, tile.positionZ)"
        :rotation="computeVector3(tile.orientation)"
        :type="tile.type"
      >
      </Tile>
    </div>
  </div>
</template>
