/**
 * service for creating new world instances
 * @author Marie Bohnert, Tom Gouthier, Victoria Thee
 */

import { reactive, readonly } from "vue";

interface ISendNewWorld {
  name: string;
}

interface INewWorldResponse {
  error: string;
  id: number;
}

const newWorldState: INewWorldResponse = reactive({
  id: -1,
  error: "",
});

/**
 * posts a world name and gets a new world instance with a unique id
 * @param name
 */
async function createWorld(name: string) {
  const url = "/api/editor/createNewWorld";

  const newWorld: ISendNewWorld = {
    name: name,
  };

  if (name.length >= 1) {
    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newWorld),
      });

      if (!response.ok) {
        newWorldState.error = response.statusText;
      } else {
        const jsondata: INewWorldResponse = await response.json();
        console.log(jsondata);

        newWorldState.error = jsondata.error;
        newWorldState.id = jsondata.id;
        console.log("worldId = ", newWorldState.id);
      }
    } catch (reason) {
      console.log(reason);
    }
  } else {
    newWorldState.error = "Name too short.";
  }

  return newWorldState.id;
}

export function useEditor() {
  return {
    createWorld,
    worldCreateData: readonly(newWorldState),
  };
}
