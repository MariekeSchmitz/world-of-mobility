<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { Vector3 } from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";

/**
 * Class for Car Components
 * @author Beate Arnold, Victoria Thee
 */
const props = withDefaults(
  defineProps<{
    scale: any;
    position: Vector3;
    rotation: number;
  }>(),
  { scale: new Vector3(1, 1, 1) }
);
</script>
<template>
  <GltfModel
    ref="model"
    src="/src/assets/models/CAR1.glb"
    :position="props.position"
    :rotation="new Vector3(0, props.rotation, 0)"
    :scale="props.scale"
  />
</template>
