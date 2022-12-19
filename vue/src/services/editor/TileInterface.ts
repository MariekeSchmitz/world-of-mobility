import { reactive } from "vue";

export interface Tile {
  type: string;
  orientation: string;
  placedObjects: string;
}
