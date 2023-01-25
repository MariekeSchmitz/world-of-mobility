<script setup lang="ts">
import { onMounted, ref } from "vue";
import GameListItem from "@/components/selectview/GameListItem.vue";

import { useMapOverview } from "@/services/useMapOverview";
import type { RouterLink } from "vue-router";
import router from "@/router";
import { useLogin } from "@/services/login/useLogin";
import Avatar from "@/components/User/Avatar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faArrowLeft, faChevronRight, faChevronLeft } from "@fortawesome/free-solid-svg-icons";
library.add(faPlus, faArrowLeft, faChevronRight, faChevronLeft);


const { mapsOverview, getMaps } = useMapOverview();
const { avatarData } = useLogin();

onMounted(async () => {
  await getMaps();
});

function changeView(name: string) {
  router.push("/gameConfig/" + name);
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
        <h1>Spielmodus</h1>
      </div>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end w-16"
      ></Avatar>
    </div>

    <div class="grid grid-cols-8 h-5/6">

      <div class="grid col-start-2 col-end-8 content-center p-20 bg-white h-5/6 mt-8">
      <!-- white box -->
        <div>
          <div class="mb-12 text-center">
            <h2 >
              Welt für neues<br/>Spiel wählen
            </h2>
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
                <div class="gameListItem" v-for="map in mapsOverview.allMaps">
                  <GameListItem
                    @click="changeView(map.mapName)"
                    :worldname="map.mapName"
                  ></GameListItem>
                </div>
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
</template>
