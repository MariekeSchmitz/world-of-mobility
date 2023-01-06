<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { ObjectEnum } from "@/services/ObjectEnum";
import { Vector3 } from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import TRAFFIC_LIGHT from "../objects/TRAFFIC_LIGHT.vue";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: Vector3;
    rotation: Vector3;
    type: string;
    placedObject: any;
  }>(),
  { width: 10, height: 10 }
);

const trafficLightLeftOffset = new Vector3(3, 0, -0.5);
const trafficLightRightOffset = new Vector3(-3.5, 0, -1);
</script>
<template>
  <GltfModel
    ref="model"
    src="/src/assets/models/STREET_STRAIGHT_CROSSWALK.glb"
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
    :rotation="new Vector3(0, 3, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
</template>
