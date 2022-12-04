<script setup lang="ts">

const props = 
  defineProps<{
    name: String;
  }>();

</script>

<template>
  <div class="wrapper">
    <div class="flex-box">
        <div class="square"></div>
        <p> {{name}} </p>
    </div>
  </div>
</template>

<style scoped>
.square {
    width: 2.5rem;
    height: 2.5rem;
    background-color: gray;
}
.wrapper {
  display: grid;
  place-items: center;
}

.flex-box {
	display:flex; 
	justify-content: space-around;
}

</style>
