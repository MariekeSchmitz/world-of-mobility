<script setup lang="ts">
import { useLogin } from "@/services/login/useLogin";

/**
 * similar to Avatar component - except it sets the user avatar state to itself when clicked
 * @author Marie Bohnert
 * @author Tom Gouthier
 */

const props = withDefaults(
  defineProps<{
    avatarPicture: string;
  }>(),
  { avatarPicture: "src/assets/avatar/Gockel.png" }
);

const { setAvatar } = useLogin();
</script>

<template>
  <!-- Avatar Picture, usage wherever the avatar is needed -->

  <img @click="setAvatar(avatarPicture)" :src="avatarPicture" alt="" />
</template>

<style scoped>
img {
  border-radius: 10em;
  border: 0.5px solid;
  display: block;
  margin: 1em;
  max-width: 100%;
  height: auto;
}

:hover {
  opacity: 0.5;
}
</style>
