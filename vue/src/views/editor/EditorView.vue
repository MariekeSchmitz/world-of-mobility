<!-- eslint-disable prettier/prettier -->
<!-- eslint-disable prettier/prettier -->
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

    import BottomMenu from "../../components/editor/BottomMenu.vue";
    import LeftMenu from "../../components/editor/LeftMenu.vue";
    import MiniMap from "../../components/editor/MiniMap.vue";
    import type { Tile } from "../../services/editor/TileInterface";
    import { Orientation } from "../../services/editor/OrientationEnum";
    import type { ExportTile } from "@/services/editor/ExportTileInterface";
    import * as useMapUpdate from "@/services/useMapUpdate"


    /**
     * in order to Execute THREE code in script tag, create a reactive item and add :ref="name" to the Vue Element
     */
    const rendererC = ref();
    const camera = ref();
    const scene = ref();
    

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

    //Tile Map width and height
    const mapWidth = tiles[0].length
    const mapHeight = tiles.length
              
    //Offset so Tiles start on Bottom Right for 0,0
    const offsetx = -(mapWidth + 1) / 2
    const offsety = -(mapHeight + 1) /2

    const loadManager = new THREE.LoadingManager();
    const loader = new THREE.TextureLoader(loadManager);

    let prevTexture:String = "";
    let overwritten = false

    //defines Tile to be placed on click and whether click triggers a Tile place
        
    interface PlaceItem{
      placeType:string,
      placeMode:boolean
    }

    let place:PlaceItem;
    place = {placeType:"none",placeMode: false}

    function setTileInfo(tileType:string){
      place = {placeType: tileType, placeMode: true}
    }




    function planeClick(tileObject:THREE.Mesh) {
      removeContextMenu();
      //Object to be Sent via REST (to be implemented)
      let toSendObj: ExportTile = {
        type: "SIDEWAY",
        orientation: "NORTH",
        prevXPos: 0,
        prevYPos: 0,
        newXPos: 0,
        newYPos: 0,
        placedObjects: []
      } 
      
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

        let posX = tileObject.position.x - offsetx -1;
        let posY = tileObject.position.y - offsety -1;
          
        //reset rotation to 0  
        tileObject.setRotationFromEuler(new THREE.Euler());
        tileObject.material.map = loader.load('src/textures/editor/'+place.placeType+'.jpg')
        tiles[posX][posY] = toPlace;
      }

    }
    
    let activeContextMenu: Array<THREE.Mesh>; 
    activeContextMenu = [];
