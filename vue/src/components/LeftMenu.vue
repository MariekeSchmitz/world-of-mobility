<script setup lang="ts">

</script>

<template>
  <div class="leftMenu">
    <div class="upperLeftMenu">
      <button class="roundButton" @click="hideElement">&lt;</button>
      <button class="roundButton">settings</button>
      <button class="roundButton">help</button>
    </div>
    <div class="bottomLeftMenu">
      <button id="gridButton">grid</button>
      <div class="firstDoubleButton">
        <button>-</button>
        <button>+</button>
      </div>
      <div class="secondDoubleButton">
        <button class="singleSecondDoubleButton">&lt;</button>
        <button class="singleSecondDoubleButton">&gt;</button>
      </div>
    </div>
  </div>
</template>

<style>

  .leftMenu{
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: repeat(3,1fr);
    position: fixed;
    bottom: 20%;
    left: 0px;
    height: 60%;
    width: 60px;
    background-color: rgb(221, 221, 221);
    row-gap: 24%;
  }

  .upperLeftMenu{
    display: grid;
    grid-template-columns: 1fr;
    grid-template-rows: repeat(3,1fr);
    row-gap: 12%;
    margin: 20%;
  }

  .bottomLeftMenu{
    grid-template-columns: 1fr;
    grid-template-rows: repeat(3,1fr);
    row-gap: 0%;
    margin: 20% 4%;
  }

  #gridButton{
    width: 80%;
    margin: 10%;
    aspect-ratio: 1/1;
    background-color: rgb(221, 221, 221);
  }

  .firstDoubleButton{
    background-color: white;
    display: grid;
    grid-template-columns: 1fr 1fr;
    margin-top: 15px;
  }

  .secondDoubleButton{
    display: grid;
    grid-template-columns: 1fr 1fr;
    margin-top: 10px;
  }

  .singleSecondDoubleButton{
    height: 180%;
    background-color: rgb(221, 221, 221);
    border: none;
  }

</style>