<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { ObjectEnum } from "@/enums/editor/ObjectEnum";
import { GltfModel } from "troisjs";
import TRAFFIC_LIGHT from "@/components/objects/TRAFFIC_LIGHT.vue";
import PEDESTRIAN_CROSSING_URL from "@/assets/models/PEDESTRIAN_CROSSING.glb?url";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    placedObject: any;
    orientation: string;
  }>(),
  { width: 10, height: 10 }
);

const trafficLightLeftOffset = new THREE.Vector3(3, 0, -0.5);
const trafficLightRightOffset = new THREE.Vector3(-3.5, 0, -1);
</script>
<template>
  <GltfModel
    ref="model"
    :src="PEDESTRIAN_CROSSING_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightLeftOffset)"
    :rotation="new THREE.Vector3(0, 0, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightRightOffset)"
    :rotation="new THREE.Vector3(0, 3, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
</template>
