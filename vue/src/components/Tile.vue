<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import type * as THREE from "three";
import SIDEWAY from "./Tiles/SIDEWAY.vue";
import STREET_CURVE from "./Tiles/STREET_CURVE.vue";
import STREET_STRAIGHT from "./Tiles/STREET_STRAIGHT.vue";
import STREET_CROSS from "./Tiles/STREET_CROSS.vue";
import STREET_T_CROSS from "./Tiles/STREET_T_CROSS.vue";
import PEDESTRIAN_CROSSING from "./Tiles/PEDESTRIAN_CROSSING.vue";
import RAIL_CURVE from "./Tiles/RAIL_CURVE.vue";
import RAIL_STRAIGHT from "./Tiles/RAIL_STRAIGHT.vue";
import GRASSTILE from "./Tiles/GRASSTILE.vue";
import { tileType } from "./TileTypes";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
  }>(),
  { width: 10, height: 10 }
);
</script>
<template>
  <SIDEWAY
    v-if="props.type === tileType.SIDEWAY"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </SIDEWAY>

  <STREET_CURVE
    v-if="props.type === tileType.STREET_CURVE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </STREET_CURVE>

  <STREET_STRAIGHT
    v-if="props.type === tileType.STREET_STRAIGHT"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </STREET_STRAIGHT>

  <STREET_CROSS
    v-if="props.type === tileType.STREET_CROSS"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </STREET_CROSS>

  <STREET_T_CROSS
    v-if="props.type === tileType.STREET_T_CROSS"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </STREET_T_CROSS>
  <PEDESTRIAN_CROSSING
    v-if="props.type === tileType.PEDESTRIAN_CROSSING"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </PEDESTRIAN_CROSSING>
  <RAIL_CURVE
    v-if="props.type === tileType.RAIL_CURVE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </RAIL_CURVE>

  <RAIL_STRAIGHT
    v-if="props.type === tileType.RAIL_STRAIGHT"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </RAIL_STRAIGHT>
  <GRASSTILE
    v-if="props.type === tileType.GRASSTILE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </GRASSTILE>
</template>
