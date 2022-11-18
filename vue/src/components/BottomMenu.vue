<script setup lang="ts">

</script>

<template>
  <div class="bottomMenu">
    <div class="typeSelector">
      <button>Infrastruktur, Landschaft</button>
      <button>NPCs</button>
    </div>
    <div class="itemSelector">
      <button>Tiles</button>
      <button>Baum</button>
      <button>Tankstelle</button>
    </div>
    <div class="items">

        <button @click="$emit('selectTile', 'STREET_STRAIGHT')">STREET_STRAIGHT</button>
        <button @click="$emit('selectTile','STREET_CURVE')">STREET_CURVE</button>
        <button @click="$emit('selectTile','STREET_T_CROSS')">STREET_T_CROSS</button>
        <button @click="$emit('selectTile','STREET_CROSS')">STREET_CROSS</button>
        <button @click="$emit('selectTile','SIDEWAY')">SIDEWAY</button>
    </div>
    <button @click="hideElement">v</button>
   </div>
</template>

<style>

    .bottomMenu{
        display: grid;
        grid-template-columns: 20% 10% 68% 2%;
        position: fixed;
        width: 50%;
        height: 10%;
        background-color: rgb(221, 221, 221);
        bottom: 10px;
        left:10%;
 
    }

    .typeSelector{
        display: grid;
        grid-template-rows: 1fr 1fr;
    }

    .itemSelector{
        display: grid;
        grid-template-rows: 1fr 1fr 1fr;
    }

    
    .items{

    }

</style>