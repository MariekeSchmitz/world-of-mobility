<script setup lang="ts">
import { reactive, onMounted } from "vue";
import { useJoinGame } from "@/services/useJoinGame";
import { forEach } from "mathjs";
import { moveableImages } from "./MoveableImages"

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
  src: "/src/textures/tiles/GRASSTILE.jpg",
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
  <div class="wrapper">
    <div>
      <img v-bind:src="moveable.src" alt="MOVABLE_IMG" />
      <h3>{{ moveable.type }}</h3>
      <button @click="prevMoveable()">prev</button>
      <button @click="nextMoveable()">next</button>
    </div>
  </div>
</template>

<style scoped>
.square {
  width: 12.5rem;
  height: 12.5rem;
  background-color: gray;
}
.wrapper {
  display: grid;
  place-items: center;
}

.userlist {
  overflow-y: scroll;
  overflow: auto;
  height: 200px;
}
</style>
