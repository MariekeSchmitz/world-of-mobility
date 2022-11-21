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
        movables: Array<IMoveable>
    }

    interface IGameState {
        gameUpdateListe: IGameUpdate[]
    }

    const gameState = reactive<IGameState> ({
        gameUpdateListe: []
    });

    async function sendCommand(clientid: number, user: string, command: string) {
        try {
            const controller = new AbortController();
            const URL = '/api/game/'+clientid+'/game-command';
            
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

    async function receiveGameUpdate() {
        const wsurl = `ws://${window.location.host}/stompbroker`;
        const DEST = "/topic/GameUpdate";
    
        const stompClient = new Client({ brokerURL: wsurl });
        stompClient.onWebSocketError = event => console.log(`ERROR: WebSocket-Error in GameUpdate: ${event}`);
        stompClient.onStompError = event => console.log(`ERROR: Stomp-Error in GameUpdate: ${event}`);
    
        stompClient.onConnect = frame => {
            console.log("Connected Stompbroker to GameUpdate");
            stompClient.subscribe(DEST, (message) => {
                console.log(`Stompbroker received message: \n${message.body}`);
                const gameUpdate: IGameUpdate = JSON.parse(message.body);
                gameState.gameUpdateListe.push(gameUpdate);
            });
        }
    
        stompClient.onDisconnect = () => {
            console.log("Disconnected Stompbroker from GameUpdate");
        }
    
        stompClient.activate();
    }

    return {
        mapUpdates: readonly(gameState),
        receiveGameUpdate,
        sendCommand
    }
}
