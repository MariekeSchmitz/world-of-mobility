/* eslint-disable prettier/prettier */
import { Client, StompSubscription } from "@stomp/stompjs";
import { reactive, readonly } from "vue";

export function useGame(): any {
  interface IMoveable {
    user: string;
    xPos: number;
    yPos: number;
    classname: String;
    capacity: number;
    velocity: number;
    orientation: string;
  }

  interface IGameControl {
    user: string;
    control: string;
  }

  interface IGameUpdate {
    moveableUpdates: Array<IMoveable>;
    trafficLightState: String;
  }

  interface IGameState {
    gameUpdateListe: IGameUpdate[];
  }

  const gameState = reactive<IGameUpdate>({
    moveableUpdates: [],
    trafficLightState: "NORTHSOUTH"
  });

  let stompSubscription: StompSubscription; 

  interface IInstanceId {
    id: number;
  }

  const instanceIdState = reactive<IInstanceId>({
    id: -1,
  });

  async function sendCommand(clientid: number, user: string, command: string) {
    try {
      const controller = new AbortController();
      const URL = "/api/game/" + clientid + "/game-command";

      const data = { user: user, control: command };

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify(data as IGameControl),
      });

      clearTimeout(id);

      if (!response.ok) {
        return false;
      }
      return true;
    } catch (reason) {
      console.log(`ERROR: Sending Command failed: ${reason}`);
      return false;
    }
  }

  async function createGameInstance(mapName: string, sessionName: string, maximumPlayerCount: number,
    npcsActivated: boolean) {
    try {
      const controller = new AbortController();
      const URL = "/api/game/create-game";

      const data = {
        mapName: mapName,
        sessionName: sessionName,
        maximumPlayerCount: maximumPlayerCount,
        npcsActivated: npcsActivated
      };

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify(data),
      });

      const jsonData = await response.json();
      clearTimeout(id);
      instanceIdState.id = jsonData;
    } catch (reason) {
      console.log(`ERROR: Sending Command failed: ${reason}`);
      return false;
    }
  }

  async function joinGame(instanceId: number, user: string, type: string, xPos:number, yPos:number) {
    try {
      const controller = new AbortController();
      const URL = "/api/game/" + instanceId + "/join-game";

      const data = { user: user, type: type, xPos:xPos, yPos:yPos};

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify(data),
      });
      if (!response.ok) {
        return false;
      }
      const jsonData = await response.json();
      clearTimeout(id);
      return jsonData;
    } catch (reason) {
      console.log(`ERROR: Sending Command failed: ${reason}`);
      return false;
    }
  }

  /**
   * Sends request to backend to remove a user from the game instance
   * @param instanceId id of game instance
   * @param user name of user to be removed
   * @param type
   * @returns if it worked or not
   * @author Astrid Klemmer
   */
  async function leaveGame(instanceId: number, user: string, type: string) {
    try {
      const controller = new AbortController();
      const URL = "/api/game/" + instanceId + "/leave-game";

      const data = { user: user, type: type };

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify(data),
      });

      clearTimeout(id);

      if (!response.ok) {
        return false;
      }
      return true;
    } catch (reason) {
      console.log(`ERROR: Sending Command failed: ${reason}`);
      return false;
    }
  }

  async function receiveGameUpdate(instanceid: number) {
    const proto = location.protocol == 'https:' ? "wss" : "ws";
    const wsurl = `${proto}://${window.location.host}/stompbroker`;
    const DEST = `/topic/game/${instanceid}`;

    const stompClient = new Client({ brokerURL: wsurl });
    stompClient.onWebSocketError = (event: any) =>
      console.log(`ERROR: WebSocket-Error in GameUpdate: ${event}`);
    stompClient.onStompError = (event: any) =>
      console.log(`ERROR: Stomp-Error in GameUpdate: ${event}`);

    stompClient.onConnect = (frame: any) => {
      console.log(`Connected Stompbroker to /topic/game/${instanceid}`);
      stompSubscription = stompClient.subscribe(DEST, (message: { body: string }) => {
        const gameUpdate: IGameUpdate = JSON.parse(message.body);
        gameState.moveableUpdates = gameUpdate.moveableUpdates;
        gameState.trafficLightState = gameUpdate.trafficLightState;
      });
    };

    stompClient.onDisconnect = () => {
      console.log("Disconnected Stompbroker from GameUpdate");
    };

    stompClient.activate();
  }

  function endReceiveGameUpdate() {
    stompSubscription.unsubscribe();
  }

  function getUserMoveable(user: string) {
    for (const moveable of gameState.moveableUpdates) {
      if (moveable.user === user) {
        return moveable;
      }
    }
  }

  async function isSpawnPointValid(instanceId: number, moveableObject: string, xPos: number, yPos: number) {
    try {
      const controller = new AbortController();

      const URL = `/api/game/${instanceId}/validate-spawnpoint?moveableObject=${moveableObject}&xPos=${xPos}&yPos=${yPos}`;

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "GET",
        signal: controller.signal
      });

      clearTimeout(id);

      if (!response.ok) {
        return false;
      }

      return await response.json();
    } catch (reason) {
      console.log(`ERROR: Something went wrong: ${reason}`);
      return false;
    }
  }

  return {
    mapUpdates: readonly(gameState),
    instanceId: readonly(instanceIdState),
    joinGame,
    receiveGameUpdate,
    sendCommand,
    getUserMoveable,
    createGameInstance,
    leaveGame,
    isSpawnPointValid,
    endReceiveGameUpdate
  };
}
