<script setup lang="ts">
import type { INpc } from "@/interfaces/npc/INpc2";



const props = defineProps<{
     npc:INpc
}>()



</script>



<template>
    <p>{{npc.user}} {{npc.xPos}} {{npc.yPos}}</p>
</template>

