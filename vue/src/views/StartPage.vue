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

async function loginAndRedirect(url: string) {
  await login(name.value);
  if (loginData.loggedIn) {
    router.push(url);
  }
}
</script>

<template>

  <div class="grid grid-cols-3 grid-rows-3">

    <div class="text-center col-start-2 col-end-2 row-start-2 row-end-2">
      
      <div class="">
        <h1 class="text-greenDark font-fredoka text-transform: uppercase">World of E-Mobility</h1>
      </div>
    
      <!-- big Avatar
      <div>
        <Avatar :avatarPicture="loginData.avatar" :size="'l'"></Avatar>
      </div> -->

      <!-- small Avatar with change function -->
      <div class="grid grid-cols-2">
        <div>
          <Avatar :avatarPicture="loginData.avatar" :size="'s'"></Avatar>
          <button @click="toggleAvatarSelection">change avatar</button>
      <div v-if="loginData.loggedIn">Logged in as {{ loginData.username }}</div>
        </div>
        <!-- input for username -->
        <input v-if="!loginData.loggedIn" type="text" v-model="name" />
        <p v-if="loginData.error !== ''">{{ loginData.error }}</p>
      </div>

      <button class="" @click="loginAndRedirect('/gameintro') ">Spielen</button>
      <button class="" @click="loginAndRedirect('/worldintro')">Baumodus</button>
      <button class="" @click="logout()">Logout</button>

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
