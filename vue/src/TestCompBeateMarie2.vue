<script setup lang="ts">
import { ref, reactive, computed } from "vue";
import {
  AmbientLight,
  Camera,
  Box,
  ToonMaterial,
  PointLight,
  Renderer,
  Plane,
  Scene,
} from "troisjs";

import { Vector3, MathUtils } from "three";

import Car from "./components/Car.vue";

const rendererC = ref();

const forwardVec = new Vector3(0, 1, 0);

const positionTemp = reactive(new Vector3(0, 0, -2.5));

//direction value is a value between 0 and 7
//0 -> north
//1 -> northWest
//... -> leftTurn : direction + 2 % 8 (expl. from north to west)
//counter-clockwise (->like radians)
const direction = ref(0);

document.addEventListener("keyup", (e) => {
  if (e.code === "KeyW") {
    const rotatedForwardVec = rotate(forwardVec, rotation.value);
    positionTemp.add(rotatedForwardVec);
  } else if (e.code === "KeyS") {
    const rotatedForwardVec = rotate(forwardVec, rotation.value);
    positionTemp.sub(rotatedForwardVec);
  } else if (e.code === "KeyD") {
    direction.value = (direction.value - 2) % 8;
  } else if (e.code === "KeyA") {
    direction.value = (direction.value + 2) % 8;
  }
});

//same Code as in Car Component
function rotate(offsetVec: Vector3, angle: number) {
  const axis = new Vector3(0, 0, 1);
  const w = offsetVec.clone();
  const res = w.applyAxisAngle(axis, angle);
  return res;
}

const pi = MathUtils.degToRad(180);

const rotation = computed(() => {
  return (direction.value * pi) / 4;
}); //rotation in radians
</script>

<template>
  <Renderer ref="rendererC" antialias resize="window">
    <Camera
      :position="{ x: 0, y: positionTemp.y - 12, z: 0 }"
      :lookAt="{ x: 0, y: positionTemp.y, z: 0 }"
      ref="camera"
    />
    <Scene background="#97FFFF">
      <!-- Licht -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="1" color="#ff6000"></AmbientLight>

      <Car :pos="positionTemp" :rotation="rotation"></Car>

      <!-- <Box :position="positionTemp" :size="0.5"> </Box> -->

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
