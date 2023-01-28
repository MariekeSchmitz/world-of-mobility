<template>
  <div id="chatMenu" class="grid grid-rows-[7%_93%] w-[15%] h-[35%] bg-white fixed bottom-[20%] right-3 p-1">
    <button id="hideElement" @click="toggle" class="grid m-2 pb-10">
      <font-awesome-icon
        icon="fa-solid fa-xmark"
        color="#2F8265"
        class="w-5 h-5 justify-self-end hover:text-greenLight"
      />
    </button>
    <div id="chat-container" class="w-full overflow-y-hidden p-2 flex flex-col-reverse ">
      <div class="w-full h-10 mt-2 flex flex-row">
        <input type="text" maxlength="23" v-model="chatInput" 
              class="w-5/6 bg-greenLight border-1 rounded-2xl p-1 pl-5 text-lg focus:border-none"/>
        <button @click="getInputAndChat()" class="w-1/6 rounded-full bg-greenDark p-2 hover:bg-orangeLight">
          <img src="@/assets/images/buttons/senden.png" alt="send" class="h-6 w-6">
        </button>
      </div>
      <ul class="list-none pl-0 font-poppins text-sm">
        <li v-for="msg in msgListe" :key="msg" class="ml-2 mb-1">{{ msg }}</li>
      </ul>
    </div>
  </div>
  <button
    id="showElementChat"
    class="editorLabel text-greenDark grid-cols-[20%_80%] items-center hidden fixed bottom-[52%] right-2"
    @click="toggle"
  >
    <font-awesome-icon
      icon="fa-solid fa-angle-left"
      color="#2F8265"
      class="w-5 h-5 pr-2"
    />
    <div class="inline">Chat</div>
  </button>
</template>

<script setup lang="ts">
import { useServerMessage } from "@/services/messaging/useServerMessage";
import { computed } from "@vue/reactivity";
import { onMounted, ref } from "vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faXmark,
  faAngleLeft
} from "@fortawesome/free-solid-svg-icons";
library.add(faXmark, faAngleLeft);

const props = defineProps<{
  instanceId: number;
  type: string,
  username: string
}>();

const { receiveMessages, updateTestMessage, msgState } = useServerMessage();

const msgListe = computed(() => {
  return msgState.msgLst.map((msg) => msg.txt);
});

onMounted(() => {
  receiveMessages(props.type, props.instanceId);
});

const chatInput = ref("");

function getInputAndChat() {
  updateTestMessage(props.type, props.username + ": " + chatInput.value, props.instanceId);
}

function toggle() {
  const chatMenu = document.getElementById("chatMenu");
  const showElement = document.getElementById("showElementChat");

  if (chatMenu != null && showElement != null) {
    if (chatMenu.style.display == "none") {
      chatMenu.style.display = "grid";
      showElement.style.display = "none";
    } else {
      chatMenu.style.display = "none";
      showElement.style.display = "grid";
    }
  }
}
</script>
