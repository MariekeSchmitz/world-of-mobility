import { Client } from "@stomp/stompjs";
import { mapTransformDependencies } from "mathjs";
import { reactive, readonly } from "vue";
// import json from './testMapOverview.json';


// Interface für eine einzige Map
interface IMap {
    mapName: string,
    // img: string
}

// Interface für reinkommendes DTO
interface IMapOverviewDTO {
   allMaps: Array<IMap>
}

const mapsOverview : IMapOverviewDTO = reactive ( {
    allMaps : []
})

async function getMaps() {

    try {
        const controller = new AbortController();
        const URL = '/api/game/get-all-maps';
        
        const id = setTimeout(() => controller.abort(), 8000);

        const response = await fetch(URL, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            signal: controller.signal,
        });


        const jsonData: IMapOverviewDTO = await response.json();

        mapsOverview.allMaps = jsonData.allMaps

        console.log(mapsOverview.allMaps)

        clearTimeout(id);

        if(!response.ok) {
            return false;
        }
        return jsonData.allMaps
        
    } catch(reason) {
        console.log(`ERROR: Config Map failed: ${reason}`);
    }
}

export function useMapOverview () {
    return {
        getMaps,
        mapsOverview: readonly(mapsOverview)
    };
}