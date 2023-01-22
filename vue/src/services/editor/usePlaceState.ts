import { readonly, ref } from "vue";

interface Place {
  type: string;
  isTile: boolean;
  isNpc: boolean;
  isPlaceable: boolean;
}

const placeState = ref<Place>({
  type: "none",
  isTile: true,
  isNpc: false,
  isPlaceable: false,
});

function setPlaceState(
  type: string,
  isTile: boolean,
  isNpc: boolean,
  isPlaceable: boolean
) {
  console.log(type);
  placeState.value.type = type;
  placeState.value.isTile = isTile;
  placeState.value.isNpc = isNpc;
  placeState.value.isPlaceable = isPlaceable;
}

export function usePlaceState() {
  return {
    readPlaceState: readonly(placeState),
    setPlaceState,
  };
}
