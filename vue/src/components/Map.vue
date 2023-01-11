<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import Tile from "@/components/Tile.vue";
import type { I3DMap } from "@/services/I3DMap";
import type { ITile } from "@/interfaces/ITile";
import { computed, onMounted, reactive, ref } from "vue";
import { useMap } from "@/services/useMap";

const squareSize = 16;
const { getGameMap } = useMap();

const defaultMap: I3DMap = {
  //@ts-ignore
  tiles: [[]],
  NPCS: undefined,
};

const props = withDefaults(
  defineProps<{
    instanceID: number;
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
      vector3.set(0, 0 * quarterTurn, 0);
      break;
    case "EAST":
      vector3.set(0, 1 * quarterTurn, 0);
      break;
    case "SOUTH":
      vector3.set(0, 2 * quarterTurn, 0);
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
  console.log(loadedMap.value);
});
</script>
<template>
  <!-- Loop to build the map -->
  <div v-for="(subTile, row) in loadedMap.tiles" :key="`${row}`">
    <div v-for="(tile, column) in subTile" :key="`${tile}`">
      <Tile
        :width="squareSize"
        :height="squareSize"
        :position="
          new THREE.Vector3(
            column * squareSize + 0.5 * squareSize,
            tile.positionY,
            -row * squareSize - 0.5 * squareSize
          )
        "
        :rotation="computeVector3(tile.orientation)"
        :type="tile.type"
        v-if="tile.placedObject !== null"
        :placed-object="tile.placedObject.type"
      >
      </Tile>
      <Tile
        v-if="tile.placedObject === null"
        :width="squareSize"
        :height="squareSize"
        :position="
          new THREE.Vector3(
            column * squareSize + 0.5 * squareSize,
            tile.positionY,
            -row * squareSize - 0.5 * squareSize
          )
        "
        :rotation="computeVector3(tile.orientation)"
        :type="tile.type"
      >
      </Tile>
    </div>
  </div>
</template>
