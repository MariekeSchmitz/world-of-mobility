<template>
    <div id="simplified-tile"></div>
</template>


<script setup lang="ts">
import { defineProps } from 'vue';

const props = defineProps<{
    tileType: string
}>();



</script>


<style scoped>

#simplified-tile {
    background-image: url("@/textures/tiles/GRASSTILE.jpg");
}

</style>