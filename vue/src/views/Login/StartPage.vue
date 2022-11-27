<script setup lang="ts">
import Avatar_Selection from "@/components/User/Avatar_Selection.vue";
import Avatar from "@/components/User/Avatar.vue";
import { useLogin } from "@/services/login/useLogin";
import { ref } from "vue";

const { loginData, login, logout } = useLogin();

const name = ref(loginData.username);
const choosingAvatar = ref(false);

const toggleAvatarSelection = () => {
  choosingAvatar.value = !choosingAvatar.value;
};
</script>

<!-- 1. uselogin + interface bauen (name, avatar, error, loggedIn) erstmal ohne backend
2. uselogin einbauen in startpage
3. avatar component (verschiedene versionen? s,m,l) und avatarliste component bauen (hier auch klicken für auswahl dieses avatars) 
4. wenn avatar geändert werden soll, dann zeig avatarliste an, sonst nicht
5. bei spielen/baumodus buttons wird name gesetzt (über uselogin)
6. mal schauen -->

<template>
  <div class="wrapper">
    <!-- big Avatar -->
    <div>
      <Avatar :avatarPicture="loginData.avatar" :size="'l'"></Avatar>
    </div>

    <!-- small Avatar with change function -->
    <div>
      <Avatar :avatarPicture="loginData.avatar" :size="'s'"></Avatar>
      <button @click="toggleAvatarSelection">change avatar</button>
      <!-- input for username -->
      <input type="text" v-model="name" />
      <p v-if="loginData.error !== ''">{{ loginData.error }}</p>
    </div>

    <button @click="login(name)">Spielen</button>
    <button @click="login(name)">Baumodus</button>
    <button @click="logout()">Logout</button>

    <!-- Avatar Selection Mode -->
    <Avatar_Selection v-if="choosingAvatar"></Avatar_Selection>
  </div>
</template>

<style scoped>
.wrapper {
  display: grid;
  place-items: center;
}

button {
  padding: 1em;
  border-radius: 1em;
  background-color: antiquewhite;
  width: 10rem;
  margin: 10px;
}
</style>
