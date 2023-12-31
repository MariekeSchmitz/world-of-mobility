<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
/**
 * @fileoverview This file contains the Game component.
 * it is the main component of the gamevue.
 * it contains the map, the camera and the renderer.
 * it also contains the logic for the camera.
 */
import * as THREE from "three";
import { ref, computed, onMounted, reactive, onUnmounted, watch } from "vue";
import { Camera, Scene, HemisphereLight, Renderer } from "troisjs";
import Map from "@/components/game/Map.vue";
import CAR1 from "@/components/objects/CAR1.vue";
import SHEEP from "@/components/objects/SHEEP.vue";
import { useGame } from "@/services/game/useGame";
import { useLogin } from "@/services/login/useLogin";
import { orientations } from "@/services/Orientations";
import TRUCK from "@/components/objects/TRUCK.vue";
import TRACTOR from "@/components/objects/TRACTOR.vue";
import PIG from "@/components/objects/PIG.vue";
import TUPEL from "@/components/objects/TUPEL.vue";

const squareSize = 16;

const props = withDefaults(
  defineProps<{
    instanceID: number;
  }>(),
  { instanceID: 1 }
);
const { sendCommand, receiveGameUpdate, mapUpdates, getUserMoveable, endReceiveGameUpdate } =
  useGame();
const { loginData } = useLogin();

const renderer = ref();
const camera = ref();

let thirdPerson = ref(true);
let freeCam = ref(true);
let switchedMode = false;
let switchedDirection = false;

/**
 * An offsetMap for every model.
 * Every model has an own egoperspective.
 */
const offsetMap: { [id: string]: THREE.Vector3 } = {
  CAR: new THREE.Vector3(-0.45, 2, 0.2),
  SHEEP: new THREE.Vector3(0, 1.6, -1),
  TRACTOR: new THREE.Vector3(0, 3, 0.8),
  TUPEL: new THREE.Vector3(0, 1.8, -0.35),
  PIG: new THREE.Vector3(0, 2.3, -0.6),
  TRUCK: new THREE.Vector3(-0.45, 3, -1.5),
};

const THIRDPERSOFFSET = new THREE.Vector3(0, 8, 15);
const FIRSTPERSONOFFSET = new THREE.Vector3(0, 0, 0.1);

/**
 * the specific offset of the driver in first person mode.
 */
const FirstPersonDriverOffset = new THREE.Vector3(-0.45, 2, 0.2);
const cameraOffset = ref(new THREE.Vector3(0, 0, 0));

const UPVECTOR = new THREE.Vector3(0, 1, 0);

const lookAt = reactive(new THREE.Vector3(0, 0, 0)); // has to be reactive somehow

/***
 * keeps every playerobject up to date.
 */
const allMoveables = computed(() => {
  return mapUpdates.moveableUpdates;
});

/**
 * returns the moveable object of the logged in user.
 */
const userMoveable = computed(() => {
  return getUserMoveable(loginData.username);
});

let oldPosition = ref(new THREE.Vector3(-1, -1, -1));
let playerPosition = ref(new THREE.Vector3(0, 0, 0));

/**
 *  watches for incoming changes in the userMovable object.
 *  calls functions that update everything needed for the camera and the camera itself.
 */
watch(userMoveable, () => {
  updatePlayerPositions();
  computeMovementVector();
  updatePlayerDirection();
  if (!(oldPlayerDirection == playerDirection)) {
    updateAzimuthAngle();
  }
  updatePlayerModel();
  computeLookAt();
  computeCameraPosition();
});

function updatePlayerPositions() {
  let tempPosition = new THREE.Vector3(0, 0, 0);
  if (playerPosition.value) {
    tempPosition = playerPosition.value.clone();
  }
  if (userMoveable.value != undefined) {
    playerPosition.value = new THREE.Vector3(
      userMoveable.value.xPos * squareSize,
      0,
      -userMoveable.value.yPos * squareSize
    );
  }
  if (oldPosition.value.equals(new THREE.Vector3(-1, -1, -1))) {
    oldPosition.value.copy(playerPosition.value);
  } else {
    oldPosition.value.copy(tempPosition);
  }
}

/**
 * computes the new movement vector.
 */
function computeMovementVector() {
  movementVector.value = playerPosition.value.clone().sub(oldPosition.value);
}
const movementVector = ref(new THREE.Vector3(0, 0, 0));

let oldPlayerDirection = "";
let playerDirection = "NORTH";

/**
 * updates the player direction.
 * oldPlayerDirection is needed to check if the direction has changed.
 */
function updatePlayerDirection() {
  if (userMoveable.value != undefined) {
    oldPlayerDirection = playerDirection;
    playerDirection = userMoveable.value.orientation;
    if (oldPlayerDirection != playerDirection) {
      switchDirection();
    }
  }
}

let oldPlayerModel = "";
let playerModel = "";
function updatePlayerModel() {
  if (userMoveable.value != undefined) {
    oldPlayerModel = playerModel;
    playerModel = userMoveable.value.classname;
    if (oldPlayerModel != playerModel) {
      FirstPersonDriverOffset.copy(offsetMap[playerModel]);
    }
  }
}

/**
 * computes the new lookAt for the camera.
 */
function computeLookAt() {
  if (userMoveable.value != undefined) {
    const newLookAt = playerPosition.value.clone();
    if (!thirdPerson.value) {
      const turnedDriverOffset = FirstPersonDriverOffset.clone().applyAxisAngle(
        UPVECTOR,
        -orientations[userMoveable.value.orientation]
      );
      newLookAt.add(turnedDriverOffset);
    }
    lookAt.copy(newLookAt);
  }
}

