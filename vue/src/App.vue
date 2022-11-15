<script setup lang="ts">
import { ref, onMounted, reactive } from "vue";
import {
  AmbientLight,
  Box,
  Camera,
  ToonMaterial,
  type MeshPublicInterface,
  PointLight,
  Renderer,
  type RendererPublicInterface,
  Plane,
  Scene,
} from "troisjs";

const rendererC = ref();
const meshC = ref();
const box = ref();

const positionTemp = reactive({
  x: 0,
  y: 0,
  z: -2.5,
});

document.addEventListener("keyup", (e) => {
  if (e.code === "KeyW") {
    positionTemp.y += 1;
  } else if (e.code === "KeyS") {
    positionTemp.y -= 1;
  } else if (e.code === "KeyD") {
    positionTemp.x += 1;
  } else if (e.code === "KeyA") {
    console.log(box.value);
    positionTemp.x -= 1;
  }
});

onMounted(() => {
  const renderer = rendererC.value as RendererPublicInterface;
  const mesh = (meshC.value as MeshPublicInterface).mesh;
  renderer.onBeforeRender(() => {
    mesh!.rotation.x += 0.01;
  });
});
</script>

<template>
  <Renderer ref="rendererC" antialias :orbit-ctrl="{ enableDamping: true }" resize="window">
    <Camera :position="{ x: 0, y: -15, z: 3 }" :lookAt="{ x: 0, y: 10, z: 0 }" ref="camera" />
    <Scene background="#97FFFF">
      <!-- Licht -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>

      <!-- Erste Box -->
      <Box ref="box" :position="{ x: positionTemp.x, y: positionTemp.y, z: positionTemp.z }">
        <ToonMaterial />
      </Box>
      <!-- Zweite Box -->
      <Box :position="{ x: 1, y: 1, z: 0 }" ref="meshC" :rotation="{ y: Math.PI / 4, z: Math.PI / 4 }">
        <ToonMaterial />
      </Box>

      <Group>
        <Box ref="body" :scale="{ x: 2, y: 3, z: 1 }" :size="1"
          :position="{ x: positionTemp.x + 2, y: positionTemp.y, z: positionTemp.z + 0.15 }">
          <BasicMaterial />
          <ToonMaterial color="#cc0000" />
        </Box>
        <Box ref="reifenLinksVorne" :size="0.5"
          :position="{ x: positionTemp.x + 3, y: positionTemp.y + 1, z: positionTemp.z - 0.25 }">
          <BasicMaterial />
          <ToonMaterial color="#123456" />
        </Box>
        <Box ref="reifenLinksHinten" :size="0.5"
          :position="{ x: positionTemp.x + 3, y: positionTemp.y - 1, z: positionTemp.z - 0.25 }">
          <BasicMaterial />
          <ToonMaterial color="#123456" />
        </Box>
        <Box ref="reifenRechtsVorne" :size="0.5"
          :position="{ x: positionTemp.x + 1, y: positionTemp.y + 1, z: positionTemp.z - 0.25 }">
          <BasicMaterial />
          <ToonMaterial color="#123456" />
        </Box>
        <Box ref="reifenRechtsHinten" :size="0.5"
          :position="{ x: positionTemp.x + 1, y: positionTemp.y - 1, z: positionTemp.z - 0.25 }">
          <BasicMaterial />
          <ToonMaterial color="#123456" />
        </Box>
        <Box ref="dach" :scale="{ x: 2, y: 2, z: 0.75 }" :size="1"
          :position="{ x: positionTemp.x + 2, y: positionTemp.y - 0.15, z: positionTemp.z + 0.9 }">
          <BasicMaterial />
          <ToonMaterial color="#cc0000" />
        </Box>
        <Box ref="lampeRechtsVorne" :size="0.5"
          :position="{ x: positionTemp.x + 1.5, y: positionTemp.y + 1.28, z: positionTemp.z }">
          <BasicMaterial />
          <ToonMaterial color="#F1C232" />
        </Box>
        <Box ref="lampeLinksVorne" :size="0.5"
          :position="{ x: positionTemp.x + 2.5, y: positionTemp.y + 1.28, z: positionTemp.z }">
          <BasicMaterial />
          <ToonMaterial color="#F1C232" />
        </Box>
      </Group>

    <Group>
      <Box ref="stamm" :scale="{ x: 1, y: 1, z: 6 }" :size="1"
          :position="{ x: positionTemp.x + 6, y: positionTemp.y - 4, z: positionTemp.z + 3 }">
          <BasicMaterial />
          <ToonMaterial color="#964B00" />
        </Box>
        <Box ref="treeDownside" :scale="{ x: 6, y: 6, z: 1.5 }" :size="1"
          :position="{ x: positionTemp.x + 6, y: positionTemp.y - 4, z: positionTemp.z + 6 }">
          <BasicMaterial />
          <ToonMaterial color="#4b6043" />
        </Box>
        <Box ref="treeDownsidesmall" :scale="{ x: 4, y: 4, z: 1 }" :size="1"
          :position="{ x: positionTemp.x + 6, y: positionTemp.y - 4, z: positionTemp.z + 5 }">
          <BasicMaterial />
          <ToonMaterial color="#4b6043" />
        </Box>
        <Box ref="treeMiddle" :scale="{ x: 4.5, y: 4.5, z: 1 }" :size="1"
          :position="{ x: positionTemp.x + 6, y: positionTemp.y - 4, z: positionTemp.z + 7 }">
          <BasicMaterial />
          <ToonMaterial color="#4b6043" />
        </Box>
        <Box ref="treeUpside" :scale="{ x: 2.5, y: 2.5, z: 1 }" :size="1"
          :position="{ x: positionTemp.x + 6, y: positionTemp.y - 4, z: positionTemp.z + 8 }">
          <BasicMaterial />
          <ToonMaterial color="#4b6043" />
        </Box>

    </Group>

    <Group>

      <Box ref="cowLeg" :scale="{ x: 0.25, y: 0.25, z: 1 }" :size="1"
          :position="{ x: positionTemp.x - 8, y: positionTemp.y , z: positionTemp.z - 0.5 }">
          <BasicMaterial />
          <ToonMaterial color="#9A7B4F" />
        </Box>

        <Box ref="cowLeg" :scale="{ x: 0.25, y: 0.25, z: 1 }" :size="1"
          :position="{ x: positionTemp.x - 7, y: positionTemp.y , z: positionTemp.z - 0.5 }">
          <BasicMaterial />
          <ToonMaterial color="#9A7B4F" />
        </Box>

        <Box ref="cowLeg" :scale="{ x: 0.25, y: 0.25, z: 1 }" :size="1"
          :position="{ x: positionTemp.x - 8, y: positionTemp.y + 2 , z: positionTemp.z - 0.5 }">
          <BasicMaterial />
          <ToonMaterial color="#9A7B4F" />
        </Box>

        <Box ref="cowLeg" :scale="{ x: 0.25, y: 0.25, z: 1 }" :size="1"
          :position="{ x: positionTemp.x - 7, y: positionTemp.y +2 , z: positionTemp.z - 0.5 }">
          <BasicMaterial />
          <ToonMaterial color="#9A7B4F" />
        </Box>
        
        <Box ref="cowBody" :scale="{ x: 1.25, y: 3.25, z: 1 }" :size="1"
          :position="{ x: positionTemp.x - 7.5, y: positionTemp.y + 1 , z: positionTemp.z + 0.5}">
          <BasicMaterial />
          <ToonMaterial color="#9A7B4F" />
        </Box>
        <Box ref="cowHead" :scale="{ x: 1, y: 0.75, z: 1 }" :size="1"
          :position="{ x: positionTemp.x - 7.5, y: positionTemp.y -0.8, z: positionTemp.z + 1.25}">
          <BasicMaterial />
          <ToonMaterial color="#9A7B4F" />
        </Box>
        <Box ref="cowEye" :scale="{ x: 0.1, y: 0.01, z: 0.05 }" :size="1"
          :position="{ x: positionTemp.x - 7.75, y: positionTemp.y -1.21, z: positionTemp.z + 1.25}">
          <BasicMaterial />
          <ToonMaterial color="#000000" />
        </Box>
        <Box ref="cowEye" :scale="{ x: 0.1, y: 0.01, z: 0.05 }" :size="1"
          :position="{ x: positionTemp.x - 7.25, y: positionTemp.y -1.21, z: positionTemp.z + 1.25}">
          <BasicMaterial />
          <ToonMaterial color="#000000" />
        </Box>
        <Box ref="cowNose" :scale="{ x: 0.4, y: 0.01, z: 0.3 }" :size="1"
          :position="{ x: positionTemp.x - 7.5, y: positionTemp.y -1.21, z: positionTemp.z + 1}">
          <BasicMaterial />
          <ToonMaterial color="#EFDECD" />
        </Box>
        <Box ref="cowNoseHole" :scale="{ x: 0.05, y: 0.02, z: 0.1 }" :size="1"
          :position="{ x: positionTemp.x - 7.6, y: positionTemp.y -1.21, z: positionTemp.z + 1}">
          <BasicMaterial />
          <ToonMaterial color="#000000" />
        </Box>
        <Box ref="cowNoseHole" :scale="{ x: 0.05, y: 0.02, z: 0.1 }" :size="1"
          :position="{ x: positionTemp.x - 7.4, y: positionTemp.y -1.21, z: positionTemp.z + 1}">
          <BasicMaterial />
          <ToonMaterial color="#000000" />
        </Box>

        <Box ref="cowGeweih" :scale="{ x: 1.5, y: 0.02, z: 0.1 }" :size="1"
          :position="{ x: positionTemp.x - 7.5, y: positionTemp.y -0.75, z: positionTemp.z + 1.4}">
          <BasicMaterial />
          <ToonMaterial color="#EFDECD" />
        </Box>
        <Box ref="cowGeweihLeft" :scale="{ x: 0.1, y: 0.02, z: 0.2 }" :size="1"
          :position="{ x: positionTemp.x - 8.2, y: positionTemp.y -0.75, z: positionTemp.z + 1.5}">
          <BasicMaterial />
          <ToonMaterial color="#EFDECD" />
        </Box>
        <Box ref="cowGeweihRight" :scale="{ x: 0.1, y: 0.02, z: 0.2 }" :size="1"
          :position="{ x: positionTemp.x - 6.8, y: positionTemp.y -0.75, z: positionTemp.z + 1.5}">
          <BasicMaterial />
          <ToonMaterial color="#EFDECD" />
        </Box>

        <Box ref="cowSchwanz" :scale="{ x: 0.1, y: 0.1, z: 1 }" :size="1"
          :position="{ x: positionTemp.x - 7.5, y: positionTemp.y + 2.625 , z: positionTemp.z }">
          <BasicMaterial />
          <ToonMaterial color="#9A7B4F" />
        </Box>


    </Group>

    <Group>

