<script setup lang="ts">
const props = defineProps<{
  name: String;
}>();
</script>

<template>
  <div class="flex flex-row">
    <div class="w-10 h-10 bg-greenOverlay"></div>
    <p class="pl-1 self-center">{{ name }}</p>
  </div>
</template>
