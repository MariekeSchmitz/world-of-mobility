<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { Plane, Texture, BasicMaterial } from "troisjs";

const props = defineProps<{
  width: number;
  height: number;
  position: THREE.Vector3;
}>();

const emit = defineEmits(["turnRight", "turnLeft", "removeTile"]);
</script>
<template>
  <!--Background-->
  <Plane :width="props.width" :height="props.height" :position="props.position">
    <BasicMaterial color="yellow" />
  </Plane>

  <!--Right Turn-->
  <Plane
    @click="$emit('turnRight')"
    :width="props.width / 4"
    :height="props.height * (2 / 3)"
    :position="props.position.clone().add(new THREE.Vector3(0.25, 0, 0.01))"
  >
    <BasicMaterial color="blue" />
  </Plane>

  <!--Left Turn-->
  <Plane
    @click="$emit('turnLeft')"
    :width="props.width / 4"
    :height="props.height * (2 / 3)"
    :position="props.position.clone().add(new THREE.Vector3(-0.25, 0, 0.01))"
  >
    <BasicMaterial color="blue" />
  </Plane>

  <!--Remove-->
  <Plane
    @click="$emit('removeTile')"
    :width="props.width / 4"
    :height="props.height * (2 / 3)"
    :position="props.position.clone().add(new THREE.Vector3(0, 0, 0.01))"
  >
    <BasicMaterial color="red" />
  </Plane>
</template>