/**
 * Creates a contextmenu with turn buttons and a remove button above the selected tile on right click.
 * Objects belonging to the Menu get saved in the "activeContextMenu" Array
 * any preexisting contextmenus get removed so that only one is active at any time.
 * 
 * @param callingObject Tile-Object that the contextmenu operates on
 * 
 */
    function createContextMenu(callingObject:THREE.Mesh){
      removeContextMenu();
     
      const x = callingObject.position.x
      const y = callingObject.position.y

      
      const contextMenuGeometry = new THREE.PlaneGeometry( 0.8, 0.3 );
      contextMenuGeometry.translate(0,0,0.02)
      let material = new THREE.MeshBasicMaterial( {color: 0xffff00, side: THREE.DoubleSide} );
      const contextMenu = new THREE.Mesh( contextMenuGeometry, material );

      const rightButtonGeometry = new THREE.PlaneGeometry(0.2,0.2)
      rightButtonGeometry.translate(0.25,0,0.03)
      material = new THREE.MeshBasicMaterial({color: "blue"})
      const rightButton = new THREE.Mesh(rightButtonGeometry,material)
      
      const leftButtonGeometry = new THREE.PlaneGeometry(0.2,0.2)
      leftButtonGeometry.translate(-0.25,0,0.03)
      material = new THREE.MeshBasicMaterial({color: "blue"})
      const leftButton = new THREE.Mesh(leftButtonGeometry,material)
      
      const removeButtonGeometry = new THREE.PlaneGeometry(0.2,0.2)
      removeButtonGeometry.translate(0,0,0.03)
      material = new THREE.MeshBasicMaterial({color:"red"})
      const removeButton = new THREE.Mesh(removeButtonGeometry,material)
      
      //moving contextMenu to correct location according to tile clicked.
      contextMenuGeometry.translate(x ,y+0.5,0)
      rightButtonGeometry.translate(x ,y+0.5,0)
      leftButtonGeometry.translate(x ,y+0.5,0)
      removeButtonGeometry.translate(x,y+0.5,0)


      //callback functions for raycast intersection
      rightButton.callback = function(){turnRight(callingObject)}
      leftButton.callback = function(){turnLeft(callingObject)}
      removeButton.callback = function(){removeTile(callingObject)};

      //Array to save current contextmenu objects
      activeContextMenu.push(rightButton,leftButton,contextMenu,removeButton)

      scene.value.add (rightButton);
      scene.value.add (leftButton);
      scene.value.add( removeButton );
      scene.value.add( contextMenu );

    }

    /**
     * Removes the currently active Contextmenu from the Scene and from the "activeContextMenu" array
     */
    function removeContextMenu(){
      if (activeContextMenu.length > 0){
        scene.value.scene.remove(activeContextMenu[0])
        scene.value.scene.remove(activeContextMenu[1])
        scene.value.scene.remove(activeContextMenu[2])
        scene.value.scene.remove(activeContextMenu[3])
        activeContextMenu.splice(0);
      }
    }
    /**
     * turns the tile left, currently only for frontend user, to be connected to backend
     * 
     * @param callingObject Tile-Object that the function operates on
     */
    function turnLeft(callingObject){
      callingObject.rotation.z += Math.PI/2  
    }
    /**
     * turns the tile right, currently only for frontend user, to be connected to backend
     * 
     * @param callingObject Tile-Object that the function operates on
     */
    function turnRight(callingObject){
      callingObject.rotation.z -= Math.PI/2 
    }

    /**
     * removes the current Tile, currently only for frontend user, to be connected to backend
     * 
     * @param callingObject Tile-Object that the function operates on
     */
    function removeTile(callingObject){
      callingObject.material.map = loader.load('src/textures/editor/Default.jpg')
    }

    onMounted(() => {
      rendererC.value.canvas.addEventListener("click", onDocumentLeftMouseDown)
      rendererC.value.canvas.addEventListener("mousemove", onMouseOver)
      rendererC.value.canvas.addEventListener("contextmenu", onDocumentRightMouseDown)
    });


    var raycaster = new THREE.Raycaster();
    var mouse = new THREE.Vector2();

    /**
     * update mouse position to properly highlight on hover 
     * 
     * @param event to determine mouse Position
     */

    function onMouseOver(event){
      mouse.x = ( event.clientX / rendererC.value.renderer.domElement.clientWidth ) * 2 - 1;
      mouse.y = - ( event.clientY / rendererC.value.renderer.domElement.clientHeight ) * 2 + 1;
      checkHover();
    }

    
    let intersected:THREE.Mesh
    /**
     * checks if mouse intersects any objects via raycaster.
     * If yes, Mesh gets highlighted and previous mesh saved, in order to restore old texture/color after leaving hover range.
     */
    function checkHover(){
      raycaster.setFromCamera( mouse, camera.value.camera );
      var intersects = raycaster.intersectObjects( scene.value.scene.children ); 
      if (intersects.length > 0){
        //check if intersected object is the same as the previous
        if (intersects[0].object != intersected){
          if ( intersected ) {
				    intersected.material.color.setHex( intersected.currentHex );
          }
			  // store reference to closest object as current intersection object
			  intersected = intersects[ 0 ].object;
			  // store color of closest object (for later restoration)
			  intersected.currentHex = intersected.material.color.getHex();
			  // set a new color for closest object
			  intersected.material.color.setHex( 0xdddddd );
        }
      }
      else{
        if ( intersected ) 
			intersected.material.color.setHex( intersected.currentHex );
		// remove previous intersection object reference
		//by setting current intersection object to "nothing"
		intersected = null;
      }
    }
    /**
     * checks if mouse intersects any Meshes via Raycaster on mouseclick.
     * If yes, calls callback of contexmenu or planeClick for Tiles.
     * 
     * @param event used to determine mouse position at click
     */
    function onDocumentLeftMouseDown( event ) {
    
        //mouse position updated
        mouse.x = ( event.clientX / rendererC.value.renderer.domElement.clientWidth ) * 2 - 1;
        mouse.y = - ( event.clientY / rendererC.value.renderer.domElement.clientHeight ) * 2 + 1;

        raycaster.setFromCamera( mouse, camera.value.camera );
        var intersects = raycaster.intersectObjects( scene.value.scene.children ); 
        if ( intersects.length > 0 ) {
            let selectedObject = intersects[0];
            if (selectedObject.object.geometry.parameters.width == 0.99 && selectedObject.object.geometry.parameters.height == 0.99){
              planeClick(selectedObject.object)
            }else{
              try {
                selectedObject.object.callback();
              } catch (error) {

              }
            }

        }

    }

    /**
     * checks if mouse intersects any Meshes via Raycaster on right click.
     * If rightclick is on a tile, a contextMenu is created.
     * 
     * @param event used to determine mouse position at click
     */
    function onDocumentRightMouseDown( event ) {
      event.preventDefault();
        mouse.x = ( event.clientX / rendererC.value.renderer.domElement.clientWidth ) * 2 - 1;
        mouse.y = - ( event.clientY / rendererC.value.renderer.domElement.clientHeight ) * 2 + 1;

        raycaster.setFromCamera( mouse, camera.value.camera );
        var intersects = raycaster.intersectObjects( scene.value.scene.children );
        if (intersects.length > 0){
          let selectedObject = intersects[0];
          if (selectedObject.object.geometry.parameters.width == 0.99 && selectedObject.object.geometry.parameters.height == 0.99){
              createContextMenu(selectedObject.object)

            }
          
        }
    }

</script>


<template>
  <div class="mapTitle">
    <p>Farmerama Map</p>
  </div>
    
    <div id="exitButton">
      <button class="roundButton"><img src="src/buttons/editor/close.png"/></button>
    </div>

  <div class="buttonMenuRight">
    <button><img src="src/buttons/editor/plus.png"/><br>Starte Spiel</button>
    <button><img src="src/buttons/editor/plus.png"/><br>Welt testen</button>
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
              <Plane :width="0.99" :height="0.99" :rotation="{ z: tiles[row-1][column-1].orientation==''?0:Orientation[tiles[row-1][column-1].orientation]}" :position="{ x: row  + offsetx, y: column + offsety, z: 0.01 }">
                <BasicMaterial :props="{map: tiles[row-1][column-1].typ==''?loader.load('src/textures/editor/Default.jpg'):loader.load('src/textures/editor/'+tiles[row-1][column-1].typ+'.jpg')}" />
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

  button > img{
    width: 15px;
    height: 15px;
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
