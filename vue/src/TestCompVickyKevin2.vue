<script setup lang="ts">
import { ref, computed, onMounted, reactive } from "vue";
import * as THREE from "three";
import {
  AmbientLight,
  Box,
  Camera,
  Scene,
  PointLight,
  Renderer,
  ToonMaterial,
  Texture,
} from "troisjs";
import Map from "@/components/Map.vue";
import { Vector3 } from "three";

const renderer = ref();
const camera = ref();
const car = ref();

const cameraOffset = reactive(new Vector3(0, 8, -15));

const positionTemp = reactive(new Vector3(15, 1, 15));

const cameraPosition = computed(() => {
  const vecTempObj = positionTemp.clone();
  const vecTempCar = cameraOffset.clone();
  return vecTempObj.add(vecTempCar);
});

onMounted(() => {
  const orbitControls = renderer.value.three.cameraCtrl;
  const cameraControls = camera.value.camera;
  const carMesh = car.value.mesh;
  orbitControls.target = positionTemp;
  orbitControls.enablePan = false;

  orbitControls.screenSpacePanning = false;
  //orbitControls.minPolarAngle = Math.PI/2;
  orbitControls.maxPolarAngle = Math.PI/2;
  //orbitControls.maxAzimuthAngle = 0;
  //orbitControls.minAzimuthAngle = 0;
  document.addEventListener("keyup", (e) => {
    if (e.code === "KeyW") {
      positionTemp.add(new Vector3(0, 0, 1));
    } else if (e.code === "KeyS") {
      positionTemp.add(new Vector3(0, 0, -1));
    } else if (e.code === "KeyD") {
      positionTemp.add(new Vector3(-1, 0, 0));
    } else if (e.code === "KeyA") {
      positionTemp.add(new Vector3(1, 0, 0));
    } else if (e.code === "KeyO") {
      const degree = Math.PI / 4;
      cameraOffset.applyAxisAngle(new Vector3(0, 1, 0), degree);
      cameraControls.position.set(cameraPosition.value);
      carMesh.rotateY(degree);
      orbitControls.update();
      console.log(cameraPosition.value);
    }
  });
});
</script>

<template>
  <Renderer ref="renderer" antialias :orbit-ctrl="true" resize="window">
    <Camera :position="cameraPosition" ref="camera" />
    <Scene background="#97FFFF">
      <!-- Light -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>
      <!-- Map -->
      <Map></Map>
      <!-- "Car" -->
      <Box :position="positionTemp" :scale="{ x: 1, y: 1, z: 2 }" ref="car"
        ><ToonMaterial>
          <Texture src="src\textures\Obsidian.jpg" /> </ToonMaterial
      ></Box>
    </Scene>
  </Renderer>
</template>
