<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import type * as THREE from "three";
import { Plane, Texture, ToonMaterial } from "troisjs";
import grassTileUrl from "@/textures/tiles/GRASSTILE.jpg"

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
  }>(),
  { width: 10, height: 10 }
);
</script>
<template>
  <Plane
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
  >
    <ToonMaterial>
      <Texture :src= "grassTileUrl"
    /></ToonMaterial>
  </Plane>
</template>
