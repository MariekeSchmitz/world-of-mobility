<script setup lang="ts">
import { useLogin } from "@/services/login/useLogin";
import { avatarImages } from "@/components/User/AvatarURLDict";


/**
 * similar to Avatar component - except it sets the user avatar state to itself when clicked
 * @author Marie Bohnert
 * @author Tom Gouthier
 */

const props = withDefaults(
  defineProps<{
    avatarPicture: string;
  }>(),
  { avatarPicture: "GOCKEL" }
);

const { setAvatar, avatarData } = useLogin();
</script>

<template>
  <!-- Avatar Picture, usage wherever the avatar is needed -->
  <div v-if="avatarPicture != avatarData.avatar">
    <img
      @click="setAvatar(avatarImages[avatarPicture])"
      :src="avatarImages[avatarPicture]"
      alt=""
      class="rounded-full w-24 m-4 hover:opacity-50"
    />
  </div>
  <div v-else>
    <div class="">
      <img
        @click="setAvatar(avatarImages[avatarPicture])"
        :src="avatarImages[avatarPicture]"
        alt=""
        class="rounded-full border-8 border-greenDark w-24 m-4 hover:opacity-50"
      />
    </div>
  </div>
</template>
