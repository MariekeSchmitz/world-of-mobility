<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import * as THREE from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import PLATFORM_URL from "@/assets/models/PLATFORM.glb?url";

const props = withDefaults(
  defineProps<{
    scale: any;
    position: THREE.Vector3;
    rotation: number;
  }>(),
  { scale: new THREE.Vector3(1, 1, 1) }
);
</script>
<template>
  <GltfModel
    ref="model"
    :src="PLATFORM_URL"
    :position="props.position"
    :rotation="new THREE.Vector3(0, props.rotation, 0)"
    :scale="props.scale"
  >
  </GltfModel>
</template>