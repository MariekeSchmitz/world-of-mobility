import {reactive, readonly} from "vue";


interface ISendNewWorld{
    name: string;
}

interface INewWorldResponse{
    error: string;
    name: string;
}

const newWorldState: INewWorldResponse = reactive({
    name: "",
    error: "",
})



async function createWorld(name: string){
    const url = "/api/editor/createNewWorld"

    const newWorld: ISendNewWorld = {
        name: name,
    }

    if(name.length >= 1 ){


    try{
        const response = await fetch(url, {
            method: "POST",
            headers:{
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newWorld)
        });

        if (!response.ok){
            console.log(response.text);
        } else{
            const jsondata: INewWorldResponse = await response.json();
            console.log(jsondata);
            if(jsondata.error === ""){
                newWorldState.error = "";
                newWorldState.name = jsondata.name;
                console.log("worldName = ", newWorldState.name)
                
            } else{
                newWorldState.error = jsondata.error;
            }
        }

    } catch(reason){
        console.log(reason)
    }
} else{
    newWorldState.error = "Name too short."
}
}

export function useEditor(){
    return{
        createWorld,
        worldCreateData: readonly(newWorldState)
    };
}

