<script setup lang="ts">

</script>

<template>
    <div id="frame">
        <button>
            <div>
                <img id="worldimage" src="" alt="">
            </div>
            <div id="flexbox">
                <div id="activeimage" >
                    <img src="" alt="">
                </div>
                <div id="textinput">
                    <p>Weltname</p>
                    <p>3 Person editieren</p>
                </div>
            </div>
        </button>
    </div>
</template>

<style>

#frame {
    width: 12.5rem;
    height: 15rem;
    background-color: red;
}

#worldimage {
    width: 100%;
    height: 7.5rem;
    background-color: blue;
}
#activeimage {
    flex: 0;
    background-color: blue;
}
#textinput{
    flex: 4;
    background-color: green;
}

#flexbox {
    display: flex;

}
#frame > button {
    width: 100%;
    height: 100%;
    background-color: red;
}


</style>