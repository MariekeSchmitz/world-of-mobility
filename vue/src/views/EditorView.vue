<!-- prettier-ignore -->
<script setup lang="ts">
//@ts-ignore

  import { ref, onMounted, reactive, watch, onUnmounted } from "vue";

  import * as THREE from 'three'
  import {
    AmbientLight,
    Camera,
    PointLight,
    Renderer,
    Scene,
  } from "troisjs";

  import BottomMenu from "@/components/editor/BottomMenu.vue";
  import LeftMenu from "@/components/editor/LeftMenu.vue";
  import EditorMap from "@/components/editor/EditorMap.vue";
  import MiniMap from "@/components/editor/MiniMap.vue";
  import UserListMenu from "@/components/editor/UserListMenu.vue";
  import {useMap} from "@/services/useMap"
  import { number } from "mathjs";
  import { useUserEditor } from "@/services/useUserEditor";
  import { useLogin } from "@/services/login/useLogin";
  import ServerChat from "@/components/ServerChat.vue";

 
  const props = defineProps<{
    editorID: string;
  }>();

  const editorID = Number(props.editorID);
  const { loginData } = useLogin();
  const { leaveEditor } = useUserEditor();

  onUnmounted(() => {
      leaveEditor(editorID, loginData.username);
  });

    /**
     * in order to Execute THREE code in script tag, create a reactive item and add :ref="name" to the Vue Element
     */
    const rendererC = ref();
    const camera = ref();
    const scene = ref();

    //Texture Loader
    const loadManager = new THREE.LoadingManager();
    const loader = new THREE.TextureLoader(loadManager);
    
    //Raycaster for Hover & Click detection
    var raycaster = new THREE.Raycaster();
    var mouse = new THREE.Vector2();

    const {saveMap} = useMap();

</script>

<template>
  <div class="mapTitle">
    <p>Farmerama Map</p>
    <button @click="saveMap('testMap2', editorID)">save</button>
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

  <UserListMenu :instanceID="editorID"></UserListMenu>

  <BottomMenu></BottomMenu>

  <!--
  sends msg on every instance, should only be in one instance for all; first player gets all msg shown as many times as there are players
        
  <ServerChat :instanceId="editorID"></ServerChat>
  -->
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
  width: 30px;
  aspect-ratio: 1/1;
  border: none;
  border-radius: 100%;
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
