<script setup lang="ts">
import { onMounted, ref } from "vue";
import GameListItem from "@/components/selectview/GameListItem.vue";

import { useMapOverview } from "@/services/useMapOverview";
import type { RouterLink } from "vue-router";
import router from "@/router";
import { useLogin } from "@/services/login/useLogin";
import Avatar from "@/components/User/Avatar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
library.add(faArrowLeft);

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
    class="grid grid-cols-8 grid-rows-6 h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
  >
    <!-- navigation -->
    <div class="grid col-span-8 row-span-1 grid-cols-2 mx-12 mt-12">
      <RouterLink to="/gameintro" class="">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          size="3xl"
          color="white"
          class="bg-greenLight rounded-full p-2 w-8 h-8 inline justify-self-start"
        />
      </RouterLink>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end w-16"
      ></Avatar>
    </div>

    <!-- white box -->
    <div
      class="grid content-center col-start-2 col-end-8 row-span-4 p-20 bg-white"
    >
      <div class="mb-20 inline-flex items-center">
        <h2 class="ml-6 mb-0 inline group-hover:text-orange">
          Welt für neues Spiel wählen
        </h2>
      </div>

      <!-- world slider -->
      <div class="grid grid-cols-7">
        <button class="relative bottom-6" @click="scrollingLeft">
          <font-awesome-icon
            icon="fa-solid fa-plus"
            size="xl"
            color="white"
            class="w-4 h-4 p-4 inline bg-greenDark rounded-full group-hover:bg-orange"
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
            icon="fa-solid fa-plus"
            size="xl"
            color="white"
            class="w-4 h-4 p-4 inline bg-greenDark rounded-full group-hover:bg-orange"
          />
        </button>
      </div>
    </div>
  </div>
</template>
