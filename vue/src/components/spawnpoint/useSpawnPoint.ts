import { readonly, reactive } from "vue";
import { useGame } from "@/services/useGame";
import { useUserFeedback } from "@/services/editor/useUserFeedback";

const { isSpawnPointValid } = useGame();
const {setUserFeedback} = useUserFeedback();

interface IState {
    boxSizing: string,
    windowWidth: string,
    numberOfRows: number
}

interface ISpawnPoint {
    xPos: number,
    yPos: number,
    tileNumber: number,
    moveableObject: string,
    instanceId: number
}

/**
 * this changes the size of the displayed minimap
 */
const SCALING_FACTOR = 7;

const windowState: IState = reactive({
    boxSizing: '0px',
    windowWidth: `${window.innerWidth / SCALING_FACTOR}px`,
    numberOfRows: 1
});

/**
 * negative numbers indicate, that the spawnpoint has not been set
 */
const spawnState: ISpawnPoint = reactive({
    xPos: -1,
    yPos: -1,
    tileNumber: -1,
    moveableObject: "MOTORIZED_OBJECT",
    instanceId: 1
})

/**
 * got this number from map.vue, might change in the future
 */
const SQUARE_SIZE = 1;

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

    async function setMoveableObject(newMoveableObject: string) {
        spawnState.moveableObject = newMoveableObject;
        const isValid = await isSpawnPointValid(spawnState.instanceId, spawnState.moveableObject, spawnState.xPos, spawnState.yPos);
        if(!isValid) {
            spawnState.xPos = -1;
            spawnState.yPos = -1;
            spawnState.tileNumber = -1;
        }
    }

    function setInstanceId(newInstanceId: number) {
        spawnState.instanceId = newInstanceId;
    }

    /**
     * TODO offset still needs to be implemented
     * @param xPos 
     * @param yPos 
     */
    async function setSpawnPoint(xPos: number, yPos: number) {
        const isValid = await isSpawnPointValid(spawnState.instanceId, spawnState.moveableObject, xPos, yPos);
        if(isValid) {
            spawnState.xPos = xPos * SQUARE_SIZE + SQUARE_SIZE / 2;
            spawnState.yPos = yPos * SQUARE_SIZE + SQUARE_SIZE / 2;
            spawnState.tileNumber = (xPos + 1) + (yPos * windowState.numberOfRows);
        } else {
            setUserFeedback("Hier darfst du dein Fortbewegungsmittel leider nicht platzieren.")
        }
    }

    return {
        miniMapScalingState: readonly(windowState),
        addWindowWidthListener,
        removeWindowWIdthListener,
        setSpawnPoint,
        setMoveableObject,
        setInstanceId,
        spawnState: readonly(spawnState)
    }
}