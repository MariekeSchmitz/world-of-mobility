/* eslint-disable prettier/prettier */
import { Client } from "@stomp/stompjs";
import { reactive } from "vue";
import type { IServerMessage } from "./IServerMessage";

interface IMsgState {
  msgLst: IServerMessage[];
}

const msgState = reactive<IMsgState>({
  msgLst: [],
});

function receiveMessages(type: string, id: number) {
  const proto = location.protocol == "https:" ? "wss" : "ws";
  const wsurl = `${proto}://${window.location.host}/stompbroker`;
  const DEST = `/topic/${type}/servermessage/${id}`;

  const stompClient = new Client({ brokerURL: wsurl });
  stompClient.onWebSocketError = (event) =>
    console.log(`ERROR: WebSocket-Error: ${event}`);
  stompClient.onStompError = (event) =>
    console.log(`ERROR: Stomp-Error: ${event}`);

  stompClient.onConnect = (frame) => {
    console.log("Connected Stombrocker to ServerMessage");
    stompClient.subscribe(DEST, (message) => {
      const backendInfoMsg: IServerMessage = JSON.parse(message.body);
      msgState.msgLst.push(backendInfoMsg);
    });
  };

  stompClient.onDisconnect = () => {
    console.log(`Disconnect Stompbroker`);
  };

  stompClient.activate();
}

async function updateTestMessage(
  type: string,
  message: string,
  instanceId: number
): Promise<void> {
  const url = `/api/${type}/servermessage/${instanceId}`;
  try {
    const data: IServerMessage = {
      id: instanceId,
      txt: message,
    };

    const reponse = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!reponse.ok) {
      throw new Error(reponse.statusText);
    }
  } catch (reason) {
    console.log(`ERROR: ${reason}`);
  }
}

export function useServerMessage() {
  return {
    receiveMessages,
    updateTestMessage,
    msgState,
  };
}