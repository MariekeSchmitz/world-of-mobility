import { reactive } from "vue";

export interface Tile {
    typ:string,
    orientation:string,
    placedObjects: []
}