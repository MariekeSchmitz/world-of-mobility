<script setup lang="ts">
import { reactive, onMounted } from "vue";
import { useJoinGame } from "@/services/useJoinGame";
import { moveableImages } from "./MoveableImages";
import grassTileUrl from "@/assets/images/tiles/GRASSTILE.jpg";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faPlus, faArrowLeft, faChevronRight, faChevronLeft } from "@fortawesome/free-solid-svg-icons";
library.add(faPlus, faArrowLeft, faChevronRight, faChevronLeft);

const { getMoveableTypes } = useJoinGame();

const emit = defineEmits<{
  (e: "changeMoveable", type: string): void;
}>();

interface IMoveable {
  type: string;
  src: string;
}

let index = 0;
const moveables: IMoveable[] = [];

onMounted(async () => {
  const getMoveables = await getMoveableTypes();
  if (getMoveables)
    for (const mov of getMoveables) {
      if (mov) {
        moveables.push({ type: mov, src: moveableImages[mov] });
      }
    }
  nextMoveable();
});

let moveable = reactive({
  type: "MOTORIZED_OBJECT",
  src: grassTileUrl,
});

function prevMoveable() {
  if (index > 0) {
    index -= 1;
  } else {
    index = moveables.length - 1;
  }
  moveable.src = moveables[index].src;
  moveable.type = moveables[index].type;
  emit("changeMoveable", moveable.type);
}

function nextMoveable() {
  if (index < moveables.length - 1) {
    index += 1;
  } else {
    index = 0;
  }
  moveable.src = moveables[index].src;
  moveable.type = moveables[index].type;
  emit("changeMoveable", moveable.type);
}
</script>

<template>
  <div class="grid grid-cols-[1fr_2fr_1fr] text-center">
    <button class="relative bottom-6" @click="prevMoveable()">
      <font-awesome-icon
        icon="fa-solid fa-chevron-left"
        size="s"
        color="white"
        class="w-5 h-5 p-3 inline rounded-full bg-greenLight hover:bg-greenDark"
      />
    </button>
    <div class="h-50 mx-auto">
      <img v-bind:src="moveable.src" class="object-scale-down h-48 w-30" />
      <h3 class="text-orange mt-5">{{ moveable.type }}</h3>
    </div>
    <button class="relative bottom-6" @click="nextMoveable()">
      <font-awesome-icon
        icon="fa-solid fa-chevron-right"
        size="s"
        color="white"
        class="w-5 h-5 p-3 inline rounded-full bg-greenLight hover:bg-greenDark"
      />
    </button>
  </div>
</template>
