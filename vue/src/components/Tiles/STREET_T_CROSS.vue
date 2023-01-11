<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { Vector3 } from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import TRAFFIC_LIGHT from "../objects/TRAFFIC_LIGHT.vue";
import { ObjectEnum } from "@/services/ObjectEnum";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: Vector3;
    rotation: Vector3;
    type: string;
    placedObject: any;
    orientation: string;
  }>(),
  { width: 10, height: 10 }
);
let trafficLightLeftOffset = new Vector3(0, 0, 0);
let trafficLightRightOffset = new Vector3(0, 0, 0);
let trafficLightStraightOffset = new Vector3(0, 0, 0);

function setPosition(orientation: String) {
  if (orientation === "WEST") {
    trafficLightLeftOffset = new Vector3(7, 0, 2);
    trafficLightRightOffset = new Vector3(1, 0, -3);
    trafficLightStraightOffset = new Vector3(-1, 0, 4.5);
  }
}
</script>
<template>
  <GltfModel
    ref="model"
    src="/src/assets/models/STREET_T_CROSS.glb"
    :position="props.position"
    :rotation="props.rotation"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightLeftOffset)"
    :rotation="new Vector3(0, 0, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightRightOffset)"
    :rotation="new Vector3(0, 1.5, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightStraightOffset)"
    :rotation="new Vector3(0, 4.5, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
</template>
