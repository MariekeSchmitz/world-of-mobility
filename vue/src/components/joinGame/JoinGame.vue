<script setup lang="ts">

import { useUser } from "@/services/useUser";
import User from "@/components/joinGame/User.vue";
import { onMounted } from "vue";
import { useGame } from "@/services/useGame";
import { useLogin } from "@/services/login/useLogin";
import router from "@/router";
const { receiveGameUpdate, joinGame } = useGame()
const { userList,getUserList } = useUser()
const { loginData } = useLogin()

const props = 
  defineProps<{
    instanceID: number;
  }>();

function join(){
    console.log("join game")
    if(props.instanceID != undefined){
      joinGame(props.instanceID, loginData.username, "MOTORIZED_OBJECT")
      receiveGameUpdate(props.instanceID)
      router.push("/game/"+props.instanceID)  
    }
}

onMounted(()=> {
    getUserList(props.instanceID)
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
    <button @click="join()">Beitreten</button>
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
