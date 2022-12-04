<script setup lang="ts">
import GameListItem from '@/components/selectview/GameListItem.vue';
import { useGameList } from "@/services/useGameList"
import { computed } from '@vue/reactivity';
import { onMounted } from 'vue';

const { gameState, getGameList } = useGameList();

const instancelist = []
onMounted(() => {
    getGameList()
});




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
            <fieldset>
                <input type="radio" id="editmode" name="selectmode" value="Wird gerade editiert">
                <label for="editmode">Wird gerade editiert</label>
                <input type="radio" id="allmode" name="selectmode" checked value="Alle">
                <label for="allmode">Alle</label>
            </fieldset>
            <div>
                <GameListItem :worldname="'Worldname'"  :people="0"></GameListItem>
            </div>
            <div v-for="ele in gameState.gameInstanceList">
                <p>{{gameState.gameInstanceList}}</p>
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