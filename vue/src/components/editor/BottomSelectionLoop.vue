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
  <li v-for="ele in enum" class="bottomMenuListStyle">
    <button
      class="itemButton"
      @click="setPlaceState(ele, boolTile, boolNpc, boolPlaceable)"
    >
      <img :src="editorTileURLs[ele]" :alt="ele" class="w-20 h-20" />
    </button>
  </li>
</template>