<Box ref="humanLeg" :scale="{ x: 0.25, y: 0.25, z: 1 }" :size="1"
    :position="{ x: positionTemp.x - 3.5, y: positionTemp.y , z: positionTemp.z }">
    <BasicMaterial />
    <ToonMaterial color="#6F8FAF" />
  </Box>
  <Box ref="humanSchuh" :scale="{ x: 0.3, y: 0.3, z: 0.25 }" :size="1"
    :position="{ x: positionTemp.x - 3.5, y: positionTemp.y , z: positionTemp.z - 0.5} ">
    <BasicMaterial />
    <ToonMaterial color="#2E1503" />
  </Box>

  <Box ref="humanLeg" :scale="{ x: 0.25, y: 0.25, z: 1 }" :size="1"
    :position="{ x: positionTemp.x - 3, y: positionTemp.y , z: positionTemp.z}">
    <BasicMaterial />
    <ToonMaterial color="#6F8FAF" />
  </Box>
  <Box ref="humanSchuh" :scale="{ x: 0.3, y: 0.3, z: 0.25 }" :size="1"
    :position="{ x: positionTemp.x - 3, y: positionTemp.y , z: positionTemp.z - 0.5} ">
    <BasicMaterial />
    <ToonMaterial color="#2E1503" />
  </Box>
  <Box ref="humanLegMiddle" :scale="{ x: 0.25, y: 0.25, z: 0.5 }" :size="1"
    :position="{ x: positionTemp.x - 3.25, y: positionTemp.y , z: positionTemp.z+ 0.25}">
    <BasicMaterial />
    <ToonMaterial color="#6F8FAF" />
  </Box>

  <Box ref="humanShirt" :scale="{ x: 0.8, y: 0.35, z: 0.85 }" :size="1"
    :position="{ x: positionTemp.x - 3.25, y: positionTemp.y , z: positionTemp.z+ 0.6}">
    <BasicMaterial />
    <ToonMaterial color="#FF0000" />
  </Box>
  <Box ref="humanArmLeft" :scale="{ x: 0.2, y: 0.25, z: 0.93 }" :size="1"
    :position="{ x: positionTemp.x - 3.8, y: positionTemp.y , z: positionTemp.z+ 0.52}">
    <BasicMaterial />
    <ToonMaterial color="#FF0000" />
  </Box>
  <Box ref="humanArmRight" :scale="{ x: 0.2, y: 0.25, z: 0.93 }" :size="1"
    :position="{ x: positionTemp.x - 2.7, y: positionTemp.y , z: positionTemp.z+ 0.52}">
    <BasicMaterial />
    <ToonMaterial color="#FF0000" />
  </Box>
  <Box ref="humanShoulders" :scale="{ x: 1.2, y: 0.25, z: 0.25 }" :size="1"
    :position="{ x: positionTemp.x - 3.25, y: positionTemp.y , z: positionTemp.z+ 0.85}">
    <BasicMaterial />
    <ToonMaterial color="#FF0000" />
  </Box>

  <Box ref="humanHals" :scale="{ x: 0.4, y: 0.25, z: 0.25 }" :size="1"
    :position="{ x: positionTemp.x - 3.25, y: positionTemp.y , z: positionTemp.z+ 1}">
    <BasicMaterial />
    <ToonMaterial color="#e0ac69" />
  </Box>

  <Box ref="humanHead" :scale="{ x: 0.6, y: 0.35, z: 0.5 }" :size="1"
    :position="{ x: positionTemp.x - 3.25, y: positionTemp.y , z: positionTemp.z+ 1.35}">
    <BasicMaterial />
    <ToonMaterial color="#e0ac69" />
  </Box>

  <Box ref="humanEyeRight" :scale="{ x: 0.1, y: 0.01, z: 0.05 }" :size="1"
    :position="{ x: positionTemp.x - 3.1, y: positionTemp.y - 0.198, z: positionTemp.z+ 1.40}">
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  <Box ref="humanEyeRight" :scale="{ x: 0.05, y: 0.01, z: 0.1 }" :size="1"
    :position="{ x: positionTemp.x - 3.1, y: positionTemp.y - 0.198, z: positionTemp.z+ 1.40}">
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  <Box ref="humanEyeLeft" :scale="{ x: 0.05, y: 0.01, z: 0.1 }" :size="1"
    :position="{ x: positionTemp.x - 3.4, y: positionTemp.y - 0.198, z: positionTemp.z+ 1.40}">
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  <Box ref="humanEyeLeft" :scale="{ x: 0.1, y: 0.01, z: 0.05 }" :size="1"
    :position="{ x: positionTemp.x - 3.4, y: positionTemp.y - 0.198, z: positionTemp.z+ 1.40}">
    <BasicMaterial />
    <ToonMaterial color="#000000" />
  </Box>

  
 


  

</Group>

  


      <!-- "Fahrbahn" -->
      <Plane :width="0.2" :height="2" :rotation="{ x: 0 }" :position="{ y: -3, z: -2.99 }">
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane :width="0.2" :height="2" :rotation="{ x: 0 }" :position="{ z: -2.99 }">
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane :width="0.2" :height="2" :rotation="{ x: 0 }" :position="{ y: 3, z: -2.99 }">
        <ToonMaterial color="#FFFFFF" />
      </Plane>
      <Plane :width="8" :height="8" :rotation="{ x: 0 }" :position="{ z: -3 }">
        <ToonMaterial color="#000000" />
      </Plane>
    </Scene>
  </Renderer>
</template>

<style>

</style>
