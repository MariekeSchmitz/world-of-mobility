import type { INpc } from "@/interfaces/npc/INpc";
import type { Tile } from "@/interfaces/editor/TileInterface";

export interface MapInterface {
    name: string,
    tiles: Array<Array<Tile>>,
    npcs: Array<INpc>
} 
