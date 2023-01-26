<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { Plane, Texture, BasicMaterial, Circle } from "troisjs";
import { editorTileURLs } from "@/components/editor/EditorTileURLDict";

const props = defineProps<{
  width: number;
  height: number;
  position: THREE.Vector3;
}>();

const emit = defineEmits(["turnRight", "turnLeft", "removeTile"]);

const rightPath = editorTileURLs["ROTATE_RIGHT"];
const leftPath = editorTileURLs["ROTATE_LEFT"];
const trashPath = editorTileURLs["TRASH"];
</script>
<template>
  <!--Background-->
  <!-- <Plane :width="props.width+0.4" :height="props.height" :position="props.position">
    <BasicMaterial color="white" />
  </Plane> -->

  <!--Right Turn-->
  <Circle
    @click="$emit('turnRight')"
    :radius="props.width/4" :segments=40
    :position="props.position.clone().add(new THREE.Vector3(0.5, 0, 0.01))"
  >
    <BasicMaterial/>  
  </Circle>

  <Plane
    @click="$emit('turnRight')"
    :width="props.width / 4"
    :height="props.height * (2 / 3)"
    :position="props.position.clone().add(new THREE.Vector3(0.5, 0, 0.01))"
  >
    <BasicMaterial :props="{ transparent: true }">
      <Texture v-bind:src="rightPath" />
    </BasicMaterial>
  </Plane>

  <!--Left Turn-->
  <Circle
    @click="$emit('turnLeft')"
    :radius="props.width/4" :segments=40
    :position="props.position.clone().add(new THREE.Vector3(-0.5, 0, 0.01))"
  >
    <BasicMaterial/>  
  </Circle>
  <Plane
    @click="$emit('turnLeft')"
    :width="props.width / 4"
    :height="props.height * (2 / 3)"
    :position="props.position.clone().add(new THREE.Vector3(-0.5, 0, 0.01))"
  >
    <BasicMaterial :props="{ transparent: true }">
      <Texture v-bind:src="leftPath" />
    </BasicMaterial>
  </Plane>

  <!--Remove-->
  <Circle
    @click="$emit('removeTile')"
    :radius="props.width/4" :segments=40
    :position="props.position.clone().add(new THREE.Vector3(0, 0, 0.01))"
  >
    <BasicMaterial/>  
  </Circle>
  <Plane
    @click="$emit('removeTile')"
    :width="props.width / 4"
    :height="props.height * (2 / 3)"
    :position="props.position.clone().add(new THREE.Vector3(0, 0, 0.01))"
  >
    <BasicMaterial :props="{ transparent: true }">
      <Texture v-bind:src="trashPath" />
    </BasicMaterial>
  </Plane>
</template>
