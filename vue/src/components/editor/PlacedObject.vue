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
  isNpc: boolean;
  width: number;
  height: number;
  position: THREE.Vector3;
  rotation: THREE.Vector3;
}>();

const objectPath = editorTileURLs[props.type];
const npcLabel = editorTileURLs["NPC_LABEL"];
</script>
<template>
  <Plane
    v-if="props.isNpc"
    :width="props.width-0.5"
    :height="props.height-0.8"
    :rotation="props.rotation"
    :position="props.position.clone().add(new THREE.Vector3(0.02, 0.32, 0.02))"
  >
    <BasicMaterial :props="{ transparent: true}" >
      <Texture v-bind:src="npcLabel" />
    </BasicMaterial >
  </Plane>
  <Plane
    :width="props.width - 0.5"
    :height="props.height - 0.5"
    :rotation="props.rotation"
    :position="props.position"
  >
    <BasicMaterial :props="{ transparent: true}" >
      <Texture v-bind:src="objectPath" />
    </BasicMaterial >
  </Plane>
</template>
