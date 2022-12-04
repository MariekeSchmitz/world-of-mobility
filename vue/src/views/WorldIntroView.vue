<script setup lang="ts">
    import GameListItem from '@/components/selectview/GameListItem.vue';
    import { useInstanceList } from "@/services/useInstanceList"
    import { computed, ref } from '@vue/reactivity';
    import { onMounted } from 'vue';

    const { instanceState, getInstanceList } = useInstanceList();

    onMounted(() => {
        getInstanceList("editor")
        console.log(instanceState)
    });

    const showAll = ref(true);

    function switchScene(mode:string){
        if(mode == "all"){
            showAll.value = true;
        }
        if(mode == "edit"){
            showAll.value = false;
        }

        console.log(showAll.value)
    }




</script>

<template>
    <div>
        <div>
            <RouterLink to="/login"><img src="../buttons/editor/arrow-left.png" alt=""></RouterLink>
            <button>
                <img src="../buttons/editor/plus.png" alt=""> Welt erstellen
            </button>
            <img id="logo" src="" alt="Logo Fehlt">
        </div>

        <hr>

        <div id="selection">
            <h1>Welt editieren</h1>
            <fieldset>
                <input type="radio" id="editmode" name="selectmode" value="Wird gerade editiert" @click="switchScene('edit')">
                <label for="editmode">Wird gerade editiert</label>
                <input type="radio" id="allmode" name="selectmode" checked value="Alle" @click="switchScene('all')">
                <label for="allmode">Alle</label>
            </fieldset>
     
            <div v-for="ele in instanceState.instancelist.instancelist">
                <GameListItem :id="ele.id" :gamename="ele.gamename" :worldname="ele.worldname"  :people="ele.playeramount"></GameListItem>
            </div>

            <div v-if="showAll" v-for="ele in instanceState.instancelist.instancelist">
                <GameListItem :id="ele.id" :gamename="ele.gamename" :worldname="ele.worldname"  :people="ele.playeramount"></GameListItem>
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