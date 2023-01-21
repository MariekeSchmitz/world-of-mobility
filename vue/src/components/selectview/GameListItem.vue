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
    worldname?: string;
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

  <div class="grid group overflow-hidden w-52 h-full justify-items-center content-start">
    <img src="@/assets/images/globe.png" alt="" class="block my-auto w-2/3 group-hover:border-orange group-hover:border-8 rounded-full"/>

    <div class="mt-4">

      <div v-if="props.people != 0" class="grid grid-cols-4 items-center">
        <div class="w-8 h-8 mr-5 rounded-full bg-orange col-span-1"></div>
        <div class="col-span-3">
          <p class="gameName" v-if="props.gamename != ''">{{ props.gamename }} </p>
          <p :class="{
            'font-semibold text-greenDark text-base': props.gamename != '',
            'font-semibold text-greenDark text-xl': props.gamename == ''
          }">{{ props.worldname }}</p>
          <p v-if="props.people > 1" >
            {{ props.people }} Personen online
          </p>
          <p v-else >
            {{ props.people }} Person online
          </p>
        </div>
      </div>
    
    
      <div v-else class="text-center">
          <p class="gameName" v-if="props.gamename != ''">{{ props.gamename }} </p>
          <p :class="{
            'font-semibold text-greenDark text-base': props.gamename != '',
            'font-semibold text-greenDark text-xl': props.gamename == ''
          }">{{ props.worldname }}</p>
      </div>
    </div>

  </div>
</template>

