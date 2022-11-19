<template>

  <!-- Second Box -->
  <Box
    :position="{ x: 1, y: 1, z: 4 }"
    ref="meshC"
    :rotation="{ y: Math.PI / 4, z: Math.PI / 4 }"
  >
    <ToonMaterial>
      <Texture src="src\textures\Obsidian.jpg" />
    </ToonMaterial>
  </Box>

  <!-- "roadpart 1" -->
  <Plane :width="0.2" :height="2" :position="{ y: -3, z: 0.01 }">
    <ToonMaterial color="#FFFFFF" />
  </Plane>
  <Plane :width="0.2" :height="2" :rotation="{ x: 0 }" :position="{ z: 0.01 }">
    <ToonMaterial color="#FFFFFF" />
  </Plane>
  <Plane
    :width="0.2"
    :height="2"
    :rotation="{ x: 0 }"
    :position="{ y: 3, z: 0.01 }"
  >
    <ToonMaterial color="#FFFFFF" />
  </Plane>
  <Plane :width="8" :height="8" :rotation="{ x: 0 }" :position="{ z: 0 }">
    <ToonMaterial color="#000000" />
  </Plane>

  <!-- "roadpart 2" -->

  <Plane
    :width="0.2"
    :height="2"
    :position="{
      x: 0 + positionTile.x,
      y: -3 + positionTile.y,
      z: 0.01 + positionTile.Z,
    }"
  >
    <ToonMaterial color="#FFFFFF" />
  </Plane>
  <Plane
    :width="0.2"
    :height="2"
    :rotation="{ x: 0 }"
    :position="{
      x: 0 + positionTile.x,
      y: 0 + positionTile.y,
      z: 0.01 + positionTile.Z,
    }"
  >
    <ToonMaterial color="#FFFFFF" />
  </Plane>
  <Plane
    :width="0.2"
    :height="2"
    :rotation="{ x: 0 }"
    :position="{
      x: 0 + positionTile.x,
      y: 3 + positionTile.y,
      z: 0.01 + positionTile.Z,
    }"
  >
    <ToonMaterial color="#FFFFFF" />
  </Plane>
  <Plane
    :width="8"
    :height="8"
    :rotation="{ x: 0 }"
    :position="{
      x: 0 + positionTile.x,
      y: 0 + positionTile.y,
      z: 0 + positionTile.Z,
    }"
  >
    <ToonMaterial color="#000000" />
  </Plane>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { Box, ToonMaterial, Plane, Texture } from "troisjs";

const meshC = ref();
const positionTile = { x: 0, y: 8, Z: 0 };

</script>
