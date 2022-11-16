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

function hideElement(event) {
  alert("hide")
}

onMounted(() => {
  const renderer = rendererC.value as RendererPublicInterface;
});
</script>

<template>
<div class="mapTitle">
  <p>Farmerama Map</p>
</div>
  
  <div id="exitButton">
    <button class="roundButton">X</button>
  </div>


<div class="leftMenu">
  <div class="upperLeftMenu">
    <button class="roundButton" @click="hideElement">&lt;</button>
    <button class="roundButton">settings</button>
    <button class="roundButton">help</button>
  </div>
  <div class="bottomLeftMenu">
    <button id="gridButton">grid</button>
    <div class="firstDoubleButton">
      <button>-</button>
      <button>+</button>
    </div>
    <div class="secondDoubleButton">
      <button class="singleSecondDoubleButton">&lt;</button>
      <button class="singleSecondDoubleButton">&gt;</button>
    </div>
  </div>
</div>

<div class="buttonMenuRight">
  <button>Starte Spiel</button>
  <button>Welt testen</button>
</div>

<div class="bottomMenu">
  <div class="typeSelector">
    <button>Infrastruktur, Landschaft</button>
    <button>NPCs</button>
  </div>
  <div class="itemSelector">
    <button>Tiles</button>
    <button>Baum</button>
    <button>Tankstelle</button>
  </div>
  <div class="items"></div>
  <button @click="hideElement">v</button>
</div>


<div class="minimap">
  <div class="minimapButtons">
    <button>-</button>
    <button>+</button>
    <button @click="hideElement">v</button>
  </div>
  <p>Minimap, separate camera view from further away, +- Buttons control minimap camera</p>
</div>

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
      <Plane @pointer-over="planeOver"  @click="planeClick" :width="0.99" :height="0.99" :rotation="{ x: 0 }" :position="{ x: tile.posX  + offsetx, y: tile.posY+offsety, z: 0.01 }">
            <BasicMaterial>
            <Texture src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Cat03.jpg/1200px-Cat03.jpg"></Texture>
            </BasicMaterial>
            </Plane>
      </template>
      
    </Scene>
  </Renderer>
</template>

<style>

html{
  overflow: hidden;
}

button{
  background-color: rgba(161, 161, 161);
  border: none;
}

button:hover{
  cursor: pointer;
  background-color: rgba(139, 139, 139, 0.7);
  border: none;
}

.mapTitle{
  background-color: rgb(221, 221, 221);
  position: fixed;
  left:50%;
  top: -20px;
  height: auto;
  transform: translate(-50%,0);
  padding: 0px 20px;
} 

#exitButton{
  position: fixed;
  left:10px;
  top: 20px;
  width: 60px;
}

.mapTitle > p{
  font-size: 24pt;
}
.leftMenu{
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(3,1fr);
  position: fixed;
  bottom: 20%;
  left: 0px;
  height: 60%;
  width: 60px;
  background-color: rgb(221, 221, 221);
  row-gap: 24%;
}

.upperLeftMenu{
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(3,1fr);
  row-gap: 12%;
  margin: 20%;
}

.bottomLeftMenu{
  grid-template-columns: 1fr;
  grid-template-rows: repeat(3,1fr);
  row-gap: 0%;
  margin: 20% 4%;
}

#gridButton{
  width: 80%;
  margin: 10%;
  aspect-ratio: 1/1;
  background-color: rgb(221, 221, 221);
}

.roundButton{
  width: 60%;
  aspect-ratio: 1/1;
  border: none;
  border-radius: 100%;
}

.firstDoubleButton{
  background-color: white;
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin-top: 15px;
}

.secondDoubleButton{
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin-top: 10px;
}

.singleSecondDoubleButton{
  height: 180%;
  background-color: rgb(221, 221, 221);
  border: none;
}

.buttonMenuRight{
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(2,1fr);
  position: fixed;
  bottom: 60%;
  right: -10px;
  width: 100px;
  height: 20%;
  row-gap: 10%;
  
}
.buttonMenuRight > button{
  margin: 10px;
  padding: 20px;
}

.bottomMenu{
  display: grid;
  grid-template-columns: 20% 10% 68% 2%;
  position: fixed;
  width: 50%;
  height: 10%;
  background-color: rgb(221, 221, 221);
  bottom: 10px;
  left:10%;
 
}

.minimap{
  position: fixed;
  bottom: 10px;
  right: 10px;
  height: 20%;
  aspect-ratio: 1/1;
  background-color: wheat;
}

.typeSelector{
  display: grid;
  grid-template-rows: 1fr 1fr;
}
.itemSelector{
  display: grid;
  grid-template-rows: 1fr 1fr 1fr;
}
.items{

}

.minimapButtons{
  position: fixed;
  bottom: 22%;
  right: 10px;
}

</style>
