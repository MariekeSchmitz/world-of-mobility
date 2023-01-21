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
import ScriptField from "@/components/editor/ScriptField.vue";
  import ServerChat from "@/components/ServerChat.vue";
  import { library } from "@fortawesome/fontawesome-svg-core";
  import {
    faPlus,
    faXmark,
    faFileArrowDown
  } from "@fortawesome/free-solid-svg-icons";
  library.add(faXmark, faPlus, faFileArrowDown);

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

    let npcx= ref(0);
    let npcy = ref(0);
    
    const npcNeedsScript = ref(false)



  function setNpcValues(x:number,y:number) {
    npcx.value = x;
    npcy.value = y;
    setNpcScriptView(true)
  } 

  
  function setNpcScriptView(val:boolean) {
    npcNeedsScript.value = val;
  }

</script>

<template>
  <div class="fixed left-1/2 -translate-y-1/2 -translate-x-1/2 top-12">
    <h2>Farmerama Map</h2>
  </div>
  <div id="exitButton">
    <RouterLink to="/worldintro" class="fixed top-4 left-4">
      <font-awesome-icon
        icon="fa-solid fa-xmark"
        size="3xl"
        color="#2F8265"
        class="p-2 w-8 h-8"
      />
    </RouterLink>
  </div>

  <div class="grid grid-rows-2 fixed top-1/4 right-6 gap-16 font-poppins">
    <button>
      <font-awesome-icon
        icon="fa-solid fa-plus"
        size="3xl"
        color="#2F8265"
        class="w-5 h-5"
      /><br />Starte Spiel
    </button>
    <button @click="saveMap('testMap2', editorID)">
      <font-awesome-icon
        icon="fa-solid fa-file-arrow-down"
        size="3xl"
        color="#2F8265"
        class="w-5 h-5"
      /><br />speichern
    </button>
  </div>

  <UserListMenu :instanceID="editorID"></UserListMenu>

  <ScriptField
    v-if="npcNeedsScript"
    :id="editorID"
    :x="npcx"
    :y="npcy"
    @script-window-closed="setNpcScriptView(false)"
  ></ScriptField>
  <BottomMenu v-if="!npcNeedsScript"></BottomMenu>

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

      <EditorMap
        :editorID="editorID"
        @npc-added="setNpcValues($event.x, $event.y)"
      ></EditorMap>
    </Scene>
  </Renderer>
</template>
