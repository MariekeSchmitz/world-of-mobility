<script setup lang="ts">
import {RouterView } from "vue-router";
import { useLogin } from "./services/login/useLogin";

const { logout } = useLogin();

/**
 * logs out user when tab is closed, reloaded or similar actions are performed
 * needed, because otherwise user is forever in backend userlist
 */
window.addEventListener("beforeunload", () => {
  logout();
});
</script>

<template>
  <RouterView></RouterView>
</template>
