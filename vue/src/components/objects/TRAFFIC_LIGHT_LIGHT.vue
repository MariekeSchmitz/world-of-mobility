<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { BasicMaterial, Sphere, SpotLight } from "troisjs";
import { defineProps } from "vue";
const props = defineProps<{
  intensity: number;
  position: THREE.Vector3;
  angle: number;
  red: boolean;
  yellow: boolean;
  green: boolean;
}>();
const distance = 7;
</script>

<template>
  <SpotLight
    ref="light"
    :intensity="props.intensity"
    :distance="distance"
    :position="props.position.clone().add(new THREE.Vector3(0, 5, 0))"
    :visible="props.red"
    :angle="props.angle"
    color="#ff0000"
  >
    <Sphere :radius="0.3"><BasicMaterial color="#ff0000" /> </Sphere>
  </SpotLight>

  <SpotLight
    ref="light"
    :intensity="props.intensity"
    :distance="distance"
    :position="props.position.clone().add(new THREE.Vector3(0, 4, 0))"
    :visible="props.yellow"
    :angle="props.angle"
    color="#ffe600"
  >
    <Sphere :radius="0.3"><BasicMaterial color="#ffe600" /> </Sphere>
  </SpotLight>

  <SpotLight
    ref="light"
    :intensity="props.intensity"
    :distance="distance"
    :position="props.position.clone().add(new THREE.Vector3(0, 3, 0))"
    :visible="props.green"
    :angle="props.angle"
    color="#00ff00"
  >
    <Sphere :radius="0.3"><BasicMaterial color="#00ff00" /> </Sphere>
  </SpotLight>
</template>
