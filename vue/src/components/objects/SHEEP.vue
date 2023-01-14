<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import SHEEP_URL from "@/assets/models/SHEEP.glb?url";

/**
 * Class for Sheep Components
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
    :src="SHEEP_URL"
    :position="props.position"
    :rotation="new THREE.Vector3(0, props.rotation, 0)"
    :scale="props.scale"
  />
</template>
