<script setup lang="ts">
/**
 * Menu with list of all users that are online on this instance
 * @defineProps instanceID - id of editorinstance
 * @author Astrid Klemmer & Marieke Schmitz
 */

import User from "@/components/joinGame/User.vue";
import { computed, onMounted, ref, watch } from "vue";
import { useLogin } from "@/services/login/useLogin";
import { useInstanceList } from "@/services/useInstanceList";
import { useRemoveInstanceState } from "@/services/useRemoveInstanceState";
import router from "@/router";
import type { IInstanceInfo } from "@/services/IInstanceInfo";
import { remove } from "@vue/shared";

const props = defineProps<{
  instanceId: number;
  type: string;
}>();

const { instanceState, getInstanceList, deleteState } = useInstanceList();
const { setRemoveState } = useRemoveInstanceState();
const { loginData } = useLogin();

onMounted(async () => {
  await getInstanceList(props.type);
});

const userlist = computed(() => {
  if (deleteState.id == props.instanceId) {
    setRemoveState(props.type, props.instanceId, true);
    router.push("/worldintro");
  } else {
    console.log("search users");
    instanceState.instancelist.forEach(function (item: IInstanceInfo) {
      if (item.id == props.instanceId) {
        console.log("found users", item.users);
        return item.users;
      }
    });
  }
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
  const showElement = document.getElementById("showElement");

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
        <img src="@/buttons/editor/arrow-left.png" />
      </button>
      <p>Liste: {{ userlist }}</p>
      <ul id="user-wrapper">
        <li v-for="user in userlist">
          <p>User: {{ user }}</p>
          <button class="itemButton" v-if="user != loginData.username">
            <User :name="user"></User>
          </button>
        </li>
      </ul>

      <button id="scrollRight" @click="scrollingRight">
        <img src="@/buttons/editor/arrow-right.png" />
      </button>
    </div>

    <button id="hideElement" @click="toggle">
      <img src="@/buttons/editor/arrow-up.png" />
    </button>
  </div>
  <button id="showElement" @click="toggle">
    <img src="@/buttons/editor/arrow-down.png" />
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

#showElement {
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
