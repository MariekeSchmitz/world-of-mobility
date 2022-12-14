<!-- prettier-ignore -->
<script setup lang="ts">
    import { ref, onMounted, reactive, watch } from "vue";

    import * as THREE from 'three'
    import {
      AmbientLight,
      Camera,
      PointLight,
      Renderer,
      Scene,
    } from "troisjs";

    import BottomMenu from "../../components/editor/BottomMenu.vue";
    import LeftMenu from "../../components/editor/LeftMenu.vue";
    import MiniMap from "../../components/editor/MiniMap.vue";
    import type { Tile } from "../../services/editor/TileInterface";
    import { Orientation } from "../../services/editor/OrientationEnum";
    import type { ExportTile } from "@/services/editor/ExportTileInterface";
    import {useMapUpdate} from "@/services/useMapUpdate"
    import {useMap} from "@/services/useMap"
    import { computed } from "vue";

    
    

import { number } from "mathjs";

    const props = defineProps({
      editorID: {
       default: 0,
       type: number
      } 
    })
    console.log(props.editorID)

    /**
     * in order to Execute THREE code in script tag, create a reactive item and add :ref="name" to the Vue Element
     */
    const rendererC = ref();
    const camera = ref();
    const scene = ref();

    onMounted(() => {
      rendererC.value.canvas.addEventListener("click", onDocumentLeftMouseDown)
      rendererC.value.canvas.addEventListener("mousemove", onMouseOver)
      rendererC.value.canvas.addEventListener("contextmenu", onDocumentRightMouseDown)
      loadedMap.then((result) => setLoadedMap(result.tiles))
    });

    //Texture Loader
    const loadManager = new THREE.LoadingManager();
    const loader = new THREE.TextureLoader(loadManager);
    
    //Raycaster for Hover & Click detection
    var raycaster = new THREE.Raycaster();
    var mouse = new THREE.Vector2();

    //defines Tile to be placed on click and whether click triggers a Tile place    
    interface PlaceItem{
      placeType:string,
      placeMode:boolean
    }

    interface mapTile{
      type: string,
      orientation: string
    }

    let place:PlaceItem;
    place = {placeType:"none",placeMode: false}

    //Contextmenu Meshes
    let activeContextMenu: Array<THREE.Mesh>; 
    activeContextMenu = [];

    //Imported REST-Controller and Stompbroker connection
    const {sendMapUpdates, receiveMapUpdates, mapUpdates} = useMapUpdate(props.editorID);
    const {getMapEditor, saveMap} = useMap();

    //connect to Stompbroker

    
      receiveMapUpdates(props.editorID);
    
    

    //Rerender Map on Stompbroker Update
    watch(mapUpdates.value,() =>{
      createMap(mapUpdates.value.map.tiles)
    
    })
    //Get Map on Loading EditorView
    const loadedMap = getMapEditor(props.editorID);

    //Dynamic Parameters, updated with Stomp re-render
    const mapWidth = ref(8)
    const mapHeight = ref(8)
    const offsetx = computed(() => (-(mapWidth.value + 1) / 2));
    const offsety = computed(() => (-(mapHeight.value + 1) / 2));

    /**
     * Function to remove Editor Tile Meshes from the Scene in order to re-render the Scene
     * 
     * Author: Timothy Doukhin
     */
    function removeMap(){
      for (let id = scene.value.scene.children.length -1 ; id >= 0 ; id--){
        if (scene.value.scene.children[id].position.z == 0.01 ){
          scene.value.scene.remove(scene.value.scene.children[id])
        }
      }
    }


    /**
     * create Editable Map in Editor from Map object
     * @param tiles 2D Array containing Tiles
     * 
     * Author: Timothy Doukhin
     */

    function createMap(tiles: mapTile[][]){
      removeMap();
      
      for(let row = 0;row < tiles.length;row++){
        for(let column =0 ;column < tiles[0].length; column++){
        
          const TileGeometry = new THREE.PlaneGeometry( 0.99, 0.99 );
          let material = new THREE.MeshBasicMaterial();
          
          let texturePath = "../src/textures/editor/GRASS.jpg"
          if (tiles[column][row] != null){
            texturePath = '../src/textures/editor/'+tiles[column][row].type+'.jpg'
          }
          
          material.map = loader.load(texturePath)
          const TileMesh = new THREE.Mesh( TileGeometry, material );
          TileMesh.position.x = row + offsetx.value +1;
          TileMesh.position.y = column + offsety.value +1;
          TileMesh.position.z = 0.01;
          TileMesh.rotation.z = 0;
          if (tiles[column][row] != null){
            let orientation: string = tiles[column][row].orientation
            TileMesh.rotation.z = Orientation[orientation]
          }
          scene.value.add(TileMesh);
          
       }
      }
      

    }

    

    /**
     * Sets Tile to be placed when selected from Bottom Menu
     * @param tileType name of Tiletype
     * 
     * Author: Astrid Klemmer
     */
    function setTileInfo(tileType:string){
      place = {placeType: tileType, placeMode: true}
    }



    /**
     * If a Tile has been Selected, handles the Placement Logic 
     * @param tileObject Object to be operated on
     * 
     * Author: Timothy Doukhin & Astrid Klemmer
     */
    function tileClick(tileObject:THREE.Mesh) {
      removeContextMenu();
      
      if (place.placeMode){
        let posX = tileObject.position.x - offsetx.value -1;
        let posY = tileObject.position.y - offsety.value -1;

        let toSendObj: ExportTile = {
        type: place.placeType,
        orientation: "NORTH",
        xPos: posX,
        yPos: posY,
        control: "PLACE",
      } 

      sendMapUpdates(toSendObj);
      }
      

    }
    
    

