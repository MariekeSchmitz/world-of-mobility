<script setup lang="ts">
/**
 * view in which a  new world can be created
 * @author Marie Bohnert, Tom Gouthier, Victoria Thee
 */
import { ref } from 'vue';
import {useEditor} from "@/services/useEditor";

const name = ref("")
const {createWorld} = useEditor()
</script>


<template>

    <h1>Neue Welt erstellen</h1>
    <div>
        <label for="name">Name</label>
        <input type="text" id="name" v-model="name"/>
    </div> 
    <button @click="createWorld(name)">Erstellen</button>

</template>