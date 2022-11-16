import { Client } from "@stomp/stompjs"
//import { IBackendInfoMessage } from "@/services/IBackendInfoMessage"


export function useServerMessage(){
    return {
        receiveMessages
    }
}
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
            //const backendInfoMsg: IBackendInfoMessage = JSON.parse(message.body);
            //console.log(`Stompbroker received message: \n ${backendInfoMsg}`)
        })
    }

    stompClient.onDisconnect = () => {
        console.log(`Disconnect Stompbroker`)
    } 

    stompClient.activate();

}

