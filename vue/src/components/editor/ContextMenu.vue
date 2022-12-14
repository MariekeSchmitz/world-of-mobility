<script setup lang="ts">
import { Vector3 } from "three";
import { Plane, Texture, BasicMaterial } from "troisjs";
import { withDefaults, defineProps, defineEmits } from "vue";


const props = defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
  }>()
  
const emit = defineEmits(['turnRight'])
</script>
<template>
    <!--Background-->
    <Plane
    :width="props.width"
    :height="props.height"
    :position="props.position"
  >
    <BasicMaterial color='yellow'/>
  </Plane>

    <!--Right Turn-->
  <Plane @click="$emit('turnRight',this)" 
    :width="props.width / 4"
    :height="props.height * (2/3)"
    :position="props.position.clone().add(new Vector3(0.25,0,0.01))"
  >
    <BasicMaterial color='blue'/>
  </Plane>

  <!--Left Turn-->
  <Plane @click="$emit('turnLeft',this)" 
    :width="props.width / 4"
    :height="props.height * (2/3)"
    :position="props.position.clone().add(new Vector3(-0.25,0,0.01))"
  >
    <BasicMaterial color='blue'/>
  </Plane>

  <!--Remove-->
  <Plane @click="$emit('removeTile',this)" 
    :width="props.width / 4" 
    :height="props.height * (2/3)"
    :position="props.position.clone().add(new Vector3(0,0,0.01))"
  >
    <BasicMaterial color='red'/>
  </Plane>
</template>