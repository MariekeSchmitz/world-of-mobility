<!-- prettier-ignore -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from 'three'
import { ref, onMounted, reactive, watch, onUnmounted } from "vue";
import {
  AmbientLight,
  Camera,
  PointLight,
  Renderer,
  Scene
} from "troisjs";

  import BottomMenu from "@/components/editor/BottomMenu.vue";
  import EditorMap from "@/components/editor/EditorMap.vue";
  import UserListMenu from "@/components/editor/UserListMenu.vue";
  import {useMap} from "@/services/useMap"
  import { useUserEditor } from "@/services/useUserEditor";
  import { useLogin } from "@/services/login/useLogin";
  import ScriptField from "@/components/editor/ScriptField.vue";
  import ServerChat from "@/components/ServerChat.vue";
  import { useRemoveInstanceState } from "@/services/useRemoveInstanceState";
  import Avatar from "@/components/User/Avatar.vue";
  import ErrorWarning from "@/components/ErrorWarning.vue";
  import SaveFeedback from "@/components/SaveFeedback.vue";
  import { useUserFeedback } from "@/services/editor/useUserFeedback";
  import type { MapInterface } from "@/services/editor/MapInterface";
  import { editorTileURLs } from "@/components/editor/EditorTileURLDict"
  import { useEditorError } from "@/services/editor/useEditorError";
  import { animateHintBox } from "@/components/HintBoxAnimation";
  import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
  import router from "@/router";
  import { library } from "@fortawesome/fontawesome-svg-core";
  import {
    faPlus,
    faFileArrowDown,
    faArrowLeft
  } from "@fortawesome/free-solid-svg-icons";
  import { usePlaceNpc } from '@/services/editor/usePlaceNpc';
  import type { NpcType } from '@/services/editor/NpcType';
  library.add(faPlus, faFileArrowDown, faArrowLeft);


  const props = defineProps<{
    editorID: string;
  }>();

  const editorID = Number(props.editorID);
  const backgroundPath = editorTileURLs['BACKGROUND'];
  const { loginData, avatarData } = useLogin();
  const { leaveEditor } = useUserEditor();
  const { removeInstanceState, setRemoveState } = useRemoveInstanceState();
  
  const remove = ref(false)
  
  onMounted(() => {
    const orbitControls = rendererC.value.three.cameraCtrl;
    orbitControls.maxDistance = 100;
    orbitControls.minDistance = 12;
  });

  onUnmounted(() => {
    if(removeInstanceState.value.id == editorID && removeInstanceState.value.type == "editor" && removeInstanceState.value.remove == true){
    }else{
      leaveEditor(editorID, loginData.username);
    }
    setEditorError("")
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
    const {placeNpc} = usePlaceNpc()

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
  } 
  
  function setScriptViewShowing(val:boolean) {
    npcNeedsScript.value = val;
  }

  async function placeNpcAndShowScript(x:number,y:number,type:NpcType) {
    await placeNpc(x,y,type,editorID)
    
    if (!errorMessage.value) {  
      setNpcValues(x,y)
      setScriptViewShowing(true)
    }
  }

  let widthWindow = ref(window.innerWidth);
  let heightWindow = ref(window.innerHeight);




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
      <h1 class="text-4xl mt-8">{{name}}</h1>
    </div>
    <RouterLink to="/worldintro" class="fixed top-7 left-7">
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

    <UserListMenu :instanceId="editorID" type="editor" ></UserListMenu>
  

    <ServerChat :instanceId="editorID" type="editor" :username="loginData.username"></ServerChat>

    <ScriptField
      v-if="npcNeedsScript"
      :id="editorID"
      :x="npcx"
      :y="npcy"
      @script-window-closed="setScriptViewShowing(false)"
    ></ScriptField>
    <BottomMenu v-if="!npcNeedsScript || errorMessage"></BottomMenu>

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
        
        <EditorMap
        :editorID="editorID"
        :placeNpcAndShowScript="placeNpcAndShowScript"
        ></EditorMap>
      </Scene>
    </Renderer>
  </div>
</template>
