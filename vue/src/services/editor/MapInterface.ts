import type { INpc } from "@/interfaces/INpc";
import type { Tile } from "@/services/editor/TileInterface";

export interface MapInterface {
    tiles: Array<Array<Tile>>,
    npcs: Array<INpc>
} 
