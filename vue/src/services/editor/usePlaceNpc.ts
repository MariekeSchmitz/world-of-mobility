import type { NpcType } from "./NpcType";

/**
 * interface that represents a npc that is supposed to be placed
 */
interface IPlaceNPC {
  x: number;
  y: number;
  type: NpcType;
}

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
      throw new Error(response.statusText);
    }
  } catch (error) {
    console.log("Error: " + error);
  }
}
export function usePlaceNpc() {
  return {
    placeNpc,
  };
}
