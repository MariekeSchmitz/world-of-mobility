<script setup lang="ts">
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser} from "@fortawesome/free-solid-svg-icons";
library.add(faUser);

const props = defineProps<{
  name: String;
}>();
</script>

<template>
  <div class="flex flex-row mb-3">
    <font-awesome-icon
        icon="fa-solid fa-user"
        color="white"
        class="w-5 h-5 p-3 inline rounded-full bg-greenLight"
      />
    <p class="ml-4 self-center font-semibold text-greenDark text-xs">{{ name }}</p>
  </div>
</template>
