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
    boxwrapper.scrollLeft -= 200;
  }
}

function scrollingRight() {
  const boxwrapper = document.getElementById("user-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollLeft += 200;
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
  <div id="userListMenu" class="menuOff">
    <div id="itemList">
      <button id="scrollLeft" @mousedown="scrollingLeft">
        <font-awesome-icon
          icon="fa-solid fa-angle-left"
          size="3xl"
          color="#2F8265"
          class="w-8 h-8"
        />
      </button>

      <ul id="user-wrapper">
        <li v-for="user in userList.users">
          <button class="itemButton" v-if="user != loginData.username">
            <User :name="user"></User>
          </button>
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
  <button id="showElementUser" @click="toggle">
    <font-awesome-icon
      icon="fa-solid fa-angle-down"
      size="3xl"
      color="#2F8265"
      class="w-8 h-8"
    />
  </button>
</template>

<style scoped>
#userListMenu {
  display: grid;
  grid-template-columns: 90% 10%;
  position: fixed;
  width: 20%;
  height: 10%;
  background-color: rgb(221, 221, 221);
  padding: 10px;
  top: 0px;
  right: 5%;
}

#showElementUser {
  display: none;
  position: fixed;
  width: 40px;
  height: 40px;
  top: 2%;
  left: 92%;
}

ul {
  height: 100%;
  width: 85%;
  overflow-y: hidden;
  overflow-x: hidden;
  white-space: nowrap;
}

li {
  list-style-type: none;
  display: inline-block;
  background-color: lightblue;
  margin: 0 10px;
}
</style>
