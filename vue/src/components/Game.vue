<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import { ref, computed, onMounted, reactive, onUnmounted, watch } from "vue";
import {
  AmbientLight,
  Box,
  Camera,
  Scene,
  PointLight,
  Renderer,
  ToonMaterial,
  Texture,
  GltfModel,
} from "troisjs";
import Map from "@/components/Map.vue";
import Car from "@/components/objects/Car.vue";
import { Vector3 } from "three";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import { orientations } from "@/services/Orientations";

const props = withDefaults(
  defineProps<{
    instanceID: number;
  }>(),
  { instanceID: 1 }
);
const {
  sendCommand,
  receiveGameUpdate,
  mapUpdates,
  getUserMoveable,
  leaveGame,
} = useGame();
const { loginData } = useLogin();

const renderer = ref();
const model = ref(null);
const camera = ref();
const car = ref();

let thirdPerson = reactive({ value: true });
let freeCam = reactive({ value: true });
let switchedMode = false;
const thirdPersonOffset = new Vector3(0, 8, -15);
const firstPersonOffset = new Vector3(0, 0, -2);
const cameraOffset = reactive(new Vector3(0, 8, -15));
const upVector = new Vector3(0, 1, 0);
let movementVector = new Vector3(0, 0, 0);   

const userMovable = computed(() => {
  return getUserMoveable(loginData.username);
});

const lookAt = reactive(new Vector3(15, 1, 15));

const cameraPosition = computed(() => {
  const vecTempTarget = lookAt.clone();
  const vecTempOffset = cameraOffset.clone();
  if (freeCam.value && camera.value && !switchedMode) {
    return camera.value.camera.position.add(movementVector);
  } else {
    if (userMovable.value != undefined) {
      vecTempOffset.applyAxisAngle(
        upVector,
        orientations[userMovable.value.orientation]
      );
    }
    switchedMode = false;
    return vecTempTarget.add(vecTempOffset);
  }
});

const allMoveables = computed(() => {
  if (userMovable.value != undefined) {
    const newLookAt = new Vector3(
      userMovable.value.xPos,
      2,
      userMovable.value.yPos
    );
    movementVector = newLookAt.clone().sub(lookAt);
    lookAt.copy(newLookAt);
  }
  return mapUpdates.moveableUpdates;
});

/**
 * switches from the Follower Cam to the Freecam and vica versa.
 */
function switchCamMode() {
  freeCam.value = !freeCam.value;
}

/**
 * switches from the third Person View to the Firstperson view and vica versa.
 */
function switchPerspective() {
  thirdPerson.value = !thirdPerson.value;
  if (thirdPerson.value) {
    cameraOffset.copy(thirdPersonOffset);
  } else {
    cameraOffset.copy(firstPersonOffset);
  }
  switchedMode = true;
}
function onReady(model: any) {
  console.log("model Ready", model);
}
/**
 * An Eventhandler for the Keyboardevents.
 * @param e a KeyboardEvent, pressed button etc.
 */
function handleKeyEvent(e: KeyboardEvent) {
  if (e.code === "KeyW") {
    sendCommand(props.instanceID, loginData.username, "SPEED_UP");
  } else if (e.code === "KeyS") {
    sendCommand(props.instanceID, loginData.username, "SPEED_DOWN");
  } else if (e.code === "KeyA") {
    sendCommand(props.instanceID, loginData.username, "RIGHT");
  } else if (e.code === "KeyD") {
    sendCommand(props.instanceID, loginData.username, "LEFT");
  } else if (e.code === "KeyV") {
    switchPerspective();
  } else if (e.code === "KeyF") {
    switchCamMode();
  }
}

/**
 * sets up a big chunk of the functionality for the gamewindow.
 * prepares the camera functionality.
 * adds the keylistener to submit the commands to the backend.
 * adds the keylisteners to switch views and cam-modes.
 */
onMounted(() => {
  const orbitControls = renderer.value.three.cameraCtrl;
  orbitControls.target = lookAt;
  orbitControls.enablePan = false;
  orbitControls.enableRotate = true;
  orbitControls.screenSpacePanning = false;
  orbitControls.maxPolarAngle = Math.PI / 2;

  function setAzimuthAngle(){
    console.log("hallo");
    if (freeCam.value && !thirdPerson.value) {
      orbitControls.minAzimuthAngle = orientations[userMovable.value.orientation] - Math.PI / 2;
      orbitControls.minAzimuthAngle = orientations[userMovable.value.orientation] + Math.PI / 2;
    } else {
      orbitControls.minAzimuthAngle =
      orientations[userMovable.value.orientation];
      orbitControls.maxAzimuthAngle =
      orientations[userMovable.value.orientation] + 1.99 * Math.PI;
    }
  }
  
  receiveGameUpdate(props.instanceID);
  document.addEventListener("keyup", handleKeyEvent);
  watch(userMovable.value,()=>setAzimuthAngle());
});
onUnmounted(() => {
  document.removeEventListener("keyup", handleKeyEvent);
  leaveGame(props.instanceID, loginData.username, "MOTORIZED_OBJECT");
});
</script>

<template>
  <Renderer ref="renderer" antialias :orbit-ctrl="true" resize="window">
    <Camera :position="cameraPosition" ref="camera" />
    <Scene background="#97FFFF">
      <!-- Light -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>
      <GltfModel
        ref="model"
        src="/src/assets/models/Qube.glb"
        @load="onReady"
        :position="new Vector3(10, 0, 10)"
      />
      <!-- Map -->
      <Map :instanceID="props.instanceID"></Map>
      <!-- "Car" -->
      <Box
        :position="{ x: 1, y: 1, z: 2 }"
        :scale="{ x: 1, y: 1, z: 2 }"
        ref="car"
        ><ToonMaterial>
          <Texture src="/src/textures/Obsidian.jpg" /> </ToonMaterial
      ></Box>

      <div v-for="(moveable, index) in allMoveables" :key="index">
        <Car :pos="new Vector3(moveable.xPos, 0.5, moveable.yPos)" :rotation="orientations[moveable.orientation]"></Car>
      </div>
    </Scene>
  </Renderer>
</template>
