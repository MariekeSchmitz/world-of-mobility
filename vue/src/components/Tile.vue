<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import type * as THREE from "three";
import SIDEWAY_THREE from "./TilesTHREE/SIDEWAY_THREE.vue";
import STREET_CURVE_THREE from "./TilesTHREE/STREET_CURVE_THREE.vue";
import STREET_STRAIGHT_THREE from "./TilesTHREE/STREET_STRAIGHT_THREE.vue";
import STREET_CROSS_THREE from "./TilesTHREE/STREET_CROSS_THREE.vue";
import STREET_T_CROSS_THREE from "./TilesTHREE/STREET_T_CROSS_THREE.vue";
import RAIL_CURVE_THREE from "./TilesTHREE/RAIL_CURVE_THREE.vue";
import RAIL_STRAIGHT_THREE from "./TilesTHREE/RAIL_STRAIGHT_THREE.vue";
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
  <SIDEWAY_THREE
    v-if="props.type === tileType.SIDEWAY"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </SIDEWAY_THREE>

  <STREET_CURVE_THREE
    v-if="props.type === tileType.STREET_CURVE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </STREET_CURVE_THREE>

  <STREET_STRAIGHT_THREE
    v-if="props.type === tileType.STREET_STRAIGHT"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </STREET_STRAIGHT_THREE>

  <STREET_CROSS_THREE
    v-if="props.type === tileType.STREET_CROSS"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </STREET_CROSS_THREE>

  <STREET_T_CROSS_THREE
    v-if="props.type === tileType.STREET_T_CROSS"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </STREET_T_CROSS_THREE>

  <RAIL_CURVE_THREE
    v-if="props.type === tileType.RAIL_CURVE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </RAIL_CURVE_THREE>

  <RAIL_STRAIGHT_THREE
    v-if="props.type === tileType.RAIL_STRAIGHT"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </RAIL_STRAIGHT_THREE>
</template>
