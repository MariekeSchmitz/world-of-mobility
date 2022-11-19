<template>
  <div>
    <SIDEWAY
      v-if="props.type === sideway"
      :width="props.heigth"
      :height="props.width"
      :posX="props.posX"
      :posY="props.posY"
      :posZ="props.posZ"
      :rotationX="props.rotationX"
      :rotationY="props.rotationY"
      :rotationZ="props.rotationZ"
      :type="props.type"
    >
    </SIDEWAY>

    <STREET_CURVE
      v-if="props.type === street_curve"
      :width="props.heigth"
      :height="props.width"
      :posX="props.posX"
      :posY="props.posY"
      :posZ="props.posZ"
      :rotationX="props.rotationX"
      :rotationY="props.rotationY"
      :rotationZ="props.rotationZ"
      :type="props.type"
    >
    </STREET_CURVE>
  </div>
</template>

<script setup lang="ts">
//--------imports-----------
import SIDEWAY from "./Tiles/SIDEWAY.vue";
import STREET_CURVE from "./Tiles/STREET_CURVE.vue";
import { defineProps, withDefaults } from "vue";

//--------props-------------
// technical debt: no use of enums
const sideway = "SIDEWAY";
const street_curve = "STREET_CURVE";

// rotation: to translate our 'orientation' to a matching Tile, we need to pass a multiple 'Math.PI / 2' to our rotationZ value;
const props = withDefaults(
  defineProps<{
    width: number;
    heigth: number;
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
</script>
