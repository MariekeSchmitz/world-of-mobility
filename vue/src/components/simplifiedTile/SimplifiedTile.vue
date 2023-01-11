<template>
    <div>
        <div id="simplifiedContainer" :class="{selected: isSelected}" @click="setSpawnPoint(props.xIndex, props.yIndex)"></div>
        <div :class="[{rotateSelected: isSelected}, {marker: isSelected}]"></div>
        <div id="simplified-tile" :class="[tileType, orientation, {selected: isSelected}]" ></div>
    </div>
</template>


<script setup lang="ts">
import { defineProps, onMounted, ref, computed } from 'vue';
import { useSpawnPoint } from '../spawnpoint/useSpawnPoint';

const { miniMapScalingState, setSpawnPoint, spawnState } = useSpawnPoint();

const props = withDefaults(
        defineProps<{
        tileType: string,
        orientation: string,
        xIndex: number,
        yIndex: number
    }>(),{
        tileType: "GRASSTILE",
        orientation: "NORTH",
        xIndex: 0,
        yIndex: 0
    }
);

const isSelected = computed(() => {
    return (spawnState.tileNumber == ((props.xIndex + 1) + (props.yIndex * miniMapScalingState.numberOfRows)));
})

</script>


<style scoped>
#simplifiedContainer {
    position: absolute;
    box-sizing: border-box;
    height: v-bind('miniMapScalingState.boxSizing');
    width: v-bind('miniMapScalingState.boxSizing');
    z-index: 1;
}

#simplified-tile {
    /* background-image: url('@/textures/tiles/GRASSTILE.jpg'); */
    box-sizing: border-box;
    background-repeat: no-repeat;
    background-size: contain;
    background-position: center;
    height: v-bind('miniMapScalingState.boxSizing');
    width: v-bind('miniMapScalingState.boxSizing');
    z-index: -2;
}

.marker {
    height: v-bind('miniMapScalingState.boxSizing');
    width: v-bind('miniMapScalingState.boxSizing');
    border: none;
    position: absolute;
    background-color: rgb(105, 105, 105);
    clip-path: polygon(50% 0%, 0% 100%, 100% 100%);
    z-index: 2;
}

@keyframes innerGlow {
    from {box-shadow: inset 0 0 5px rgba(29, 29, 29, 0.388);}
    to {box-shadow: inset 0 0 50px rgba(196, 246, 18, 0.54);}
}

@keyframes rotateMarker {
    from {transform: rotate(180deg) scale(50%) rotateY(0deg) translateY(calc(v-bind('miniMapScalingState.boxSizing')));}
    to {transform: rotate(180deg) scale(50%) rotateY(180deg) translateY(calc(v-bind('miniMapScalingState.boxSizing') / 2));}
}

.rotateSelected {
    animation-name: rotateMarker;
    animation-iteration-count: infinite;
    animation-direction: alternate;
    animation-duration: 1s;
}

.selected {
    animation-name: innerGlow;
    animation-iteration-count: infinite;
    animation-direction: alternate;
    animation-duration: 1s;
}

.NORTH {
    transform: rotate(0);
}

.EAST {
    transform: rotate(90deg);
}

.SOUTH {
    transform: rotate(180deg);
}

.WEST {
    transform: rotate(270deg);
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