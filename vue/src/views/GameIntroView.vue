<script setup lang="ts">
/**
 * View to select either creating a new game from an existing world or to play an existing game
 * @author Finn Schindel, Astrid Klemmer
 */

import { useInstanceList } from "@/services/useInstanceList";
import GameListItem from "@/components/selectview/GameListItem.vue";
import { computed, onMounted, ref } from "vue";
import { useLogin } from "@/services/login/useLogin";
import Avatar from "@/components/User/Avatar.vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faArrowLeft } from "@fortawesome/free-solid-svg-icons";
library.add(faPlus, faArrowLeft);

const { instanceState, getInstanceList } = useInstanceList();
const { avatarData } = useLogin();

onMounted(async () => {
  await getInstanceList("game");
});

const instancelist = computed(() => {
  return instanceState.instancelist;
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
    class="grid grid-cols-8 grid-rows-6 h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
  >
    <!-- navigation -->
    <div class="grid col-span-8 row-span-1 grid-cols-2 mx-12 mt-12">
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
        class="justify-self-end w-16"
      ></Avatar>
    </div>

    <!-- white box -->
    <div
      class="grid content-center col-start-2 col-end-8 row-span-4 p-20 bg-white"
    >
      <!-- Create Game -->
      <div class="grid group mb-20 justify-center">
        <RouterLink to="/worldSelection">
          <button class="inline-flex items-center">
            <font-awesome-icon
              icon="fa-solid fa-plus"
              size="xl"
              color="white"
              class="w-8 h-8 p-4 inline bg-greenDark rounded-full group-hover:bg-orange"
            />
            <h2 class="ml-6 mb-0 inline group-hover:text-orange">
              Neues Spiel erstellen
            </h2>
          </button>
        </RouterLink>
      </div>

      <!-- World Selection -->
      <div>
        <div class="mb-20 inline-flex items-center">
          <h2 class="inline mb-0 mr-20">Spiel beitreten</h2>
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
              icon="fa-solid fa-plus"
              size="xl"
              color="white"
              class="w-4 h-4 p-4 inline bg-greenDark rounded-full group-hover:bg-orange"
            />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
