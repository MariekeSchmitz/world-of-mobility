<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
import { ref, computed, onMounted, reactive } from "vue";
import {
  AmbientLight,
  Box,
  Camera,
  Scene,
  PointLight,
  Renderer,
  ToonMaterial,
  Texture,
} from "troisjs";
import Map from "@/components/Map.vue";
import Car from "@/components/objects/Car.vue";
import { MathUtils, Vector3 } from "three";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";

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
const car = ref();

//TODO: could be an interface
const gameControl = "";
const degree = Math.PI / 4;

const restPath = `/${props.instanceID}/game-command`;

let thirdPerson = true;
let freeCam = true;
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
  if (freeCam && camera.value) {
    return camera.value.camera.position.add(movementVector);
  } else {
    const vecTempOffset = cameraOffset.clone();
    if (userMovable.value != undefined) {
      vecTempOffset.applyAxisAngle(
        upVector,
        orientation2angle(userMovable.value.orientation)
      );
    }
    return vecTempTarget.add(vecTempOffset);
  }
});

const allMoveables = computed(() => {
  //console.log(mapUpdates.moveableUpdates);
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
 * In this method we set the KeyListner for the Gameview
 * and setup the cam to work as intended.
 */
onMounted(() => {
  const orbitControls = renderer.value.three.cameraCtrl;
  const cameraControls = camera.value.camera;
  orbitControls.target = lookAt;
  orbitControls.enablePan = false;

  orbitControls.screenSpacePanning = false;
  //orbitControls.minPolarAngle = Math.PI/2;
  orbitControls.maxPolarAngle = Math.PI / 2;
  //orbitControls.maxAzimuthAngle = 0;
  //orbitControls.minAzimuthAngle = 0;
  document.addEventListener("keyup", (e) => {
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
      freeCam = !freeCam;
      orbitControls.enableRotate = !orbitControls.enableRotate;
    }
  });

  receiveGameUpdate(props.instanceID);
});

const pi = MathUtils.degToRad(180);

/**
 * returns the radiant value of a passed amount of steps - value.
 * @param value each step represents 1/8 of an 360* turn.
 */
function rotation(value: number) {
  return (value * pi) / 4;
}
/**
 * returns the rotation value as radiant based of our orientation.
 * calls the rotation method for that.
 * @param orientation the orientation of an object as string.
 */
function orientation2angle(orientation: string) {
  switch (orientation) {
    case "NORTH":
      return rotation(0);
    case "NORTH_EAST":
      return rotation(1);
    case "EAST":
      return rotation(2);
    case "SOUTH_EAST":
      return rotation(3);
    case "SOUTH":
      return rotation(4);
    case "SOUTH_WEST":
      return rotation(5);
    case "WEST":
      return rotation(6);
    case "NORTH_WEST":
      return rotation(7);
    default:
      return rotation(0);
  }
}

function switchPerspective() {
  thirdPerson = !thirdPerson;
  if (thirdPerson) {
    cameraOffset.copy(thirdPersonOffset);
  } else {
    cameraOffset.copy(firstPersonOffset);
  }
}
</script>

<template>
  <Renderer ref="renderer" antialias :orbit-ctrl="true" resize="window">
    <Camera :position="cameraPosition" ref="camera" />
    <Scene background="#97FFFF">
      <!-- Light -->
      <PointLight :position="{ x: 0, y: 0, z: 10 }" />
      <AmbientLight :intensity="0.1" color="#ff6000"></AmbientLight>
      <!-- Map -->
      <Map></Map>
      <!-- "Car" -->
      <Box
        :position="{ x: 1, y: 1, z: 2 }"
        :scale="{ x: 1, y: 1, z: 2 }"
        ref="car"
        ><ToonMaterial>
          <Texture src="src\textures\Obsidian.jpg" /> </ToonMaterial
      ></Box>

      <div v-for="(moveable, index) in allMoveables" :key="index">
        <Car
          :pos="new Vector3(moveable.xPos, 0, moveable.yPos)"
          :rotation="orientation2angle(moveable.orientation)"
        ></Car>
      </div>
    </Scene>
  </Renderer>
</template>
