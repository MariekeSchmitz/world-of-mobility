<script setup lang="ts">
import { ref, watch, onMounted } from "vue";

const props = withDefaults(
  defineProps<{
    feedbackMsg: string;
  }>(),
  { feedbackMsg: "",
  }
);

</script>

<template>
  
  <div id="feedbackBox" class="bg-white w-1/5 p-12 absolute right-0 top-16 text-left duration-200 ease-out transition-all opacity-0 z-50">
    <h3 class="text-greenDark">Erfolgreich</h3>
    <p class="text-greenDark">{{ props.feedbackMsg }}</p>
  </div>

</template>
