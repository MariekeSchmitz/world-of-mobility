<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import type * as THREE from "three";
import SIDEWAY from "@/components/Tiles/SIDEWAY.vue";
import STREET_CURVE from "@/components/Tiles/STREET_CURVE.vue";
import STREET_STRAIGHT from "@/components/Tiles/STREET_STRAIGHT.vue";
import STREET_CROSS from "@/components/Tiles/STREET_CROSS.vue";
import STREET_T_CROSS from "@/components/Tiles/STREET_T_CROSS.vue";
import RAIL_CURVE from "@/components/Tiles/RAIL_CURVE.vue";
import RAIL_STRAIGHT from "@/components/Tiles/RAIL_STRAIGHT.vue";
import FARM from "@/components/Tiles/FARM.vue";
import GRASSTILE from "@/components/Tiles/GRASSTILE.vue";
import { tileType } from "@/components/TileTypes";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    placedObject?: any;
    orientation: string;
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
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </SIDEWAY>

  <STREET_CURVE
    v-if="props.type === tileType.STREET_CURVE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </STREET_CURVE>

  <STREET_STRAIGHT
    v-if="props.type === tileType.STREET_STRAIGHT"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </STREET_STRAIGHT>

  <STREET_CROSS
    v-if="props.type === tileType.STREET_CROSS"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </STREET_CROSS>

  <STREET_T_CROSS
    v-if="props.type === tileType.STREET_T_CROSS"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </STREET_T_CROSS>

  <RAIL_CURVE
    v-if="props.type === tileType.RAIL_CURVE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </RAIL_CURVE>

  <RAIL_STRAIGHT
    v-if="props.type === tileType.RAIL_STRAIGHT"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </RAIL_STRAIGHT>
  <FARM
    v-if="props.type === tileType.FARM"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
  >
  </FARM>
  <GRASSTILE
    v-if="props.type === tileType.GRASSTILE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </GRASSTILE>
</template>
