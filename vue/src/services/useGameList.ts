/* eslint-disable prettier/prettier */
import { Client } from "@stomp/stompjs";
import { reactive, readonly, ref } from "vue";

export function useGameList(): any {

    interface IGameInstanceState{
        instancelist:IGameInstanceItem[]
    }

    interface IGameInstanceItem {
        map: {
            tiles: [],
            name: string,
            npcs: []
        }
        id: number,
        moveableObjects: [],
        name: string
    }
 
    const gameState = reactive<IGameInstanceState>({gameInstanceList:[]})

    async function getGameList(clientid: number, user: string, command: string) {
        try {
            const controller = new AbortController();
            const URL = '/api/game/gamelist';
            
            const data = {user, command};
    
            const id = setTimeout(() => controller.abort(), 8000);
    
            const response = await fetch(URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                signal: controller.signal,
                body: JSON.stringify(data)
            });
            if(!response.ok) {
                return false;
            }
            const jsonData: IGameInstanceState[] = await response.json();
            
            clearTimeout(id);

            // jsonData.forEach(element => {
                
            // });
    
            console.log(jsonData);

      
            return true;

        } catch(reason) {
            console.log(`ERROR: Sending Command failed: ${reason}`);
            return false;
        }
        
    }



    return {
        gameState: readonly(gameState),
        getGameList
    }
}
