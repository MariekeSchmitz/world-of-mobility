<script setup lang="ts">
import { ref, onMounted, reactive } from "vue";
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
import ServerChat from "./components/ServerChat.vue"

const rendererC = ref();
const meshC = ref();
const box = ref();

const positionTemp = reactive({
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

onMounted(() => {
  const renderer = rendererC.value as RendererPublicInterface;
  const mesh = (meshC.value as MeshPublicInterface).mesh;
  renderer.onBeforeRender(() => {
    mesh!.rotation.x += 0.01;
  });
});
</script>


<template>
  <ServerChat></ServerChat>
  <Renderer
    ref="rendererC"
    antialias
    :orbit-ctrl="{ enableDamping: true }"
    resize="window"
  >
    <Camera
      :position="{ x: 0, y: -15, z: 3 }"
      :lookAt="{ x: 0, y: 10, z: 0 }"
      ref="camera"
    />
    <Scene background="#97FFFF">
      <!-- Licht -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>

      <!-- Erste Box -->
      <Box
        ref="box"
        :position="{ x: positionTemp.x, y: positionTemp.y, z: positionTemp.z }"
      >
        <ToonMaterial />
      </Box>
      <!-- Zweite Box -->
      <Box
        :position="{ x: 1, y: 1, z: 0 }"
        ref="meshC"
        :rotation="{ y: Math.PI / 4, z: Math.PI / 4 }"
      >
        <ToonMaterial />
      </Box>

      <!-- "Fahrbahn" -->
      <Plane
        :width="0.2"
        :height="2"
        :rotation="{ x: 0 }"
        :position="{ y: -3, z: -2.99 }"
      >
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane
        :width="0.2"
        :height="2"
        :rotation="{ x: 0 }"
        :position="{ z: -2.99 }"
      >
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane
        :width="0.2"
        :height="2"
        :rotation="{ x: 0 }"
        :position="{ y: 3, z: -2.99 }"
      >
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane :width="8" :height="8" :rotation="{ x: 0 }" :position="{ z: -3 }">
        <ToonMaterial color="#000000" />
      </Plane>
    </Scene>
  </Renderer>
</template>

<style></style>
