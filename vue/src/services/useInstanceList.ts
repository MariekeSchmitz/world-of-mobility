/* eslint-disable prettier/prettier */
import { Client } from "@stomp/stompjs";
import { reactive, readonly, ref } from "vue";

export function useInstanceList(): any {
    
    interface IInstanceInfo{
        id: number, 
        gamename: string, 
        worldname: string, 
        playeramount: number
    }
    
    interface IInstanceInfoListe{
        instancelist: IInstanceInfo[]
    }

    const instanceState = reactive<IInstanceInfoListe>({instancelist:[]})

    async function getInstanceList(path: string) {
        try {
    
            const controller = new AbortController();
            const URL = '/api/' + path + '/instancelist';
            
            //clientid: number, user: string, command: string, 
            //const data = {user, command};
    
            const id = setTimeout(() => controller.abort(), 8000);
    
            const response = await fetch(URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                signal: controller.signal,
                //body: JSON.stringify(data)
            });
            if(!response.ok) {
                return false;
            }
            const jsonData = await response.json();
            
            console.log(jsonData)

            instanceState.instancelist = jsonData

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
        instanceState: readonly(instanceState),
        getInstanceList
    }
}
