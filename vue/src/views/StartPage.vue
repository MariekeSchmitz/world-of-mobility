<script setup lang="ts">
// Start Page with toggleable Avatar Selection, Username choice and login/logout functionality
import Avatar_Selection from "@/components/User/Avatar_Selection.vue";
import Avatar from "@/components/User/Avatar.vue";
import { useLogin } from "@/services/login/useLogin";
import { ref } from "vue";
import router from "@/router";

const { loginData, login, logout } = useLogin();

const name = ref(loginData.username);
const choosingAvatar = ref(false);

const toggleAvatarSelection = () => {
  choosingAvatar.value = !choosingAvatar.value;
};

async function loginAndRedirect(url:string) {
  await login(name.value)
  if (loginData.loggedIn) {
    router.push(url)
  }
}

</script>



<template>
  <div class="grid grid-cols-12 gap-4 bg-orange-light w-full h-screen">
    
    <div class="col-start-4 col-end-9">
      <h1 class="text-green-dark font-fredoka text-transform: uppercase text-4xl">World of E-Mobility</h1>
    </div>
    <!-- big Avatar
    <div>
      <Avatar :avatarPicture="loginData.avatar" :size="'l'"></Avatar>
    </div> -->

    <!-- small Avatar with change function -->
    <div class="col-start-4 col-end-9">
      <Avatar :avatarPicture="loginData.avatar" :size="'s'"></Avatar>
      <button @click="toggleAvatarSelection">change avatar</button>
      <!-- input for username -->
      <input type="text" v-model="name" />
      <p v-if="loginData.error !== ''">{{ loginData.error }}</p>
    </div>

    <button class="col-start-4 col-end-9" @click="loginAndRedirect('/gameintro') ">Spielen</button>
    <button class="col-start-4 col-end-9" @click="loginAndRedirect('/worldintro')">Baumodus</button>
    <button class="col-start-4 col-end-9" @click="logout()">Logout</button>

    <!-- Avatar Selection Mode -->
    <Avatar_Selection v-if="choosingAvatar"></Avatar_Selection>
  </div>
</template>

<style scoped>
/* .wrapper {
  display: grid;
  place-items: center;
}

button {
  padding: 1em;
  border-radius: 1em;
  background-color: antiquewhite;
  width: 10rem;
  margin: 10px;
} */
</style>
