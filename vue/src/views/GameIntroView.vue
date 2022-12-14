<script setup lang="ts">
/**
 * View to select either creating a new game from an existing world or to play an existing game
 * @author Finn Schindel, Astrid Klemmer
 */

import { useInstanceList } from "@/services/useInstanceList";
import GameListItem from "@/components/selectview/GameListItem.vue";
import { onMounted, ref } from "vue";

const { instanceState, getInstanceList } = useInstanceList();

onMounted(() => {
  getInstanceList("game");
  console.log(instanceState);
});
</script>

<template>
  <div>
    <div>
      <RouterLink to="/login"
        ><img src="../buttons/editor/arrow-left.png" alt=""
      /></RouterLink>

      <RouterLink to="/worldSelection">
        <button>
          <img src="../buttons/editor/plus.png" alt="" /> Neues Spiel erstellen
        </button>
      </RouterLink>

      <img src="" alt="Logo Fehlt" />
    </div>

    <hr />

    <div id="selection">
      <h1>Spiel beitreten</h1>
    </div>

    <div v-for="ele in instanceState.instancelist.instancelist">
      <RouterLink :to="{ path: '/joingame/' + ele.id }">
        <GameListItem
          :gamename="ele.gamename"
          :worldname="ele.worldname"
          :people="ele.playeramount"
        ></GameListItem>
      </RouterLink>
    </div>
  </div>
</template>

<style>
img {
  width: 5rem;
  height: 5rem;
}

button {
  text-align: center;
}
</style>
