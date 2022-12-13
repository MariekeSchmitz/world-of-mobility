<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { Orientation } from "@/services/editor/OrientationEnum";
import type * as THREE from "three";
import { Plane, Texture, BasicMaterial } from "troisjs";
import { withDefaults, defineProps, defineEmits } from "vue";

const emit = defineEmits(['tileclick'])
const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
  }>(),
  { width: 0.99, height: 0.99 }
);
let texturePath = "../src/textures/editor/"+props.type+".jpg"
</script>
<template>
  <!-- tileclick into Editormap! -->
  <Plane @click="$emit('tileclick')" 
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
  >
    <BasicMaterial>
      <Texture v-bind:src="texturePath"/>
    </BasicMaterial>
  </Plane>
</template>