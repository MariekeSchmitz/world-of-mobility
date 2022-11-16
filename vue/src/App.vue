<script setup lang="ts">
import { ref, onMounted, reactive } from "vue";
import {
  AmbientLight,
  Box,
  Camera,
  ToonMaterial,
  PointLight,
  Renderer,
  Plane,
  Scene,
} from "troisjs";

import Car from "./components/Car.vue";

const rendererC = ref();

const positionTemp = reactive({
  x: 0,
  y: 0,
  z: -2.5,
});

const rotation = reactive({
  x: 0,
  y: 0,
  z: 0
})

document.addEventListener("keyup", (e) => {
  if (e.code === "KeyW") {
    positionTemp.y += 1;
    console.log(`y: ${positionTemp.y}`)
  } else if (e.code === "KeyS") {
    positionTemp.y -= 1;
  } else if (e.code === "KeyD") {
    rotation.y += 1;
    console.log(`x: ${positionTemp.x}`)
  } else if (e.code === "KeyA") {
    rotation.y -= 1;
    console.log(`x: ${positionTemp.x}`)
  }
});


</script>

<template>
  <Renderer ref="rendererC" antialias resize="window">
    <Camera :position="{ x:  0, y: positionTemp.y -12 , z: 0}" :lookAt="{ x: 0 , y: positionTemp.y, z: 0}"  ref="camera" />
    <Scene background="#97FFFF">
      <!-- Licht -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="1" color="#ff6000"></AmbientLight>

      <Car :x="positionTemp.x" :y="positionTemp.y" :z="positionTemp.z"></Car>
   
      <!-- "Fahrbahn" -->
      <Plane :width="0.2" :height="2" :rotation="{ x: 0 }" :position="{ y: -3, z: -2.99 }">
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane :width="0.2" :height="2" :rotation="{ x: 0 }" :position="{ z: -2.99 }">
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane :width="0.2" :height="2" :rotation="{ x: 0 }" :position="{ y: 3, z: -2.99 }">
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane :width="8" :height="8" :rotation="{ x: 0 }" :position="{ z: -3 }">
        <ToonMaterial color="#000000" />
      </Plane>
    </Scene>
  </Renderer>
</template>

<style>

</style>
