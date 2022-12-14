<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import Tile from "@/components/Tile.vue";
import type { I3DMap } from "@/services/I3DMap";
import type { ITile } from "@/interfaces/ITile";
import { computed, onMounted, reactive, ref } from "vue";
import { useMap } from "@/services/useMap";

const squareSize = 10;
const { getGameMap } = useMap();

const defaultMap: I3DMap = {
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

const props = withDefaults(
  defineProps<{
    instanceID: number;
  }>(),
  { instanceID: 1 }
);

const mapReactive = reactive({
  value:defaultMap
})

let loadedMap = ref(defaultMap);

//const mapReactive: I3DMap = defaultMap;
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
}onMounted(async()=>{

  loadedMap.value= await getGameMap(props.instanceID);
})
</script>
<template>
  <!-- Loop to build the map -->
  <div v-for="(subTile, row) in loadedMap.tiles" >
    <div v-for="(tile, column) in subTile" :key="`${tile}`">
      <Tile
        :width="squareSize"
        :height="squareSize"
        :position="
          new THREE.Vector3(
            column * squareSize,
            tile.positionY,
            row * squareSize
          )
        "
        :rotation="computeVector3(tile.orientation)"
        :type="tile.type"
        v-if="tile != null"
      >
      </Tile>
    </div>
  </div>
</template>
