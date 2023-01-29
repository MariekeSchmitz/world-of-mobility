<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import Tile from "@/components/Tiles/Tile.vue";
import type { I3DMap } from "@/interfaces/game/I3DMap";
import { onMounted, ref } from "vue";
import { useMap } from "@/services/game/useMap";
import Enviroment from "@/components/enviroment/Enviroment.vue";

const { getGameMap } = useMap();

const defaultMap: I3DMap = {
  //@ts-ignore
  tiles: [[]],
  NPCS: undefined,
};

const props = withDefaults(
  defineProps<{
    squareSize: number;
    instanceID: number;
    trafficLightState: string;
  }>(),
  { instanceID: 1 }
);

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
      vector3.set(0, 2 * quarterTurn, 0);
      break;
    case "EAST":
      vector3.set(0, 1 * quarterTurn, 0);
      break;
    case "SOUTH":
      vector3.set(0, 0 * quarterTurn, 0);
      break;
    case "WEST":
      vector3.set(0, 3 * quarterTurn, 0);
      break;
    default:
      return vector3;
  }
  return vector3;
}
onMounted(async () => {
  loadedMap.value = await getGameMap(props.instanceID);
});
</script>
<template>
  <!-- Loop to build the map -->
  <Enviroment
    :position="
      new THREE.Vector3(
        loadedMap.tiles.length * squareSize * 0.5,
        0,
        -loadedMap.tiles[0].length * squareSize * 0.5
      )
    "
    :scale="loadedMap.tiles.length * squareSize"
    :rotation="0"
  ></Enviroment>
  <div v-for="(subTile, row) in loadedMap.tiles" :key="`${row}`">
    <div v-for="(tile, column) in subTile" :key="`${tile}`">
      <Tile
        :width="props.squareSize"
        :height="props.squareSize"
        :position="
          new THREE.Vector3(
            column * props.squareSize + 0.5 * props.squareSize,
            tile.positionY,
            -row * props.squareSize - 0.5 * props.squareSize
          )
        "
        :rotation="computeVector3(tile.orientation)"
        :type="tile.type"
        :orientation="tile.orientation"
        v-if="tile.placedObject !== null"
        :placed-object="tile.placedObject.type"
        :traffic-light-state="props.trafficLightState"
      >
      </Tile>
      <Tile
        v-if="tile.placedObject === null"
        :width="props.squareSize"
        :height="props.squareSize"
        :position="
          new THREE.Vector3(
            column * props.squareSize + 0.5 * props.squareSize,
            tile.positionY,
            -row * props.squareSize - 0.5 * props.squareSize
          )
        "
        :rotation="computeVector3(tile.orientation)"
        :type="tile.type"
        :orientation="tile.orientation"
      >
      </Tile>
    </div>
  </div>
</template>
