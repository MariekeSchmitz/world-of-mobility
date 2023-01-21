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
    class="h-screen w-screen box-border bg-[url('/src/assets/images/home_Blur.png')] bg-cover"
  >
    <!-- navigation -->
    <div class="grid grid-cols-3 mx-12 pt-12 h-1/6">
      <RouterLink to="/login" class="">
        <font-awesome-icon
          icon="fa-solid fa-arrow-left"
          size="xl"
          color="white"
          class="bg-greenLight rounded-full p-3 w-6 h-6 inline justify-self-start white hover:bg-greenDark"
        />
      </RouterLink>
      <div class="text-center">
        <h1>Baumodus</h1>
      </div>
      <Avatar
        :avatarPicture="avatarData.avatar"
        class="justify-self-end w-16"
      ></Avatar>
    </div>

    <div class="grid grid-cols-8 h-5/6">

      <!-- white box -->

      <div class="grid col-start-2 col-end-8 p-20 bg-white h-5/6 mt-8 content-center">

        <div class="grid content-center text-center">
          <h2 class="place-self-center mb-14">
            Neue Welt <br/>erstellen
          </h2>

          <!-- world name input -->
          <div class="flex gap-20 place-self-center">
            <label for="name" class="text-greenDark font-bold text-left"
              >Weltname</label
            >
            <input type="text" id="name" v-model="name" />
          </div>

          <button
            @click="createWorldAndForwardToEditor(name)"
            class="buttonOrange w-3/12 p-4 place-self-center mt-16"
          >
            Erstellen
          </button>
          <ErrorWarning :errorMsg="worldCreateData.error" v-if="worldCreateData.error"> </ErrorWarning>
        </div>

      </div>

    </div>
  </div>
</template>
