<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import { Box, ToonMaterial } from "troisjs";
import { Vector3 } from "three";
import { computed } from "vue";

const props = withDefaults(
  defineProps<{
    pos: Vector3;
    rotation: number; //in radians
  }>(),
  { pos: new Vector3(0, 0, -2.5), rotation: 0 }
);

function rotate(offsetVec: Vector3, angle: number) {
  const axis = new Vector3(0, 0, 1);
  const w = offsetVec.clone();
  const res = w.applyAxisAngle(axis, angle);
  return res;
}

const offsets = {
  offsetBody: new Vector3(2, 0, 0.15),
  offsetTop: new Vector3(2, -0.15, 0.9),
  // offsetTireLeftFront: new Vector3(3, 1, -0.25),
  // offsetTireLeftBack: new Vector3(3, -1, -0.25),
  // offsetTireRigthFront: new Vector3(1, 1, -0.25),
  // offsetTireRightBack: new Vector3(1, -1, -0.25),
  // offsetLightRightFront: new Vector3(1.5, 1.28, 0),
  // offsetLightLeftFront: new Vector3(2.5, 1.28, 0),
};

const tires = [
  { offset: new Vector3(3, 1, -0.25), name: "tireLeftFront" },
  { offset: new Vector3(3, -1, -0.25), name: "tireLeftBack" },
  { offset: new Vector3(1, 1, -0.25), name: "tireRightFront" },
  { offset: new Vector3(1, -1, -0.25), name: "tireRightBack" },
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
  { offset: new Vector3(1.5, 1.28, 0), name: "lightRightFront" },
  { offset: new Vector3(2.5, 1.28, 0), name: "lightLeftFront" },
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
    :scale="new Vector3(2, 3, 1)"
    :size="1"
    :position="bodyPosRotated"
    :rotation="new Vector3(0, 0, props.rotation)"
  >
    <ToonMaterial color="#cc0000" />
  </Box>
  <Box
    ref="top"
    :scale="new Vector3(2, 2, 0.75)"
    :size="1"
    :position="topPosRotated"
    :rotation="new Vector3(0, 0, props.rotation)"
  >
    <ToonMaterial color="#cc0000" />
  </Box>

  <!-- add the tires -->
  <li v-for="tire in rotatedTires">
    <Box
      ref="{{tire.name}}"
      :size="0.5"
      :position="tire.position"
      :rotation="new Vector3(0, 0, props.rotation)"
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
      :rotation="new Vector3(0, 0, props.rotation)"
    >
      <ToonMaterial color="#F1C232" />
    </Box>
  </li>
</template>
