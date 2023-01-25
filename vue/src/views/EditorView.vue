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
  Plane,
  BasicMaterial,
  Texture
} from "troisjs";

import BottomMenu from "@/components/editor/BottomMenu.vue";
import EditorMap from "@/components/editor/EditorMap.vue";
import MiniMap from "@/components/editor/MiniMap.vue";
import UserListMenu from "@/components/editor/UserListMenu.vue";
import {useMap} from "@/services/useMap"
import { useUserEditor } from "@/services/useUserEditor";
import { useLogin } from "@/services/login/useLogin";
import ScriptField from "@/components/editor/ScriptField.vue";
import ServerChat from "@/components/ServerChat.vue";
import Avatar from "@/components/User/Avatar.vue";
import ErrorWarning from "@/components/ErrorWarning.vue";
import SaveFeedback from "@/components/SaveFeedback.vue";

import { useUserFeedback } from "@/services/editor/useUserFeedback";
import type { MapInterface } from "@/services/editor/MapInterface";
import { editorTileURLs } from "@/components/editor/EditorTileURLDict"
import { library } from "@fortawesome/fontawesome-svg-core";
import router from "@/router";
import {
  faPlus,
  faFileArrowDown,
  faArrowLeft
} from "@fortawesome/free-solid-svg-icons";
import { useEditorError } from "@/services/editor/useEditorError";
import { animateHintBox } from "@/components/HintBoxAnimation";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { RouterLink } from "vue-router";
  library.add(faPlus, faFileArrowDown, faArrowLeft);


  const props = defineProps<{
    editorID: string;
  }>();


  const editorID = Number(props.editorID);
  const backgroundPath = editorTileURLs['BACKGROUND'];
  const { loginData, avatarData } = useLogin();
  const { leaveEditor } = useUserEditor();

  
  onMounted(() => {
    const orbitControls = rendererC.value.three.cameraCtrl;
    orbitControls.maxDistance = 100;
    orbitControls.minDistance = 12;
  });

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

    const {saveMap, getMapEditor} = useMap();
    const {errorMessage, setEditorError} = useEditorError()
    const {feedbackMessage, setUserFeedback} = useUserFeedback()

    const loadedMap = getMapEditor(props.editorID);
    const name = ref();
    onMounted(() => {
    loadedMap.then((result: MapInterface) => (name.value = result.name));
	
});

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
    console.log("set NPCScriptView, Wert:", val)
  }

  let widthWindow = ref(window.innerWidth);
  let heightWindow = ref(window.innerHeight);

  watch(errorMessage, (neu, alt) => {
    const errorBox = document.getElementById("errorBox");
    animateHintBox((errorMessage.value != ""), errorBox);
  });

  watch(feedbackMessage, (neu, alt) => {
    const hintBox = document.getElementById("feedbackBox");
    animateHintBox((feedbackMessage.value != ""), hintBox);
    console.log(feedbackMessage.value)
  });

  function startGame(){
  saveMap(props.editorID)
  router.push("/gameConfig/" + name.value);
  }
	


</script>

<template>

  <div class="absolute">
    <img :src="backgroundPath" alt="background" class="w-screen h-screen "/>
  </div>

  <div class="absolute">
  
    <div class="fixed left-1/2 -translate-y-1/2 -translate-x-1/2 top-16">
      <h1>{{name}}</h1>
    </div>
    <RouterLink
      to="/worldintro"
      class="fixed top-7 left-7"
      @click="setEditorError('')"
    >
      <font-awesome-icon
        icon="fa-solid fa-arrow-left"
        color="white"
        class="bg-greenLight rounded-full p-3 w-6 h-6 inline justify-self-start white hover:bg-greenDark"
      />
    </RouterLink>
    
    <ErrorWarning :errorMsg="errorMessage"></ErrorWarning>
    <SaveFeedback :feedbackMsg="feedbackMessage"></SaveFeedback>

    <Avatar
      :avatarPicture="avatarData.avatar"
      class="w-16 h-16 fixed top-7 right-7"
    ></Avatar>

    <div
      class="grid grid-rows-2 fixed top-[20%] left-0 gap-4 font-poppins text-sm font-semibold text-greenDark"
    >
      <button @click="startGame()" class="group bg-white p-5 hover:bg-greenLight">
        <font-awesome-icon
          icon="fa-solid fa-plus"
          color="#2F8265"
          class="w-5 h-5"
        /><br />Spiel<br />starten
      </button>
      <button @click="saveMap(editorID)" class="bg-white hover:bg-greenLight">
        <font-awesome-icon
          icon="fa-solid fa-file-arrow-down"
          color="#2F8265"
          class="w-5 h-5"
        /><br />Welt<br />speichern
      </button>
    </div>

    <ErrorWarning :errorMsg="errorMessage"></ErrorWarning>
    <ErrorWarning :errorMsg="feedbackMessage"></ErrorWarning>
      <!-- <p v-if="feedbackMessage">{{ feedbackMessage }}</p> -->

    <UserListMenu :instanceID="editorID"></UserListMenu>

    <ScriptField
      v-if="npcNeedsScript && !errorMessage"
      :id="editorID"
      :x="npcx"
      :y="npcy"
      @script-window-closed="setNpcScriptView(false)"
    ></ScriptField>
    <BottomMenu v-if="!npcNeedsScript || errorMessage"></BottomMenu>

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
      :alpha="true"
    >
      <Camera
        :position="{ x: 0, y: 0, z: 10 }"
        :lookAt="{ x: 0, y: 0, z: 0 }"
        ref="camera"
      />

      <Scene ref="scene">
        <PointLight :position="{ x: 0, y: 0, z: 10 }" />
        <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>
        <!-- <Plane
            :width="widthWindow"
            :height="heightWindow"
            :position="{ x: 0, y: 0, z: 5 }"
          >
            <BasicMaterial>
              <Texture v-bind:src="backgroundPath" />
            </BasicMaterial >
          </Plane> -->
        
        <EditorMap
        :editorID="editorID"
        @npc-added="setNpcValues($event.x, $event.y)"
        ></EditorMap>
      </Scene>
    </Renderer>
  </div>
</template>
