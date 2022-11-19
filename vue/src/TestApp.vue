<script setup lang="ts">
import { ref, reactive } from "vue";
import {
  AmbientLight,
  Box,
  PointLight,
  Renderer,
  Scene,
  ToonMaterial,
} from "troisjs";
import TestMap from "@/components/TestMap.vue";
import CarCam from "@/components/CarCam.vue";

const renderer = ref();

const positionTemp = reactive({
  x: 0,
  y: 0,
  z: 0.5,
});

const rotationCarTemp = reactive({
  degree: 180,
});

document.addEventListener("keyup", (e) => {
  if (e.code === "KeyW") {
    positionTemp.y += 1;
  } else if (e.code === "KeyS") {
    positionTemp.y -= 1;
  } else if (e.code === "KeyD") {
    positionTemp.x += 1;
  } else if (e.code === "KeyA") {
    positionTemp.x -= 1;
  } else if (e.code === "KeyO") {
    rotationCarTemp.degree += 10;
  } else if (e.code === "KeyP") {
    rotationCarTemp.degree -= 10;
  }
  console.log(rotationCarTemp.degree);
});
</script>

<template>
  <Renderer ref="renderer" antialiasds resize="window">
    <CarCam
      :car-position="{
        x: positionTemp.x,
        y: positionTemp.y,
        z: positionTemp.z,
      }"
      :car-rotation="rotationCarTemp.degree"
      :distance-ground="10"
      :distance-heigth="5"
    />
    <Scene background="#97FFFF">
      <!-- Light -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>
      <TestMap></TestMap>
      <Box
        ref="car"
        :position="{ x: positionTemp.x, y: positionTemp.y, z: positionTemp.z }"
        :rotation="{ z: -(rotationCarTemp.degree * (Math.PI / 180)) }"
      >
        <ToonMaterial />
      </Box>
    </Scene>
  </Renderer>
</template>

<style></style>
