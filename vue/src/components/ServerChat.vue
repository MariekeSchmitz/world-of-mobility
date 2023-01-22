<template>
  <div id="chat-container">
    <div id="input-btn">
      <input type="text" v-model="chatInput" />
      <button @click="getInputAndChat()">Send</button>
    </div>
    <ul id="chat-lst">
      <li v-for="msg in msgListe" :key="msg">{{ msg }}</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { useServerMessage } from "@/services/useServerMessage";
import { computed } from "@vue/reactivity";
import { onMounted, ref } from "vue";

const props = defineProps<{
  instanceId: number;
}>();

const { receiveMessages, updateTestMessage, msgState } = useServerMessage();

const msgListe = computed(() => {
  return msgState.msgLst.map((msg) => msg.txt);
});

onMounted(() => {
  receiveMessages();
});

const chatInput = ref("");

function getInputAndChat() {
  updateTestMessage(chatInput.value, props.instanceId);
}
</script>

<style scoped>
ul {
  list-style: none;
  color: rgb(44, 44, 44);
  padding-left: 0px;
  font-size: 12pt;
}

#chat-container {
  position: absolute;
  display: flex;
  flex-direction: column-reverse;
  top: 100px;
  left: 10px;

  height: 200px;
  width: 350px;
  overflow-y: hidden;
  padding: 10px;
}

#input-btn {
  display: flex;
  flex-direction: row;
  font-size: 12pt;
}

#input-btn input {
  background-color: rgba(255, 255, 255, 0.437);
  border: 3px solid rgba(188, 188, 188, 0.165);
  border-radius: 10px;
  box-shadow: 3px 3px 3px rgba(0, 0, 0, 0.177);
  padding: 5px;
}

#input-btn input:focus {
  border: none;
  outline: none;
}

#input-btn button {
  background-color: rgba(203, 203, 203, 0.672);
  border: 3px solid rgba(188, 188, 188, 0.165);
  border-radius: 10px;
  box-shadow: 3px 3px 3px rgba(0, 0, 0, 0.177);
  font-weight: bold;
  color: rgb(44, 44, 44);
  padding: 5px;
}
</style>
