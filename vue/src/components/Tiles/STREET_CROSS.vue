<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { Vector3 } from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import { ObjectEnum } from "@/services/ObjectEnum";
import TRAFFIC_LIGHT from "../objects/TRAFFIC_LIGHT.vue";

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

const trafficLightPair1aOffset = new Vector3(-2, 0, 3);
const trafficLightPair2bOffset = new Vector3(3, 0, 2);
const trafficLightPair2aOffset = new Vector3(-3, 0, -2);
const trafficLightPair1bOffset = new Vector3(2, 0, -3);
</script>
<template>
  <GltfModel
    ref="model"
    src="/src/assets/models/STREET_CROSS.glb"
    :position="props.position"
    :rotation="props.rotation"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair1aOffset)"
    :rotation="new Vector3(0, 4.71, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair2bOffset)"
    :rotation="new Vector3(0, 0, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair2aOffset)"
    :rotation="new Vector3(0, 3.14, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair1bOffset)"
    :rotation="new Vector3(0, 1.57, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
</template>
