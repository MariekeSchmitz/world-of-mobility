import { readonly, ref } from "vue";


const CMState = ref<String> (
    "none"
)



function setCMState(state:String){
    CMState.value = state
}


export function useContextMenu(){
    return{
        readCMState: readonly(CMState),
        setCMState
    }
}