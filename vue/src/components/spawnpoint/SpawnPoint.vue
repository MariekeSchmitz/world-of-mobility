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

const emit = defineEmits<{
  (e: "setSpawnPoint"): void;
}>();

const testObj = ref({
  tiles: [
    [
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "SOUTH",
        placedObjects: [],
        type: "STREET_CROSS",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "GRASSTILE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "RAIL_CURVE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "RAIL_STRAIGHT",
      },
    ],
    [
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "SIDEWAY",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "STREET_CURVE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "STREET_STRAIGHT",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "STREET_T_CROSS",
      },
    ],
    [
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "STREET_CROSS",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "GRASSTILE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "RAIL_CURVE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "RAIL_STRAIGHT",
      },
    ],
    [
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "SIDEWAY",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "STREET_CURVE",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
        type: "STREET_STRAIGHT",
      },
      {
        allowedDirections: ["NORTH", "EAST", "SOUTH", "WEST"],
        orientation: "NORTH",
        placedObjects: [],
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

<template>
  <div >
    <div class="flex flex-col-reverse">
      <div v-for="(tileRow, y) in testObj.tiles" class="flex flex-row mx-auto bg-greenDark">
        <div v-for="(tile, x) in tileRow" class="flex flex-row">
          <SimplifiedTile
            :tile-type="tile.type"
            :orientation="tile.orientation"
            :x-index="x"
            :y-index="y"
            @set-spawn-point="emit('setSpawnPoint')"
          />
        </div>
      </div>
    </div>
  </div>
</template>

