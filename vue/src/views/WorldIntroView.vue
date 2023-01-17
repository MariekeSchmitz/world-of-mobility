<script setup lang="ts">
/**
 * View to select either creating a new World or to continue building an existing world
 * @author Finn Schindel, Astrid Klemmer
 */

import GameListItem from "@/components/selectview/GameListItem.vue";
import { useInstanceList } from "@/services/useInstanceList";
import { computed, reactive, ref } from "@vue/reactivity";
import { onMounted, watch } from "vue";
import { useMapOverview } from "@/services/useMapOverview";
import { useEditor } from "@/services/useEditor";
import { RouterLink } from "vue-router";
import router from "@/router";
import { useUserEditor } from "@/services/useUserEditor";
import { useLogin } from "@/services/login/useLogin";

const { instanceState, getInstanceList } = useInstanceList();
const { mapsOverview, getMaps } = useMapOverview();
const { createWorld } = useEditor();
const { joinEditor } = useUserEditor();
const { loginData } = useLogin();

interface MaplistState {
  maplist: Array<string>;
}

const maplistState: MaplistState = reactive({ maplist: [] });

onMounted(async () => {
  await getMaps();
  await getInstanceList("editor");
});

const instancelist = computed(() => {
  maplistState.maplist = mapsOverview.allMaps.map((map) => map.mapName);
  if (maplistState.maplist != undefined) {
    for (let i = maplistState.maplist.length - 1; i >= 0; i--) {
      for (let j = instanceState.instancelist.length - 1; j >= 0; j--) {
        if (
          instanceState.instancelist[j].worldname == maplistState.maplist[i]
        ) {
          maplistState.maplist.splice(i, 1);
        }
      }
    }
  }
  return instanceState.instancelist;
});

const showAll = ref(true);

function switchScene(mode: string) {
  if (mode == "all") {
    showAll.value = true;
  }
  if (mode == "edit") {
    showAll.value = false;
  }
}

async function addUserAndJoin(id: number) {
  joinEditor(id, loginData.username);
  router.push(`/editor/${id}`);
}

async function getWorldAndForwardToEditor(name: string) {
  const id = await createWorld(name, "createWorldFromMap");
  joinEditor(id, loginData.username);
  router.push(`/editor/${id}`);
}
</script>

<template>
  <div>
    <div>
      <RouterLink to="/login"
        ><img src="../buttons/editor/arrow-left.png" alt=""
      /></RouterLink>
      <RouterLink to="/createWorld">
        <button>
          <img src="../buttons/editor/plus.png" alt="" /> Welt erstellen
        </button>
      </RouterLink>
      <img id="logo" src="" alt="Logo Fehlt" />
    </div>

    <hr />

    <div id="selection">
      <h1>Welt editieren</h1>
      <fieldset>
        <input
          type="radio"
          id="editmode"
          name="selectmode"
          value="Wird gerade editiert"
          @click="switchScene('edit')"
        />
        <label for="editmode">Wird gerade editiert</label>
        <input
          type="radio"
          id="allmode"
          name="selectmode"
          checked
          value="Alle"
          @click="switchScene('all')"
        />
        <label for="allmode">Alle</label>
      </fieldset>

      <div class="flexbox">
        <div class="box" v-for="ele in instancelist">
          <button @click="addUserAndJoin(ele.id)">
            <GameListItem
              :worldname="ele.worldname"
              :people="ele.playeramount"
            ></GameListItem>
          </button>
        </div>

        <div class="box" v-if="showAll" v-for="ele in maplistState.maplist">
          <button
            v-if="ele != undefined"
            @click="getWorldAndForwardToEditor(ele)"
          >
            <GameListItem :worldname="ele"></GameListItem>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
#logo {
  width: 5rem;
  height: 5rem;
}

button {
  text-align: center;
}

.flexbox {
  display: flex;
}

.box {
  flex-direction: row;
}
</style>
