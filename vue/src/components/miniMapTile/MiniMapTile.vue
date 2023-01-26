<template>
    <div id="simplifiedContainer">
        <div id="objectAsset"></div>
        <div id="simplifiedTile" :class="[orientation]"></div>
    </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from "vue";
import { editorTileURLs } from "../editor/EditorTileURLDict";

const props = withDefaults(
defineProps<{
    tileType: string,
    orientation: string,
    xIndex: number,
    yIndex: number,
    asset: string,
    user: string,
    rowCount: number
}>(),
{
    tileType: "GRASSTILE",
    orientation: "NORTH",
    xIndex: 0,
    yIndex: 0,
    rowCount: 0
}
);
const tileTexturePath =`url('${editorTileURLs[props.tileType]}')`;
const objectAsset = `url('${props.asset ? editorTileURLs[props.asset] : ""}')`;
const tileSize = `${100 / props.rowCount}%`;
</script>

<style scoped>
#simplifiedContainer {
    position: absolute;
    box-sizing: border-box;
    height: v-bind(tileSize);
    width: v-bind(tileSize);
    z-index: 10;
}

#simplifiedTile {
    background-image: v-bind(tileTexturePath);
    box-sizing: border-box;
    background-repeat: no-repeat;
    background-size: contain;
    background-position: center;
    width: 100%;
    height: 100%;
    z-index: -2;
}

#objectAsset {
    position: absolute;
    background-image: v-bind(objectAsset);
    box-sizing: border-box;
    background-repeat: no-repeat;
    background-size: contain;
    background-position: center;
    width: 100%;
    height: 100%;
    z-index: 5;
}

.NORTH {
    transform: rotate(0);
}

.EAST {
    transform: rotate(90deg);
}

.SOUTH {
    transform: rotate(180deg);
}

.WEST {
    transform: rotate(270deg);
}

</style>
  