<script setup lang="ts">
/**
 * Menu with list of all users that are online on this instance
 * @defineProps instanceID - id of editorinstance
 * @author Astrid Klemmer & Marieke Schmitz
 */

import User from "@/components/joinGame/User.vue";
import { computed, onMounted, onUnmounted } from "vue";
import { useLogin } from "@/services/login/useLogin";
import { useInstanceList } from "@/services/useInstanceList";
import { useRemoveInstanceState } from "@/services/useRemoveInstanceState";
import router from "@/router";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faXmark,
  faAngleRight,
  faAngleDown,
  faAngleUp,
} from "@fortawesome/free-solid-svg-icons";
library.add(faXmark, faAngleRight, faAngleDown, faAngleUp);

const props = defineProps<{
  instanceId: number;
  type: string;
}>();

const { getInstanceList, deleteState, getUserlist, endReceiveInstanceUpdates } = useInstanceList();
const { setRemoveState } = useRemoveInstanceState();
const { loginData } = useLogin();

onMounted(async () => {
  await getInstanceList(props.type);
});

onUnmounted(async () => {
  endReceiveInstanceUpdates();
});

const userlist = computed(() => {
  if (deleteState.id == props.instanceId) {
    setRemoveState(props.type, props.instanceId, true);
    router.push("/worldintro");
  }

  return getUserlist(props.instanceId);
});

function scrollingUp() {
  const boxwrapper = document.getElementById("user-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollBy(0, -50);
  }
}

function scrollingDown() {
  const boxwrapper = document.getElementById("user-wrapper");
  if (boxwrapper != null) {
    boxwrapper.scrollBy(0, 50);
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
    class="grid grid-rows-[10%_90%] w-[15%] h-[25%] bg-white fixed bottom-8 left-8 p-1 pb-5"
  >
    <button id="hideElement" @click="toggle" class="grid m-2 pb-10">
      <font-awesome-icon
        icon="fa-solid fa-xmark"
        color="#2F8265"
        class="w-5 h-5 justify-self-end hover:text-greenLight"
      />
    </button>

    <div class="w-3/4 mx-auto grid grid-rows-[10%_80%_10%]">
      <button id="scrollLeft" @mousedown="scrollingUp" class="h-full">
        <font-awesome-icon
          icon="fa-solid fa-angle-up"
          color="#2F8265"
          class="w-4 h-4 hover:text-greenLight"
        />
      </button>

      <ul
        id="user-wrapper"
        class="h-full pt-4 whitespace-nowrap overflow-y-scroll scrollbar-hide"
      >
        <li v-for="user in userlist" class="list-none">
          <User :name="user" v-if="user != loginData.username"></User>
        </li>
      </ul>

      <button id="scrollRight" @click="scrollingDown">
        <font-awesome-icon
          icon="fa-solid fa-angle-down"
          color="#2F8265"
          class="w-4 h-4 hover:text-greenLight"
        />
      </button>
    </div>
  </div>
  <button
    id="showElementUser"
    class="editorLabel text-greenDark grid-cols-[80%_20%] items-center hidden fixed bottom-[24%] left-2"
    @click="toggle"
  >
  <div class="inline">Userliste</div>
  <font-awesome-icon
    icon="fa-solid fa-angle-right"
    color="#2F8265"
    class="w-5 h-5 pr-2"
  />
  </button>
</template>
