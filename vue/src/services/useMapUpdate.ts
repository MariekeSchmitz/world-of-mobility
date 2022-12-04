/* eslint-disable prettier/prettier */
import { Client } from "@stomp/stompjs";
import { reactive, readonly } from "vue";

export function useMapUpdate(mapName: string): any {

    interface IMapUpdate {
        tiletype: string,
        orientation: string,
        prevXPos: number,
        prevYPos: number,
        newXPos: number,
        newYPos: number,
        placedObjects: Array<any>
    }

    interface IMapState {
        map: IMapDTO
    }

    interface ITile {
        type: string,
        orientation: string,
        placedObjects: []
    }

    interface INpc {
        user: string,
        xPos: number, 
        yPos: number,
        classname: string
    }

    interface IMapDTO {
        tiles: Array<Array<ITile>>,
        NPCS: Array<any>
    }

    const mapState = reactive<IMapState> ({
        map: {
            tiles: [[]],
            NPCS: []
        }
    });
    
    function receiveMapUpdates(editorId: number) {
        const wsurl = `ws://${window.location.host}/stompbroker`;
        const DEST = `/topic/editor/${editorId}`;
    
        const stompClient = new Client({ brokerURL: wsurl });
        stompClient.onWebSocketError = event => console.log(`ERROR: WebSocket-Error in MapUpdate: ${event}`);
        stompClient.onStompError = event => console.log(`ERROR: Stomp-Error in MapUpdate: ${event}`);
    
        stompClient.onConnect = frame => {
            console.log("Connected Stompbroker to MapUpdate");
            stompClient.subscribe(DEST, (message) => {
                console.log(`Stompbroker received message: \n${message.body}`);
                const mapUpdate: IMapDTO = JSON.parse(message.body);
                mapState.map = mapUpdate;
            });
        }
    
        stompClient.onDisconnect = () => {
            console.log("Disconnected Stompbroker from MapUpdate");
        }
    
        stompClient.activate();
    }

    async function sendMapUpdates(mapUpdateObj: IMapUpdate) {
        try {
            const controller = new AbortController();
            const URL = '/api/editor/mapupdate';

            const id = setTimeout(() => controller.abort(), 8000);

            const data: IMapUpdate = mapUpdateObj;

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

        } catch(reason) {
            console.log(`ERROR: POST MapUpdate failed: ${reason}`);
        }
    }

    return {
        mapUpdates: readonly(mapState),
        sendMapUpdates,
        receiveMapUpdates
    }
}