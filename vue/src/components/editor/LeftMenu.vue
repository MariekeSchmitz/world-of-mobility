<script setup lang="ts">
function toggle() {
  const leftMenu = document.getElementById("leftMenu");
  const showSideBar = document.getElementById("showSideBar");

  if (leftMenu != null && showSideBar != null) {
    if (leftMenu.style.display == "none") {
      leftMenu.style.display = "grid";
      showSideBar.style.display = "none";
    } else {
      leftMenu.style.display = "none";
      showSideBar.style.display = "block";
    }
  }
}
</script>

<template>
  <div id="leftMenu">
    <div class="upperLeftMenu">
      <button class="roundButton" @click="toggle">
        <img src="@/assets/images/buttons/editor/arrow-left.png" />
      </button>
      <button class="roundButton">
        <img src="@/assets/images/buttons/editor/settings.png" />
      </button>
      <button class="roundButton">
        <img src="@/assets/images/buttons/editor/question.png" />
      </button>
    </div>
    <div class="bottomLeftMenu">
      <button id="gridButton"><img src="@/assets/images/buttons/editor/grid.png" /></button>
      <div class="firstDoubleButton">
        <button><img src="@/assets/images/buttons/editor/minus.png" /></button>
        <button><img src="@/assets/images/buttons/editor/plus.png" /></button>
      </div>
      <div class="secondDoubleButton">
        <button class="singleSecondDoubleButton">
          <img src="@/assets/images/buttons/editor/arrow-left.png" />
        </button>
        <button class="singleSecondDoubleButton">
          <img src="@/assets/images/buttons/editor/arrow-right.png" />
        </button>
      </div>
    </div>
  </div>

  <button id="showSideBar" @click="toggle">
    <img src="@/assets/images/buttons/editor/arrow-right.png" />
  </button>
</template>

<style>
#leftMenu {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(3, 1fr);
  position: fixed;
  bottom: 20%;
  left: 0px;
  height: 60%;
  width: 60px;
  background-color: rgb(221, 221, 221);
  row-gap: 24%;
}

.upperLeftMenu {
  display: grid;
  grid-template-columns: 1fr;
  grid-template-rows: repeat(3, 1fr);
  row-gap: 12%;
  margin: 20%;
}

.bottomLeftMenu {
  grid-template-columns: 1fr;
  grid-template-rows: repeat(3, 1fr);
  row-gap: 0%;
  margin: 20% 4%;
}

#gridButton {
  width: 80%;
  margin: 10%;
  aspect-ratio: 1/1;
  background-color: rgb(221, 221, 221);
}

.firstDoubleButton {
  background-color: white;
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin-top: 15px;
}

.secondDoubleButton {
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin-top: 10px;
}

.singleSecondDoubleButton {
  height: 180%;
  background-color: rgb(221, 221, 221);
  border: none;
}

#showSideBar {
  display: none;
  position: fixed;
  bottom: 75%;
  left: 0px;
}
</style>
