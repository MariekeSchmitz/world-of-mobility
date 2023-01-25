import { readonly, ref } from "vue";

interface IRemoveInstance {
  type: string;
  id: number;
  remove: boolean;
}

const removeState = ref<IRemoveInstance>({
  type: "",
  id: 0,
  remove: false,
});

function setRemoveState(type: string, id: number, remove: boolean) {
  removeState.value.type = type;
  removeState.value.id = id;
  removeState.value.remove = remove;
}

export function useRemoveInstanceState() {
  return {
    removeInstanceState: readonly(removeState),
    setRemoveState,
  };
}
