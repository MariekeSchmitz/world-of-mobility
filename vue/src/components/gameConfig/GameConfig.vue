<script setup lang="ts">

import { ref } from "vue";

const name = ""
const playerLimit = 0
const npcs = false

</script>

<template>
  <div class="wrapper">
    <h1>Neues Spiel</h1>
    <div class="square"></div>
    <p>Spielname</p>
    <input v-model="name" placeholder="Spielname eingeben">
    <p>Spieleranzahl</p>
    <input type="number" v-model="playerLimit">
    <p>NPCs platzieren</p>
    <label class="switch">
        <input type="checkbox">
        <span class="slider round"></span>
    </label>
  </div>
</template>

<style scoped>
.square {
    width: 12.5rem;
    height: 12.5rem;
    background-color: gray;
}
.wrapper {
  display: grid;
  place-items: center;
}

button {
  padding: 1em;
  border-radius: 1em;
  background-color: antiquewhite;
  width: 10rem;
  margin: 10px;
}

/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>
