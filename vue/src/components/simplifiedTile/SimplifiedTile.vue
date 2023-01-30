<script setup lang="ts">
import {computed } from "vue";
import { useSpawnPoint } from "@/services/useSpawnPoint"
import { editorTileURLs } from "../editor/EditorTileURLDict";
/**
 * A maptile, that is used to build a the clickable spawnpoint map
 */

const { miniMapScalingState, setSpawnPoint, spawnState } = useSpawnPoint();

const emit = defineEmits<{
  (e: "setSpawnPoint"): void;
}>();

const props = withDefaults(
  defineProps<{
    tileType: string,
    orientation: string,
    xIndex: number,
    yIndex: number,
    asset: string
  }>(),
  {
    tileType: "GRASSTILE",
    orientation: "NORTH",
    xIndex: 0,
    yIndex: 0,
  }
);
const tileTexturePath =`url('${editorTileURLs[props.tileType]}')`;
const objectAsset = `url('${props.asset ? editorTileURLs[props.asset] : ""}')`;

/**
 * Uses the x and y coordinates of the tile, to set them as the new spawnpoint in the  spawnState
 */
function setSpawnPointEvent() {
  setSpawnPoint(props.xIndex, props.yIndex)
  emit("setSpawnPoint");
}

/**
 * Checks if this tile is the selected tile
 */
const isSelected = computed(() => {
  return (
    spawnState.tileNumber ==
    props.xIndex + 1 + props.yIndex * miniMapScalingState.numberOfRows
  );
});
</script>

<template>
  <div>
    <div id="simplifiedContainer" :class="{ selected: isSelected }" @click="setSpawnPointEvent()"></div>
    <div :class="[{ rotateSelected: isSelected }, { marker: isSelected }]"></div>
    <div id="objectAsset"></div>
    <div id="simplifiedTile" :class="[orientation, { selected: isSelected }]"></div>
  </div>
</template>

<style scoped>
#simplifiedContainer {
  position: absolute;
  box-sizing: border-box;
  height: v-bind("miniMapScalingState.boxSizing");
  width: v-bind("miniMapScalingState.boxSizing");
  z-index: 10;
}

#simplifiedTile {
  background-image: v-bind(tileTexturePath);
  box-sizing: border-box;
  background-repeat: no-repeat;
  background-size: contain;
  background-position: center;
  height: v-bind("miniMapScalingState.boxSizing");
  width: v-bind("miniMapScalingState.boxSizing");
  z-index: -2;
}

#objectAsset {
  position: absolute;
  background-image: v-bind(objectAsset);
  box-sizing: border-box;
  background-repeat: no-repeat;
  background-size: contain;
  background-position: center;
  height: v-bind("miniMapScalingState.boxSizing");
  width: v-bind("miniMapScalingState.boxSizing");
  z-index: 5;
}

.marker {
  height: v-bind("miniMapScalingState.boxSizing");
  width: v-bind("miniMapScalingState.boxSizing");
  border: none;
  position: absolute;
  background-color: rgb(105, 105, 105);
  clip-path: polygon(50% 0%, 0% 100%, 100% 100%);
  z-index: 10;
}

@keyframes innerGlow {
  from {
    box-shadow: inset 0 0 5px rgba(29, 29, 29, 0.388);
  }
  to {
    box-shadow: inset 0 0 50px rgba(196, 246, 18, 0.54);
  }
}

@keyframes rotateMarker {
  from {
    transform: rotate(180deg) scale(50%) rotateY(0deg)
      translateY(calc(v-bind("miniMapScalingState.boxSizing")));
  }
  to {
    transform: rotate(180deg) scale(50%) rotateY(180deg)
      translateY(calc(v-bind("miniMapScalingState.boxSizing") / 2));
  }
}

.rotateSelected {
  animation-name: rotateMarker;
  animation-iteration-count: infinite;
  animation-direction: alternate;
  animation-duration: 1s;
}

.selected {
  animation-name: innerGlow;
  animation-iteration-count: infinite;
  animation-direction: alternate;
  animation-duration: 1s;
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

.GRASSTILE {
  background-image: url("@/assets/images/tiles/GRASSTILE.jpg");
}

.SIDEWAY {
  background-image: url("@/assets/images/tiles/SIDEWAY.png");
}
.SIDEWAY_BICYCLE_CURVE {
  background-image: url("@/assets/images/tiles/SIDEWAY_BICYCLE_CURVE.png");
}
.SIDEWAY_PASSENGER_CURVE {
  background-image: url("@/assets/images/tiles/SIDEWAY_PASSENGER_CURVE.png");
}

.STREET_CROSS {
  background-image: url("@/assets/images/tiles/STREET_CROSS.jpg");
}

.STREET_CURVE {
  background-image: url("@/assets/images/tiles/STREET_CURVE.jpg");
}

.STREET_STRAIGHT {
  background-image: url("@/assets/images/tiles/STREET_STRAIGHT.jpg");
}

.STREET_T_CROSS {
  background-image: url("@/assets/images/tiles/STREET_T_CROSS.jpg");
}
</style>
