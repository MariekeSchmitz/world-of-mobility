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

const renderer = ref();
const camera = ref();
const car = ref();

//TODO: dynamisch anpassen
const instanceID = 1;
const user = "NPC0";

const { sendCommand, receiveGameUpdate, mapUpdates } = useGame();

//TODO: could be an interface
const gameControl = "";

const restPath = `/${instanceID}/game-command`;

const cameraOffset = reactive(new Vector3(0, 8, -15));

const positionTemp = reactive(new Vector3(15, 1, 15));

const cameraPosition = computed(() => {
  const vecTempObj = positionTemp.clone();
  const vecTempCar = cameraOffset.clone();
  return vecTempObj.add(vecTempCar);
});

const allMoveables = computed(() => {
      console.log(mapUpdates.moveableUpdates);
      return mapUpdates.moveableUpdates;
});

onMounted(() => {
  const orbitControls = renderer.value.three.cameraCtrl;
  const cameraControls = camera.value.camera;
  const carMesh = car.value.mesh;
  orbitControls.target = positionTemp;
  orbitControls.enablePan = false;

  orbitControls.screenSpacePanning = false;
  //orbitControls.minPolarAngle = Math.PI/2;
  orbitControls.maxPolarAngle = Math.PI / 2;
  //orbitControls.maxAzimuthAngle = 0;
  //orbitControls.minAzimuthAngle = 0;
  document.addEventListener("keyup", (e) => {
    if (e.code === "KeyW") {
      sendCommand(instanceID, user, "SPEED_UP");
    } else if (e.code === "KeyS") {
      sendCommand(instanceID, user, "SPEED_DOWN");
    } else if (e.code === "KeyA") {
      sendCommand(instanceID, user, "RIGHT");
    } else if (e.code === "KeyD") {
      sendCommand(instanceID, user, "LEFT");
    } else if (e.code === "KeyO") {
      const degree = Math.PI / 4;
      cameraOffset.applyAxisAngle(new Vector3(0, 1, 0), degree);
      cameraControls.position.set(cameraPosition.value);
      carMesh.rotateY(degree);
      orbitControls.update();
    }
  });

  receiveGameUpdate(instanceID);
});

const pi = MathUtils.degToRad(180);

function rotation(value: number) {
  return (value * pi) / 4;
}

function orientation2angle(orientation: string) {
  switch(orientation) {
    case "NORTH":
      return rotation(0)
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
      <div v-for="moveable in allMoveables">
        <Car v-if="moveable.classname === 'Passenger'" :pos="new Vector3( moveable.xPos, 1, moveable.yPos )" :rotation="orientation2angle(moveable.orientation)">

        </Car>
      </div>
    </Scene>
  </Renderer>
</template>
