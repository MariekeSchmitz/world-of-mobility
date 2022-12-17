<template>
    <div id="simplified-tile" :class="tileType"></div>
</template>


<script setup lang="ts">
import { defineProps, onMounted, ref } from 'vue';
import { useMiniMapScaling } from '../spawnpoint/useMiniMapScaling';

const props = defineProps<{
    tileType: string
}>();

const { miniMapScalingState } = useMiniMapScaling();

</script>


<style scoped>

#simplified-tile {
    /* background-image: url('@/textures/tiles/GRASSTILE.jpg'); */
    background-repeat: no-repeat;
    background-size: contain;
    background-position: center;
    height: v-bind('miniMapScalingState.boxSizing');
    width: v-bind('miniMapScalingState.boxSizing');
}

.GRASSTILE {
    background-image: url('@/textures/tiles/GRASSTILE.jpg');
}

.RAIL_CURVE {
    background-image: url('@/textures/tiles/RAIL_CURVE.jpg');
}

.RAIL_STRAIGHT {
    background-image: url('@/textures/tiles/RAIL_STRAIGHT.jpg');
}

.SIDEWAY {
    background-image: url('@/textures/tiles/SIDEWAY.jpg');
}

.STREET_CROSS {
    background-image: url('@/textures/tiles/STREET_CROSS.jpg');
}

.STREET_CURVE {
    background-image: url('@/textures/tiles/STREET_CURVE.jpg');
}

.STREET_STRAIGHT {
    background-image: url('@/textures/tiles/STREET_STRAIGHT.jpg');
}

.STREET_T_CROSS {
    background-image: url('@/textures/tiles/STREET_T_CROSS.jpg');
}

</style>