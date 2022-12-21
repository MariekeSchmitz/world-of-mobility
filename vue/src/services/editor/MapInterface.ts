import type { Tile } from "@/services/editor/TileInterface";

export interface MapInterface {
    name: string,
    tiles: Array<Array<Tile>>,
    NPCS: Array<any>
} 
