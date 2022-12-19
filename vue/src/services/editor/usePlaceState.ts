import { readonly, ref } from "vue";

interface Place {
    type: string;
}

const placeState = ref<Place> ({
    type: "none"
})



function setPlaceState(state:string){
    placeState.value.type = state
    //console.log(placeState.value)
}


export function usePlaceState(){
    return{
        readPlaceState: readonly(placeState),
        setPlaceState
    }
}