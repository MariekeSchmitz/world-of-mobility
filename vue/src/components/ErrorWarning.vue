<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    errorMsg: string;
  }>(),
  { errorMsg: "" }
);
</script>

<template>
  <div class="bg-greenDark w-1/5 p-12 absolute right-28 top-28 text-left z-50">
    <h3 class="text-greenLight">Fehler</h3>
    <p class="text-white">{{ props.errorMsg }}</p>
  </div>
</template>
