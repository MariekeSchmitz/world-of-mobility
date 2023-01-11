<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { NaturObjectEnum } from "@/services/NaturObjectEnum";
import Tree from "@/components/objects/Tree.vue";
import { Vector3 } from "three";
import { GltfModel } from "troisjs";
import { withDefaults, defineProps } from "vue";
import SHEEP from "../objects/SHEEP.vue";
import PIG from "../objects/PIG.vue";

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
    placedObject: any;
    orientation: string;
  }>(),
  { width: 10, height: 10 }
);

const treeOffset = new Vector3(1, 0, 1);
const sheepOneOffset = new Vector3(2, -0.5, 0.5);
const sheepTwoOffset = new Vector3(3, -0.5, 4);
const pigOffset = new Vector3(2, -0.5, 1);
</script>
<template>
  <GltfModel
    ref="model"
    src="/src/assets/models/GRASSTILE.glb"
    :position="props.position"
    :rotation="props.rotation"
  />
  <Tree
    v-if:="props.placedObject === NaturObjectEnum.TREE"
    :position="props.position.clone().add(treeOffset)"
    :rotation="new Vector3(0, 0, 0)"
    :type="NaturObjectEnum.TREE"
  />
  <SHEEP
    v-if:="props.placedObject === NaturObjectEnum.SHEEP"
    :position="props.position.clone().add(sheepOneOffset)"
    :rotation="new Vector3(0, 4.5, 0)"
    :type="NaturObjectEnum.SHEEP"
  />
  <SHEEP
    v-if:="props.placedObject === NaturObjectEnum.SHEEP"
    :position="props.position.clone().add(sheepTwoOffset)"
    :rotation="new Vector3(0, 0, 0)"
    :type="NaturObjectEnum.SHEEP"
  />
  <PIG
    v-if:="props.placedObject === NaturObjectEnum.PIG"
    :position="props.position.clone().add(pigOffset)"
    :rotation="new Vector3(0, 0, 0)"
    :type="NaturObjectEnum.PIG"
  />
</template>
