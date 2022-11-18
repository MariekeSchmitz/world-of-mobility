<script setup lang="ts">

</script>

<template>
    <div class="minimap">
        <div class="minimapButtons">
            <button>-</button>
            <button>+</button>
            <button @click="hideElement">v</button>
        </div>
        <p>Minimap, separate camera view from further away, +- Buttons control minimap camera</p>
    </div>
</template>

<style>

    .minimap{
        position: fixed;
        bottom: 10px;
        right: 10px;
        height: 20%;
        aspect-ratio: 1/1;
        background-color: wheat;
    }

    .minimapButtons{
        position: fixed;
        bottom: 22%;
        right: 10px;
    }

</style>