<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import GAS_STATION_URL from "@/assets/models/GAS_STATION.glb?url";

/**
 * Class for Car Charger Components
 * @author Beate Arnold, Victoria Thee
 */
const props = withDefaults(
  defineProps<{
    scale?: any;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
  }>(),
  { scale: new THREE.Vector3(1, 1, 1) }
);
</script>
<template>
  <GltfModel
    ref="model"
    :src="GAS_STATION_URL"
    :position="props.position"
    :rotation="props.rotation"
    :scale="props.scale"
  />
</template>
