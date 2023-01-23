<script setup lang="ts">
/**
 * Menu with list of all users that are online on this instance
 * @defineProps instanceID - id of editorinstance
 * @author Astrid Klemmer & Marieke Schmitz
 */

import { useUserEditor } from "@/services/useUserEditor";
import User from "@/components/joinGame/User.vue";
import { onMounted } from "vue";
import { useLogin } from "@/services/login/useLogin";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faXmark,
  faAngleLeft,
  faAngleDown,
  faAngleUp,
} from "@fortawesome/free-solid-svg-icons";
library.add(faXmark, faAngleLeft, faAngleDown, faAngleUp);

const { userList, getUserlistEditor } = useUserEditor();
const { loginData } = useLogin();

const props = defineProps<{
  instanceID: number;
}>();

onMounted(() => {
  getUserlistEditor(props.instanceID);
});

function scrollingLeft() {
  const boxwrapper = document.getElementById("user-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollLeft -= 50;
  }
}

function scrollingRight() {
  const boxwrapper = document.getElementById("user-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollLeft += 50;
  }
}

function toggle() {
  const userListMenu = document.getElementById("userListMenu");
  const showElement = document.getElementById("showElementUser");

  if (userListMenu != null && showElement != null) {
    if (userListMenu.style.display == "none") {
      userListMenu.style.display = "grid";
      showElement.style.display = "none";
    } else {
      userListMenu.style.display = "none";
      showElement.style.display = "grid";
    }
  }
}
</script>

<template>
  <div
    id="userListMenu"
    class="grid grid-rows-[10%_90%] w-[13%] h-1/5 bg-white fixed top-1/4 right-0 p-3 pb-5"
  >
    <button id="hideElement" @click="toggle" class="grid">
      <font-awesome-icon
        icon="fa-solid fa-xmark"
        size="3xl"
        color="#242526"
        class="w-5 h-5 justify-self-end"
      />
    </button>

    <div class="grid grid-rows-[10%_80%_10%]">
      <button id="scrollLeft" @mousedown="scrollingLeft" class="h-full">
        <font-awesome-icon
          icon="fa-solid fa-angle-up"
          size="3xl"
          color="#242526"
          class="w-4 h-4"
        />
      </button>

      <ul
        id="user-wrapper"
        class="h-full pt-4 whitespace-nowrap overflow-hidden"
      >
        <li v-for="user in userList.users" class="bottomMenuListStyle">
          <User :name="user" v-if="user != loginData.username"></User>
        </li>
      </ul>

      <button id="scrollRight" @click="scrollingRight">
        <font-awesome-icon
          icon="fa-solid fa-angle-down"
          size="3xl"
          color="#242526"
          class="w-4 h-4"
        />
      </button>
    </div>
  </div>
  <button
    id="showElementUser"
    class="editorLabel text-greenDark grid-cols-[20%_80%] items-center hidden fixed top-1/4 right-2"
    @click="toggle"
  >
    <font-awesome-icon
      icon="fa-solid fa-angle-left"
      size="3xl"
      color="#2F8265"
      class="w-5 h-5 pr-2"
    />
    <div class="inline">Userliste</div>
  </button>
</template>
