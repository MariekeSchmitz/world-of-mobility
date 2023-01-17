<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import { ObjectEnum } from "@/services/ObjectEnum";
import TRAFFIC_LIGHT from "@/components/objects/TRAFFIC_LIGHT.vue";
import STREET_CROSS_URL from "@/assets/models/STREET_CROSS.glb?url";
import TRAFFIC_LIGHT_LIGHT from "@/components/objects/TRAFFIC_LIGHT_LIGHT.vue";

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

const trafficLightPair1aOffset = new THREE.Vector3(-6.5, 0, 6.5);
const trafficLightPair2bOffset = new THREE.Vector3(6.5, 0, 6.5);
const trafficLightPair2aOffset = new THREE.Vector3(-6.5, 0, -6.5);
const trafficLightPair1bOffset = new THREE.Vector3(6.5, 0, -6.5);
</script>
<template>
  <GltfModel
    ref="model"
    :src="STREET_CROSS_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair1aOffset)"
    :rotation="new THREE.Vector3(0, 4.71, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair1aOffset)"
    :angle="3"
    :red="true"
    :yellow="false"
    :green="false"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair2bOffset)"
    :rotation="new THREE.Vector3(0, 0, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair2bOffset)"
    :angle="3"
    :red="false"
    :yellow="false"
    :green="true"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair2aOffset)"
    :rotation="new THREE.Vector3(0, 3.14, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair2aOffset)"
    :angle="3"
    :red="false"
    :yellow="false"
    :green="true"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair1bOffset)"
    :rotation="new THREE.Vector3(0, 1.57, 0)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="props.position.clone().add(trafficLightPair1bOffset)"
    :angle="3"
    :red="true"
    :yellow="false"
    :green="false"
  />
</template>
