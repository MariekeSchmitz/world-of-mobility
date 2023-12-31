<template>
  <div id="miniMap">
    <div id="positionIndicator"></div>
    <div id="minimap-container">
      <div v-for="(tileRow, y) in testObj.tiles" :key="testObj.name" id="tile-row">
        <div v-for="(tile, x) in tileRow" :key="tile.type" id="tile-column">
          <MiniMapTile :tile-type="tile.type" :orientation="tile.orientation" :x-index="x" :y-index="y" :asset="nonNullTile(tile.placedObject)" :user="props.user" :row-count="rowCount" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * A Minimap, that displays the various objects placed and the current position on the Map
 */
import { computed, onMounted, ref, type Ref } from "vue";
import MiniMapTile from "@/components/miniMapTile/MiniMapTile.vue";
import { useMap, type IMapDTO, type IPlacedObject } from "@/services/game/useMap";
import { useGame } from "@/services/game/useGame";

const { getGameMap } = useMap();
const { getUserMoveable } = useGame();

const props = withDefaults(
  defineProps<{
    instanceId: number,
    user: string
  }>(),{
    instanceId: 1,
    user: ""
  }
);

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

/**
 * Number of rows / columns of the minimap
 */
const rowCount = computed(() => testObj.value.tiles[0].length);
const userMoveable = computed(() => getUserMoveable(props.user));

/**
 * Element that contains the minimap
 */
let miniCont = document.getElementById('minimap-container');

/**
 * Indicates the size of the position indicator
 */
const posIndicatorSize = computed(() => {
  const miniConti = document.getElementById('positionIndicator');
  if(!miniConti) {
    return 0;
  }
  return miniConti.offsetHeight;
});

const playerXPos = computed(() => {
  miniCont = document.getElementById('minimap-container');
  if(userMoveable.value) {
    if(!miniCont) {
      return 0;
    }
    const relPos = miniCont.offsetHeight * (userMoveable.value.xPos / rowCount.value) - posIndicatorSize.value / 2;
    return `${relPos}px`;
  } else {
    return 0;
  }
});

const playerYPos = computed(() => {
  if(userMoveable.value) {
    if(!miniCont) {
      return 0;
    }
    const relPos = miniCont.offsetHeight - miniCont.offsetHeight * (userMoveable.value.yPos / rowCount.value) - posIndicatorSize.value / 2;
    return `${relPos}px`;
  } else {
    return 0;
  }
});

onMounted(async () => {
  testObj.value = await getGameMap(props.instanceId);
});
</script>

<style scoped>

#miniMap {
  position: absolute;
  width: 15vw;
  height: 15vw;
  box-shadow: 0 0 15px 5px rgb(23, 23, 23);
}

#positionIndicator {
  position: absolute;
  height: 10px;
  width: 10px;
  background-color: red;
  z-index: 20;
  border-radius: 5px;
  top: v-bind(playerYPos);
  left: v-bind(playerXPos);

  animation-name: posAnimation;
  animation-iteration-count: infinite;
  animation-direction: alternate;
  animation-duration: 0.5s;
}

@keyframes posAnimation {
  from {
    transform: scale(75%);
  }
  to {
    transform: scale(125%);
  }
}

#minimap-container {
  position: absolute;
  background-color: rgb(27, 154, 61);
  display: flex;
  flex-direction: column-reverse;
  width: 15vw;
  height: 15vw;
  min-width: 100px;
  min-height: 100px;
  border: 2px solid rgb(27, 154, 61);
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
