
<script setup lang="ts">
//@ts-ignore
import { Orientation } from "@/services/editor/OrientationEnum";

import { onMounted, defineProps, ref, watch, computed } from "vue";
import {useMapUpdate} from "@/services/useMapUpdate"
import {useMap} from "@/services/useMap"
import type {MapInterface} from "@/services/MapInterface"
import { number } from "mathjs";
import EditorTile from "../../components/editor/EditorTile.vue";
import * as THREE from "three";


const props = defineProps({
    editorID: {
    default: 0,
    type: number
    } 
})

const {sendMapUpdates, receiveMapUpdates, mapUpdates} = useMapUpdate(props.editorID);
const {getMapEditor, saveMap} = useMap();

receiveMapUpdates(props.editorID);
const loadedMap = getMapEditor(props.editorID);

const mapWidth = ref(8)
const mapHeight = ref(8)
const offsetx = computed(() => (-(mapWidth.value + 1) / 2));
const offsety = computed(() => (-(mapHeight.value + 1) / 2));

const mapDefault :MapInterface = {
    tiles: [[{
            type: "Default",
            orientation: "NORTH",
            placedObjects: [],
          }]],
    NPCS: []
    }

const mapReactive = ref(mapDefault);

watch(mapUpdates.value,() =>{
      mapReactive.value = mapUpdates.value.map;
})



onMounted(() => {
    loadedMap.then((result) => mapReactive.value = {...result})
});

</script>

<template>
    <template v-for="(subTile, column) in mapReactive.tiles">
        <template v-for="(tile, row) in subTile" :key="tile">
            <EditorTile
            :width="0.99"
            :height="0.99"
            :position="new THREE.Vector3(row + offsetx + 1, column + offsety + 1, 0.01)"
            :rotation="new THREE.Vector3(0, 0, tile ? Orientation[tile.orientation] : 0)"
            :type="tile?tile.type:'Default'"
            >
            </EditorTile>
        </template>
    </template>
</template>