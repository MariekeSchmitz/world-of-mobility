import { readonly, ref } from "vue";

interface Place {
  type: string;
  isTile: boolean;
}

const placeState = ref<Place>({
  type: "none",
  isTile: true,
});

function setPlaceState(type: string, isTile: boolean) {
  console.log(type)
  placeState.value.type = type;
  placeState.value.isTile = isTile;
}

export function usePlaceState() {
  return {
    readPlaceState: readonly(placeState),
    setPlaceState,
  };
}
