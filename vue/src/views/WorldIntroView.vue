<script setup lang="ts">
/**
 * View to select either creating a new World or to continue building an existing world
 * @author Finn Schindel, Astrid Klemmer
 */

import GameListItem from "@/components/selectview/GameListItem.vue";
import { useInstanceList } from "@/services/useInstanceList";
import { computed, ref } from "@vue/reactivity";
import { onMounted } from "vue";
import { useMapOverview } from "@/services/useMapOverview";
import { useEditor } from "@/services/useEditor";
import { RouterLink } from "vue-router";
import router from "@/router";
import { useUserEditor } from "@/services/useUserEditor";
import { useLogin } from "@/services/login/useLogin";
import Avatar from "@/components/User/Avatar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faArrowLeft } from "@fortawesome/free-solid-svg-icons";
library.add(faPlus, faArrowLeft);

const { instanceState, getInstanceList } = useInstanceList();
const { mapsOverview, getMaps } = useMapOverview();
const { createWorld } = useEditor();
const { joinEditor } = useUserEditor();
const { loginData, avatarData } = useLogin();

onMounted(() => {
  getInstanceList("editor");
  getMaps();
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
  <div class="m-32">
    <div class="grid grid-cols-2 mb-28">
      <RouterLink to="/login" class="">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          size="3xl"
          color="white"
          class="bg-greenLight rounded-full p-2 w-8 h-8 inline justify-self-start"
        />
      </RouterLink>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end"
      ></Avatar>
    </div>

    <div class="">
      <div class="mb-12">
        <RouterLink to="/createWorld">
          <button class="inline-flex items-center">
            <font-awesome-icon
              icon="fa-solid fa-plus"
              size="xl"
              color="white"
              class="w-12 h-12 p-4 inline bg-greenDark rounded-full"
            />
            <h2 class="ml-8 mb-0 inline">Welt erstellen</h2>
          </button>
        </RouterLink>
      </div>

      <hr class="mb-12 border-2 border-greenDark" />

      <div id="selection">
        <div class="mb-12 inline-flex items-center">
          <h2 class="inline mb-0 mr-20">Welt editieren</h2>
          <fieldset class="inline-flex items-center">
            <div class="mr-12 inline-flex items-center">
              <input
                type="radio"
                id="editmode"
                name="selectmode"
                value="Wird gerade editiert"
                class="radioButton"
                @click="switchScene('edit')"
              />
              <label for="editmode">Wird gerade editiert</label>
            </div>
            <div class="inline-flex items-center">
              <input
                type="radio"
                id="allmode"
                name="selectmode"
                checked
                value="Alle"
                class="radioButton"
                @click="switchScene('all')"
              />
              <label for="allmode">Alle</label>
            </div>
          </fieldset>
        </div>
        <div class="grid grid-cols-5">
          <div class="" v-for="ele in instanceState.instancelist.instancelist">
            <button @click="addUserAndJoin(ele.id)">
              <GameListItem
                :worldname="ele.worldname"
                :people="ele.playeramount"
              ></GameListItem>
            </button>
          </div>

          <div class="" v-if="showAll" v-for="ele in mapsOverview.allMaps">
            <button @click="getWorldAndForwardToEditor(ele.mapName)">
              <GameListItem :worldname="ele.mapName"></GameListItem>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
