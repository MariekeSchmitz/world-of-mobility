import { readonly, ref } from "vue";


const errorMessage = ref("")

/**
 * function to replace ErrorMessage in editor
 * @author Marie Bohnert
 * @param error errormessage
 */
function setEditorError(error:string){

    errorMessage.value = error;

}

export function useEditorError(){

    return {
       errorMessage: readonly(errorMessage),
       setEditorError
    }

}