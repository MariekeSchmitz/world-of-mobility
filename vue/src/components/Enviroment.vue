<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import * as THREE from "three";
import BACKGROUND from "@/components/objects/BACKGROUND.vue";
import ISLAND from "@/components/objects/ISLAND.vue";
import SKYBALLON from "@/components/objects/SKYBALLON.vue";

const props = withDefaults(
  defineProps<{
    scale: any;
    position: THREE.Vector3;
    rotation: number;
  }>(),
  { scale: new THREE.Vector3(1, 1, 1) }
);
</script>
<template>
  <BACKGROUND
    :position="props.position"
    :rotation="0"
    :scale="new THREE.Vector3(800, 800, 800)"
  ></BACKGROUND>
  <ISLAND
    :position="props.position.clone().add(new THREE.Vector3(0, 1, 0))"
    :rotation="0"
    :scale="new THREE.Vector3(35, 35, 35)"
  >
  </ISLAND>
  <SKYBALLON
    :position="props.position.clone().add(new THREE.Vector3(500, 100, -400))"
    :rotation="120"
    :scale="new THREE.Vector3(4, 4, 4)">
  </SKYBALLON>
</template>
