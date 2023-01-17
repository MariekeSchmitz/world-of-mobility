<script setup lang="ts">
/**
 * @defineProps gamename - name of the game
 * @defineProps worldname - name of the world
 * @defineProps people - amount of people playing in this world
 * @defineProps picture - source for the picture of this world
 * @author Finn Schindel, Astrid Klemmer
 */
const props = withDefaults(
  defineProps<{
    gamename?: string;
    worldname: string;
    people?: number;
    picture?: string;
  }>(),
  {
    gamename: "",
    picture: "",
    people: 0,
  }
);
</script>

<template>
  <div id="frame">
    <button id="framebutton">
      <div>
        <img id="worldimage" src="" alt="" />
        <!-- ToDo Picture  -->
      </div>
      <div id="flexbox">
        <div v-if="props.people != 0" id="activeimage">
          <img class="frameimg" src="../../buttons/gameintro/rec.png" alt="" />
        </div>
        <div id="textinput">
          <p id="gamename" v-if="props.gamename != ''">{{ props.gamename }}</p>
          <p id="worldname">{{ props.worldname }}</p>
          <p id="moreInfo" v-if="props.people != 0">
            {{ props.people }} Person online
          </p>
        </div>
      </div>
    </button>
  </div>
</template>

<style>
#frame {
  width: 12.5rem;
  height: 17.5rem;
}

#worldimage {
  width: 100%;
  height: 11rem;
  background-color: rgb(189, 189, 189);
}
#gamename {
  font-size: 16pt;
  margin: 3px 0 0 0;
}
#worldname {
  font-size: 14pt;
  margin: 3px 0 0 0;
}
#moreInfo {
  font-size: 10pt;
  margin: 3px 0 0 0;
}
#activeimage {
  flex: 1;
  align-self: center;
}
#textinput {
  flex: 4;
  text-align: left;
  padding-left: 10px;
}

#flexbox {
  display: flex;
}
#framebutton {
  width: 100%;
  height: 100%;
  background-color: white;
  border: none;
  border-radius: 5px;
}
#framebutton:hover {
  background-color: rgb(234, 234, 234);
  cursor: pointer;
}
.frameimg {
  height: 2rem;
  width: 2rem;
}
</style>
