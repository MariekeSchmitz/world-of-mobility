<script setup lang="ts">

import { useUser } from "@/services/useUser";
import User from "@/components/joinGame/User.vue";
import { onMounted } from "vue";
const { userList,getUserList } = useUser()

const props = 
  defineProps<{
    instancename: String;
  }>();

function joinGame(){
    console.log("join game")
}

onMounted(()=> {
    getUserList(props.instancename)
})
</script>

<template>
  <div class="wrapper">
    <h1>Fortbewegungsmittel wählen</h1>
    <h2>Beigetretene Spieler</h2>
    <div class="userlist">
        <div v-for="user in userList.users">
            <User :name="user"></User>
        </div>
    </div>
    <button @click="joinGame()">Beitreten</button>
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

.userlist{
    overflow-y: scroll;
    overflow: auto;
    height: 200px;
}
</style>