const trafficLightState = computed(() => {
  return mapUpdates.trafficLightState;
});

/**
 * returns the position of the camera.
 * keeps every playerobject up to date.
 */
const cameraPosition = ref(new THREE.Vector3(0, 0, 0));

/**
 * computes the new camera position.
 * third person mode:
 * we want the user to be able to look freely around his model.
 * changing the camera position wont reset on switching the direction.
 * first person mode:
 * the user can look around, but changing the direction will reset the camera position.
 * 
 * free cam mode disabled will disable the ability to move the camera around.
 * first person mode:
 * its position will be set to the position of the player
 * third person mode:
 * its position will be set to the position of the player + the camera offset.
 */
function computeCameraPosition() {
  if (
    freeCam.value &&
    camera.value &&
    !switchedMode &&
    ((!switchedDirection && !thirdPerson.value) || thirdPerson.value)
  ) {
    cameraPosition.value = camera.value.camera.position.add(
      movementVector.value
    );
  } else {
    if (userMoveable.value != undefined) {
      const turnedOffset = cameraOffset.value
        .clone()
        .applyAxisAngle(
          UPVECTOR,
          -orientations[userMoveable.value.orientation]
        );

      const newCameraPosition = playerPosition.value.clone().add(turnedOffset);
      if (!thirdPerson.value) {
        const turnedDriverOffset =
          FirstPersonDriverOffset.clone().applyAxisAngle(
            UPVECTOR,
            -orientations[userMoveable.value.orientation]
          );
        newCameraPosition.add(turnedDriverOffset);
      }
      cameraPosition.value = newCameraPosition;
    }
    switchedMode = false;
    switchedDirection = false;
  }
}

/**
 * switches the cam Mode
 */
function switchCamMode() {
  freeCam.value = !freeCam.value;
  if (renderer.value) {
    renderer.value.three.cameraCtrl.enabled =
      !renderer.value.three.cameraCtrl.enabled;
  }
  updateAzimuthAngle();
}

/**
 * switches from the third Person View to the Firstperson view and vica versa.
 * Sets the maxDistance of the OrbitControls to 0.01 if the firstperson view is active.
 * Sets the maxDistance of the OrbitControls to 20 if the thirdperson view is active.
 */
function switchPerspective() {
  thirdPerson.value = !thirdPerson.value;
  updateCameraOffset();
  updateMaxDistance();
  updateAzimuthAngle();
  switchedMode = true;
}

/**
 * switches the switchedDirection-flag of the player.
 */
function switchDirection() {
  switchedDirection = !switchedDirection;
}

/**
 * updates the cameraOffset.
 */
function updateCameraOffset() {
  if (thirdPerson.value) {
    cameraOffset.value.copy(THIRDPERSOFFSET);
  } else {
    cameraOffset.value.copy(FIRSTPERSONOFFSET);
  }
}

/**
 * updates the maxDistance of the OrbitControls.
 */
function updateMaxDistance() {
  if (thirdPerson.value) {
    if (renderer.value) {
      const orbitControls = renderer.value.three.cameraCtrl;
      orbitControls.maxDistance = 20;
    }
  } else {
    if (renderer.value) {
      const orbitControls = renderer.value.three.cameraCtrl;
      orbitControls.maxDistance = 0.01;
    }
  }
}

/**
 * updates the azimuthAngle of the OrbitControls.
 */
function updateAzimuthAngle() {
  if (!renderer.value || !userMoveable.value) return;
  const orbitControls = renderer.value.three.cameraCtrl;
  if (freeCam.value && !thirdPerson.value) {
    orbitControls.minAzimuthAngle =
      -orientations[userMoveable.value.orientation] - Math.PI / 2 + 0.1;
    orbitControls.maxAzimuthAngle =
      -orientations[userMoveable.value.orientation] + Math.PI / 2;
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

  switchPerspective();
  switchPerspective();
  receiveGameUpdate(props.instanceID);
  document.addEventListener("keyup", handleKeyEvent);
});

/**
 * removes the keylistener.
 */
onUnmounted(() => {
  document.removeEventListener("keyup", handleKeyEvent);
  endReceiveGameUpdate();
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
        :squareSize="squareSize"
      ></Map>
      <div v-for="(moveable, index) in allMoveables" :key="index">
        <CAR1
          v-if="moveable.classname == 'CAR'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * squareSize, 0.7, -moveable.yPos * squareSize)
          "
          :rotation="-orientations[moveable.orientation]"
          :name="moveable.user"
        />
        <SHEEP
          v-if="moveable.classname == 'SHEEP'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * squareSize, 0.7, -moveable.yPos * squareSize)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
          :name="moveable.user"
        />
        <TRUCK
          v-if="moveable.classname == 'TRUCK'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * squareSize, 0.7, -moveable.yPos * squareSize)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
          :name="moveable.user"
        />
        <TRACTOR
          v-if="moveable.classname == 'TRACTOR'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * squareSize, 0.7, -moveable.yPos * squareSize)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
          :name="moveable.user"
        />
        <PIG
          v-if="moveable.classname == 'PIG'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * squareSize, 0.7, -moveable.yPos * squareSize)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
          :name="moveable.user"
        />
        <TUPEL
          v-if="moveable.classname == 'TUPEL'"
          :scale="new THREE.Vector3(1, 1, 1)"
          :position="
            new THREE.Vector3(moveable.xPos * squareSize, 0.7, -moveable.yPos * squareSize)
          "
          :rotation="-orientations[moveable.orientation]"
          :type="moveable.classname"
          :name="moveable.user"
        />
      </div>
    </Scene>
  </Renderer>
</template>
