<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import { Orientation } from "@/services/editor/OrientationEnum";
import * as THREE from "three";
import ContextMenu from "../../components/editor/ContextMenu.vue";
import { Plane, Texture, BasicMaterial } from "troisjs";
import { withDefaults, defineProps, defineEmits,ref,computed ,toRef, watch} from "vue";
import { usePlaceState } from "@/services/editor/usePlaceState";
import { useContextMenu } from "@/services/editor/useContextMenu";
import { useMapUpdate } from "@/services/useMapUpdate";

const emit = defineEmits(['tileclick'])
const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    editorID: string
    cmVisible: boolean
  }>(),
  { width: 0.99, height: 0.99, cmVisible: false}

  
);
let cmVisible = ref(false)
//const cmVisible = ref(false);
let contextMenuVector: THREE.Vector3 = props.position.clone().add(new THREE.Vector3(0,0.5,0.02))
//console.log("vector",contextMenuVector);
let texturePath = "../src/textures/editor/"+props.type+".jpg"

const {readPlaceState} = usePlaceState();
const {readCMState, setCMState} = useContextMenu();
const {sendMapUpdates, receiveMapUpdates, mapUpdates} = useMapUpdate(props.editorID);


function rightclick() {
  console.log("rightclick")
}
const mapWidth = ref(8)
const mapHeight = ref(8)
const offsetx = computed(() => (-(mapWidth.value + 1) / 2));
const offsety = computed(() => (-(mapHeight.value + 1) / 2));

watch(readCMState.value, (newValue,oldValue) => {
  console.log("cmState read")
  cmVisible.value = false;
})

function tileClick(tileObject) {
      
      
      let posX = tileObject.position.x - offsetx.value -1;
      let posY = tileObject.position.y - offsety.value -1;
      console.log(tileObject);
      
      if(tileObject.type != "Default"){
          setCMState("bla");
          cmVisible.value = true;
          
      }
  
        if (readPlaceState.value.type != "none"){
          
  
          let toSendObj: ExportTile = {
          type: readPlaceState.value.type,
          orientation: "NORTH",
          xPos: posX,
          yPos: posY,
          control: "PLACE",
        } 
        
        sendMapUpdates(toSendObj);
      }
    }

</script>
<template>
  
   
  <Plane @click="tileClick(this)"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
  >
    <BasicMaterial>
      <Texture v-bind:src="texturePath"/>
    </BasicMaterial>
  </Plane>

  <ContextMenu v-if="cmVisible"
  :width="0.8"
  :height="0.3"
  :position="contextMenuVector"
   ></ContextMenu>
</template>