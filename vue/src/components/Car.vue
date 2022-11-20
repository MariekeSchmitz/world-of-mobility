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
    ref="body"
    :scale="{ x: 2, y: 3, z: 1 }"
    :size="1"
    :position="{ x: props.x + 2, y: props.y, z: props.z + 0.15 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#cc0000" />
  </Box>
  <Box
    ref="tireLeftFront"
    :size="0.5"
    :position="{ x: props.x + 3, y: props.y + 1, z: props.z - 0.25 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#123456" />
  </Box>
  <Box
    ref="tireLeftBack"
    :size="0.5"
    :position="{ x: props.x + 3, y: props.y - 1, z: props.z - 0.25 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#123456" />
  </Box>
  <Box
    ref="tireRightFront"
    :size="0.5"
    :position="{ x: props.x + 1, y: props.y + 1, z: props.z - 0.25 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#123456" />
  </Box>
  <Box
    ref="tireRightBack"
    :size="0.5"
    :position="{ x: props.x + 1, y: props.y - 1, z: props.z - 0.25 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#123456" />
  </Box>
  <Box
    ref="top"
    :scale="{ x: 2, y: 2, z: 0.75 }"
    :size="1"
    :position="{ x: props.x + 2, y: props.y - 0.15, z: props.z + 0.9 }"
  >
    <BasicMaterial />
    <ToonMaterial color="#cc0000" />
  </Box>
  <Box
    ref="lightRightFront"
    :size="0.5"
    :position="{ x: props.x + 1.5, y: props.y + 1.28, z: props.z }"
  >
    <BasicMaterial />
    <ToonMaterial color="#F1C232" />
  </Box>
  <Box
    ref="lightLeftFront"
    :size="0.5"
    :position="{ x: props.x + 2.5, y: props.y + 1.28, z: props.z }"
  >
    <BasicMaterial />
    <ToonMaterial color="#F1C232" />
  </Box>
</template>
