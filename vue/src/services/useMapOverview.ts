import { Client } from "@stomp/stompjs";
import { reactive, readonly } from "vue";
import json from './testMapOverview.json';


// Interface für eine einzige Mao
interface IMap {
    name: string,
    img: string
}

// Interface für reinkommendes DTO
interface IMapOverviewDTO {
    maps: Array<IMap>
}

const mapsOverview : IMapOverviewDTO = json


export function useMapOverview () {
    return {
        mapsOverview: readonly(mapsOverview)
    };
}

    

