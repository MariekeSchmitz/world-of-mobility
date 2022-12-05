/* eslint-disable prettier/prettier */
import { Client } from "@stomp/stompjs";
import { reactive, readonly } from "vue";

export function useGame(): any {

    interface IMoveable {
        user: string,
        xPos: number,
        yPos: number,
        classname: String,
        capacity: number,
        velocity: number,
        orientation: string
    }

    interface IGameControl {
        user: string,
        control: string
    }

    interface IGameUpdate {
        moveableUpdates: Array<IMoveable>
    }

    interface IGameState {
        gameUpdateListe: IGameUpdate[]
    }

    const gameState = reactive<IGameUpdate> ({
        moveableUpdates: []
    });

    interface IInstanceId {
        id:number
    }

    const instanceIdState = reactive<IInstanceId> ({
        id: -1 
    });

    async function sendCommand(clientid: number, user: string, command: string) {
        try {
            const controller = new AbortController();
            const URL = '/api/game/'+clientid+'/game-command';
            
            const data = {user: user, control: command};
    
            const id = setTimeout(() => controller.abort(), 8000);
    
            const response = await fetch(URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                signal: controller.signal,
                body: JSON.stringify(data as IGameControl)
            });
            
            clearTimeout(id);
    
            console.log(response.text());
            if(!response.ok) {
                return false;
            }
            return true;

        } catch(reason) {
            console.log(`ERROR: Sending Command failed: ${reason}`);
            return false;
        }
        
    }

    async function createGameInstance(mapName: string, sessionName: string) {
        try {
            const controller = new AbortController();
            const URL = '/api/game/create-game';
            
            const data = {mapName: mapName, sessionName: sessionName};
    
            const id = setTimeout(() => controller.abort(), 8000);
    
            const response = await fetch(URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                signal: controller.signal,
                body: JSON.stringify(data)
            });
            
            const jsonData = await response.json()
            clearTimeout(id);
            console.log(jsonData)
            instanceIdState.id = jsonData
        } catch(reason) {
            console.log(`ERROR: Sending Command failed: ${reason}`);
            return false;
        }
        
    }

    async function joinGame(instanceId: number, user: string, type: string) {
        try {
            const controller = new AbortController();
            const URL = '/api/game/' + instanceId + '/join-game';
            
            const data = {user: user, type: type};
    
            const id = setTimeout(() => controller.abort(), 8000);
    
            const response = await fetch(URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                signal: controller.signal,
                body: JSON.stringify(data)
            });
            
            clearTimeout(id);
    
            console.log(response.text());
            if(!response.ok) {
                return false;
            }
            return true;
        } catch(reason) {
            console.log(`ERROR: Sending Command failed: ${reason}`);
            return false;
        }
        
    }



    async function receiveGameUpdate(instanceid:number) {
        const wsurl = `ws://${window.location.host}/stompbroker`;
        const DEST = `/topic/game/${instanceid}`;
    
        const stompClient = new Client({ brokerURL: wsurl });
        stompClient.onWebSocketError = (event: any) => console.log(`ERROR: WebSocket-Error in GameUpdate: ${event}`);
        stompClient.onStompError = (event: any) => console.log(`ERROR: Stomp-Error in GameUpdate: ${event}`);
    
        stompClient.onConnect = (frame: any) => {
            console.log("Connected Stompbroker to GameUpdate");
            stompClient.subscribe(DEST, (message: { body: string; }) => {
                const gameUpdate: IGameUpdate = JSON.parse(message.body);
                gameState.moveableUpdates = gameUpdate.moveableUpdates;
            });
        }
    
        stompClient.onDisconnect = () => {
            console.log("Disconnected Stompbroker from GameUpdate");
        }
    
        stompClient.activate();
    }

    return {
        mapUpdates: readonly(gameState),
        instanceId: readonly(instanceIdState),
        joinGame,
        receiveGameUpdate,
        sendCommand,
        createGameInstance
    }
}
