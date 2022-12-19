interface IPlaceObject {
  type: string;
  control: string;
  xPos: number;
  yPos: number;
}

async function placeObject(
  instanceId: number,
  type: string,
  control: string,
  xPos: number,
  yPos: number
) {
  try {
    const controller = new AbortController();
    const URL = "/api/editor/placeableObjectUpdate/" + instanceId;

    const data: IPlaceObject = {
      type: type,
      control: control,
      xPos: xPos,
      yPos: yPos,
    };

    const id = setTimeout(() => controller.abort(), 8000);

    const response = await fetch(URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      signal: controller.signal,
      body: JSON.stringify(data),
    });

    clearTimeout(id);

    if (!response.ok) {
      return false;
    }
    return true;
  } catch (reason) {
    console.log(`ERROR: Sending Command failed: ${reason}`);
    return false;
  }
}

export function usePlaceObject() {
  return {
    placeObject,
  };
}
