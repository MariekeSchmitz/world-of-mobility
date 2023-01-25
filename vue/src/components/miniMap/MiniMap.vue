<template>
  <div id="minimap-container">
    <div v-for="(tileRow, y) in testObj.tiles" :key="testObj.name" id="tile-row">
      <div v-for="(tile, x) in tileRow" :key="tile.type" id="tile-column">
        <MiniMapTile :tile-type="tile.type" :orientation="tile.orientation" :x-index="x" :y-index="y" :asset="nonNullTile(tile.placedObject)" :user="props.user" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref, type Ref } from "vue";
import MiniMapTile from "@/components/miniMapTile/MiniMapTile.vue";
import { useSpawnPoint } from "@/components/spawnpoint/useSpawnPoint";
import { useMap, type IMapDTO, type IPlacedObject } from "@/services/useMap";
import { useGame } from "@/services/useGame";

const { miniMapScalingState } = useSpawnPoint();
const { getGameMap } = useMap();
const { getUserMovable } = useGame();

const props = withDefaults(
  defineProps<{
    instanceId: number,
    user: string
  }>(),{}
);

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
  console.log('I am here')
});
</script>

<style scoped>
#minimap-container{
  position: absolute;
  background-color: rgb(66, 66, 66);
  display: flex;
  flex-direction: column-reverse;
}

#tile-row {
  display: flex;
  flex-direction: row;
}

</style>
