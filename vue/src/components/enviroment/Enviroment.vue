<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import * as THREE from "three";
import BACKGROUND from "@/components/enviroment/BACKGROUND.vue";
import SKYBALLON from "@/components/enviroment/SKYBALLON.vue";
import SURROUNDING from "@/components/enviroment/SURROUNDING.vue";
import PLATFORM from "@/components/enviroment/PLATFORM.vue";

const DEFAULTMAPSIZE = 16 * 8;

const props = defineProps<{
  scale: number;
  position: THREE.Vector3;
  rotation: number;
}>();

console.log(props.scale)
</script>;
<template>
  <BACKGROUND
    :position="props.position"
    :rotation="0"
    :scale="new THREE.Vector3(800, 800, 800)"
  ></BACKGROUND>
  <PLATFORM
    :position="props.position.add(new THREE.Vector3(0, 0, 0))"
    :rotation="0"
    :scale="
      new THREE.Vector3(
        26 * (props.scale / DEFAULTMAPSIZE),
        26 * (props.scale / DEFAULTMAPSIZE),
        26 * (props.scale / DEFAULTMAPSIZE)
      )
    "
  >
  </PLATFORM>
  <SURROUNDING
    :position="props.position.clone().add(new THREE.Vector3(0, 0, 0))"
    :rotation="0"
    :scale="new THREE.Vector3(20, 20, 20)"
  ></SURROUNDING>
  <SKYBALLON
    :position="props.position.clone().add(new THREE.Vector3(500, 100, -400))"
    :rotation="120"
    :scale="new THREE.Vector3(4, 4, 4)"
  >
  </SKYBALLON>
</template>
