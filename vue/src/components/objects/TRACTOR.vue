<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { Vector3 } from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";

/**
 * Class for Tractor Components
 * @author Beate Arnold, Victoria Thee
 */
const props = withDefaults(
  defineProps<{
    scale: any;
    position: Vector3;
    rotation: Vector3;
    type: string;
  }>(),
  { scale: new Vector3(1, 1, 1) }
);
</script>
<template>
  <GltfModel
    ref="model"
    src="/src/assets/models/TRACTOR.glb"
    :position="props.position"
    :rotation="props.rotation"
    :scale="props.scale"
  />
</template>
