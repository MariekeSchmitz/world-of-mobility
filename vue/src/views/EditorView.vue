<script setup lang="ts">
    import { ref, onMounted, reactive } from "vue";

    import * as THREE from 'three'
    import {
      AmbientLight,
      Camera,
      BasicMaterial,
      ToonMaterial,
      PointLight,
      Renderer,
      
      type RendererPublicInterface,
      Plane,
      Scene,
    } from "troisjs";

    import BottomMenu from "../components/BottomMenu.vue";
    import LeftMenu from "../components/LeftMenu.vue";
    import MiniMap from "../components/MiniMap.vue";



    /**
     * in order to Execute THREE code in script tag, create a reactive item and add :ref="name" to the Vue Element
     */
    const rendererC = ref();
    const camera = ref();
    const scene = ref();

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

    //defines Tile to be placed on click and whether click triggers a Tile place
        
    interface PlaceItem{
      placeType:string,
      placeMode:boolean
    }

    let place:PlaceItem;

    function setTileInfo(tileType:string){
      place = {placeType: tileType, placeMode: true}
    }


    function planeClick(event) {
    //Object to be Sent via REST (to be implemented)
      let toSend:Object = {
      "type": "SIDEWAY",
      "orientation": "NORTH",
      "prevXPos": null,
      "prevYPos": null,
      "newXPos": 0,
      "newYPos": 0,
      "placedObjects":0
    } 

    if (place.placeMode){
      let toPlace:Tile = {
                      typ: place.placeType,
                      orientation: "NORTH",
                      placedObjects:[]
                  }
        let posX = event.component.o3d.position.x - offsetx -1;
        let posY = event.component.o3d.position.y - offsety -1;
        
        //reset rotation to 0  
        event.component.o3d.setRotationFromEuler(new THREE.Euler());
        console.log("POSX="+posX+ " PosY="+posY)
        event.component.mesh.material.map = loader.load('src/textures/'+place.placeType+'.jpg')
        tiles[posX][posY] = toPlace;
    }
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
      "EAST" = -(Math.PI/2),
      "SOUTH" = 2 * -(Math.PI/2),
      "WEST" = 3 * -(Math.PI/2)
    }


</script>

<template>
  <div class="mapTitle">
    <p>Farmerama Map</p>
  </div>
    
    <div id="exitButton">
      <button class="roundButton">X</button>
    </div>

  <div class="buttonMenuRight">
    <button>Starte Spiel</button>
    <button>Welt testen</button>
  </div>

  <LeftMenu/>

  <BottomMenu v-on:selectTile="setTileInfo($event)"></BottomMenu>

  <MiniMap/>

    <Renderer
      ref="rendererC"
      antialias
      :orbit-ctrl="{ enableDamping: true, enableRotate:false }"
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

      
        
        <Plane :width="tiles.length" :height="tiles[0].length" :rotation="{ x: 0 }" :position="{ x: 0, y: 0, z: 0 }" ref="tile{{n}},{{m}}">
          <ToonMaterial color="green" :props="{ side:THREE.DoubleSide}"/>
        </Plane>

        <template v-for="row in mapWidth" >
        <template v-for="column in mapHeight" >
            
            <Plane @pointer-over="planeOver" @click="planeClick" :width="0.99" :height="0.99" :rotation="{ z: tiles[row-1][column-1].orientation==''?0:Orientation[tiles[row-1][column-1].orientation]}" :position="{ x: row  + offsetx, y: column + offsety, z: 0.01 }">
              <BasicMaterial :props="{map: tiles[row-1][column-1].typ==''?loader.load('src/textures/Default.jpg'):loader.load('src/textures/'+tiles[row-1][column-1].typ+'.jpg')}">
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
  .roundButton{
    width: 60%;
    aspect-ratio: 1/1;
    border: none;
    border-radius: 100%;
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


</style>
