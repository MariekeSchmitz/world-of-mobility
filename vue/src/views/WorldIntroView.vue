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
import Avatar from "@/components/User/Avatar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faArrowLeft, faChevronRight, faChevronLeft } from "@fortawesome/free-solid-svg-icons";
library.add(faPlus, faArrowLeft, faChevronRight, faChevronLeft);

const { instanceState, getInstanceList } = useInstanceList();
const { mapsOverview, getMaps } = useMapOverview();
const { createWorld } = useEditor();
const { joinEditor } = useUserEditor();
const { loginData, avatarData } = useLogin();

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

function scrollingLeft() {
  const boxwrapper = document.getElementById("worldWrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollLeft -= 50;
  }

}

function scrollingRight() {
  const boxwrapper = document.getElementById("worldWrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollLeft += 50;
  }
}
</script>

<template>
  <div
    class="h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
  >
    <!-- navigation -->
    <div class="grid grid-cols-3 mx-12 pt-12 h-1/6">
      <button @click="$router.go(-1)" class="place-self-start">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          size="xl"
          color="white"
          class="bg-greenLight rounded-full p-3 w-6 h-6 inline justify-self-start white hover:bg-greenDark"
        />
      </button>
      <div class="text-center">
        <h1>Baumodus</h1>
      </div>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end w-16"
      ></Avatar>
    </div>

    <div class="grid grid-cols-8 h-5/6">
      
      <!-- white box -->
      <div
        class="grid col-start-2 col-end-8 content-center p-20 bg-white h-5/6 mt-8"
      >
        <div>
          <!-- Create World -->
          <div class="grid group mb-16 mt-5 justify-center">
            <RouterLink to="/createWorld">
              <button class="inline-flex items-center">
                <font-awesome-icon
                  icon="fa-solid fa-plus"
                  size="xl"
                  color="white"
                  class="w-8 h-8 p-3 inline bg-greenDark rounded-full group-hover:bg-orange"
                />
                <h2 class="ml-6 mb-0 inline group-hover:text-orange">
                  Welt erstellen
                </h2>
              </button>
            </RouterLink>
          </div>
    
          <!-- World Selection -->
          <div>
            <div class="mb-16 inline-flex items-center">
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
                  <label class="font-light" for="editmode">Wird gerade editiert</label>
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
                  <label class="font-light" for="allmode">Alle</label>
                </div>
              </fieldset>
            </div>
  
            <!-- world slider -->
            <div class="grid grid-cols-7">
              <button class="group relative bottom-6" @click="scrollingLeft">
                <font-awesome-icon
                  icon="fa-solid fa-chevron-left"
                  size="2xl"
                  color="white"
                  class="w-5 h-5 p-3 inline rounded-full bg-greenLight hover:bg-greenDark"
                />
              </button>
  
              <div
                class="flex overflow-x-scroll scrollbar-hide col-span-5"
                id="worldWrapper"
              >
                <div class="flex flex-nowrap">
                  <div
                    class="gameListItem"
                    v-for="ele in instancelist"
                    @click="addUserAndJoin(ele.id)"
                  >
                    <GameListItem
                      :worldname="ele.worldname"
                      :people="ele.playeramount"
                    ></GameListItem>
                  </div>
                  <div
                    class="gameListItem"
                    v-if="showAll"
                    v-for="ele in maplistState.maplist"
                    @click="getWorldAndForwardToEditor(ele)"
                  >
                    <GameListItem
                      :worldname="ele"
                      class="gameListItem"
                    ></GameListItem>
                  </div>
                    <GameListItem
                      class="gameListItem invisible"
                    ></GameListItem>
                  </div>
              </div>
  
              <button class="relative bottom-6" @click="scrollingRight">
                <font-awesome-icon
                  icon="fa-solid fa-chevron-right"
                  size="s"
                  color="white"
                  class="w-5 h-5 p-3 inline rounded-full bg-greenLight hover:bg-greenDark"
                />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

  
  </div>
</template>
