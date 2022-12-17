<template>
    <div id="spanwpoint-container">
        <div v-for="tileRow in testObj.tiles" id="tile-row">
            <div v-for="tile in tileRow" id="tile-column">
                <SimplifiedTile :tile-type="tile.type"/>
            </div>
        </div>
    </div>
</template>


<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue';
import SimplifiedTile from '../simplifiedTile/SimplifiedTile.vue';
import { useMiniMapScaling } from '@/components/spawnpoint/useMiniMapScaling';

const { miniMapScalingState, addWindowWidthListener, removeWindowWIdthListener } = useMiniMapScaling();

const testObj = ref({
    tiles: [
        [
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "STREET_CROSS"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "GRASSTILE"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "RAIL_CURVE"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "RAIL_STRAIGHT"
            },
        ],
        [
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "SIDEWAY"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "STREET_CURVE"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "STREET_STRAIGHT"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "STREET_T_CROSS"
            },
        ],
        [
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "STREET_CROSS"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "GRASSTILE"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "RAIL_CURVE"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "RAIL_STRAIGHT"
            },
        ],
        [
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "SIDEWAY"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "STREET_CURVE"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "STREET_STRAIGHT"
            },
            {
                allowedDirections: [
                    "NORTH",
                    "EAST",
                    "SOUTH",
                    "WEST"
                ],
                orientation: "NORTH",
                placedObjects: [],
                type: "STREET_T_CROSS"
            },
        ]
    ],
    name: "test",
    npcs: [
        {
            orientation: "NORTH",
            xPos: 0.0,
            yPos: 0.0,
            maxVelocity: 1.0,
            capacity: 1.0,
            currentVelocity: 0.0,
            script: "",
            type: "PASSANGER"
        }
    ]
});

onMounted(() => {
    const numberOfRows = testObj.value.tiles[0].length;
    addWindowWidthListener(numberOfRows);
});

onUnmounted(() => {
    removeWindowWIdthListener();
})

</script>


<style scoped>
#spanwpoint-container {
    height: v-bind('miniMapScalingState.windowWidth');
    width: v-bind('miniMapScalingState.windowWidth');
    background-color: rgb(66, 66, 66);
}

#tile-row {
    display: flex;
    flex-direction: row;
}

#tile-column {
    display: flex;
    flex-direction: column;
}
</style>