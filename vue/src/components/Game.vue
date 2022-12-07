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
import { Orientation } from "@/services/editor/OrientationEnum";
import { useLogin } from "@/services/login/useLogin";

const props = withDefaults(
  defineProps<{
    instanceID: number;
    user: string;
  }>(),
  { instanceID: 1, user: "NPC0" }
);
const { sendCommand, receiveGameUpdate, mapUpdates } = useGame();
const { loginData } = useLogin();

const renderer = ref();
const camera = ref();
const car = ref();
let playerOrientation = reactive({ o: Orientation.NORTH });


//TODO: could be an interface
const gameControl = "";
const degree = Math.PI / 4;

const restPath = `/${props.instanceID}/game-command`;

let thirdPerson = true;
const thirdPersonOffset = new Vector3(0, 8, -15);
const firstPersonOffset = new Vector3(0, 1, -3);
const cameraOffset = reactive(new Vector3(0, 8, -15));
const upVector = new Vector3(0, 1, 0);

const positionTemp = reactive(new Vector3(15, 1, 15));

const cameraPosition = computed(() => {
  const vecTempTarget = positionTemp.clone();
  const vecTempOffset = cameraOffset.clone();
  vecTempOffset.applyAxisAngle(upVector, playerOrientation.o);
  return vecTempTarget.add(vecTempOffset);
});

const allMoveables = computed(() => {
  //console.log(mapUpdates.moveableUpdates);
  return mapUpdates.moveableUpdates;
});
/**
 * In this method we set the KeyListner for the Gameview
 * and setup the cam to work as intended.
 */
onMounted(() => {
  const orbitControls = renderer.value.three.cameraCtrl;
  const cameraControls = camera.value.camera;
  orbitControls.target = positionTemp;
  orbitControls.enablePan = false;

  orbitControls.screenSpacePanning = false;
  //orbitControls.minPolarAngle = Math.PI/2;
  orbitControls.maxPolarAngle = Math.PI / 2;
  //orbitControls.maxAzimuthAngle = 0;
  //orbitControls.minAzimuthAngle = 0;
  document.addEventListener("keyup", (e) => {
    if (e.code === "KeyW") {
      playerOrientation.o = Orientation.NORTH;
      sendCommand(props.instanceID, loginData.username, "SPEED_UP");
    } else if (e.code === "KeyS") {
      playerOrientation.o = Orientation.SOUTH;
      sendCommand(props.instanceID, loginData.username, "SPEED_DOWN");
    } else if (e.code === "KeyA") {
      sendCommand(props.instanceID, loginData.username, "RIGHT");
      playerOrientation.o = Orientation.WEST;
    } else if (e.code === "KeyD") {
      sendCommand(props.instanceID, loginData.username, "LEFT");
      playerOrientation.o = Orientation.EAST;
    } else if (e.code === "KeyV") {
      switchPerspective();
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
      <Box :position="positionTemp" :scale="{ x: 1, y: 1, z: 2 }" ref="car"
        ><ToonMaterial>
          <Texture src="src\textures\Obsidian.jpg" /> </ToonMaterial
      ></Box>
      <Car :pos="positionTemp" :rotation="orientation2angle('NORTH')"> </Car>

      <div v-for="(moveable, index) in allMoveables" :key="index">
        <Car
          v-if="moveable.classname === 'Passenger'"
          :pos="new Vector3(moveable.xPos, 1, moveable.yPos)"
          :rotation="orientation2angle(moveable.orientation)"
        ></Car>
      </div>
    </Scene>
  </Renderer>
</template>
