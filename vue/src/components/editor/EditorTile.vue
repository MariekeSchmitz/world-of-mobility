<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import ContextMenu from "@/components/editor/ContextMenu.vue";
import PlacedObject from "@/components/editor/PlacedObject.vue";
import { Plane, Texture, BasicMaterial } from "troisjs";
import { ref, computed, watch, onMounted } from "vue";
import { usePlaceState } from "@/services/editor/usePlaceState";
import { useContextMenu } from "@/services/editor/useContextMenu";
import { useMapUpdate } from "@/services/useMapUpdate";
import type { ExportTile } from "@/services/editor/ExportTileInterface";
import { usePlaceObject } from "@/services/usePlaceObject";
import { ControlEnum } from "@/services/ControlEnum";
import { NpcType } from "@/services/editor/NpcType";
import { usePlaceNpc } from "@/services/editor/usePlaceNpc";
import type { INpc } from "@/interfaces/INpc";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    placedObject: string;
    editorID: number;
    cmVisible: boolean;
    placedNpc?: INpc;
  }>(),
  { width: 0.99, height: 0.99, cmVisible: false }
);

const cmVisible = ref(false);
let texturePath = "../src/textures/editor/" + props.type + ".jpg";

const { readPlaceState } = usePlaceState();
const { readCMState, setCMState } = useContextMenu();
const { sendMapUpdates } = useMapUpdate(props.editorID);
const { placeObject } = usePlaceObject();
const { placeNpc } = usePlaceNpc();

const mapWidth = ref(8);
const mapHeight = ref(8);
const offsetx = computed(() => -(mapWidth.value + 1) / 2);
const offsety = computed(() => -(mapHeight.value + 1) / 2);

watch(readCMState.value, () => {
  cmVisible.value = false;
});

function tileHover(event: any) {
  //console.log("tileHover: ",event);
  event.component.mesh.material.color.set(event.over ? "#dddddd" : "#ffffff");
}

/**
 * If a Tile/Object has been Selected, handles the Placement Logic
 * @param tileObject Object to be operated on
 *
 * Author: Timothy Doukhin & Astrid Klemmer
 */

function placeItem() {
  let posX = props.position.x - offsetx.value - 1;
  let posY = props.position.y - offsety.value - 1;
  setCMState();

  if (readPlaceState.value.isTile) {
    if (props.type != "GRASSTILE") {
      //make contextMenu visible after removing all ContextMenus from all tiles
      setTimeout(() => {
        cmVisible.value = true;
      }, 10);
    } else if (props.placedObject == "none") {
      let toSendObj: ExportTile = {
        type: readPlaceState.value.type,
        orientation: "NORTH",
        xPos: posX,
        yPos: posY,
        control: "PLACE",
      };

      sendMapUpdates(toSendObj);
    }
  } else if (!readPlaceState.value.isTile) {
    if (
      readPlaceState.value.type === NpcType.PASSENGER ||
      readPlaceState.value.type === NpcType.MOTORIZED
    ) {
      placeNpc(posY, posX, readPlaceState.value.type, props.editorID);
    } else {
      sendPlaceObject(posX, posY, props.placedObject);
    }
  }
}

/**
 * Object Placement Logic to add or remove it
 * @param posX X position of tile that object is placed on
 * @param posY Y position of tile that object is placed on
 * @param placedObject object that is or isnt placed on tile
 * @author Astrid Klemmer
 */
async function sendPlaceObject(
  posX: number,
  posY: number,
  placedObject: string
) {
  let isPlaced: boolean;
  if (readPlaceState.value.type == "REMOVE" && placedObject != "none") {
    isPlaced = await placeObject(
      props.editorID,
      placedObject,
      ControlEnum.REMOVE,
      posX,
      posY
    );
  } else if (readPlaceState.value.type != "REMOVE") {
    isPlaced = await placeObject(
      props.editorID,
      readPlaceState.value.type,
      ControlEnum.ADD,
      posX,
      posY
    );
  }
}

/**
 * Rest request to turn Tile to the Left
 * @param callingObject Tile-Object that the function operates on
 *
 * Author: Timothy Doukhin
 */
function turnLeft() {
  let posX = props.position.x - offsetx.value - 1;
  let posY = props.position.y - offsety.value - 1;
  let turnleftDTO: ExportTile = {
    type: "SIDEWAY",
    orientation: "NORTH",
    xPos: posX,
    yPos: posY,
    control: "TURN_LEFT",
  };
  sendMapUpdates(turnleftDTO);
}

/**
 * Rest request to turn Tile to the Right
 * @param callingObject Tile-Object that the function operates on
 *
 * Author: Timothy Doukhin
 */
function turnRight() {
  let posX = props.position.x - offsetx.value - 1;
  let posY = props.position.y - offsety.value - 1;
  console.log("POSX=", posX, " POSY=", posY);
  let turnrightDTO: ExportTile = {
    type: "SIDEWAY",
    orientation: "NORTH",
    xPos: posX,
    yPos: posY,
    control: "TURN_RIGHT",
  };
  sendMapUpdates(turnrightDTO);
}

/**
 * Rest request to remove current Tile
 * @param callingObject Tile-Object that the function operates on
 *
 * Author: Timothy Doukhin
 */
function removeTile() {
  let posX = props.position.x - offsetx.value - 1;
  let posY = props.position.y - offsety.value - 1;
  let removeDTO: ExportTile = {
    type: "SIDEWAY",
    orientation: "NORTH",
    xPos: posX,
    yPos: posY,
    control: "REMOVE",
  };
  sendMapUpdates(removeDTO);
}
</script>
<template>
  <Plane
    @click="placeItem"
    @pointer-over="tileHover"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position"
  >
    <BasicMaterial>
      <Texture v-bind:src="texturePath" />
    </BasicMaterial>
  </Plane>

  <ContextMenu
    v-if="cmVisible"
    v-on:turnRight="turnRight()"
    v-on:turnLeft="turnLeft()"
    v-on:removeTile="removeTile()"
    :width="0.8"
    :height="0.3"
    :position="props.position.clone().add(new THREE.Vector3(0, 0.4, 0.03))"
  ></ContextMenu>

  <PlacedObject
    v-if="props.placedObject != 'none'"
    :type="props.placedObject"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position.clone().add(new THREE.Vector3(0.1, -0.1, 0.02))"
  ></PlacedObject>

  <PlacedObject
    v-if="props.placedNpc"
    :type="'TREE'"
    :width="props.width"
    :height="props.height"
    :rotation="props.rotation"
    :position="props.position.clone().add(new THREE.Vector3(0.1, -0.1, 0.02))"
  ></PlacedObject>
</template>
