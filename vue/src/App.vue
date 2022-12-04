<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
</script>

<template>
    <nav>
        <RouterLink to="/login">Login</RouterLink>
        <RouterLink to="/game">Game</RouterLink>
        <RouterLink to="/worldintro">WorldSelect</RouterLink>
        <RouterLink to="/gameintro">GameSelect</RouterLink>
        <RouterLink to="/createWorld">CreateWorld</RouterLink>
        <RouterLink to="/worldSelection">WorldSelection</RouterLink>
    </nav>
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