/**
 * Creates a contextmenu with turn buttons and a remove button above the selected tile on right click.
 * Objects belonging to the Menu get saved in the "activeContextMenu" Array
 * any preexisting contextmenus get removed so that only one is active at any time.
 * 
 * @param callingObject Tile-Mesh that the contextmenu operates on
 * 
 * Author: Timothy Doukhin
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
     * 
     * Author: Timothy Doukhin
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
     * Rest request to turn Tile to the Left 
     * @param callingObject Tile-Object that the function operates on
     * 
     * Author: Timothy Doukhin
     */
    function turnLeft(callingObject: THREE.Mesh){
        let posX = callingObject.position.x - offsetx.value -1;
        let posY = callingObject.position.y - offsety.value -1;
        let turnleftDTO: ExportTile = {
          type: "SIDEWAY",
          orientation: "NORTH",
          xPos: posX,
          yPos: posY,
          control: "TURN_LEFT",
        }
        sendMapUpdates(turnleftDTO);
    }

    /**
     * Rest request to turn Tile to the Right
     * @param callingObject Tile-Object that the function operates on
     * 
     * Author: Timothy Doukhin
     */
    function turnRight(callingObject: THREE.Mesh){
      let posX = callingObject.position.x - offsetx.value -1;
        let posY = callingObject.position.y - offsety.value -1;
        let turnrightDTO: ExportTile = {
          type: "SIDEWAY",
          orientation: "NORTH",
          xPos: posX,
          yPos: posY,
          control: "TURN_RIGHT",
        }
        sendMapUpdates(turnrightDTO);
    }

    /**
     * Rest request to remove current Tile 
     * @param callingObject Tile-Object that the function operates on
     * 
     * Author: Timothy Doukhin
     */
    function removeTile(callingObject: THREE.Mesh){
        let posX = callingObject.position.x - offsetx.value -1;
        let posY = callingObject.position.y - offsety.value -1;
        let removeDTO: ExportTile = {
          type: "SIDEWAY",
          orientation: "NORTH",
          xPos: posX,
          yPos: posY,
          control: "REMOVE",
        }
        sendMapUpdates(removeDTO);
    }


