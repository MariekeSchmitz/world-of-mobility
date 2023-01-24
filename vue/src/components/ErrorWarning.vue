<script setup lang="ts">
import { ref, watch, onMounted } from "vue";

const props = withDefaults(
  defineProps<{
    errorMsg: string;
    errorExisting: boolean
  }>(),
  { errorMsg: "",
    errorExisting: false
  }
);

</script>

<template>
  
  <div id="errorBox" class="hover:animate-pulse bg-greenDark w-1/5 p-12 absolute -right-80 top-28 text-left duration-200 ease-out transition-all opacity-0 z-50">
    <h3 class="text-greenLight">Hinweis</h3>
    <p class="text-white">{{ props.errorMsg }}</p>
  </div>

  
</template>
