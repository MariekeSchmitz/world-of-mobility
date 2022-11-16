<template>
  <Plane :width="props.width" :height="props.height"
    :rotation="{ x: props.rotationX, y: props.rotationY, z: props.rotationZ }"
    :position="{ x: props.posX, y: props.posY, z: props.posZ }">
    <ToonMaterial>
      <Texture src="src\textures\tiles\SIDEWAY.jpg" />
    </ToonMaterial>
  </Plane>
  <!-- place for objects that are meant for this tile  -->
  <Box 
    :position="{ x: props.posX, y: props.posY+(props.height/6), z: props.posZ+0.5}" 
    :rotation="{ x: props.rotationX, y: props.rotationY, z: props.rotationZ }">
    <ToonMaterial />
  </Box>
</template>

<script setup lang="ts">
//--------imports-----------

import { defineProps } from "vue";
import {
  Box,
  ToonMaterial,
  Plane,
  Texture,
} from "troisjs";

//--------props-------------

// rotation: to translate our 'orientation' to a matching Tile, we need to pass a multiple 'Math.PI / 2' to our rotationZ value;
const props = defineProps<{
  width: number;
  height: number;
  posX: number;
  posY: number;
  posZ: number;
  rotationX: number;
  rotationY: number;
  rotationZ: number;
  type: string;
}>();

//--------code------------
</script>
