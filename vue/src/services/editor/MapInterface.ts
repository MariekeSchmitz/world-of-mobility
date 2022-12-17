import type { Tile } from "@/services/editor/TileInterface";

export interface MapInterface {
    tiles: Array<Array<Tile>>,
    NPCS: Array<any>
} 
