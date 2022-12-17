import { readonly, reactive } from "vue";

interface IState {
    boxSizing: string,
    windowWidth: string,
    numberOfRows: number
}

interface ISpawnPoint {
    xPos: number,
    yPos: number,
    tileNumber: number
}

const windowState: IState = reactive({
    boxSizing: '0px',
    windowWidth: `${window.innerWidth / 2}px`,
    numberOfRows: 1
});

/**
 * negative numbers indicate, that the spawnpoint has not been set
 */
const spawnState: ISpawnPoint = reactive({
    xPos: -1,
    yPos: -1,
    tileNumber: -1
})

const SCALING_FACTOR = 2;

/**
 * got this number from map.vue, might change in the future
 */
const SQUARE_SIZE = 10;

export function useSpawnPoint() {

    function addWindowWidthListener(newNumberOfRows: number) {
        windowState.numberOfRows = newNumberOfRows;
        window.addEventListener('resize', changeWindowWidth);
        changeWindowWidth();
    }

    function removeWindowWIdthListener() {
        window.removeEventListener('resize', changeWindowWidth);
    }

    function changeWindowWidth() {
        windowState.windowWidth = `${window.innerWidth / SCALING_FACTOR}px`;
        windowState.boxSizing = `${window.innerWidth / (SCALING_FACTOR * windowState.numberOfRows)}px`
    }

    function setSpawnPoint(xPos: number, yPos: number) {
        spawnState.xPos = xPos * SQUARE_SIZE;
        spawnState.yPos = yPos * SQUARE_SIZE;
        spawnState.tileNumber = (xPos + 1) + (yPos * windowState.numberOfRows);
        console.log(`x: ${xPos}, y: ${yPos}, ${spawnState.tileNumber}`);
    }

    return {
        miniMapScalingState: readonly(windowState),
        addWindowWidthListener,
        removeWindowWIdthListener,
        setSpawnPoint,
        spawnState: readonly(spawnState)
    }
}