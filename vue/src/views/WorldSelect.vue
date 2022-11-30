<script setup lang="ts">
import GameListItems from '@/components/selectview/GameListItems.vue';
</script>



<template>
    <div>
        <div>
            <button>
                <img src="../buttons/editor/arrow-left.png" alt="">
            </button>
            <button>
                <img src="../buttons/editor/plus.png" alt=""> Welt erstellen
            </button>
            <img id="logo" src="" alt="Logo Fehlt">
        </div>

        <hr>

        <div id="selection">
            <h1>Welt editieren</h1>
            <div>
                <input type="radio">
                <label for="">Wird gerade editiert</label>
            </div>

            <div>
                <input type="radio">
                <label for="">Alle</label>
            </div>

            <div>
                <GameListItems></GameListItems>
            </div>
        </div>
    </div>
</template>

<style>

#logo {
    width: 5rem;
    height: 5rem;
}

button {
    text-align:center;
}

</style>