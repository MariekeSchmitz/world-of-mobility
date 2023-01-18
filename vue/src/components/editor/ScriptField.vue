<script setup lang="ts">
import { useScript } from "@/services/useScript";
import { number } from "mathjs";
import { ref } from "vue";
import ScriptInfoBox from "./ScriptInfoBox.vue";

const postScript = useScript();

const props = defineProps<{
  id: number;
  x: number;
  y: number;
}>();

const emit = defineEmits<{
  (e: "scriptWindowClosed"): void;
}>();

function windowClosed(): void {
  emit("scriptWindowClosed");
}

function submitAndClose() {
  postScript(props.id, script.value, props.x, props.y);
  windowClosed();
}
const info = ref(false);
const script = ref("");
</script>

<template>
  <div>
    <label for="script">Script for NPC</label>
    <input type="text" id="script" v-model="script" />

    <button @click="submitAndClose()">Submit</button>
    <button @click="windowClosed">Use Default</button>
    <button @click="() => (info = !info)">
      {{ info ? "close Info" : "Info" }}
    </button>

    <div v-if="info">
      <ScriptInfoBox></ScriptInfoBox>
    </div>
  </div>
</template>
