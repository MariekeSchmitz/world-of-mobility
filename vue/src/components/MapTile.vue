<template>
  <tileType> </tileType>
</template>

<script setup lang="ts">
//--------imports-----------
import SIDEWAY from "./Tiles/SIDEWAY.vue";
import STREET_CURVE from "./Tiles/STREET_CURVE.vue";
import { defineProps, withDefaults } from "vue";

//--------props-------------

// rotation: to translate our 'orientation' to a matching Tile, we need to pass a multiple 'Math.PI / 2' to our rotationZ value;
const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    posX: number;
    posY: number;
    posZ: number;
    rotationX: number;
    rotationY: number;
    rotationZ: number;
    type: string;
  }>(),
  { width: 10, heigth: 10 }
);

//--------code------------

let tileType: any = null;

switch (props.type) {
  case "SIDEWAY":
    tileType = SIDEWAY;
    break;
  case "STREET_CURVE":
    tileType = STREET_CURVE;
    break;
  default:
    console.log("type not found");
}
</script>
