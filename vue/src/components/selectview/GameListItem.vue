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
    gamename: string;
    worldname: string;
    people: number;
    picture: string;
  }>(),
  {
    gamename: "",
    picture: "",
    people: 0,
  }
);
</script>

<template>

  <div class="group">
    <div>
      <img src="@/assets/images/globe.png" alt="" class="w-2/3 group-hover:border-orange group-hover:border-8 rounded-full"/>
    </div>
    <div class="grid grid-cols-4 items-center">
      <div v-if="props.people != 0" class="w-8 h-8 rounded-full bg-orange col-span-1">
        <!-- <img class="frameimg" src="../../buttons/gameintro/rec.png" alt="" /> -->
      </div>

      <div class="mt-2 col-span-3">
        <p class="gameName" v-if="props.gamename != ''">{{ props.gamename }} </p>
        <p class="gameName">{{ props.worldname }}</p>
        <p v-if="props.people != 0">
          {{ props.people }} Person online
        </p>
      </div>

    </div>
  </div>
</template>

