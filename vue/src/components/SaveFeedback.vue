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
  
  <div id="errorBox" :class="{
      'feedbackInvisible':!feedbackMsg,
      'feedbackVisible':feedbackMsg,
      }">    
    <h3 class="text-greenDark">Erfolgreich</h3>
    <p class="text-greenDark">{{ props.feedbackMsg }}</p>
  </div>

</template>
