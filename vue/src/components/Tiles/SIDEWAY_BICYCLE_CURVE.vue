<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import SIDEWAY_BICYCLE_CURVE_URL from "@/assets/models/SIDEWAY_BICYCLE_CURVE.glb?url";

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
    :src="SIDEWAY_BICYCLE_CURVE_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
</template>
