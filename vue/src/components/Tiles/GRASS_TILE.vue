<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { Vector3 } from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";

/**
 * Class for Grass Components
 * @author Beate Arnold, Victoria Thee
 */
const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: Vector3;
    rotation: Vector3;
    type: string;
  }>(),
  { width: 10, height: 10 }
);
</script>
<template>
  <GltfModel
    ref="model"
    src="/src/assets/models/GRASS_TILE.glb"
    :position="props.position"
    :rotation="props.rotation"
  />
</template>
