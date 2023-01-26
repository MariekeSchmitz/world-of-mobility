<script setup lang="ts">
/**
 * View to select either creating a new game from an existing world or to play an existing game
 * @author Finn Schindel, Astrid Klemmer
 */

import { useInstanceList } from "@/services/useInstanceList";
import GameListItem from "@/components/selectview/GameListItem.vue";
import { computed, onMounted } from "vue";
import type { IInstanceInfo } from "@/services/IInstanceInfo";
import { useLogin } from "@/services/login/useLogin";
import Avatar from "@/components/User/Avatar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faArrowLeft, faChevronRight, faChevronLeft } from "@fortawesome/free-solid-svg-icons";
library.add(faPlus, faArrowLeft,  faChevronRight, faChevronLeft);

const { instanceState, getInstanceList } = useInstanceList();
const { avatarData } = useLogin();

onMounted(async () => {
  await getInstanceList("game");
});

const instancelist = computed(() => {
  const gameSelection = document.getElementById("gameSelection");
  if (instanceState.instancelist.length == 0 && gameSelection != null) {
    gameSelection.style.display = "none";
  } else if (gameSelection != null) {
    gameSelection.style.display = "grid";
  }

  let list:IInstanceInfo[] = []
  list = instanceState.instancelist.filter((instance: IInstanceInfo) => {
      if(instance.maxPlayerCount != instance.playeramount){
        return instance
      }
    }
  );
  return list;
});

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
          color="white"
          class="bg-greenLight rounded-full p-3 w-6 h-6 inline justify-self-start white hover:bg-greenDark"
        />
      </button>
      <div class="text-center">
        <h1>Spielmodus</h1>
      </div>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end w-16"
      ></Avatar>
    </div>

    <div class="grid grid-cols-8 h-5/6">
      <!-- white box -->
      <div
        class="grid col-start-2 col-end-8 content-center p-20 bg-white h-4/5 mt-8"
      >
        <!-- Create Game -->
        <div class="grid group mb-12 mt-8 justify-center">
          <RouterLink to="/worldSelection">
            <button class="inline-flex items-center">
              <font-awesome-icon
                icon="fa-solid fa-plus"
                color="white"
                class="w-8 h-8 p-3 inline bg-greenDark rounded-full group-hover:bg-orange"
              />
              <h2 class="ml-6 mb-0 inline group-hover:text-orange">
                Neues Spiel erstellen
              </h2>
            </button>
          </RouterLink>
        </div>

        <!-- Game Selection -->
        <div class="" id="gameSelection">
          <div class="mb-10 inline-flex items-center">
            <h2 class="inline mb-0 mr-20">Spiel beitreten</h2>
          </div>

          <!-- game slider -->
          <div class="grid grid-cols-7">
            <button class="relative bottom-6" @click="scrollingLeft">
              <font-awesome-icon
                icon="fa-solid fa-chevron-left"
                color="white"
                class="w-5 h-5 p-3 inline rounded-full bg-greenLight hover:bg-greenDark"
              />
            </button>

            <div
              class="flex overflow-x-scroll scrollbar-hide col-span-5"
              id="worldWrapper"
            >
              <div v-if="instancelist.length > 0" class="flex flex-nowrap">
                <div class="gameListItem" v-for="ele in instancelist">
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

            <button class="relative bottom-6" @click="scrollingRight">
              <font-awesome-icon
                icon="fa-solid fa-chevron-right"
                color="white"
                class="w-5 h-5 p-3 inline rounded-full bg-greenLight hover:bg-greenDark"
              />
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
