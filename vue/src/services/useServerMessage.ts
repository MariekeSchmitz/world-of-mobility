/* eslint-disable prettier/prettier */
import { Client } from "@stomp/stompjs"
import { reactive } from "vue"
import type { IServerMessage } from "./IServerMessage"

interface IMsgState {
    msgLst: IServerMessage[]
}

const msgState = reactive<IMsgState> ({
    msgLst: []
})

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
            const backendInfoMsg: IServerMessage = JSON.parse(message.body);
            console.log(`Stompbroker received message: \n ${backendInfoMsg}`);
            msgState.msgLst.push(backendInfoMsg);
        })
    }

    stompClient.onDisconnect = () => {
        console.log(`Disconnect Stompbroker`)
    } 

    stompClient.activate();

}

async function updateTestMessage(message: string): Promise<void> {
    const url = '/api/ServerMessage' 
    try {
        const data: IServerMessage = {
            id: 1,
            txt: message
        }

        const reponse = await fetch(url, {method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data) 
        })

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
        updateTestMessage,
        msgState
    }
}