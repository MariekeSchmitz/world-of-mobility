import type { tileType } from "@/components/TileTypes";
import type { orientationType } from "@/components/OrientationTypes";

export interface ITile {
  type: string;
  orientation: string;
  placedObject?: any;
  positionX?: number;
  positionY?: number;
  positionZ?: number;
}
