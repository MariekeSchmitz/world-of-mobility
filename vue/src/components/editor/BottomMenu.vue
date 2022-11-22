<script setup lang="ts">
    import { TileName } from "../../services/editor/TileNameEnum";

    function scrollingLeft(){
        document.getElementById('box-wrapper').scrollLeft -= 200;
    }

    function scrollingRight(){
        document.getElementById('box-wrapper').scrollLeft += 200;
    }
    
    function toggle(){
      if(document.getElementById('bottomMenu').style.display == "none"){
        document.getElementById('bottomMenu').style.display = "grid";
        document.getElementById('showElement').style.display = "none";
        
      }else{
        document.getElementById('bottomMenu').style.display = "none";
        document.getElementById('showElement').style.display = "block";
      }
    }

    function switchItems(element:string){
      switch(element){
        case 'componentItems':
          document.getElementById('streetItems').style.display = "none";
          document.getElementById('componentItems').style.display = "grid";
          document.getElementById('otherItems').style.display = "none";
          break;
        case 'otherItems':
          document.getElementById('streetItems').style.display = "none";
          document.getElementById('componentItems').style.display = "none";
          document.getElementById('otherItems').style.display = "grid";
          break;
        default:
          document.getElementById('streetItems').style.display = "grid";
          document.getElementById('componentItems').style.display = "none";
          document.getElementById('otherItems').style.display = "none";
          break;
      }
    }

    function switchContent(element:string){
      switch(element){
        case 'npcMenu':
          document.getElementById('landscapeMenu').style.display = "none";
          document.getElementById('npcMenu').style.display = "grid";
          break;
        default:
          document.getElementById('landscapeMenu').style.display = "grid";
          document.getElementById('npcMenu').style.display = "none";
          break;
      }
    }

</script>

<template>

  <div id="bottomMenu" class="menuOff">

      <div class="typeSelector">
        <button @click="switchContent('landscapeMenu')">Infrastruktur, Landschaft</button>
        <button @click="switchContent('npcMenu')">NPCs</button>
      </div>

      <div id="landscapeMenu">

        <div class="itemSelector">
          <button @click="switchItems('streetItems')"><img src="src/textures/editor/STREET_STRAIGHT.jpg"/></button>
          <button @click="switchItems('componentItems')">Baum</button>
          <button @click="switchItems('otherItems')">Tankstelle</button>
        </div>

        <!--
          für beliebige menge an tiles auswahl selbst erstellen lassen - src klappt nicht, wie angeben
        <div class="items" v-for="tileType in TileName">
          <p>{{tileType}}</p>
            <button @click="$emit('selectTile', tileType)"><img :props="{src: 'src/textures/editor/'+tileType+'.jpg'}"/></button>
        </div>
        -->

        <div id="streetItems">

          <button id="scrollLeft" @mousedown="scrollingLeft"><img src="src/buttons/editor/arrow-left.png"/></button>

          <ul id="box-wrapper">
              <li><button class="tileButton" @click="$emit('selectTile', TileName.STREET_STRAIGHT)"><img src="src/textures/editor/STREET_STRAIGHT.jpg"/></button></li>
              <li><button class="tileButton" @click="$emit('selectTile', TileName.STREET_CURVE)"><img src="src/textures/editor/STREET_CURVE.jpg"/></button></li>
              <li><button class="tileButton" @click="$emit('selectTile', TileName.STREET_T_CROSS)"><img src="src/textures/editor/STREET_T_CROSS.jpg"/></button></li>
              <li><button class="tileButton" @click="$emit('selectTile', TileName.STREET_CROSS)"><img src="src/textures/editor/STREET_CROSS.jpg"/></button></li>
              <li><button class="tileButton" @click="$emit('selectTile', TileName.SIDEWAY)"><img src="src/textures/editor/SIDEWAY.jpg"/></button></li>
          </ul>
          
          <button id="scrollRight" @click="scrollingRight"><img src="src/buttons/editor/arrow-right.png"/></button>

        </div>

        <div id="componentItems">
        </div>

        <div id="otherItems">
        </div>  

      </div>
      
      <button id="hideElement"  @click="toggle"><img src="src/buttons/editor/arrow-down.png"/></button>
      
      <div id="npcMenu">

      </div>

      
  </div>
  <button id="showElement" @click="toggle"><img src="src/buttons/editor/arrow-up.png"/></button>

</template>

<style>

    #bottomMenu{
        display: grid;
        grid-template-columns: 20% 75% 5%;
        position: fixed;
        width: 50%;
        height: 14%;
        background-color: rgb(221, 221, 221);
        padding: 10px;
        bottom: 0px;
        left:10%;
 
    }

    #landscapeMenu{
      display: grid;
      grid-template-columns: 20% 80%;
    }

    #npcMenu{
      display: grid;
      display: grid;
      grid-template-columns: 20% 80%;
    }

    #showElement{
      display: none;
      position: fixed;
      bottom: 2%;
      left:58%;
    }

    .typeSelector{
        display: grid;
        grid-template-rows: 1fr 1fr;
    }

    .itemSelector{
        display: grid;
        grid-template-rows: 1fr 1fr 1fr;
    }

    
    #streetItems{
        display: grid;
        grid-template-columns: 10% 80% 10%;
        margin: auto 0;
    }

    .tileButton > img{
      width: 80px;
      height: 80px;
    }

    ul {
      height:100%;
      width: 85%;
      overflow-y:hidden;
      overflow-x: hidden;
      white-space:nowrap;
    }
    
    li {
      list-style-type: none;
      display:inline-block;
      background-color:lightblue;
      margin: 0 5px;
    }



</style>