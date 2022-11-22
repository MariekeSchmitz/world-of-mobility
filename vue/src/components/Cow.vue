<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import { Box, ToonMaterial } from "troisjs";

const props = withDefaults(
  defineProps<{
    x: number;
    y: number;
    z: number;
  }>(),
  { x: 0, y: 0, z: -2.5 }
);
</script>

<template>
  <Box
    ref="cowLeg"
    :scale="{ x: 0.25, y: 0.25, z: 1 }"
    :size="1"
    :position="{ x: props.x - 8, y: props.y, z: props.z - 0.5 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#9A7B4F" />
  </Box>

  <Box
    ref="cowLeg"
    :scale="{ x: 0.25, y: 0.25, z: 1 }"
    :size="1"
    :position="{ x: props.x - 7, y: props.y, z: props.z - 0.5 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#9A7B4F" />
  </Box>

  <Box
    ref="cowLeg"
    :scale="{ x: 0.25, y: 0.25, z: 1 }"
    :size="1"
    :position="{ x: props.x - 8, y: props.y + 2, z: props.z - 0.5 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#9A7B4F" />
  </Box>

  <Box
    ref="cowLeg"
    :scale="{ x: 0.25, y: 0.25, z: 1 }"
    :size="1"
    :position="{ x: props.x - 7, y: props.y + 2, z: props.z - 0.5 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#9A7B4F" />
  </Box>

  <Box
    ref="cowBody"
    :scale="{ x: 1.25, y: 3.25, z: 1 }"
    :size="1"
    :position="{ x: props.x - 7.5, y: props.y + 1, z: props.z + 0.5 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#9A7B4F" />
  </Box>
  <Box
    ref="cowHead"
    :scale="{ x: 1, y: 0.75, z: 1 }"
    :size="1"
    :position="{ x: props.x - 7.5, y: props.y - 0.8, z: props.z + 1.25 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#9A7B4F" />
  </Box>
  <Box
    ref="cowEye"
    :scale="{ x: 0.1, y: 0.01, z: 0.05 }"
    :size="1"
    :position="{ x: props.x - 7.75, y: props.y - 1.21, z: props.z + 1.25 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>
  <Box
    ref="cowEye"
    :scale="{ x: 0.1, y: 0.01, z: 0.05 }"
    :size="1"
    :position="{ x: props.x - 7.25, y: props.y - 1.21, z: props.z + 1.25 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>
  <Box
    ref="cowNose"
    :scale="{ x: 0.4, y: 0.01, z: 0.3 }"
    :size="1"
    :position="{ x: props.x - 7.5, y: props.y - 1.21, z: props.z + 1 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#EFDECD" />
  </Box>
  <Box
    ref="cowNoseHole"
    :scale="{ x: 0.05, y: 0.02, z: 0.1 }"
    :size="1"
    :position="{ x: props.x - 7.6, y: props.y - 1.21, z: props.z + 1 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>
  <Box
    ref="cowNoseHole"
    :scale="{ x: 0.05, y: 0.02, z: 0.1 }"
    :size="1"
    :position="{ x: props.x - 7.4, y: props.y - 1.21, z: props.z + 1 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  <Box
    ref="cowGeweih"
    :scale="{ x: 1.5, y: 0.02, z: 0.1 }"
    :size="1"
    :position="{ x: props.x - 7.5, y: props.y - 0.75, z: props.z + 1.4 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#EFDECD" />
  </Box>
  <Box
    ref="cowGeweihLeft"
    :scale="{ x: 0.1, y: 0.02, z: 0.2 }"
    :size="1"
    :position="{ x: props.x - 8.2, y: props.y - 0.75, z: props.z + 1.5 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#EFDECD" />
  </Box>
  <Box
    ref="cowGeweihRight"
    :scale="{ x: 0.1, y: 0.02, z: 0.2 }"
    :size="1"
    :position="{ x: props.x - 6.8, y: props.y - 0.75, z: props.z + 1.5 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#EFDECD" />
  </Box>

  <Box
    ref="cowSchwanz"
    :scale="{ x: 0.1, y: 0.1, z: 1 }"
    :size="1"
    :position="{ x: props.x - 7.5, y: props.y + 2.625, z: props.z }"
  >
    <BasicMaterial />
    <ToonMaterial color="#9A7B4F" />
  </Box>
</template>
