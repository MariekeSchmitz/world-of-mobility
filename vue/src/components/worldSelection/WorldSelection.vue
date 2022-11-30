<script setup lang="ts">

import { ref } from "vue";
import GameListItem from "@/components/selectview/GameListItem.vue";

import { useMapOverview } from "@/services/useMapOverview";

const { mapsOverview } = useMapOverview()

</script>

<template>
    <div class="wrapper">
        <h1>Welt für neues Spiel wählen</h1>
        <div class="flex-box">
            <div v-for="map in (mapsOverview.maps)">
                <GameListItem :worldname="(map.name)"></GameListItem>
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