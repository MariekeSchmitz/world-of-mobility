<script setup lang="ts">
import { GltfModel } from "troisjs";
function onReady(model: any) {
  console.log("Ready", model);
}
</script>

<template>
  <GltfModel src="@/models/plane_1/scene.gltf" @load="onReady" />
</template>
