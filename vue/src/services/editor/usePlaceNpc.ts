import type { NpcType } from "../../enums/editor/NpcType";
import { useEditorError } from "./useEditorError";


/**
 * interface that represents a npc that is supposed to be placed
 */
interface IPlaceNPC {
  x: number;
  y: number;
  type: NpcType;
}

/**
 * interface that represents a npc that is supposed to be deleted
 */
interface IRemoveNPC {
  x: number;
  y: number;
}

const {setEditorError} = useEditorError()

/**
 * places Npc on specific coordinates
 * @param x x coordinate of npc
 * @param y y coordinate of npc
 * @param type type of npc
 * @param id id of editor-instance
 */
async function placeNpc(x: number, y: number, type: NpcType, id: number) {
  
  const npc: IPlaceNPC = {
    x: x,
    y: y,
    type: type,
  };

  const url = `/api/editor/${id}/placeNpc`;

  try {
    console.log("Placing npc with coordinates", x, y);
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(npc),
    });

    if (!response.ok) {
      const json = await response.json()
      console.log(json)
      setEditorError(json.message)
    } else{
      setEditorError("")
    }
  } catch (error:any) {
    setEditorError(error.toString())
  }
}

/**
 * removes Npc on specific coordinates
 * @param x x coordinate of npc
 * @param y y coordinate of npc
 * @param id id of editor-instance
 */
async function removeNpc(x: number, y: number, id: number) {
  const npc: IRemoveNPC = {
    x: x,
    y: y,
  };

  const url = `/api/editor/${id}/removeNpc`;

  try {
    console.log("Delete npc with coordinates", x, y);
    const response = await fetch(url, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(npc),
    });

    if (!response.ok) {
      const json = await response.json()
      setEditorError(json.message)
    } else{
      setEditorError("")
    }
  } catch (error:any) {
    setEditorError(error.toString())
  }
}

export function usePlaceNpc() {
  return {
    placeNpc,
    removeNpc,
  };
}
