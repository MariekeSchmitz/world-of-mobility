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
async function createWorld(name: string, path: string) {
  const url = `/api/editor/${path}`;

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

        newWorldState.error = jsondata.error;
        newWorldState.id = jsondata.id;
      }
    } catch (reason) {
      console.log("ERROR: Fetching new Instance failed: ", reason);
    }
  } else {
    newWorldState.error = "Der Name ist zu kurz. Er braucht mindestens drei Zeichen.";
  }

  return newWorldState.id;
}

function resetError() {
  newWorldState.error = "";
}
export function useEditor() {
  return {
    createWorld,
    resetError,
    worldCreateData: readonly(newWorldState),
  };
}
