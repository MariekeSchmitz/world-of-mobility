<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { Plane, Texture, BasicMaterial } from "troisjs";

const props = defineProps<{
  type: string;
  width: number;
  height: number;
  position: THREE.Vector3;
  rotation: THREE.Vector3;
}>();

let objectPath = "../src/assets/objects/" + props.type + ".png";
</script>
<template>
  <Plane
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
  >
    <BasicMaterial>
      <Texture v-bind:src="objectPath" />
    </BasicMaterial>
  </Plane>
</template>
