<script setup lang="ts">
import { usePlaceState } from "@/services/editor/usePlaceState";
import { editorTileURLs } from "@/components/editor/EditorTileURLDict";

const props = defineProps<{
  enum: string[];
  boolTile: boolean;
  boolNpc: boolean;
  boolPlaceable: boolean;
}>();

const { setPlaceState } = usePlaceState();
</script>

<template>
  <li v-for="ele in enum" class="peer bottomMenuListStyle">
    <button
      class="itemButton"
      @click="setPlaceState(ele, boolTile, boolNpc, boolPlaceable)"
    >
      <img :src="editorTileURLs[ele]" :alt="ele" class="w-20 h-20 hover:border-orange hover:border-4 checked:border-orange peer-checked:border-4" />
    </button>
  </li>
</template>
