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
  <div
    class="fixed inset-0 bg-greenOverlay bg-opacity-50 overflow-y-auto h-full w-full grid grid-cols-3 content-center"
  >
    <div class="bg-white p-16 grid col-start-2 col-end-2">
      <label for="script"><h2>Eigenes Script für den NPC eingeben:</h2></label>
      <input type="text" id="script" v-model="script" />

      <button
        @click="submitAndClose()"
        class="buttonOrange bg-orangeLight mt-12 w-1/2 justify-self-center"
      >
        Submit
      </button>
      <button
        @click="windowClosed"
        class="buttonGreen w-1/2 justify-self-center"
      >
        Use Default
      </button>
      <button
        @click="() => (info = !info)"
        class="buttonGreen w-1/3 justify-self-center"
      >
        {{ info ? "close Info" : "Info" }}
      </button>

      <div v-if="info">
        <ScriptInfoBox></ScriptInfoBox>
      </div>
    </div>
  </div>
</template>
