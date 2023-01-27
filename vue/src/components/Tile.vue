<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import type * as THREE from "three";
import SIDEWAY from "@/components/Tiles/SIDEWAY.vue";
import SIDEWAY_BICYCLE_CURVE from "@/components/Tiles/SIDEWAY_BICYCLE_CURVE.vue";
import SIDEWAY_PASSENGER_CURVE from "@/components/Tiles/SIDEWAY_PASSENGER_CURVE.vue";
import PEDESTRIAN_CROSSING from "@/components/Tiles/PEDESTRIAN_CROSSING.vue";
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
    trafficLightState?: any;
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
  <SIDEWAY_BICYCLE_CURVE
    v-if="props.type === tileType.SIDEWAY_BICYCLE_CURVE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </SIDEWAY_BICYCLE_CURVE>
  <SIDEWAY_PASSENGER_CURVE
    v-if="props.type === tileType.SIDEWAY_PASSENGER_CURVE"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </SIDEWAY_PASSENGER_CURVE>

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
    :traffic-light-state="props.trafficLightState"
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
    :trafficLightState="props.trafficLightState"
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
    :trafficLightState="props.trafficLightState"
  >
  </STREET_T_CROSS>
  <PEDESTRIAN_CROSSING
    v-if="props.type === tileType.PEDESTRIAN_CROSSING"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
    :type="props.type"
    :placedObject="props.placedObject"
    :orientation="props.orientation"
  >
  </PEDESTRIAN_CROSSING>
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
    :placedObject="props.placedObject"
    :orientation="props.orientation"
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
