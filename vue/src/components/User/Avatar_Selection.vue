<script setup lang="ts">
import Chooseable_Avatar from "@/components/User/Chooseable_Avatar.vue";

const avatarPictures: string[] = [
  "src/assets/avatar/Gockel.png",
  "src/assets/avatar/Huhn.png",
  "src/assets/avatar/Kuh.png",
  "src/assets/avatar/Schwein.png",
];
</script>

<template>
  <h1>Choose your Avatar</h1>
  <div>
    <Chooseable_Avatar
      :avatarPicture="avatar"
      :size="'s'"
      v-for="avatar in avatarPictures"
      :key="avatarPictures.indexOf(avatar)"
    ></Chooseable_Avatar>
  </div>
</template>

<style scoped>
img {
  border-radius: 10em;
  border: 0.5px solid;
  display: block;
  max-width: 200px;
}

div {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 5px;
  max-width: 50%;
  background-color: lightgray;
}
</style>
