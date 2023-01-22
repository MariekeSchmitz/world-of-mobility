<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { ref, computed, onMounted, reactive, onUnmounted, watch } from "vue";
import { Camera, Scene, HemisphereLight, Renderer } from "troisjs";
import Map from "@/components/Map.vue";
import CAR1 from "@/components/objects/CAR1.vue";
import SHEEP from "@/components/objects/SHEEP.vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import { orientations } from "@/services/Orientations";
import TRUCK from "@/components/objects/TRUCK.vue";
import TRACTOR from "@/components/objects/TRACTOR.vue";
import PIG from "@/components/objects/PIG.vue";
import TUPEL from "@/components/objects/TUPEL.vue";

const SIZE = 16;

const props = withDefaults(
  defineProps<{
    instanceID: number;
  }>(),
  { instanceID: 1 }
);
const { sendCommand, receiveGameUpdate, mapUpdates, getUserMoveable } =
  useGame();
const { loginData } = useLogin();

const renderer = ref();
const camera = ref();

let thirdPerson = ref(true);
let freeCam = ref(true);
let switchedMode = false;
const hoverHeight = 2;
const thirdPersonOffset = new THREE.Vector3(0, 8, 15);
const firstPersonOffset = new THREE.Vector3(0, 0, 2);
const cameraOffset = reactive(new THREE.Vector3(0, 8, 15));
const upVector = new THREE.Vector3(0, 1, 0);
let movementVector = new THREE.Vector3(0, 0, 0);
const lookAt = reactive(new THREE.Vector3(0, 0, 0));

/**
 * returns the moveable object of the logged in user.
 */
const userMovable = computed(() => {
  return getUserMoveable(loginData.username);
});

watch(userMovable, () => {
  setAzimuthAngle();
});

/**
 * returns the position of the camera.
 */
const cameraPosition = computed(() => {
  const vecTempTarget = lookAt.clone();
  const vecTempOffset = cameraOffset.clone();
  if (freeCam.value && camera.value && !switchedMode) {
    return camera.value.camera.position.add(movementVector);
  } else {
    if (userMovable.value != undefined) {
      vecTempOffset.applyAxisAngle(
        upVector,
        -orientations[userMovable.value.orientation]
      );
    }
    switchedMode = false;
    return vecTempTarget.add(vecTempOffset);
  }
});

const trafficLightState = computed(() => {
  return mapUpdates.trafficLightState;
});

/***
 * keeps every playerobject up to date.
 */
const allMoveables = computed(() => {
  if (userMovable.value != undefined) {
    const newLookAt = new THREE.Vector3(
      userMovable.value.xPos * SIZE,
      hoverHeight,
      -userMovable.value.yPos * SIZE
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
  if (renderer.value) {
    renderer.value.three.cameraCtrl.enabled =
      !renderer.value.three.cameraCtrl.enabled;
  }
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

function setAzimuthAngle() {
  if (!renderer.value || !userMovable.value) return;
  const orbitControls = renderer.value.three.cameraCtrl;
  if (freeCam.value && !thirdPerson.value) {
    orbitControls.minAzimuthAngle =
      -orientations[userMovable.value.orientation] - Math.PI / 2;
    orbitControls.maxAzimuthAngle =
      -orientations[userMovable.value.orientation] + Math.PI / 2;
  } else {
    orbitControls.minAzimuthAngle = Infinity;
    orbitControls.maxAzimuthAngle = Infinity;
  }
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
    sendCommand(props.instanceID, loginData.username, "LEFT");
  } else if (e.code === "KeyD") {
    sendCommand(props.instanceID, loginData.username, "RIGHT");
  } else if (e.code === "KeyV") {
    switchPerspective();
  } else if (e.code === "KeyF") {
    switchCamMode();
  }
  setAzimuthAngle();
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
  orbitControls.maxDistance = 20;

  receiveGameUpdate(props.instanceID);
  document.addEventListener("keyup", handleKeyEvent);
});
onUnmounted(() => {
  document.removeEventListener("keyup", handleKeyEvent);
});
</script>

<template>
  <Renderer ref="renderer" antialias :orbit-ctrl="true" resize="window">
    <Camera :position="cameraPosition" ref="camera" />
    <Scene background="#97FFFF">
      <!-- Light -->
      <HemisphereLight
        :position="new THREE.Vector3(1, 1, 1)"
        :intensity="2"
        color="#ffffff"
      />
      <!-- <AmbientLight :intensity="0.85" color="#ffffff"></AmbientLight> -->
      <!-- Map -->
      <Map
        :instanceID="props.instanceID"
        :trafficLightState="trafficLightState"
      ></Map>
      <!-- "Car" -->
      <!-- <Box
        :position="{ x: 1, y: 1, z: 2 }"
        :scale="{ x: 1, y: 1, z: 2 }"
        ref="car"
        ><ToonMaterial>
          <Texture src="@/textures/Obsidian.jpg" /> </ToonMaterial
      ></Box> -->
      <div v-for="(moveable, index) in allMoveables" :key="index">
        <CAR1
          v-if="moveable.classname == 'CAR'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * SIZE, 0.7, -moveable.yPos * SIZE)
          "
          :rotation="-orientations[moveable.orientation]"
        />
        <SHEEP
          v-if="moveable.classname == 'SHEEP'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * SIZE, 0.7, -moveable.yPos * SIZE)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
        />
        <TRUCK
          v-if="moveable.classname == 'TRUCK'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * SIZE, 0.7, -moveable.yPos * SIZE)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
        />
        <TRACTOR
          v-if="moveable.classname == 'TRACTOR'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * SIZE, 0.7, -moveable.yPos * SIZE)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
        />
        <PIG
          v-if="moveable.classname == 'PIG'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * SIZE, 0.7, -moveable.yPos * SIZE)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
        />
        <TUPEL
          v-if="moveable.classname == 'TUPEL'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * SIZE, 0.7, -moveable.yPos * SIZE)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
        />
      </div>
    </Scene>
  </Renderer>
</template>
