<script setup lang="ts">
//@ts-ignore
import { orientations } from "@/services/Orientations";
import { onMounted, ref, watch, computed } from "vue";
import { useMapUpdate } from "@/services/useMapUpdate";
import { useMap } from "@/services/useMap";
import { number } from "mathjs";
import EditorTile from "@/components/editor/EditorTile.vue";
import * as THREE from "three";
import type { MapInterface } from "@/services/editor/MapInterface";
import type { INpc } from "@/interfaces/INpc";

const props = withDefaults(
  defineProps<{
    editorID: number;
  }>(),
  { editorID: 0 }
);

const { receiveMapUpdates, mapUpdates } = useMapUpdate(props.editorID);
const { getMapEditor } = useMap();

receiveMapUpdates(props.editorID);
const loadedMap = getMapEditor(props.editorID);

const mapWidth = ref(8);
const mapHeight = ref(8);
const offsetx = computed(() => -(mapWidth.value + 1) / 2);
const offsety = computed(() => -(mapHeight.value + 1) / 2);

const mapDefault: MapInterface = {
  name: "Default",
  tiles: [
    [
      {
        type: "GRASSTILE",
        orientation: "NORTH",
        placedObject: { type: "TREE" },
      },
    ],
  ],
  npcs: [],
};

const mapReactive = ref(mapDefault);

/**
 * checks whether there is a npc on specific tile position
 * @param x x coordinate of tile
 * @param y y coordinate of tile
 * @returns npc (undefined if none is found)
 */
function findNpc(x: number, y: number): INpc | undefined {
  const npc: INpc | undefined = mapReactive.value.npcs.find((npc: INpc) => {
    return npc.xPos === x && npc.yPos === y;
  });

  return npc;
}

watch(mapUpdates.value, () => {
  mapReactive.value = mapUpdates.value.map;
});

onMounted(() => {
  loadedMap.then((result: MapInterface) => (mapReactive.value = result));
});
</script>

<template>
  <template v-for="(subTile, column) in mapReactive.tiles">
    <template v-for="(tile, row) in subTile" :key="tile.type">
      <div v-if="tile.placedObject !== null">
        <EditorTile
          :width="0.99"
          :height="0.99"
          :position="
            new THREE.Vector3(row + offsetx + 1, column + offsety + 1, 0.01)
          "
          :rotation="new THREE.Vector3(0, 0, -orientations[tile.orientation])"
          :type="tile.type"
          :placedObject="tile.placedObject.type"
          :editorID="editorID"
          :cmVisible="false"
          :placedNpc="findNpc(row, column)"
          @npc-added="$emit('npc-added', $event)"
        >
        </EditorTile>
      </div>
      <div v-else>
        <EditorTile
          :width="0.99"
          :height="0.99"
          :position="
            new THREE.Vector3(row + offsetx + 1, column + offsety + 1, 0.01)
          "
          :rotation="new THREE.Vector3(0, 0, -orientations[tile.orientation])"
          :type="tile.type"
          placedObject="none"
          :editorID="editorID"
          :cmVisible="false"
          :placedNpc="findNpc(row, column)"
          @npc-added="$emit('npc-added', $event)"
        >
        </EditorTile>
      </div>
    </template>
  </template>
</template>
