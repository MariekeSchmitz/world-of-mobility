<script setup lang="ts">
import { ref, watch, onMounted } from "vue";

const props = withDefaults(
  defineProps<{
    errorMsg: string;
  }>(),
  { errorMsg: "",
  }
);

</script>

<template>
  
  <div id="errorBox" class="bg-greenDark w-1/5 p-12 absolute right-0 top-16 text-left duration-200 ease-out transition-all opacity-0 z-50 shadow-md shadow-greenDark">
    <h3 class="text-greenLight">Hinweis</h3>
    <p class="text-white">{{ props.errorMsg }}</p>
  </div>

  
</template>
