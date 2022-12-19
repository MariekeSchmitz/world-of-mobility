<script setup lang="ts">
//@ts-ignore
import { orientations } from "@/services/Orientations";
import { onMounted, ref, watch, computed, toRef } from "vue";
import { useMapUpdate } from "@/services/useMapUpdate";
import { useMap } from "@/services/useMap";
import { number } from "mathjs";
import EditorTile from "../../components/editor/EditorTile.vue";
import * as THREE from "three";
import type { MapInterface } from "@/services/editor/MapInterface";

const props = defineProps({
  editorID: {
    default: 0,
    type: number,
  },
});

let editorID: number = props.editorID;

const { receiveMapUpdates, mapUpdates } = useMapUpdate(props.editorID);
const { getMapEditor } = useMap();

receiveMapUpdates(props.editorID);
const loadedMap = getMapEditor(props.editorID);

const mapWidth = ref(8);
const mapHeight = ref(8);
const offsetx = computed(() => -(mapWidth.value + 1) / 2);
const offsety = computed(() => -(mapHeight.value + 1) / 2);

const mapDefault: MapInterface = {
  tiles: [
    [
      {
        type: "Default",
        orientation: "NORTH",
        placedObject: { type: "TREE" },
      },
    ],
  ],
  NPCS: [],
};

const mapReactive = ref(mapDefault);

watch(mapUpdates.value, () => {
  mapReactive.value = mapUpdates.value.map;
});

onMounted(() => {
  loadedMap.then((result: MapInterface) => (mapReactive.value = result));
});
</script>

<template>
  <template v-for="(subTile, column) in mapReactive.tiles">
    <template v-for="(tile, row) in subTile" :key="tile">
      <div v-if="tile.placedObject.type !== null">
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
        >
        </EditorTile>
      </div>
    </template>
  </template>
</template>
