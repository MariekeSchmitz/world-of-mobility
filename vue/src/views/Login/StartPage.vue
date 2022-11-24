<script setup lang="ts">
import { ref } from "vue";

const username = ref("");
const avatar = ref("");
</script>

<template>
  <img src="" alt="" />
  <img src="" alt="" />
  <input type="text" v-model="username" />
  <button>Spielen</button>
  <button>Baumodus</button>
</template>
