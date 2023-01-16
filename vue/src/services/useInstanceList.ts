/* eslint-disable prettier/prettier */
import { Client } from "@stomp/stompjs";
import { reactive, readonly, ref } from "vue";

export function useInstanceList(): any {
  /**
   * Fetch List with Information of Instances from Backend
   * @returns instanceState with List of Instances and getInstanceList
   * @author Finn Schindel, Astrid Klemmer
   */

  interface IInstanceInfo {
    id: number;
    gamename: string;
    worldname: string;
    playeramount: number;
  }

  interface IInstanceState {
    instancelist: Array<IInstanceInfo>;
  }

  const instanceState: IInstanceState = reactive({ instancelist: [] });

  function processInstanceUpdate(instanceUpdate: IInstanceInfo) {
    let found = 0;
    console.log("whole list: ", instanceState.instancelist);

    instanceState.instancelist.forEach(function (item) {
      console.log("item: ", item.id, " update ", instanceUpdate.id);
      if (item.id == instanceUpdate.id) {
        if (item.playeramount != instanceUpdate.playeramount) {
          item.playeramount = instanceUpdate.playeramount;
          found = 1;
          console.log(
            "found: ",
            item.playeramount,
            " new: ",
            instanceUpdate.playeramount
          );
        }
      }
    });

    if (found == 0) {
      instanceState.instancelist.unshift(instanceUpdate);
      console.log("added new");
    } else {
      found = 0;
    }
  }

  function receiveInstanceUpdates(path: string) {
    const proto = location.protocol == "https:" ? "wss" : "ws";
    const wsurl = `${proto}://${window.location.host}/stompbroker`;
    const DEST = `/topic/${path}/instanceInfo`;
    console.log("Update:instanceInfo");
    const stompClient = new Client({ brokerURL: wsurl });
    stompClient.onWebSocketError = (event) =>
      console.log(`ERROR: WebSocket-Error in InstanceUpdate: ${event}`);
    stompClient.onStompError = (event) =>
      console.log(`ERROR: Stomp-Error in InstanceUpdate: ${event}`);

    stompClient.onConnect = (frame) => {
      console.log("Connected Stompbroker to InstanceUpdate");
      stompClient.subscribe(DEST, (message) => {
        const instanceUpdate: IInstanceInfo = JSON.parse(message.body);
        console.log(instanceUpdate);
        processInstanceUpdate(instanceUpdate);
      });
    };

    stompClient.onDisconnect = () => {
      console.log("Disconnected Stompbroker from MapUpdate");
    };

    stompClient.activate();
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
      console.log("getInstancelist json: ", jsonData);
      instanceState.instancelist = jsonData.instancelist;
      console.log("getinstancelist obj ", instanceState.instancelist);

      receiveInstanceUpdates(path);

      clearTimeout(id);

      return true;
    } catch (reason) {
      console.log(`ERROR: Sending Command failed: ${reason}`);
      return false;
    }
  }

  return {
    instanceState: readonly(instanceState),
    getInstanceList,
  };
}
