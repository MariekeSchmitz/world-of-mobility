<script setup lang="ts">

import { onMounted, ref } from "vue";
import GameListItem from "@/components/selectview/GameListItem.vue";

import { useMapOverview } from "@/services/useMapOverview";
import type { RouterLink } from "vue-router";
import router from "@/router";

const { mapsOverview, getMaps } = useMapOverview()


onMounted( async() => {
  await getMaps()
})

function changeView(name : string) {
  console.log(name)
  router.push("/gameConfig/"+name)  
}


</script>

<template>
    <div class="wrapper">
        <h1>Welt für neues Spiel wählen</h1>
        <div class="flex-box">
            <div v-for="map in (mapsOverview.allMaps)">
                <GameListItem @click="changeView(map.mapName)" :worldname="(map.mapName)"></GameListItem>
            </div>
        </div>
    </div>

</template>

<style scoped>


.flex-box {
	display:flex; 
	justify-content: space-around;
}
.flex-box > div {
	display: flex;
	flex-direction: row;
	align-items:center;
}

.wrapper {
  display: grid;
  place-items: center;
}

button {
  padding: 1em;
  border-radius: 1em;
  background-color: antiquewhite;
  width: 10rem;
  margin: 10px;
}
</style>