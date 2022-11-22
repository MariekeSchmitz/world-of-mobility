<script setup lang="ts">
    
    function toggle(){
      if(document.getElementById('minimap').style.display == "none"){
        document.getElementById('minimap').style.display = "grid";
        document.getElementById('showMap').style.display = "none";
        
      }else{
        document.getElementById('minimap').style.display = "none";
        document.getElementById('showMap').style.display = "block";
      }
    }
</script>

<template>
    <div id="minimap">
        <div class="minimapButtons">
            <button><img src="src/buttons/editor/minus.png"/></button>
            <button><img src="src/buttons/editor/plus.png"/></button>
            <button @click="toggle"><img src="src/buttons/editor/arrow-down.png"/></button>
        </div>
        <p>Minimap, separate camera view from further away, +- Buttons control minimap camera</p>
    </div>

    <button id="showMap" @click="toggle"><img src="src/buttons/editor/arrow-up.png"/></button>

</template>

<style>

    #minimap{
        position: fixed;
        bottom: 10px;
        right: 10px;
        height: 20%;
        aspect-ratio: 1/1;
        background-color: wheat;
    }

    .minimapButtons{
        position: fixed;
        bottom: 22%;
        right: 10px;
    }

    #showMap{
      display: none;
      position: fixed;
      bottom: 2%;
      right:10px;
    }

</style>