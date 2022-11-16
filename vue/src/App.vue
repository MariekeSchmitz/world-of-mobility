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

interface Tile{
  typ:string,
  orientation:string,
  placedObjects: []
}


//List of Tiles
let tiles: Tile[][] = [
            [
            {
                typ: "SIDEWAY",
                orientation: "NORTH",
                placedObjects:[]
            },
            {
                typ: "STREET_T_CROSS",
                orientation: "SOUTH",
                placedObjects:[]
            },
            {
                typ: "",
                orientation: "",
                placedObjects:[]
            },
            {
                typ: "",
                orientation: "",
                placedObjects:[]
            }
          ],
          [
            {
              typ: "STREET_STRAIGHT",
                orientation: "EAST",
                placedObjects:[]
            },
            {
                typ: "STREET_CURVE",
                orientation: "SOUTH",
                placedObjects:[]
            },
            {
                typ: "STREET_CURVE",
                orientation: "EAST",
                placedObjects:[]
            },
            {
                typ: "",
                orientation: "",
                placedObjects:[]
            }
          ],
          [
            {
              typ: "STREET_STRAIGHT",
                orientation: "WEST",
                placedObjects:[]
            },
            {
                typ: "STREET_CURVE",
                orientation: "WEST",
                placedObjects:[]
            },
            {
                typ: "STREET_CURVE",
                orientation: "NORTH",
                placedObjects:[]
            },
            {
                typ: "",
                orientation: "",
                placedObjects:[]
            }
          ],
          [
            {
              typ: "STREET_STRAIGHT",
                orientation: "NORTH",
                placedObjects:[]
            },
            {
                typ: "",
                orientation: "",
                placedObjects:[]
            },
            {
                typ: "",
                orientation: "",
                placedObjects:[]
            },
            {
                typ: "",
                orientation: "",
                placedObjects:[]
            }
          ]
          ]

//Offset so Tiles start on Bottom Right for 0,0
const mapWidth = tiles[0].length
const mapHeight = tiles.length
  
const offsetx = -(mapWidth + 1) / 2
const offsety = -(mapHeight + 1) /2

const loadManager = new THREE.LoadingManager();
const loader = new THREE.TextureLoader(loadManager);

let prevTexture:String = "";
let overwritten = false
function planeOver(event){
  event.component.mesh.material.color.set(event.over ? "#dddddd":"#ffffff");
  /** 
 if (event.component.mesh.material.map.image === undefined){
  event.component.mesh.material.map = event.over ? loader.load('src/textures/STREET_STRAIGHT.jpg') : loader.load("")
 } 
  */
}
enum TileNames {
  STREET_STRAIGHT = "STREET_STRAIGHT",
  STREET_CURVE = "STREET_CURVE",
  STREET_T_CROSS = "STREET_T_CROSS",
  STREET_CROSS = "STREET_CROSS",
  SIDEWAY = "SIDEWAY"
 }


let placeType:string; 
function selectTile(tileType:string){
  placeType = tileType;
}

function planeClick(event) {
 let toSend:Object = {
  "type": "SIDEWAY",
  "orientation": "NORTH",
  "prevXPos": null,
  "prevYPos": null,
  "newXPos": 0,
  "newYPos": 0,
  "placedObjects":0
 } 
 let toPlace:Tile = {
              typ: placeType,
                orientation: "NORTH",
                placedObjects:[]
            }
  let posX = event.component.o3d.position.x - offsetx -1;
  let posY = event.component.o3d.position.y - offsety -1;
  //event.component.o3d.rotation.set(0);
  console.log("POSX="+posX+ " PosY="+posY)
  event.component.mesh.material.map = loader.load('src/textures/'+placeType+'.jpg')
  tiles[posX][posY] = toPlace;
}

function hideElement(event) {
  alert("hide")
}
const tileColor = ref("red")

onMounted(() => {
  const renderer = rendererC.value as RendererPublicInterface;
});


enum Orientation {
  "NORTH" = 0,
  "EAST" = -1.5708,
  "SOUTH" = 2* -1.5708,
  "WEST" = 3* -1.5708
}
 

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
  <div class="items">
  <button @click="selectTile('STREET_STRAIGHT')">STREET_STRAIGHT</button>
  <button @click="selectTile('STREET_CURVE')">STREET_CURVE</button>
  <button @click="selectTile('STREET_T_CROSS')">STREET_T_CROSS</button>
  <button @click="selectTile('STREET_CROSS')">STREET_CROSS</button>
  <button @click="selectTile('SIDEWAY')">SIDEWAY</button>
  </div>
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

    
      
      <Plane :width="4" :height="4" :rotation="{ x: 0 }" :position="{ x: 0, y: 0, z: 0 }" ref="tile{{n}},{{m}}">
        <ToonMaterial color="green" :props="{ side:THREE.DoubleSide}"/>
      </Plane>

      <template v-for="row in mapWidth" >
       <template v-for="column in mapHeight" >
          
          <Plane @pointer-over="planeOver" @click="planeClick" :width="0.99" :height="0.99" :rotation="{ z: tiles[row-1][column-1].orientation==''?0:Orientation[tiles[row-1][column-1].orientation]}" :position="{ x: row  + offsetx, y: column + offsety, z: 0.01 }">
            <BasicMaterial :props="{map: loader.load('src/textures/'+tiles[row-1][column-1].typ+'.jpg')}">
            </BasicMaterial>
          </Plane>
       </template>
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
