import { reactive } from "vue";

interface IPlacedObject {
  type: string;
}
export interface Tile {
  type: string;
  orientation: string;
  placedObject: IPlacedObject;
}
