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
  import EditorMap from "../../components/editor/EditorMap.vue";
  import MiniMap from "../../components/editor/MiniMap.vue";
  import { usePlaceState } from "@/services/editor/usePlaceState";
  import {useMap} from "@/services/useMap"
  import { number } from "mathjs";

    const props = defineProps({
      editorID: {
       default: 0,
       type: number
      } 
    })


    /**
     * in order to Execute THREE code in script tag, create a reactive item and add :ref="name" to the Vue Element
     */
    const rendererC = ref();
    const camera = ref();
    const scene = ref();

    onMounted(() => {
      rendererC.value.canvas.addEventListener("mousemove", onMouseOver)
    });

    //Texture Loader
    const loadManager = new THREE.LoadingManager();
    const loader = new THREE.TextureLoader(loadManager);
    
    //Raycaster for Hover & Click detection
    var raycaster = new THREE.Raycaster();
    var mouse = new THREE.Vector2();

    const {saveMap} = useMap();
    const {setPlaceState} = usePlaceState();
   
    /**
     * Sets Tile to be placed when selected from Bottom Menu
     * @param tileType name of Tiletype
     * 
     * Author: Astrid Klemmer
     */
    //PASS REACTIVE COMPONENT TO BOTTOM MENU AND EDITORMAP, SO PLACEMENT LOGIC CAN BE IMPLEMENTED IN EDITORMAP
    function setTileInfo(tileType:string){
      setPlaceState(tileType);
    }

    
    /**
     * update mouse position to properly highlight on hover 
     * @param event to determine mouse Position
     * 
     * Author: Timothy Doukhin
     */

    function onMouseOver(event: MouseEvent){
      mouse.x = ( event.clientX / rendererC.value.renderer.domElement.clientWidth ) * 2 - 1  ;
      mouse.y = - ( event.clientY / rendererC.value.renderer.domElement.clientHeight ) * 2 + 1 ;
      checkHover();
    }

    
    let intersected:THREE.Mesh
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


</script>

<template>
  <div class="mapTitle">
    <p>Farmerama Map</p>
    <button @click="saveMap('testMap2', props.editorID)">save</button>
  </div>
  <div id="exitButton">
    <button class="roundButton">
      <RouterLink to="/worldintro">
        <img src="@/buttons/editor/close.png" />
      </RouterLink>
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

      <EditorMap :editorID="editorID"></EditorMap>

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
