import { readonly, ref } from "vue";


const feedbackMessage = ref("")

/**
 * function to give user feedback
 * @author Marie Bohnert
 * @param feedback feedbackMessage
 */
async function setUserFeedback(feedback:string){

    feedbackMessage.value = feedback;
    await new Promise(f => setTimeout(f, 3000));
    feedbackMessage.value ="";

}

export function useUserFeedback(){

    return {
        feedbackMessage: readonly(feedbackMessage),
       setUserFeedback
    }

}