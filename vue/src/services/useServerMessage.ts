import { Client } from "@stomp/stompjs"
import type { ITestInfoMessage } from "./ITestInfoMessage"

function receiveMessages(){
    const wsurl = `ws://${window.location.host}/stompbroker`
    const DEST = "/topic/ServerMessage"

    const stompClient = new Client({brokerURL : wsurl})
    stompClient.onWebSocketError = (event) => console.log(`ERROR: WebSocket-Error: ${event}`);
    stompClient.onStompError = (event) => console.log(`ERROR: Stomp-Error: ${event}`);

    stompClient.onConnect = (frame) => {
        console.log("Connected Stombrocker to ServerMessage");
        stompClient.subscribe(DEST, (message) => {
            console.log(`Stompbroker received message: \n ${message.body}`)
            const backendInfoMsg: ITestInfoMessage = JSON.parse(message.body);
            console.log(`Stompbroker received message: \n ${backendInfoMsg.newXPos}`)
        })
    }

    stompClient.onDisconnect = () => {
        console.log(`Disconnect Stompbroker`)
    } 

    stompClient.activate();

}

async function updateTestMessage(): Promise<void> {
    const url = '/api/ServerMessage' 
    try {
        const reponse = await fetch(url)

        if (!reponse.ok) {
            console.log("couldnt fetch!")
            throw new Error(reponse.statusText)
        }

    } catch (reason) {
        console.log(`ERROR: ${reason}`)
    }

}

export function useServerMessage(){
    return {
        receiveMessages,
        updateTestMessage
    }
}