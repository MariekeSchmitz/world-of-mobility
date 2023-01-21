<script setup lang="ts">
/**
 * view in which a  new world can be created
 * @author Marie Bohnert, Tom Gouthier, Victoria Thee
 */
import { ref } from "vue";
import { useEditor } from "@/services/useEditor";
import { create } from "mathjs";
import router from "@/router";
import { useLogin } from "@/services/login/useLogin";
import { useUserEditor } from "@/services/useUserEditor";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import Avatar from "@/components/User/Avatar.vue";
import ErrorWarning from "@/components/ErrorWarning.vue";

library.add(faArrowLeft);
const name = ref("");
const { createWorld, worldCreateData, resetError } = useEditor();
const { joinEditor } = useUserEditor();
const { loginData, avatarData } = useLogin();

async function createWorldAndForwardToEditor(name: string) {
  const id = await createWorld(name, "createNewWorld");
  if (worldCreateData.error === "") {
    joinEditor(id, loginData.username);
    router.push(`/editor/${id}`);
  }
}
</script>

<template>
  <div
    class="grid grid-cols-8 grid-rows-6 h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
  >
    <!-- navigation -->
    <div class="grid col-span-8 row-span-1 grid-cols-3 mx-12 mt-12">
      <RouterLink to="/worldintro" class="col-span-1">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          size="3xl"
          color="white"
          class="bg-greenLight rounded-full p-2 w-8 h-8"
          @click="resetError"
        />
      </RouterLink>
      <h1 class="col-span-1 place-self-center">Baumodus</h1>

      <Avatar
        :avatarPicture="avatarData.avatar"
        class="w-16 justify-self-end"
      ></Avatar>
    </div>
    <!-- white box -->

    <div class="grid col-start-2 col-end-8 row-span-4 p-20 bg-white gap-8">
      <h2 class="place-self-center leading-9">
        Neue Welt <br />
        erstellen
      </h2>

      <!-- input mit Beschriftung-->
      <div class="flex gap-20 place-self-center">
        <label for="name" class="text-greenDark text-xl font-bold text-left"
          >Weltname</label
        >
        <input type="text" id="name" v-model="name" />
      </div>

      <button
        @click="createWorldAndForwardToEditor(name)"
        class="buttonOrange w-3/12 p-4 place-self-center"
      >
        Erstellen
      </button>
      <div v-if="worldCreateData.error">
        <ErrorWarning :errorMsg="worldCreateData.error"> </ErrorWarning>
      </div>
    </div>
  </div>
</template>
