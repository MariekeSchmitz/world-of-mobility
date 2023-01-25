<script setup lang="ts">
import { onMounted, onUnmounted, ref, type Ref } from "vue";
import SimplifiedTile from "@/components/simplifiedTile/SimplifiedTile.vue";
import { useSpawnPoint } from "@/components/spawnpoint/useSpawnPoint";
import { useMap, type IMapDTO, type IPlacedObject } from "@/services/useMap";

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

function nonNullTile(str: IPlacedObject) {
  return str ? str.type : "";
}

const testObj: Ref<IMapDTO> = ref({
  tiles: [
    [
      {
        type: "",
        orientation: "",
        placedObject: {
          type: ""
        }
      }
    ]
  ],
  NPCS: [

  ],
  name: ""
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
  <div id="spanwpoint-container">
    <div v-for="(tileRow, y) in testObj.tiles" :key="testObj.name" id="tile-row">
      <div v-for="(tile, x) in tileRow" :key="tile.type" id="tile-column">
        <SimplifiedTile :tile-type="tile.type" :orientation="tile.orientation" :x-index="x" :y-index="y" :asset="nonNullTile(tile.placedObject)"/>
      </div>
    </div>
  </div>
</template>

