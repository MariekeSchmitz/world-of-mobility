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

      <Box ref="stamm" :scale="{ x: 1, y: 1, z: 6 }" :size="1"
          :position="{ x: props.x + 6, y: props.y - 4, z: props.z + 3 }">
          <BasicMaterial />
          <ToonMaterial color="#964B00" />
        </Box>
        <Box ref="treeDownside" :scale="{ x: 6, y: 6, z: 1.5 }" :size="1"
          :position="{ x: props.x + 6, y: props.y - 4, z: props.z + 6 }">
          <BasicMaterial />
          <ToonMaterial color="#4b6043" />
        </Box>
        <Box ref="treeDownsidesmall" :scale="{ x: 4, y: 4, z: 1 }" :size="1"
          :position="{ x: props.x + 6, y: props.y - 4, z: props.z + 5 }">
          <BasicMaterial />
          <ToonMaterial color="#4b6043" />
        </Box>
        <Box ref="treeMiddle" :scale="{ x: 4.5, y: 4.5, z: 1 }" :size="1"
          :position="{ x: props.x + 6, y: props.y - 4, z: props.z + 7 }">
          <BasicMaterial />
          <ToonMaterial color="#4b6043" />
        </Box>
        <Box ref="treeUpside" :scale="{ x: 2.5, y: 2.5, z: 1 }" :size="1"
          :position="{ x: props.x + 6, y: props.y - 4, z: props.z + 8 }">
          <BasicMaterial />
          <ToonMaterial color="#4b6043" />
        </Box>



</template>