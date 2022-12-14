import { readonly, ref } from "vue";


const CMState = ref (
    {
        state: 0
    }
)



function setCMState(){
    CMState.value.state++
}


export function useContextMenu(){
    return{
        readCMState: readonly(CMState),
        setCMState
    }
}