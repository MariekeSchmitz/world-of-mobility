<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { GltfModel, Text } from "troisjs";
import SHEEP_URL from "@/assets/models/SHEEP.glb?url";

/**
 * Class for Sheep Components
 * @author Beate Arnold, Victoria Thee
 */
const props = withDefaults(
  defineProps<{
    scale?: any;
    position: THREE.Vector3;
    rotation: number;
    type: string;
    name: string;
  }>(),
  { scale: new THREE.Vector3(1, 1, 1) }
);
</script>
<template>
  <Text
    :text="props.name"
    font-src="\src\assets\fonts\helvetiker_regular.typeface.json"
    :position="props.position.clone().add(new THREE.Vector3(0, 2, 0))"
    align="center"
    :size="0.3"
    :height="0.01"
    :rotation="new THREE.Vector3(0, props.rotation, 0)"
  >
  </Text>
  <GltfModel
    ref="model"
    :src="SHEEP_URL"
    :position="props.position"
    :rotation="new THREE.Vector3(0, props.rotation, 0)"
    :scale="props.scale"
  />
</template>
