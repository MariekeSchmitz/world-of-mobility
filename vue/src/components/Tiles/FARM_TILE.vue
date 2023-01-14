<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import FARM_TILE_URL from "@/assets/models/FARM_TILE.glb?url";

/**
 * Class for Farm Components
 * @author Beate Arnold, Victoria Thee
 */
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
    :src="FARM_TILE_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
</template>
