import type { Tile } from "@/services/TileInterface";

export interface MapInterface {
    tiles: Array<Array<Tile>>,
    NPCS: Array<any>
} 
