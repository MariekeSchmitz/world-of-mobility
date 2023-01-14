<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { NaturObjectEnum } from "@/services/NaturObjectEnum";
import Tree from "@/components/objects/Tree.vue";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import SHEEP from "@/components/objects/SHEEP.vue";
import PIG from "@/components/objects/PIG.vue";
import { Texture, ToonMaterial } from "troisjs";
import GRASSTILE_URL from "@/assets/models/GRASSTILE.glb?url";

/**
 * Class for Grass Components
 * @author Beate Arnold, Victoria Thee
 */
const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    placedObject: any;
    orientation: string;
  }>(),
  { width: 10, height: 10 }
);

const treeOffset = new THREE.Vector3(1, 0, 1);
const sheepOneOffset = new THREE.Vector3(-4, 0.8, 5);
const sheepTwoOffset = new THREE.Vector3(3, 0.8, 1);
const pigOffset = new THREE.Vector3(2, 0.8, 1);
</script>
<template>
  <GltfModel
    ref="model"
    :src="GRASSTILE_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
  <Tree
    v-if:="props.placedObject === NaturObjectEnum.TREE"
    :position="props.position.clone().add(treeOffset)"
    :rotation="new THREE.Vector3(0, 0, 0)"
    :type="NaturObjectEnum.TREE"
  />
  <SHEEP
    v-if:="props.placedObject === NaturObjectEnum.SHEEP"
    :position="props.position.clone().add(sheepOneOffset)"
    :rotation="4.5"
    :type="NaturObjectEnum.SHEEP"
  />
  <SHEEP
    v-if:="props.placedObject === NaturObjectEnum.SHEEP"
    :position="props.position.clone().add(sheepTwoOffset)"
    :rotation="1.31"
    :type="NaturObjectEnum.SHEEP"
  />
  <PIG
    v-if:="props.placedObject === NaturObjectEnum.PIG"
    :position="props.position.clone().add(pigOffset)"
    :rotation="3.14"
    :type="NaturObjectEnum.PIG"
  />
</template>
