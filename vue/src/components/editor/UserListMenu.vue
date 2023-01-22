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
  faAngleRight,
  faAngleLeft,
  faAngleDown,
  faAngleUp,
} from "@fortawesome/free-solid-svg-icons";
library.add(faAngleRight, faAngleLeft, faAngleDown, faAngleUp);

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
      showElement.style.display = "block";
    }
  }
}
</script>

<template>
  <div
    id="userListMenu"
    class="grid grid-cols-[90%_10%] w-1/4 h-[10%] fixed top-0 right-[8%] p-3"
  >
    <div class="grid grid-cols-[10%_80%_10%] my-auto">
      <button id="scrollLeft" @mousedown="scrollingLeft">
        <font-awesome-icon
          icon="fa-solid fa-angle-left"
          size="3xl"
          color="#2F8265"
          class="w-8 h-8"
        />
      </button>

      <ul id="user-wrapper" class="h-full whitespace-nowrap overflow-hidden">
        <li v-for="user in userList.users" class="bottomMenuListStyle">
          <User :name="user" v-if="user != loginData.username"></User>
        </li>
      </ul>

      <button id="scrollRight" @click="scrollingRight">
        <font-awesome-icon
          icon="fa-solid fa-angle-right"
          size="3xl"
          color="#2F8265"
          class="w-8 h-8"
        />
      </button>
    </div>

    <button id="hideElement" @click="toggle">
      <font-awesome-icon
        icon="fa-solid fa-angle-up"
        size="3xl"
        color="#2F8265"
        class="w-8 h-8"
      />
    </button>
  </div>
  <button
    id="showElementUser"
    class="hidden fixed top-2 right-[8.5%]"
    @click="toggle"
  >
    <font-awesome-icon
      icon="fa-solid fa-angle-down"
      size="3xl"
      color="#2F8265"
      class="w-8 h-8"
    />
  </button>
</template>

