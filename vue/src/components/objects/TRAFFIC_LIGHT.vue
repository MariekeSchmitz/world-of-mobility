<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import TRAFFIC_LIGHT_URL from "@/assets/models/TRAFFIC_LIGHT.glb?url";
import { Vector3 } from "three";

/**
 * Class for traffic light Components
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
    :src="TRAFFIC_LIGHT_URL"
    :position="props.position"
    :rotation="props.rotation"
    :scale="props.scale"
  />
</template>
