/* eslint-disable prettier/prettier */

import type { INpc } from "@/interfaces/npc/INpc";
import { useUserFeedback } from "../editor/useUserFeedback";

export interface IPlacedObject {
  type: string;
}

const {setUserFeedback} = useUserFeedback()
export interface ITile {
  type: string;
  orientation: string;
  placedObject: IPlacedObject;
}

export interface IMapDTO {
  tiles: Array<Array<ITile>>;
  NPCS: Array<INpc>;
  name: string
}

export function useMap(): any {

  // const mapState = reactive<IMapDTO> ({
  //     tiles: [[]],
  //     NPCS: []
  // });

  async function getGameMap(instanceID: number): Promise<IMapDTO | undefined> {
    try {
      const controller = new AbortController();
      const URL = `/api/game/getmap/${instanceID}`;

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
      });

      const jsonData: IMapDTO = await response.json();
      clearTimeout(id);
      return jsonData;
    } catch (reason) {
      console.log(`ERROR: Fetching Map failed: ${reason}`);
    }
  }

  async function getMapEditor(editorId: number) {
    try {
      const controller = new AbortController();
      const URL = `/api/editor/getmap/editor?editorId=${editorId}`;

      const id = setTimeout(() => controller.abort(), 8000);

      const response = await fetch(URL);

      const jsonData: IMapDTO = await response.json();

      clearTimeout(id);

      return jsonData;
    } catch (reason) {
      console.log(`ERROR: Fetching Map failed: ${reason}`);
    }
  }

  async function saveMap(mapId: number) {
    try {
      const controller = new AbortController();
      const URL = "/api/editor/savemap";

      const data = { mapId };
      console.log(data);
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
        setUserFeedback("Die Map konnte nicht gespeichert werden")
        return false;
      }
      setUserFeedback("Die Map wurde erfolgreich gespeichert.")
      return true;
    } catch (reason) {
      console.log(`ERROR: Saving Map failed: ${reason}`);
      return false;
    }
  }

  return {
    getGameMap,
    saveMap,
    getMapEditor,
  };
}
