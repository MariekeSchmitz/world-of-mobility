<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import { ObjectEnum } from "@/enums/editor/ObjectEnum";
import TRAFFIC_LIGHT from "@/components/objects/TRAFFIC_LIGHT.vue";
import STREET_CROSS_URL from "@/assets/models/STREET_CROSS.glb?url";
import TRAFFIC_LIGHT_LIGHT from "@/components/objects/TRAFFIC_LIGHT_LIGHT.vue";
import { computed } from "vue";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    placedObject: any;
    orientation: string;
    trafficLightState: any;
  }>(),
  { width: 10, height: 10 }
);

const trafficLight = computed(() => {
  if (props.trafficLightState == "NORTHSOUTH") {
    return {
      redOnPair1: true,
      redOnPair2: false,
      greenOnPair1: false,
      greenOnPair2: true,
      yellowOn: false,
    };
  } else if (props.trafficLightState == "EASTWEST") {
    return {
      redOnPair1: false,
      redOnPair2: true,
      greenOnPair1: true,
      greenOnPair2: false,
      yellowOn: false,
    };
  }
  return {
    redOnPair1: false,
    redOnPair2: false,
    greenOnPair1: false,
    greenOnPair2: false,
    yellowOn: true,
  };
});

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
    :red="trafficLight.redOnPair1"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOnPair1"
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
    :red="trafficLight.redOnPair2"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOnPair2"
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
    :red="trafficLight.redOnPair2"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOnPair2"
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
    :red="trafficLight.redOnPair1"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOnPair1"
  />
</template>
