<script setup lang="ts">
/**
 * A clickable minimap, that let's the user choose a tile to spawn on, before starting a game
 */
import { onMounted, onUnmounted, ref, type Ref } from "vue";
import SimplifiedTile from "@/components/simplifiedTile/SimplifiedTile.vue";
import { useSpawnPoint } from "@/services/useSpawnPoint"
import { useMap, type IMapDTO, type IPlacedObject } from "@/services/game/useMap";

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

/**
 * Checks if an IPlacedObject is null
 * @param str An IPlacedObject
 * @returns a string representing the object-type or an empty string
 */
function nonNullTile(str: IPlacedObject) {
  return str ? str.type : "";
}

/**
 * Object used for initial loading while the real map is being fetched
 */
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
  <div >
    <div class="flex flex-col-reverse">
      <div v-for="(tileRow, y) in testObj.tiles" :key="testObj.name" class="flex flex-row mx-auto bg-greenDark">
        <div v-for="(tile, x) in tileRow" :key="tile.type" class="flex flex-row">
          <SimplifiedTile :tile-type="tile.type" :orientation="tile.orientation" :x-index="x" :y-index="y" :asset="nonNullTile(tile.placedObject)" @set-spawn-point="emit('setSpawnPoint')"/>
        </div>
      </div>
    </div>
  </div>
</template>

