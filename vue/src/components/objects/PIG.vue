<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import PIG_URL from "@/assets/models/PIG.glb?url";

/**
 * Class for Pig Components
 * @author Beate Arnold, Victoria Thee
 */
const props = withDefaults(
  defineProps<{
    scale?: any;
    position: THREE.Vector3;
    rotation: number;
    type: string;
  }>(),
  { scale: new THREE.Vector3(1, 1, 1) }
);
</script>
<template>
  <GltfModel
    ref="model"
    :src="PIG_URL"
    :position="props.position"
    :rotation="new THREE.Vector3(0, props.rotation, 0)"
    :scale="props.scale"
  />
</template>
