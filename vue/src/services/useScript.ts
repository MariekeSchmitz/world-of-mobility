
import { reactive, readonly } from "vue";


interface ISendScript{
    script: string,
    npcId: number
}




async function postScript(id:number,script:string, npcId:number) {

    const scriptForNpc: ISendScript = {
        script: script,
        npcId : npcId
    }
    const url:string = `/api/editor/${id}/loadScript`

    const response = await fetch (url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body:JSON.stringify(scriptForNpc)
    })
    

}

export function useScript() {
    return postScript   
}