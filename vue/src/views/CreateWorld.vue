<script setup lang="ts">
import { ref } from 'vue';

import {createWorld} from "@/services/useEditor";

 

const name = ref("")



</script>


<template>

    <h1>Neue Welt erstellen</h1>
    <div>
        <label for="name">Name</label>
        <input type="text" id="name" v-model="name"/>
    </div> 
    <button @click="createWorld(name)">Erstellen</button>


</template>