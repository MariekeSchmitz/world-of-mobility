import type { NpcType } from "./NpcType";

interface IPlaceNPC {
    x: number,
    y: number,
    type: NpcType
}


/**
 * places Npc on specific tile
 * @param x x coordinate of npc
 * @param y y coordinate of npc
 * @param type type of npc
 * @param id id of editor-instance
 */
export async function usePlaceNpc(x:number,y:number,type:NpcType,id:number){
    const npc:IPlaceNPC = {
        x:x,
        y:y,
        type:type
    }

    const url = `/api/editor/${id}/placeNpc`

    const response=await fetch(url,{
         method:"POST",
         headers: {
            "Content-Type":"application/json"
         },
         body:JSON.stringify(npc)  
    })




}