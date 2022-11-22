<script setup lang="ts">
import { ref, onMounted, reactive, render } from "vue";
import {
  AmbientLight,
  Box,
  Camera,
  ToonMaterial,
  type MeshPublicInterface,
  PointLight,
  Renderer,
  type RendererPublicInterface,
  Plane,
  Scene,
} from "troisjs";

import Car from "./components/Car.vue";
import Cow from "./components/Cow.vue";
import Tree from "./components/Tree.vue";
import Human from "./components/Human.vue";


const rendererC = ref();
const meshC = ref();
const box = ref();

const positionTemp = reactive({
  x: 0,
  y: 0,
  z: -2.5,
});


const dropValue = reactive({
  x: 0,
  y: 0,
  z: -2.5,
});

document.addEventListener("keyup", (e) => {
  if (e.code === "KeyW") {
    positionTemp.y += 1;
  } else if (e.code === "KeyS") {
    positionTemp.y -= 1;
  } else if (e.code === "KeyD") {
    positionTemp.x += 1;
  } else if (e.code === "KeyA") {
    console.log(box.value);
    positionTemp.x -= 1;
  }
});

</script>


<template>
  <Renderer ref="rendererC" antialias :orbit-ctrl="{ enableDamping: true }" resize="window">
    <Camera :position="{ x: 0, y: -15, z: 3 }" :lookAt="{ x: 0, y: 10, z: 0 }" ref="camera" />
    <Scene background="#97FFFF">
      <!-- Licht -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>

      <Car :x="positionTemp.x" :y="positionTemp.y" :z="positionTemp.z"></Car>
      <Human :x="positionTemp.x" :y="positionTemp.y" :z="positionTemp.z"></Human>
      <Tree :x="dropValue.x" :y="dropValue.y" :z="dropValue.z"></Tree>
      <Cow :x="positionTemp.x" :y="positionTemp.y" :z="positionTemp.z"></Cow>

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
