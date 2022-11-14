<script setup lang="ts">
import { ref, onMounted, reactive } from "vue";

import * as THREE from 'three'
import {
  AmbientLight,
  Box,
  Camera,
  PhongMaterial,
  StandardMaterial,
  Texture,
  useTextures,
  BasicMaterial,
  LambertMaterial,
  ToonMaterial,
  type MeshPublicInterface,
  PointLight,
  Renderer,
  
  type RendererPublicInterface,
  Plane,
  Scene,
} from "troisjs";
const rendererC = ref();

interface colored{
  name:string,
  posX: number,
  posY: number,
  color: string
}

//List of Tiles
let tiles: colored[] = [{name:"Street", posX:0, posY:0, color:"red"},{name:"street", posX:1, posY:0,color:"yellow"},{name:"street", posX:2, posY:0,color:"purple"},{name:"street", posX:3, posY:0,color:"black"},{name:"street", posX:4, posY:0,color:"white"},{name:"street", posX:0, posY:1,color:"magenta"},{name:"street", posX:1, posY:1,color:"brown"},{name:"street", posX:2, posY:1,color:"blue"},{name:"street", posX:3, posY:1,color:"yellow"},{name:"street", posX:4, posY:1,color:"red"}]

//Offset so Tiles start on Bottom Right for 0,0
const offsetx = -2
const offsety = -2
  
function planeOver(event){
  event.component.mesh.material.color.set(event.over ? 0xdddddd : "white");
}
function planeClick(event) {
  alert("Meow")
}

onMounted(() => {
  const renderer = rendererC.value as RendererPublicInterface;
});
</script>

<template>
  <Renderer
    ref="rendererC"
    antialias
    :orbit-ctrl="{ enableDamping: true }"
    resize="window"
  >
    <Camera
      :position="{ x: 0, y: 0, z: 10 }"
      :lookAt="{ x: 0, y: 0, z: 0 }"
      ref="camera"
    />
    <Scene background="#97FFFF" ref="scene">
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>

    
      
      <Plane :width="5" :height="5" :rotation="{ x: 0 }" :position="{ x: 0, y: 0, z: 0 }" ref="tile{{n}},{{m}}">
        <ToonMaterial color="green" :props="{ side:THREE.DoubleSide}"/>
      </Plane>

      <template v-for="tile in tiles" >
      <Plane @pointer-over="planeOver"  @click="planeClick" :width="1" :height="1" :rotation="{ x: 0 }" :position="{ x: tile.posX  + offsetx, y: tile.posY+offsety, z: 0.01 }">
            <BasicMaterial>
            <Texture src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Cat03.jpg/1200px-Cat03.jpg"></Texture>
            </BasicMaterial>
            </Plane>
      </template>
      
    </Scene>
  </Renderer>
</template>

<style></style>
