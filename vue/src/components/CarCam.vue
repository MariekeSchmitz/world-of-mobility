<template>
  <Camera
    :position="{ x: eyePosition.x, y: eyePosition.y, z: eyePosition.z }"
    :lookAt="props.carPosition"
    ref="camera"
  />
</template>

<script setup lang="ts">
/**
 * A Component to manage the camera
 */
import { Camera } from "troisjs";
import { matrix, cos, sin, multiply, type Matrix } from "mathjs";
import { computed, onMounted, ref } from "vue";
import * as THREE from "three";

const props = withDefaults(
  defineProps<{
    carPosition: { x: number; y: number; z: number };
    carRotation: number;
    distanceGround: number;
    distanceHeigth: number;
  }>(),
  { distanceGround: 10, distanceHeigth: 5 }
);
const camera = ref();

/**
 * default position of the cam - behind the car
 */
const eyePositionStart = computed(() => {
  console.log("upvector" + camera.value?.up);
  return {
    x: props.carPosition.x,
    y: props.carPosition.y - props.distanceGround,
    z: props.carPosition.z + props.distanceHeigth,
  };
});

/**
 * actual position of the cam - uses the rotation of the car and a rotation matrix.
 */
const eyePosition = computed(() => {
  return rotate_z(props.carPosition, eyePositionStart.value, props.carRotation);
});

/**
 * rotates the cam around the car to fit the rotation of the car
 * @param carPosition the position of the car
 * @param eyePositionStart  the default position of the cam
 * @param degrees the rotation of the car
 */
function rotate_z(
  carPosition: { x: number; y: number; z: number },
  eyePositionStart: { x: number; y: number; z: number },
  degrees: number
) {
  const positionMatrix = matrix([
    eyePositionStart.x - carPosition.x,
    eyePositionStart.y - carPosition.y,
    eyePositionStart.z - carPosition.z,
    1,
  ]);
  const angle = degrees * (Math.PI / 180);
  const rotationMatrix = matrix([
    [cos(angle), -sin(angle), 0, 0],
    [sin(angle), cos(angle), 0, 0],
    [0, 0, 1, 0],
    [0, 0, 0, 1],
  ]);
  const newPositionMatrix: Matrix = multiply(positionMatrix, rotationMatrix);
  const newPosition = {
    x: newPositionMatrix.get([0]) + carPosition.x,
    y: newPositionMatrix.get([1]) + carPosition.y,
    z: newPositionMatrix.get([2]) + carPosition.z,
  };
  console.log(props.carPosition);
  console.log(newPosition);
  return newPosition;
}
onMounted(() => {
  if (camera.value) {
    const cameraObject = camera.value.camera;
    console.log("Camera up value" + cameraObject.up);
    cameraObject.up = new THREE.Vector3(0, 0, 1);
}

});
</script>
