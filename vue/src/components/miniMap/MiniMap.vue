<template>
  <div id="minimap-container">
    <div v-for="(tileRow, y) in testObj.tiles" :key="testObj.name" id="tile-row">
      <div v-for="(tile, x) in tileRow" :key="tile.type" id="tile-column">
        <MiniMapTile :tile-type="tile.type" :orientation="tile.orientation" :x-index="x" :y-index="y" :asset="nonNullTile(tile.placedObject)" :user="props.user" :row-count="rowCount" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref, type Ref } from "vue";
import MiniMapTile from "@/components/miniMapTile/MiniMapTile.vue";
import { useMap, type IMapDTO, type IPlacedObject } from "@/services/useMap";
import { useGame } from "@/services/useGame";

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

const rowCount = computed(() => testObj.value.tiles[0].length)

onMounted(async () => {
  testObj.value = await getGameMap(props.instanceId);
  console.log('I am here')
});
</script>

<style scoped>
#minimap-container {
  box-sizing: border-box;
  position: absolute;
  background-color: rgb(66, 66, 66);
  display: flex;
  flex-direction: column-reverse;
  width: 15vw;
  height: 15vw;
  min-width: 100px;
  min-height: 100px;
}

#minimap-container > * {
  flex-basis: 100%;
}

#tile-row {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 100%;
}

#tile-row > * {
  flex-basis: 100%;
  width: 100%;
  height: 100%;
}

</style>
