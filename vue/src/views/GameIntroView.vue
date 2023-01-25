<script setup lang="ts">
/**
 * View to select either creating a new game from an existing world or to play an existing game
 * @author Finn Schindel, Astrid Klemmer
 */

import { useInstanceList } from "@/services/useInstanceList";
import GameListItem from "@/components/selectview/GameListItem.vue";
import { computed, onMounted } from "vue";
import type { IInstanceInfo } from "@/services/IInstanceInfo";

const { instanceState, getInstanceList } = useInstanceList();

onMounted(async () => {
  await getInstanceList("game");
});

const instancelist = computed(() => {
  let list:IInstanceInfo[] = []
  list = instanceState.instancelist.filter((instance: IInstanceInfo) => {
      if(instance.maxPlayerCount != instance.playeramount){
        return instance
      }
    }
  );
  return list;
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

    <div v-if="instancelist.length > 0">
      <div v-for="ele in instancelist">
        <RouterLink :to="{ path: '/joingame/' + ele.id }">
          <GameListItem
            :gamename="ele.gamename"
            :worldname="ele.worldname"
            :people="ele.playeramount"
          ></GameListItem>
        </RouterLink>
      </div>
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
