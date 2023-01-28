<script setup lang="ts">
// Start Page with toggleable Avatar Selection, Username choice and login/logout functionality
import Avatar_Selection from "@/components/User/Avatar_Selection.vue";
import Avatar from "@/components/User/Avatar.vue";
import { useLogin } from "@/services/login/useLogin";
import { ref, watch } from "vue";
import router from "@/router";
import ErrorWarning from "@/components/error/ErrorWarning.vue";
import { animateHintBox } from "@/components/error/HintBoxAnimation";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPen } from "@fortawesome/free-solid-svg-icons";
library.add(faPen);

const { loginData, login, logout, avatarData } = useLogin();

const name = ref(loginData.username);

const errorExisting = ref(false)

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

watch(loginData, (neu, alt) => {

  const errorBox = document.getElementById("errorBox");
  if (neu.error == "" && errorExisting.value) {
    errorExisting.value = false;

  } else if (neu.error != "" && !errorExisting.value) {
    if (errorBox != null) {
    errorExisting.value = true;
    }
  }
});
 

watch(errorExisting, (neu, alt) => {
  const errorBox = document.getElementById("errorBox");
  animateHintBox(errorExisting.value, errorBox);
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

      <!-- small Avatar with change function -->
      <div class="grid grid-cols-3 gap-6 items-center">
        <div class="grid justify-items-end items-end">
          <Avatar
            :avatarPicture="avatarData.avatar"
            class="inline w-28"
          ></Avatar>
          <button
            @click="toggleAvatarSelection"
            class="bg-orange absolute rounded-full w-11 h-11 hover:bg-orangeLight"
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
          Hallo {{ loginData.username }}!
        </p>
        
        
      </div>

      <div class="w-1/2 mt-8">
        <button class="buttonOrange" @click="loginAndRedirect('/gameintro')">
          Spielmodus
        </button>
        <button class="buttonGreen" @click="loginAndRedirect('/worldintro')">
          Baumodus
        </button>
      </div>

      <Avatar_Selection v-if="choosingAvatar"></Avatar_Selection>
    </div>
    <button
      v-if="loginData.loggedIn"
      class="text-white absolute -right-0 m-10 py-3 px-5 bg-orange rounded-full font-poppins font-semibold hover:bg-orangeLight"
      @click="logout()"
    >
      Logout
    </button>
    <ErrorWarning :errorMsg="loginData.error"> </ErrorWarning>

  </div>
</template>
