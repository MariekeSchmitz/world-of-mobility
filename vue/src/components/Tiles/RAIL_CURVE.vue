<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import RAIL_CURVE_URL from "@/assets/models/RAIL_CURVE.glb?url";
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
</script>
<template>
  <GltfModel
    ref="model"
    :src="RAIL_CURVE_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
</template>
