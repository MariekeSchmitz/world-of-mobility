<script setup lang="ts">
// component that represents a single Avatar 

/**
 * Avatar takes in a picture url 
 * @author Marie Bohnert
 * @author Tom Gouthier
 * */

import { avatarImages } from "@/components/User/AvatarURLDict";

const props = withDefaults(
  defineProps<{
    avatarPicture: string;
  }>(),
  { avatarPicture: avatarImages["Gockel"] }
);
</script>

<template>
  <!-- Avatar Picture, usage wherever the avatar is needed -->

  <img :src="avatarPicture" alt=""  />
</template>

<style scoped>

</style>
