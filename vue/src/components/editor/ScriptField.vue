<script setup lang="ts">
import { useScript } from '@/services/useScript';
import { number } from 'mathjs';
import { ref } from 'vue';

const postScript = useScript()

const props = defineProps<{
     id: number,
     npcId: number
}>()
    
const script = ref("")
</script>


<template>
    <label for="script">Script for NPC</label>
    <input type="text" id="script" v-model="script"/>

    <button @click="postScript(props.id, script, props.npcId)"></button>

</template>