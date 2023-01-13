<script setup lang="ts">
import Chooseable_Avatar from "@/components/User/Chooseable_Avatar.vue";
import { useLogin } from "@/services/login/useLogin";

/**
 * Component for Avatar Selection. Includes one Chooseable Avatar per available Avatar Choice
 * @author Tom Gouthier
 * @author Marie Bohnert
 */

const { loginData, login, logout } = useLogin();

/**
 * all available Avatars
 * @author Marie Bohnert
 * @author Tom Gouthier
 */
const avatarPictures: string[] = [
  "src/assets/avatar/Gockel.png",
  "src/assets/avatar/Huhn.png",
  "src/assets/avatar/Kuh.png",
  "src/assets/avatar/Schwein.png",
  "src/assets/avatar/avatar.png",
];
</script>

<template>
  <div
    class="fixed inset-0 bg-orange bg-opacity-50 overflow-y-auto h-full w-full grid grid-cols-3 content-center"
  >
    <div class="bg-white p-16 grid col-start-2 col-end-2">
      <h2 class="">Choose <br />your Avatar</h2>
      <div class="grid grid-cols-3">
        <div
          v-for="avatar in avatarPictures"
          :key="avatarPictures.indexOf(avatar)"
        >
          <div v-if="avatar != loginData.avatar">
            <Chooseable_Avatar :avatarPicture="avatar" :size="'s'">
            </Chooseable_Avatar>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
