<script setup lang="ts">
/**
 * Set object information and style
 * @defineProps type - type of object to be placed
 * @defineProps width - size of object
 * @defineProps height - size of object
 * @defineProps position - where object gets placed
 * @defineProps rotation - turn object if tile was turned
 * @author Astrid Klemmer
 */

//@ts-ignore
import * as THREE from "three";
import { Plane, Texture, BasicMaterial } from "troisjs";
import { editorTileURLs } from "@/components/editor/EditorTileURLDict"

const props = defineProps<{
  type: string;
  width: number;
  height: number;
  position: THREE.Vector3;
  rotation: THREE.Vector3;
}>();

const objectPath = editorTileURLs[props.type];
</script>
<template>
  <Plane
    :width="props.width - 0.5"
    :height="props.height - 0.5"
    :rotation="props.rotation"
    :position="props.position"
  >
    <BasicMaterial>
      <Texture v-bind:src="objectPath" />
    </BasicMaterial>
  </Plane>
</template>
