<script setup lang="ts">
import { useScript } from "@/services/editor/useScript";
import { ref, watch } from "vue";
import ScriptInfoBox from "./ScriptInfoBox.vue";

const postScript = useScript();

const props = defineProps<{
  id: number;
  x: number;
  y: number;
}>();

const scriptSet = ref(false)

const emit = defineEmits<{
  (e: "scriptWindowClosed"): void;
}>();

function windowClosed(): void {
  emit("scriptWindowClosed");
}

function submitAndClose() {
  if(script.value){
    postScript(props.id, script.value, props.x, props.y);
    windowClosed();
  }
}
const info = ref(false);
const script = ref("");

watch(script, () => {
  if(script.value != ""){
    scriptSet.value = true
  }else{
    scriptSet.value = false
  }
});
</script>

<template>
  <div
    class="fixed inset-0 bg-greenOverlay bg-opacity-50 overflow-y-auto h-full w-full grid grid-cols-3 content-center"
  >
    <div class="bg-white p-16 grid col-start-2 col-end-2">
      <label for="script"><h2>Eigenes Script für den NPC eingeben:</h2></label>
      <textarea type="text" id="script" rows="4" cols="50" class="bg-greenLight" v-model="script">
      </textarea>
      <button
        @click="submitAndClose()"
        :class="{
          'buttonOrange bg-orangeLight mt-12 w-1/2 justify-self-center': !scriptSet,
          'buttonOrange mt-12 w-1/2 justify-self-center': scriptSet,
          }"      >
        Submit
      </button>
      <button
        @click="windowClosed()"
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
