<script setup lang="ts">

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
            <img src="" alt="Logo Fehlt">
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
        </div>
    </div>
</template>

<style>

img {
    width: 5rem;
    height: 5rem;
}

button {
    text-align:center;
}

</style>