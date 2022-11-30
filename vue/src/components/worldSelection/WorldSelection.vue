<script setup lang="ts">

import { ref } from "vue";
import { useMapOverview } from "@/services/useMapOverview";

const { mapsOverview } = useMapOverview()

</script>

<template>
    <div class="wrapper">
        <h1>Welt für neues Spiel wählen</h1>
        <ul>
            <div class ="square" v-for="map in (mapsOverview.maps)">
            
            </div>
        </ul>

    </div>

</template>

<style scoped>

.square {
    width: 12.5rem;
    height: 15rem;
    background-color: red;
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