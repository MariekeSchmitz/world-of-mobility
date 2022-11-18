<template>
  <div>
    
    <SIDEWAY v-if="(props.type === tileType.SIDEWAY)"
      :width= props.heigth
      :height= props.width
      :posX= props.posX
      :posY= props.posY
      :posZ= props.posZ
      :rotationX= props.rotationX
      :rotationY= props.rotationY
      :rotationZ= props.rotationZ
      :type=props.type>
    </SIDEWAY>

    <STREET_CURVE v-if="(props.type === tileType.STREET_CURVE)"
      :width= props.heigth
      :height= props.width
      :posX= props.posX
      :posY= props.posY
      :posZ= props.posZ
      :rotationX= props.rotationX
      :rotationY= props.rotationY
      :rotationZ= props.rotationZ
      :type=props.type>
    </STREET_CURVE>

    <STREET_STRAIGHT v-if="(props.type === tileType.STREET_STRAIGHT)"
      :width= props.heigth
      :height= props.width
      :posX= props.posX
      :posY= props.posY
      :posZ= props.posZ
      :rotationX= props.rotationX
      :rotationY= props.rotationY
      :rotationZ= props.rotationZ
      :type=props.type>
    </STREET_STRAIGHT>

    <STREET_CROSS v-if="(props.type === tileType.STREET_CROSS)"
      :width= props.heigth
      :height= props.width
      :posX= props.posX
      :posY= props.posY
      :posZ= props.posZ
      :rotationX= props.rotationX
      :rotationY= props.rotationY
      :rotationZ= props.rotationZ
      :type=props.type>
    </STREET_CROSS>

    <STREET_T_CROSS v-if="(props.type === tileType.STREET_T_CROSS)"
      :width= props.heigth
      :height= props.width
      :posX= props.posX
      :posY= props.posY
      :posZ= props.posZ
      :rotationX= props.rotationX
      :rotationY= props.rotationY
      :rotationZ= props.rotationZ
      :type=props.type>
    </STREET_T_CROSS>

    <RAIL_CURVE v-if="(props.type === tileType.RAIL_CURVE)"
      :width= props.heigth
      :height= props.width
      :posX= props.posX
      :posY= props.posY
      :posZ= props.posZ
      :rotationX= props.rotationX
      :rotationY= props.rotationY
      :rotationZ= props.rotationZ
      :type=props.type>
    </RAIL_CURVE>

    <RAIL_STRAIGHT v-if="(props.type === tileType.RAIL_STRAIGHT)"
      :width= props.heigth
      :height= props.width
      :posX= props.posX
      :posY= props.posY
      :posZ= props.posZ
      :rotationX= props.rotationX
      :rotationY= props.rotationY
      :rotationZ= props.rotationZ
      :type=props.type>
    </RAIL_STRAIGHT>
</div>
</template>

<script setup lang="ts">
//--------imports-----------
import SIDEWAY from "./Tiles/SIDEWAY.vue";
import STREET_CURVE from "./Tiles/STREET_CURVE.vue";
import STREET_STRAIGHT from "./Tiles/STREET_STRAIGHT.vue";
import STREET_CROSS from "./Tiles/STREET_CROSS.vue";
import STREET_T_CROSS from "./Tiles/STREET_T_CROSS.vue";
import RAIL_CURVE from "./Tiles/RAIL_CURVE.vue";
import RAIL_STRAIGHT from "./Tiles/RAIL_STRAIGHT.vue";
import {tileType} from "./TileTypes"
import { defineProps, withDefaults } from "vue";

//--------props-------------
// technical debt: no use of enums

const sideway="SIDEWAY"
const street_curve="STREET_CURVE"

// rotation: to translate our 'orientation' to a matching Tile, we need to pass a multiple 'Math.PI / 2' to our rotationZ value;
const props = withDefaults(
  defineProps<{
    width: number;
    heigth: number;
    posX: number;
    posY: number;
    posZ: number;
    rotationX: number;
    rotationY: number;
    rotationZ: number;
    type: string;
  }>(),
  { width: 10, heigth: 10 }
);

//--------code------------


</script>
