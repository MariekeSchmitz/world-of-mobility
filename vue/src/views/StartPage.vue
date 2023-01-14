<script setup lang="ts">
// Start Page with toggleable Avatar Selection, Username choice and login/logout functionality
import Avatar_Selection from "@/components/User/Avatar_Selection.vue";
import Avatar from "@/components/User/Avatar.vue";
import { useLogin } from "@/services/login/useLogin";
import { ref, watch } from "vue";
import router from "@/router";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPen } from "@fortawesome/free-solid-svg-icons";
import ErrorWarning from "@/components/ErrorWarning.vue";
// Then add it to library
library.add(faPen);

const { loginData, login, logout, avatarData } = useLogin();

const name = ref(loginData.username);
const choosingAvatar = ref(false);

const toggleAvatarSelection = () => {
  choosingAvatar.value = !choosingAvatar.value;
};

async function loginAndRedirect(url: string) {
  await login(name.value);
  if (loginData.loggedIn) {
    router.push(url);
  }
}

watch(avatarData, (neu, alt) => {
  toggleAvatarSelection();
});
</script>

<template>
  <div
    class="grid grid-cols-3 content-center h-screen box-border bg-[url('/src/assets/images/home.png')] bg-cover"
  >
    <div class="grid text-center col-start-2 col-end-2 justify-items-center">
      <div class="">
        <h1 class="">World of <br />E-Mobility</h1>
      </div>

      <!-- big Avatar
      <div>
        <Avatar :avatarPicture="loginData.avatar" :size="'l'"></Avatar>
      </div> -->

      <!-- small Avatar with change function -->
      <div class="grid grid-cols-3 gap-6 items-center">
        <div class="grid justify-items-end items-end">
          <Avatar
            :avatarPicture="avatarData.avatar"
            class="inline w-28"
          ></Avatar>
          <button
            @click="toggleAvatarSelection"
            class="bg-orange absolute rounded-full w-11 h-11"
          >
            <font-awesome-icon icon="fa-solid fa-pen" size="xl" color="white" />
          </button>
        </div>

        <!-- input for username -->
        <input
          v-if="!loginData.loggedIn"
          type="text"
          v-model="name"
          placeholder="Bitte Namen eingeben"
          class="col-span-2 h-1/4 mr-12 font-poppins font-semibold"
        />
        <p
          class="text-greenDark text-xl font-bold text-left"
          v-if="loginData.loggedIn"
        >
          {{ loginData.username }}
        </p>

        <div v-if="loginData.error !== ''">
          <ErrorWarning :errorMsg="loginData.error"> </ErrorWarning>
        </div>
      </div>

      <div class="w-1/2 mt-8">
        <button
          class="buttonStartPage bg-orange"
          @click="loginAndRedirect('/gameintro')"
        >
          Spielmodus
        </button>
        <button
          class="buttonStartPage bg-greenDark"
          @click="loginAndRedirect('/worldintro')"
        >
          Baumodus
        </button>
      </div>

      <!-- Avatar Selection Mode -->
      <Avatar_Selection v-if="choosingAvatar"></Avatar_Selection>
    </div>
    <button
      v-if="loginData.loggedIn"
      class="text-white absolute -right-0 m-10 py-3 px-5 bg-orange rounded-full font-poppins font-semibold"
      @click="logout()"
    >
      Logout
    </button>

    <!-- <div class="fixed block inset-0 bg-orange bg-opacity-50 overflow-y-auto h-full w-full" id="my-modal"></div> -->
  </div>
</template>
