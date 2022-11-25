<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    avatarPicture: string;
    size: string;
  }>(),
  { avatarPicture: "src/assets/avatar/Gockel.png" }
);
</script>

<template>
  <!-- Avatar Picture, usage wherever the avatar is needed -->

  <img :src="avatarPicture" alt="" :class="size" />
</template>

<style scoped>
img {
  margin: 1em;
  max-width: 100%;
  height: auto;
}

.s {
  max-width: 100px;
}
.m {
  max-width: 200px;
}
.l {
  max-width: 500px;
}
</style>
