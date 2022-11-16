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
  <Renderer ref="rendererC" antialias :orbit-ctrl="{ enableDamping: true }" resize="window">
    <Camera :position="{ x: 0, y: -15, z: 3 }" :lookAt="{ x: 0, y: 10, z: 0 }" ref="camera" />
    <Scene background="#97FFFF">
      <!-- Licht -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>

      <Group>
        <Box ref="body" :scale="{ x: 2, y: 3, z: 1 }" :size="1"
          :position="{ x: positionTemp.x + 2, y: positionTemp.y, z: positionTemp.z + 0.15 }">
          <BasicMaterial />
          <ToonMaterial color="#cc0000" />
        </Box>
        <Box ref="tireLeftFront" :size="0.5"
          :position="{ x: positionTemp.x + 3, y: positionTemp.y + 1, z: positionTemp.z - 0.25 }">
          <BasicMaterial />
          <ToonMaterial color="#123456" />
        </Box>
        <Box ref="tireLeftBack" :size="0.5"
          :position="{ x: positionTemp.x + 3, y: positionTemp.y - 1, z: positionTemp.z - 0.25 }">
          <BasicMaterial />
          <ToonMaterial color="#123456" />
        </Box>
        <Box ref="tireRightFront" :size="0.5"
          :position="{ x: positionTemp.x + 1, y: positionTemp.y + 1, z: positionTemp.z - 0.25 }">
          <BasicMaterial />
          <ToonMaterial color="#123456" />
        </Box>
        <Box ref="tireRightBack" :size="0.5"
          :position="{ x: positionTemp.x + 1, y: positionTemp.y - 1, z: positionTemp.z - 0.25 }">
          <BasicMaterial />
          <ToonMaterial color="#123456" />
        </Box>
        <Box ref="top" :scale="{ x: 2, y: 2, z: 0.75 }" :size="1"
          :position="{ x: positionTemp.x + 2, y: positionTemp.y - 0.15, z: positionTemp.z + 0.9 }">
          <BasicMaterial />
          <ToonMaterial color="#cc0000" />
        </Box>
        <Box ref="lightRightFront" :size="0.5"
          :position="{ x: positionTemp.x + 1.5, y: positionTemp.y + 1.28, z: positionTemp.z }">
          <BasicMaterial />
          <ToonMaterial color="#F1C232" />
        </Box>
        <Box ref="lightLeftFront" :size="0.5"
          :position="{ x: positionTemp.x + 2.5, y: positionTemp.y + 1.28, z: positionTemp.z }">
          <BasicMaterial />
          <ToonMaterial color="#F1C232" />
        </Box>
      </Group>

   
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
