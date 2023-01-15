<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { Box, ToonMaterial } from "troisjs";
import { computed } from "vue";

const props = defineProps<{
  pos: THREE.Vector3;
  rotation: number; //in radians
}>();

function rotate(offsetVec: THREE.Vector3, angle: number) {
  const axis = new THREE.Vector3(0, 1, 0);
  const w = offsetVec.clone();
  const res = w.applyAxisAngle(axis, angle);
  return res;
}

const offsets = {
  offsetBody: new THREE.Vector3(0, 0.15, 0),
  offsetTop: new THREE.Vector3(0, 0.9, -0.15),
  // offsetTireLeftFront: new Vector3(3, -0.25, 1),
  // offsetTireLeftBack: new Vector3(3, -0.25, -1),
  // offsetTireRigthFront: new Vector3(1, -0.25, 1),
  // offsetTireRightBack: new Vector3(1, -0.25, -1),
  // offsetLightRightFront: new Vector3(1.5, 0, 1.28),
  // offsetLightLeftFront: new Vector3(2.5, 0, 1.28),
};

const tires = [
  { offset: new THREE.Vector3(1, -0.25, 1), name: "tireLeftFront" },
  { offset: new THREE.Vector3(1, -0.25, -1), name: "tireLeftBack" },
  { offset: new THREE.Vector3(-1, -0.25, 1), name: "tireRightFront" },
  { offset: new THREE.Vector3(-1, -0.25, -1), name: "tireRightBack" },
];

const rotatedTires = computed(() => {
  return tires.map((tire) => {
    const tirePosition = props.pos.clone();
    const rotatedOffset = rotate(tire.offset, props.rotation);
    tirePosition.add(rotatedOffset);

    return {
      offset: rotatedOffset,
      position: tirePosition,
      name: tire.name,
    };
  });
});

const lights = [
  { offset: new THREE.Vector3(-0.5, 0, 1.28), name: "lightRightFront" },
  { offset: new THREE.Vector3(0.5, 0, 1.28), name: "lightLeftFront" },
];

const rotatedLights = computed(() => {
  return lights.map((light) => {
    const lightPosition = props.pos.clone();
    const rotatedOffset = rotate(light.offset, props.rotation);
    lightPosition.add(rotatedOffset);

    return {
      offset: rotatedOffset,
      position: lightPosition,
      name: light.name,
    };
  });
});

// right now we do basically the very same thing for tires and lights (and to some degree for the car torso)
// TODO: refactor and remove code duplication
// structure above can be combined into one car tree that takes care of all objects belonging to the car
// const carTree = {
//   torso: [],
//   tires: [],
//   lights: [],
// };

// const computedCar = computed(() => {});

const bodyPosRotated = computed(() => {
  const v = props.pos.clone();
  return v.add(rotate(offsets.offsetBody, props.rotation));
});

const topPosRotated = computed(() => {
  const v = props.pos.clone();
  return v.add(rotate(offsets.offsetTop, props.rotation));
});
</script>

<template>
  <!-- Main Torso of the car -->
  <Box
    ref="body"
    :scale="new THREE.Vector3(2, 1, 3)"
    :size="1"
    :position="bodyPosRotated"
    :rotation="new THREE.Vector3(0, props.rotation, 0)"
  >
    <ToonMaterial color="#cc0000" />
  </Box>
  <Box
    ref="top"
    :scale="new THREE.Vector3(2, 0.75, 2)"
    :size="1"
    :position="topPosRotated"
    :rotation="new THREE.Vector3(0, props.rotation, 0)"
  >
    <ToonMaterial color="#cc0000" />
  </Box>

  <!-- add the tires -->
  <li v-for="tire in rotatedTires">
    <Box
      ref="{{tire.name}}"
      :size="0.5"
      :position="tire.position"
      :rotation="new THREE.Vector3(0, props.rotation, 0)"
    >
      <ToonMaterial color="#123456" />
    </Box>
  </li>

  <!-- add the lights -->
  <li v-for="light in rotatedLights">
    <Box
      ref="{{light.name}}"
      :size="0.5"
      :position="light.position"
      :rotation="new THREE.Vector3(0, props.rotation, 0)"
    >
      <ToonMaterial color="#F1C232" />
    </Box>
  </li>
</template>
