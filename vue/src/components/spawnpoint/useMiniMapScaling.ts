import { readonly, reactive } from "vue";

interface IState {
    boxSizing: string,
    windowWidth: string,
    numberOfRows: number
}

const state: IState = reactive({
    boxSizing: '0px',
    windowWidth: `${window.innerWidth / 2}px`,
    numberOfRows: 1
});

const SCALING_FACTOR = 2;

export function useMiniMapScaling() {

    function addWindowWidthListener(newNumberOfRows: number) {
        state.numberOfRows = newNumberOfRows;
        window.addEventListener('resize', changeWindowWidth);
        changeWindowWidth();
    }

    function removeWindowWIdthListener() {
        window.removeEventListener('resize', changeWindowWidth);
    }

    function changeWindowWidth() {
        state.windowWidth = `${window.innerWidth / SCALING_FACTOR}px`;
        state.boxSizing = `${window.innerWidth / (SCALING_FACTOR * state.numberOfRows)}px`
    }

    return {
        miniMapScalingState: readonly(state),
        addWindowWidthListener,
        removeWindowWIdthListener
    }
}