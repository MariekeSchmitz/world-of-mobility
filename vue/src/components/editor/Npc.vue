<script setup lang="ts">
import type { INpc } from "@/services/INpc";



const props = defineProps<{
     npc:INpc
}>()



</script>



<template>
    <p>{{npc.user}} {{npc.xPos}} {{npc.yPos}}</p>
</template>

