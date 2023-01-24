<template>
  <div id="spanwpoint-container">
    <div v-for="(tileRow, y) in testObj.tiles" :key="testObj.name" id="tile-row">
      <div v-for="(tile, x) in tileRow" :key="tile.type" id="tile-column">
        <SimplifiedTile :tile-type="tile.type" :orientation="tile.orientation" :x-index="x" :y-index="y" :asset="tile.placedObject"/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref } from "vue";
import SimplifiedTile from "@/components/simplifiedTile/SimplifiedTile.vue";
import { useSpawnPoint } from "@/components/spawnpoint/useSpawnPoint";
import { useMap } from "@/services/useMap";

const {
  miniMapScalingState,
  addWindowWidthListener,
  removeWindowWIdthListener,
} = useSpawnPoint();
const { getGameMap } = useMap();

const props = withDefaults(
  defineProps<{
    instanceId: number;
  }>(),
  {
    instanceId: 1,
  }
);

const testObj = ref({
  tiles: [
    [
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "SOUTH",
        placedObject: "",
        type: "STREET_CROSS",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "GRASSTILE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "RAIL_CURVE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "RAIL_STRAIGHT",
      },
    ],
    [
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "SIDEWAY",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "STREET_CURVE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "STREET_STRAIGHT",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "STREET_T_CROSS",
      },
    ],
    [
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "STREET_CROSS",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "GRASSTILE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "RAIL_CURVE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "RAIL_STRAIGHT",
      },
    ],
    [
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "SIDEWAY",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "STREET_CURVE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "STREET_STRAIGHT",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObject: "",
        type: "STREET_T_CROSS",
      },
    ],
  ],
  name: "test",
  npcs: [
    {
      orientation: "NORTH",
      xPos: 0.0,
      yPos: 0.0,
      maxVelocity: 1.0,
      capacity: 1.0,
      currentVelocity: 0.0,
      script: "",
      type: "PASSANGER",
    },
  ],
});

onMounted(async () => {
  testObj.value = await getGameMap(props.instanceId);
  const numberOfRows = testObj.value.tiles[0].length;
  addWindowWidthListener(numberOfRows);
});

onUnmounted(() => {
  removeWindowWIdthListener();
});
</script>

<style scoped>
#spanwpoint-container {
  height: v-bind("miniMapScalingState.windowWidth");
  width: v-bind("miniMapScalingState.windowWidth");
  background-color: rgb(66, 66, 66);
  display: flex;
  flex-direction: column-reverse;
}

#tile-row {
  display: flex;
  flex-direction: row;
}

</style>
