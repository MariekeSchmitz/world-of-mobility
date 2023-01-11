import FARM_URL from "@/textures/editor/objects/FARM.png"
import GAS_STATION_URL from "@/textures/editor/objects/GAS_STATION.png"
import PIG_URL from "@/textures/editor/objects/PIG.png"
import REMOVE_URL from "@/textures/editor/objects/REMOVE.png"
import SHEEP_URL from "@/textures/editor/objects/SHEEP.png"
import TRAFFIC_LIGHT_URL from "@/textures/editor/objects/TRAFFIC_LIGHT.png"
import TREE_URL from "@/textures/editor/objects/TREE.png"

export const editorObjectURLs: { [id: string]: string } = {
    FARM: FARM_URL,
    GAS_STATION: GAS_STATION_URL,
    PIG: PIG_URL,
    REMOVE: REMOVE_URL,
    SHEEP: SHEEP_URL,
    TRAFFIC_LIGHT: TRAFFIC_LIGHT_URL,
    TREE: TREE_URL,
};

console.log("Objects", JSON.stringify(editorObjectURLs));