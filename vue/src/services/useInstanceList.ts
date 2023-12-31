/* eslint-disable prettier/prettier */
import { reactive, readonly} from "vue";
import type { IInstanceInfo } from "@/interfaces/messaging/IInstanceInfo";
import { Client, StompSubscription } from "@stomp/stompjs";

export function useInstanceList(): any {
  /**
   * Fetch List with Information of Instances from Backend
   * @returns instanceState with List of Instances and getInstanceList
   * @author Finn Schindel, Astrid Klemmer
   */

  interface IInstanceState {
    instancelist: Array<IInstanceInfo>;
  }

  interface IDeleteInstance {
    id: number;
  }

  const instanceState: IInstanceState = reactive({
    instancelist: [],
  });

  const deleteState: IDeleteInstance = reactive({
    id: 0,
  });

  let subscription: StompSubscription

  function processInstanceUpdate(instanceUpdate: IInstanceInfo) {
    let found = 0;
    if (instanceUpdate.command == "CREATE") {
      instanceState.instancelist.forEach(function (item) {
        if (item.id == instanceUpdate.id) {
          if (item.playeramount != instanceUpdate.playeramount) {
            item.playeramount = instanceUpdate.playeramount;
            found = 1;
          }
          if (item.users != instanceUpdate.users) {
            item.users = instanceUpdate.users;
            found = 1;
          }
        }
      });

      if (found == 0) {
        instanceState.instancelist.unshift(instanceUpdate);
      } else {
        found = 0;
      }
    } else if (instanceUpdate.command == "DELETE") {
      deleteState.id = instanceUpdate.id;
      const deleteIndex = instanceState.instancelist.indexOf(instanceUpdate);
      instanceState.instancelist.splice(deleteIndex, 1);
    }
  }

  function receiveInstanceUpdates(path: string) {
    const proto = location.protocol == "https:" ? "wss" : "ws";
    const wsurl = `${proto}://${window.location.host}/stompbroker`;
    const DEST = `/topic/${path}/instanceInfo`;
    const stompClient = new Client({ brokerURL: wsurl });
    stompClient.onWebSocketError = (event) =>
      console.log(`ERROR: WebSocket-Error in InstanceUpdate: ${event}`);
    stompClient.onStompError = (event) =>
      console.log(`ERROR: Stomp-Error in InstanceUpdate: ${event}`);

    stompClient.onConnect = (frame) => {
      console.log("Connected Stompbroker to InstanceUpdate");
      subscription = stompClient.subscribe(DEST, (message) => {
        const instanceUpdate: IInstanceInfo = JSON.parse(message.body);
        processInstanceUpdate(instanceUpdate);
      });
    };

    stompClient.onDisconnect = () => {
      console.log("Disconnected Stompbroker from MapUpdate");
    };

    stompClient.activate();
  }

  function endReceiveInstanceUpdates(){
    subscription.unsubscribe()
  }

  /**
   * Fetch List from Backend
   * @param path either gets List of Instances from game or editor
   * @returns boolean if fetch worked or not
   */
  async function getInstanceList(path: string) {
    try {
      const controller = new AbortController();
      const URL = `/api/${path}/instancelist`;

      //clientid: number, user: string, command: string,
      //const data = {user, command};

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        //body: JSON.stringify(data)
      });
      if (!response.ok) {
        return false;
      }
      const jsonData: IInstanceState = await response.json();
      instanceState.instancelist = jsonData.instancelist;

      receiveInstanceUpdates(path);

      clearTimeout(id);

      return true;
    } catch (reason) {
      console.log(`ERROR: Sending Command failed: ${reason}`);
      return false;
    }
  }

  function getGamename(gameId: number){
    let gamename: string = "";
    instanceState.instancelist.forEach((instance:IInstanceInfo) => {
      if(instance.id == gameId){
        gamename = instance.gamename
        return
      }
    });
    return gamename
  }

  function getUserlist(instanceId: number){
    let users: string[] = [];
    instanceState.instancelist.forEach(function (item: IInstanceInfo) {
      if (item.id == instanceId) {
        users = item.users;
      }
    });
    return users
  }

  function getAvailableInstances(){
    let list:IInstanceInfo[] = []
    list = instanceState.instancelist.filter((instance: IInstanceInfo) => {
        if(instance.maxPlayerCount != instance.playeramount){
          return instance
        }
      }
    );

    return list
  }

  return {
    instanceState: readonly(instanceState),
    getInstanceList,
    deleteState: readonly(deleteState),
    getGamename,
    getUserlist,
    getAvailableInstances,
    endReceiveInstanceUpdates
  };
}
