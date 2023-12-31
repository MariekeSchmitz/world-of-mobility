/* eslint-disable prettier/prettier */
import { Client, StompSubscription } from "@stomp/stompjs";
import {readonly, ref } from "vue";
import type { INpc } from "@/interfaces/npc/INpc";
import { useEditorError } from "../editor/useEditorError";

export function useMapUpdate(editorId: number): any {

  const {setEditorError} = useEditorError()
  interface IMapUpdate {
    tiletype: string;
    orientation: string;
    prevXPos: number;
    prevYPos: number;
    newXPos: number;
    newYPos: number;
    placedObject: IPlacedObject;
  }

  interface IMapDTO {
    tiles: Array<Array<ITile>>;
    npcs: Array<INpc>;
  }

  interface IMapState {
    map: IMapDTO;
  }

  interface ITile {
    type: string;
    orientation: string;
    placedObject: IPlacedObject;
  }

  interface IPlacedObject {
    type: string;
  }

  const mapState = ref<IMapState>({
    map: {
      tiles: [[]],
      npcs: [],
    },
  });

  let subscription: StompSubscription

  function receiveMapUpdates() {
    const proto = location.protocol == "https:" ? "wss" : "ws";
    const wsurl = `${proto}://${window.location.host}/stompbroker`;
    const DEST = `/topic/editor/${editorId}`;
    const stompClient = new Client({ brokerURL: wsurl });
    stompClient.onWebSocketError = (event) =>
      console.log(`ERROR: WebSocket-Error in MapUpdate: ${event}`);
    stompClient.onStompError = (event) =>
      console.log(`ERROR: Stomp-Error in MapUpdate: ${event}`);

    stompClient.onConnect = (frame) => {
      console.log("Connected Stompbroker to MapUpdate");
      subscription = stompClient.subscribe(DEST, (message) => {
        const mapUpdate: IMapDTO = JSON.parse(message.body);
        mapState.value.map = mapUpdate;
      });
    };

    stompClient.onDisconnect = () => {
      console.log("Disconnected Stompbroker from MapUpdate");
    };

    stompClient.activate();
  }

  function endReceiveMapupdate(){
    subscription.unsubscribe()
  }

  async function sendMapUpdates(mapUpdateObj: IMapUpdate) {
    try {
      const controller = new AbortController();
      const URL = `/api/editor/mapupdate/${editorId}`;

      const id = setTimeout(() => controller.abort(), 8000);

      const data: IMapUpdate = mapUpdateObj;

      const response = await fetch(URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify(data),
      });

      clearTimeout(id);

      if(response.ok){
        setEditorError("")
      } else{
        throw new Error()
      }

    } catch (reason) {
      setEditorError(`ERROR: POST MapUpdate failed: ${reason}`);
    }
  }

  return {
    mapUpdates: readonly(mapState),
    sendMapUpdates,
    receiveMapUpdates,
    endReceiveMapupdate
  };
}
