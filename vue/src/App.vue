<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
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

<style>
nav {
  width: 100%;
  font-size: 20px;
  text-align: center;
  margin-top: 2rem;
  margin-bottom: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}
</style>
