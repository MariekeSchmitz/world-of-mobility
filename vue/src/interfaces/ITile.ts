import type { tileType } from "@/components/TileTypes";
import type { orientationType } from "@/components/OrientationTypes";

export interface ITile {
  type: string;
  orientation: string;
  placedObjects: any;
  positionX?: number;
  positionY?: number;
  positionZ?: number;
}
