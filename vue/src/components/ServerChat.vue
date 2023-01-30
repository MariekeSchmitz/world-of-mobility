<template>
  <div id="chatMenu" class="grid grid-rows-[7%_93%] w-[16%] h-1/2 bg-white fixed bottom-8 right-8 p-3">
    <button id="hideElement" @click="toggle" class="grid m-2 pb-10">
      <font-awesome-icon
        icon="fa-solid fa-xmark"
        color="#2F8265"
        class="w-5 h-5 justify-self-end hover:text-greenLight"
      />
    </button>
    <div id="chat-container" class="w-full overflow-y-hidden p-2 flex flex-col-reverse ">
      <div class="w-full h-10 mt-2 flex flex-row">
        <input type="text" maxlength="30" v-model="chatInput" 
              class="w-5/6  border-1 p-1  text-lg "/>
        <button @click="getInputAndChat()" class="ml-1 w-10 h-10 rounded-full bg-greenDark p-2 hover:bg-greenLight">
          <img src="@/assets/images/buttons/senden.png" alt="send" class="pl-1 h-5 w-5">
        </button>
      </div>
      <ul class="list-none pl-0 font-poppins text-sm">
        <li v-for="msg in msgListe" :key="msg" class="ml-2 mb-1">{{ msg }}</li>
      </ul>
    </div>
  </div>
  <button
    id="showElementChat"
    :class="{
      'editorLabel text-greenDark grid-cols-[20%_80%] items-center hidden fixed bottom-1/2 right-2':props.type == 'editor',
      'editorLabel text-white grid-cols-[20%_80%] items-center hidden fixed bottom-1/2 right-2':props.type == 'game',
      }"
    @click="toggle"
  >
    <font-awesome-icon
      icon="fa-solid fa-angle-left"
      color="#2F8265"
      :class="{
      'w-5 h-5 pr-2 text-greenDark':props.type == 'editor',
      'w-5 h-5 pr-2 text-white':props.type == 'game',
      }"
    />
    <div class="inline">Chat</div>
  </button>
</template>

<script setup lang="ts">
import { useServerMessage } from "@/services/messaging/useServerMessage";
import { computed } from "@vue/reactivity";
import { onMounted, onUnmounted, ref } from "vue";
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

const { receiveMessages, updateTestMessage, msgState, endReceiveMessages } = useServerMessage();

const msgListe = computed(() => {
  return msgState.msgLst.map((msg) => msg.txt);
});

onMounted(() => {
  receiveMessages(props.type, props.instanceId);
});

onUnmounted(() => {
  endReceiveMessages()
})

const chatInput = ref("");

function getInputAndChat() {
  updateTestMessage(props.type, props.username + ": " + chatInput.value, props.instanceId);
  chatInput.value = ""
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
