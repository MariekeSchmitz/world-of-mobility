import { reactive, readonly } from "vue";
import { useEditorError } from "./editor/useEditorError";

/**
 * interface that represents a script to be loaded for npc on specific coordinates
 */
interface ISendScript {
  script: string;
  x: number;
  y: number;
}

const {setEditorError} = useEditorError()

/**
 * posts script for npc on specific coordinates
 * @param id id of instance
 * @param script script to be loaded
 * @param x x coordinate of npc
 * @param y y coordinate of npc
 */
async function postScript(id: number, script: string, x: number, y: number) {
  const scriptForNpc: ISendScript = {
    script: script,
    x: x,
    y: y,
  };
  const url: string = `/api/editor/${id}/loadScript`;

  try{

  const response = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(scriptForNpc),
  });

  if (!response.ok) {
    const jsondata = await response.json();
    setEditorError(jsondata.message)
  } else{
    setEditorError("")
  }
} catch(error:any){
  setEditorError(error.toString())
  
}
}


export function useScript() {
  return postScript;
}
