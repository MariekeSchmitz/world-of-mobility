<script setup lang="ts">

const props = withDefaults(
  defineProps<{
    errorMsg: string;
  }>(),
  { errorMsg: "",
  }
);

</script>

<template>
  

  <div id="errorBox" :class="{
      'errorBoxInvisible':!errorMsg,
      'errorBoxVisible':errorMsg,
      }">
    <h3 class="text-greenLight">Hinweis</h3>
    <p class="text-white">{{ props.errorMsg }}</p>
  </div>

  
</template>
