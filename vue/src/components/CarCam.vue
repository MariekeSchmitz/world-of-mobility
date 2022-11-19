<template>
  <Camera
    :position="{ x: eyePosition.x, y: eyePosition.y, z: eyePosition.z }"
    :lookAt="props.carPosition"
    ref="camera"
  />
</template>

<script setup lang="ts">
import { Camera } from "troisjs";
import { matrix, cos, sin, multiply, type Matrix } from "mathjs";
import { computed, ref } from "vue";

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

const eyePositionStart = computed(() => {
  return {
    x: props.carPosition.x,
    y: props.carPosition.y - props.distanceGround,
    z: props.carPosition.z + props.distanceHeigth,
  };
});

const eyePosition = computed(() => {
  return rotate_z(props.carPosition, eyePositionStart.value, props.carRotation);
});

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
  console.log(newPositionMatrix);
  const newPosition = {
    x: newPositionMatrix.get([0]) + carPosition.x,
    y: newPositionMatrix.get([1]) + carPosition.y,
    z: newPositionMatrix.get([2]) + carPosition.z,
  };
  console.log(newPosition);
  return newPosition;
}
</script>
