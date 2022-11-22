<script setup lang="ts">

import {
  Box,
  ToonMaterial,
} from "troisjs";


const props = withDefaults(
    defineProps<{
        x:number
        y:number
        z:number
    }>(),
    {x:0, y:0, z:-2.5}
)


    

</script>


<template>

<Box ref="humanLeg" :scale="{ x: 0.25, y: 0.25, z: 1 }" :size="1"
    :position="{ x: props.x - 3.5, y: props.y , z: props.z }">
    <BasicMaterial />
    <ToonMaterial color="#6F8FAF" />
  </Box>
  <Box ref="humanSchuh" :scale="{ x: 0.3, y: 0.3, z: 0.25 }" :size="1"
    :position="{ x: props.x - 3.5, y: props.y , z: props.z - 0.5} ">
    <BasicMaterial />
    <ToonMaterial color="#2E1503" />
  </Box>

  <Box ref="humanLeg" :scale="{ x: 0.25, y: 0.25, z: 1 }" :size="1"
    :position="{ x: props.x - 3, y: props.y , z: props.z}">
    <BasicMaterial />
    <ToonMaterial color="#6F8FAF" />
  </Box>
  <Box ref="humanSchuh" :scale="{ x: 0.3, y: 0.3, z: 0.25 }" :size="1"
    :position="{ x: props.x - 3, y: props.y , z: props.z - 0.5} ">
    <BasicMaterial />
    <ToonMaterial color="#2E1503" />
  </Box>
  <Box ref="humanLegMiddle" :scale="{ x: 0.25, y: 0.25, z: 0.5 }" :size="1"
    :position="{ x: props.x - 3.25, y: props.y , z: props.z+ 0.25}">
    <BasicMaterial />
    <ToonMaterial color="#6F8FAF" />
  </Box>

  <Box ref="humanShirt" :scale="{ x: 0.8, y: 0.35, z: 0.85 }" :size="1"
    :position="{ x: props.x - 3.25, y: props.y , z: props.z+ 0.6}">
    <BasicMaterial />
    <ToonMaterial color="#FF0000" />
  </Box>
  <Box ref="humanArmLeft" :scale="{ x: 0.2, y: 0.25, z: 0.93 }" :size="1"
    :position="{ x: props.x - 3.8, y: props.y , z: props.z+ 0.52}">
    <BasicMaterial />
    <ToonMaterial color="#FF0000" />
  </Box>
  <Box ref="humanArmRight" :scale="{ x: 0.2, y: 0.25, z: 0.93 }" :size="1"
    :position="{ x: props.x - 2.7, y: props.y , z: props.z+ 0.52}">
    <BasicMaterial />
    <ToonMaterial color="#FF0000" />
  </Box>
  <Box ref="humanShoulders" :scale="{ x: 1.2, y: 0.25, z: 0.25 }" :size="1"
    :position="{ x: props.x - 3.25, y: props.y , z: props.z+ 0.85}">
    <BasicMaterial />
    <ToonMaterial color="#FF0000" />
  </Box>

  <Box ref="humanHals" :scale="{ x: 0.4, y: 0.25, z: 0.25 }" :size="1"
    :position="{ x: props.x - 3.25, y: props.y , z: props.z+ 1}">
    <BasicMaterial />
    <ToonMaterial color="#e0ac69" />
  </Box>

  <Box ref="humanHead" :scale="{ x: 0.6, y: 0.35, z: 0.5 }" :size="1"
    :position="{ x: props.x - 3.25, y: props.y , z: props.z+ 1.35}">
    <BasicMaterial />
    <ToonMaterial color="#e0ac69" />
  </Box>

  <Box ref="humanEyeRight" :scale="{ x: 0.1, y: 0.01, z: 0.05 }" :size="1"
    :position="{ x: props.x - 3.1, y: props.y - 0.198, z: props.z+ 1.40}">
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  <Box ref="humanEyeRight" :scale="{ x: 0.05, y: 0.01, z: 0.1 }" :size="1"
    :position="{ x: props.x - 3.1, y: props.y - 0.198, z: props.z+ 1.40}">
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  <Box ref="humanEyeLeft" :scale="{ x: 0.05, y: 0.01, z: 0.1 }" :size="1"
    :position="{ x: props.x - 3.4, y: props.y - 0.198, z: props.z+ 1.40}">
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  <Box ref="humanEyeLeft" :scale="{ x: 0.1, y: 0.01, z: 0.05 }" :size="1"
    :position="{ x: props.x - 3.4, y: props.y - 0.198, z: props.z+ 1.40}">
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  



</template>