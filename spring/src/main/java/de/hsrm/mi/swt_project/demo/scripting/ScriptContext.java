package de.hsrm.mi.swt_project.demo.scripting;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * Context that needs to be passed in a script to
 * control a moveable object is bundled inside this
 * class.
 * 
 * The class holds references to all relevant context
 * objects, but serves only the ones near the moveable
 * that will be controlled by the script.
 * 
 * @author Sascha Scheid
 * @author Finn Schindel
 * @author Timothy Doukhin
 */
public class ScriptContext {

    public static final int LOOK_AHEAD = 1;

    protected MoveableObject moveable;
    
    protected Tile[][] gameMap; 
    protected List<MoveableObject> allMoveables;
    
    /**
     * Creates a new ScriptContext.
     * 
     * @param moveable Moveable object that is controlled by a script.
     * @param gameMap 2D array of tiles representing the whole map
     * @param allMoveables All moveable objects of an instance
     */
    public ScriptContext(MoveableObject moveable, Tile[][] gameMap, List<MoveableObject> allMoveables) {
        this.moveable = moveable;
        this.gameMap = gameMap;
        this.allMoveables = allMoveables;
    }

    /**
     * Creates a map context of size {@value #LOOK_AHEAD} around
     * the controlled moveable object's position.
     * 
     * If the moveable is currently positioned at the edge of the
     * map, the part of the context map that portrais the overlap
     * will remain uninitialized.
     * 
     * @return 2D array that contains all tiles surrounding the
     *         tile where the moveable is positioned on with
     *         radius {@value #LOOK_AHEAD}
     */
    public Tile[][] provideMapContext() {

        int contextSize = 2 * LOOK_AHEAD + 1;
        Tile[][] mapContext = new Tile[contextSize][contextSize];

        if (this.gameMap == null) {
            return mapContext;
        }

        int tileRow = (int) this.moveable.getYPos();
        int tileCol = (int) this.moveable.getXPos();

        int rowStart = tileRow - LOOK_AHEAD;
        int rowEnd   = tileRow + LOOK_AHEAD;

        int colStart = tileCol - LOOK_AHEAD;
        int colEnd   = tileCol + LOOK_AHEAD;

        for (int row = rowStart, contextRow = 0; row <= rowEnd; row++, contextRow++) {
            for (int col = colStart, contextCol = 0; col <= colEnd; col++, contextCol++) {

                if (row >= 0 && row < this.gameMap.length && col >= 0 && col < this.gameMap.length) {
                    mapContext[contextRow][contextCol] = this.gameMap[row][col];
                }

            }
        }

        return mapContext;
    }    

    /**
     * Creates a context containing all moveable objects
     * that are currently positioned on a tile in a 
     * tile-radius of {@value #LOOK_AHEAD} around the tile
     * where the controlled moveable object is placed.
     * 
     * @return List containing all moveable objects that
     *         are nearby the controlled moveable object
     */
    public List<MoveableObject> provideOtherMoveablesContext() {

        if (this.allMoveables == null) {
            return new ArrayList<>();
        }

        int row = (int) this.moveable.getYPos();
        int col = (int) this.moveable.getXPos();

        int rowStart = row - LOOK_AHEAD;
        int rowEnd   = row + LOOK_AHEAD;

        int colStart = col - LOOK_AHEAD;
        int colEnd   = col + LOOK_AHEAD;

        return this.allMoveables.stream().filter(mov -> {
            
            int movRow = (int) mov.getYPos();
            int movCol = (int) mov.getXPos();

            return mov != this.moveable 
                && movRow >= rowStart 
                && movRow <= rowEnd 
                && movCol >= colStart 
                && movCol <= colEnd;

        }).toList();
    }
}