/**
 * Sets the parameters for the World and creates map after loading
 * @param receivedTiles TileArray for Map
 * 
 * Author: Timothy Doukhin
 */
    function setLoadedMap(receivedTiles: mapTile[][]){
      mapWidth.value = receivedTiles[0].length
      mapHeight.value = receivedTiles.length
      createMap(receivedTiles);
    }

   

    /**
     * update mouse position to properly highlight on hover 
     * @param event to determine mouse Position
     * 
     * Author: Timothy Doukhin
     */

    function onMouseOver(event: MouseEvent){
      mouse.x = ( event.clientX / rendererC.value.renderer.domElement.clientWidth ) * 2 - 1;
      mouse.y = - ( event.clientY / rendererC.value.renderer.domElement.clientHeight ) * 2 + 1;
      checkHover();
    }

    
    let intersected:THREE.Mesh<THREE.PlaneGeometry, THREE.MeshBasicMaterial>
    /**
     * checks if mouse intersects any objects via raycaster.
     * If yes, Mesh gets highlighted and previous mesh saved, in order to restore old texture/color after leaving hover range.
     * 
     * Author: Timothy Doukhin
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
     * If yes, calls callback of contexmenu or tileClick for Tiles.
     * 
     * @param event used to determine mouse position at click
     * 
     * Author: Timothy Doukhin
     */
    function onDocumentLeftMouseDown( event: MouseEvent ) {
    
        //mouse position updated
        mouse.x = ( event.clientX / rendererC.value.renderer.domElement.clientWidth ) * 2 - 1;
        mouse.y = - ( event.clientY / rendererC.value.renderer.domElement.clientHeight ) * 2 + 1;

        raycaster.setFromCamera( mouse, camera.value.camera );
        var intersects = raycaster.intersectObjects( scene.value.scene.children ); 
        if ( intersects.length > 0 ) {
            let selectedObject = intersects[0];
            if (selectedObject.object.geometry.parameters.width == 0.99 && selectedObject.object.geometry.parameters.height == 0.99){
              tileClick(selectedObject.object)
            }else{
              try {
                selectedObject.object.callback();
              } catch (error) {
                console.log("cannot call back")
              }
            }

        }

    }

    /**
     * checks if mouse intersects any Meshes via Raycaster on right click.
     * If rightclick is on a Tile-Mesh, a contextMenu is created.
     * 
     * @param event used to determine mouse position at click
     * 
     * Author: Timothy Doukhin
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
    <button @click="saveMap('testMap2', 1)">save</button>
  </div>
  <div id="exitButton">
    <button class="roundButton">
      <img src="@/buttons/editor/close.png" />
    </button>
  </div>

  <div class="buttonMenuRight">
    <button><img src="@/buttons/editor/plus.png" /><br />Starte Spiel</button>
    <button><img src="@/buttons/editor/plus.png" /><br />Welt testen</button>
  </div>

  <LeftMenu />

  <BottomMenu v-on:selectTile="setTileInfo($event)"></BottomMenu>

  <MiniMap />

  <Renderer
    ref="rendererC"
    antialias
    :orbit-ctrl="{ enableDamping: true, enableRotate: false }"
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

      <!--   
        <template v-for="row in mapWidth" >
          <template v-for="column in mapHeight" >
              <Plane :width="0.99" :height="0.99" :rotation="{ z: tiles[row-1][column-1].orientation==''?0:Orientation[tiles[row-1][column-1].orientation]}" :position="{ x: row  + offsetx, y: column + offsety, z: 0.01 }">
                <BasicMaterial :props="{map: tiles[row-1][column-1].typ==''?loader.load('src/textures/editor/Default.jpg'):loader.load('src/textures/editor/'+tiles[row-1][column-1].typ+'.jpg')}" />
              </Plane>
          </template>
        </template>-->
    </Scene>
  </Renderer>
</template>

<style>
html {
  overflow: hidden;
}

button {
  background-color: rgba(161, 161, 161);
  border: none;
}

button > img {
  width: 15px;
  height: 15px;
}

button:hover {
  cursor: pointer;
  background-color: rgba(139, 139, 139, 0.7);
  border: none;
}

.mapTitle {
  background-color: rgb(221, 221, 221);
  position: fixed;
  left: 50%;
  top: -20px;
  height: auto;
  transform: translate(-50%, 0);
  padding: 0px 20px;
}

#exitButton {
  position: fixed;
  left: 10px;
  top: 20px;
  width: 60px;
}

.mapTitle > p {
  font-size: 24pt;
}
.roundButton {
  width: 60%;
  aspect-ratio: 1/1;
  border: none;
  border-radius: 100%;
}

.buttonMenuRight {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(2, 1fr);
  position: fixed;
  bottom: 60%;
  right: -10px;
  width: 100px;
  height: 20%;
  row-gap: 10%;
}
.buttonMenuRight > button {
  margin: 10px;
  padding: 20px;
}
</style>
