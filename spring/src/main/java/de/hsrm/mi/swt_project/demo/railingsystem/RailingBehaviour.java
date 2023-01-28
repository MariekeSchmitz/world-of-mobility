package de.hsrm.mi.swt_project.demo.railingsystem;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Streetile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * This class provides rules for moveables 
 * to give them a behaviour like driving on Rails.
 * Its main and current use is to control the driving
 * behaviour of NPCs
 * 
 * @author Fabio Bertels
 */
public class RailingBehaviour {

    public static final float UP_ALIGNMENT_OFFSET = 0.7f;
    public static final float RIGHT_ALIGNMENT_OFFSET = 0.7f;
    public static final float DOWN_ALIGNMENT_OFFSET = 0.3f;
    public static final float LEFT_ALIGNMENT_OFFSET = 0.3f;

    public static final float APPROXIMATION_STEP = 0.002f;

    private Map<String, RailingMemoryCell> railingMemory = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * takes a moveable and the tile its on and calls the right method for the specific tile
     * also puts the moveable to the railingMemory or refreshes its turning state if its on a new tile 
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param dir direction the moveable want to drive to (only for crosses and t-crosses)
     */
    public void railCoordinates(String key, MoveableObject moveable, Tile tile, Direction dir) {
        if(railingMemory.containsKey(key)){
            RailingMemoryCell memoryCell = railingMemory.get(key);
            if(memoryCell.leftTile((int)moveable.getXPos(), (int)moveable.getYPos())){
                memoryCell.setAlreadyTurned(false);
                railingMemory.put(key, memoryCell);
            }
        } else {
            RailingMemoryCell memoryCell = new RailingMemoryCell((int)moveable.getXPos(), (int)moveable.getYPos());
            railingMemory.put(key, memoryCell);
        }

        float movement = moveable.getCurrentVelocity() * moveable.getMaxVelocity();
        if (tile.getType().equals(Tiletype.STREET_STRAIGHT) || tile.getType().equals(Tiletype.PEDESTRIAN_CROSSING)) {
            straightBehaviour(moveable, tile, movement);

        } else if (tile.getType().equals(Tiletype.STREET_CURVE)) {
            curveBehaviour(key, moveable, tile, movement);

        } else if(tile.getType().equals(Tiletype.STREET_CROSS)){
            crossBehaviour(key, moveable, tile, movement, railingMemory.get(key).getRandomDirection());

        } else if(tile.getType().equals(Tiletype.STREET_T_CROSS)){
            tCrossBehaviour(key, moveable, tile, movement, railingMemory.get(key).getRandomDirection());

        } 

    }

    /**
     * this method declares the behaviour rules for t-crosses
     * by checking if the direction is valid and changing it to a valid direction if not 
     * if direction is left or right but not possible it changes the direction to straight
     * if direction is straight it changes to random left or right (50/50 chance)
     * then calls cross behaviour with the valid direction 
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param movement calculated movement the moveable should move in a specific direction 
     * @param dir direction the moveable wants to drive to 
     */
    public void tCrossBehaviour(String key, MoveableObject moveable, Tile tile, float movement, Direction dir){
        Orientation orientation = moveable.getOrientation();
        Streetile sTile = (Streetile)tile;
        switch (dir) {
            case LEFT:
                if(!sTile.getAllowedDirections().contains(orientation.prev().prev())){
                    dir = Direction.STRAIGHT;
                }
                break;
            case RIGHT:
                if(!sTile.getAllowedDirections().contains(orientation.next().next())){
                    dir = Direction.STRAIGHT;
                }
                break;
            case STRAIGHT:
                if(!sTile.getAllowedDirections().contains(orientation)){
                    dir = railingMemory.get(key).getRandomDirection();
                } 
                break;
        }
        crossBehaviour(key, moveable, tile, movement, dir);
    }

    /**
     * this method declares the behaviour rules for crosses
     * by applying straight and curve behaviour at the right moment using the railing memory 
     * for each specific Direction with a Conversion tile either straight or curved
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param movement calculated movement the moveable should move in a specific direction 
     * @param dir direction the moveable wants to drive to 
     */
    public void crossBehaviour(String key, MoveableObject moveable, Tile tile, float movement, Direction dir){
        Tile straightConversionTile = Tiletype.STREET_STRAIGHT.createTile();
        Tile curveConversionTile = Tiletype.STREET_CURVE.createTile();
        Orientation orientation = moveable.getOrientation();
        boolean alreadyTurned = railingMemory.get(key).isAlreadyTurned();
        switch (dir) {
            case LEFT:
                if(alreadyTurned){
                    straightConversionTile.setOrientation(orientation);
                    straightBehaviour(moveable, straightConversionTile, movement);
                } else{
                    curveConversionTile.setOrientation(orientation);
                    curveBehaviour(key, moveable, curveConversionTile, movement);
                }
                break;
            case RIGHT:
                if(alreadyTurned){
                    straightConversionTile.setOrientation(orientation);
                    straightBehaviour(moveable, straightConversionTile, movement);
                } else{
                    curveConversionTile.setOrientation(orientation.prev().prev());
                    curveBehaviour(key, moveable, curveConversionTile, movement);
                }
                break;
            case STRAIGHT:
                straightConversionTile.setOrientation(orientation);
                straightBehaviour(moveable, straightConversionTile, movement);
                break;
        }
    }

    /**
     * this method declares the behaviour rules for straight streets
     * by locking either x or y codinate depending on the orientation of the tile 
     * to a specific offset and only adding the movement to the other one 
     * moveables can only move in a straight direction 
     * 
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param movement calculated movement the moveable should move in a specific direction 
     */
    public void straightBehaviour(MoveableObject moveable, Tile tile, float movement) {

        float xPos = moveable.getXPos();
        float yPos = moveable.getYPos();

        Orientation orientation = moveable.getOrientation();

        if (tile.getOrientation() == Orientation.NORTH || tile.getOrientation() == Orientation.SOUTH) {

            if (orientation == Orientation.NORTH) {

                moveable.setXPos((int) xPos + RIGHT_ALIGNMENT_OFFSET);
                moveable.setYPos(yPos + movement);

            } else if (orientation == Orientation.SOUTH) {

                moveable.setXPos((int) xPos + LEFT_ALIGNMENT_OFFSET);
                moveable.setYPos(yPos - movement);

            }

        } else {

            if (orientation == Orientation.EAST) {

                moveable.setYPos((int) yPos + DOWN_ALIGNMENT_OFFSET);
                moveable.setXPos(xPos + movement);

            } else if (orientation == Orientation.WEST) {

                moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);
                moveable.setXPos(xPos - movement);

            }
        }
    }  

    /**
     * this method declares the behaviour rules for curve streets
     * by calling the right curve behaviour method for each possible direction
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param movement calculated movement the moveable should move in a specific direction 
     */
    public void curveBehaviour(String key, MoveableObject moveable, Tile tile, float movement) {

        float xPos = moveable.getXPos();
        float yPos = moveable.getYPos();

        RailingMemoryCell memoryCell = railingMemory.get(key);

        switch (tile.getOrientation()) {
            case NORTH:
                curveNorthOrientation(key, moveable, tile, movement, xPos, yPos, memoryCell);
                break;
            case EAST:
                curveEastOrientation(key, moveable, tile, movement, xPos, yPos, memoryCell);
                break;
            case SOUTH:
                curveSouthOrientation(key, moveable, tile, movement, xPos, yPos, memoryCell);
                break;
            case WEST:
                curveWestOrientation(key, moveable, tile, movement, xPos, yPos, memoryCell);  
                break;
            default:
                break;
        }
    }  

    /**
     * this method declares the behaviour rules for curve streets with north orientation 
     * depending on the direction of the moveable it either locks x or y codinate 
     * unitl it reaches a specific predefined turning point
     * if it drives over the turning point it calls the doCurveAproximationStep method 
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param movement calculated movement the moveable should move in a specific direction 
     * @param xPos current x position of the moveable
     * @param yPos current y position of the moveable
     * @param memoryCell memory cell for the specific moveable 
     */
    private void curveNorthOrientation(String key, MoveableObject moveable, Tile tile, float movement, float xPos, float yPos, RailingMemoryCell memoryCell){
        Orientation orientation = moveable.getOrientation();
        if (orientation == Orientation.NORTH) {

            moveable.setXPos((int) xPos + RIGHT_ALIGNMENT_OFFSET);

            if (yPos + movement > ((int) yPos + UP_ALIGNMENT_OFFSET)) {
                moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);
                doCurveAproximationStep(key, memoryCell, moveable, tile, Direction.LEFT);

            } else {
                moveable.setYPos(yPos + movement);
            }

        } else if (orientation == Orientation.EAST) {

            moveable.setYPos((int) yPos + DOWN_ALIGNMENT_OFFSET);

            if (xPos + movement > (int) xPos + LEFT_ALIGNMENT_OFFSET) {

                moveable.setXPos((int) xPos + LEFT_ALIGNMENT_OFFSET);
                doCurveAproximationStep(key, memoryCell, moveable, tile, Direction.RIGHT);

            } else {
                moveable.setXPos(xPos + movement);
            }

        } else if (orientation == Orientation.SOUTH) {

            moveable.setXPos((int) xPos + LEFT_ALIGNMENT_OFFSET);
            moveable.setYPos(yPos - movement);

        } else if (orientation == Orientation.WEST) {

            moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);
            moveable.setXPos(xPos - movement);

        }
    }

    /**
     * this method declares the behaviour rules for curve streets with east orientation 
     * depending on the direction of the moveable it either locks x or y codinate 
     * unitl it reaches a specific predefined turning point
     * if it drives over the turning point it calls the doCurveAproximationStep method 
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param movement calculated movement the moveable should move in a specific direction 
     * @param xPos current x position of the moveable
     * @param yPos current y position of the moveable
     * @param memoryCell memory cell for the specific moveable 
     */
    private void curveEastOrientation(String key, MoveableObject moveable, Tile tile, float movement, float xPos, float yPos, RailingMemoryCell memoryCell){
        Orientation orientation = moveable.getOrientation();
        if (orientation == Orientation.NORTH) {

            moveable.setXPos((int) xPos + RIGHT_ALIGNMENT_OFFSET);
            moveable.setYPos(yPos + movement);

        } else if (orientation == Orientation.EAST) {

            moveable.setYPos((int) yPos + DOWN_ALIGNMENT_OFFSET);

            if (xPos + movement > (int) xPos + RIGHT_ALIGNMENT_OFFSET) {

                moveable.setXPos((int) xPos + RIGHT_ALIGNMENT_OFFSET);
                doCurveAproximationStep(key, memoryCell, moveable, tile, Direction.LEFT);

            } else {
                moveable.setXPos(xPos + movement);
            }

        } else if (orientation == Orientation.SOUTH) {

            moveable.setXPos((int) xPos + LEFT_ALIGNMENT_OFFSET);

            if (yPos - movement < (int) yPos + UP_ALIGNMENT_OFFSET) {

                moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);
                doCurveAproximationStep(key, memoryCell, moveable, tile, Direction.RIGHT);

            } else {
                moveable.setYPos(yPos - movement);
            }

        } else if (orientation == Orientation.WEST) {

            moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);
            moveable.setXPos(xPos - movement);

        }
    }

    /**
     * this method declares the behaviour rules for curve streets with south orientation 
     * depending on the direction of the moveable it either locks x or y codinate 
     * unitl it reaches a specific predefined turning point
     * if it drives over the turning point it calls the doCurveAproximationStep method 
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param movement calculated movement the moveable should move in a specific direction 
     * @param xPos current x position of the moveable
     * @param yPos current y position of the moveable
     * @param memoryCell memory cell for the specific moveable 
     */
    private void curveSouthOrientation(String key, MoveableObject moveable, Tile tile, float movement, float xPos, float yPos, RailingMemoryCell memoryCell){
        Orientation orientation = moveable.getOrientation();
        if (orientation == Orientation.NORTH) {

            moveable.setXPos((int) xPos + RIGHT_ALIGNMENT_OFFSET);
            moveable.setYPos(yPos + movement);

        } else if (orientation == Orientation.EAST) {

            moveable.setYPos((int) yPos + DOWN_ALIGNMENT_OFFSET);
            moveable.setXPos(xPos + movement);

        } else if (orientation == Orientation.SOUTH) {

            moveable.setXPos((int) xPos + LEFT_ALIGNMENT_OFFSET);

            if (yPos - movement < (int) yPos + DOWN_ALIGNMENT_OFFSET) {

                moveable.setYPos((int) yPos + DOWN_ALIGNMENT_OFFSET);
                doCurveAproximationStep(key, memoryCell, moveable, tile, Direction.LEFT);

            } else {
                moveable.setYPos(yPos - movement);
            }

        } else if (orientation == Orientation.WEST) {

            moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);

            if (xPos - movement < (int) xPos + RIGHT_ALIGNMENT_OFFSET) {

                moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);
                doCurveAproximationStep(key, memoryCell, moveable, tile, Direction.RIGHT);

            } else {
                moveable.setXPos(xPos - movement);
            }
        }

    }

    /**
     * this method declares the behaviour rules for curve streets with west orientation 
     * depending on the direction of the moveable it either locks x or y codinate 
     * unitl it reaches a specific predefined turning point
     * if it drives over the turning point it calls the doCurveAproximationStep method 
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param movement calculated movement the moveable should move in a specific direction 
     * @param xPos current x position of the moveable
     * @param yPos current y position of the moveable
     * @param memoryCell memory cell for the specific moveable 
     */
    private void curveWestOrientation(String key, MoveableObject moveable, Tile tile, float movement, float xPos, float yPos, RailingMemoryCell memoryCell){
        Orientation orientation = moveable.getOrientation();
        if (orientation == Orientation.NORTH) {

            moveable.setXPos((int) xPos + RIGHT_ALIGNMENT_OFFSET);

            if (yPos + movement > (int) yPos + DOWN_ALIGNMENT_OFFSET) {

                moveable.setYPos((int) yPos + DOWN_ALIGNMENT_OFFSET);
                doCurveAproximationStep(key, memoryCell, moveable, tile, Direction.RIGHT);

            } else {
                moveable.setYPos(yPos + movement);
            }

        } else if (orientation == Orientation.EAST) {

            moveable.setYPos((int) yPos + DOWN_ALIGNMENT_OFFSET);
            moveable.setXPos(xPos + movement);

        } else if (orientation == Orientation.SOUTH) {

            moveable.setXPos((int) xPos + LEFT_ALIGNMENT_OFFSET);
            moveable.setYPos(yPos - movement);

        } else if (orientation == Orientation.WEST) {

            moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);

            if (xPos - movement < (int) xPos + LEFT_ALIGNMENT_OFFSET) {

                moveable.setYPos((int) yPos + UP_ALIGNMENT_OFFSET);
                doCurveAproximationStep(key, memoryCell, moveable, tile, Direction.LEFT);

            } else {
                moveable.setXPos(xPos - movement);
            }
        }
    }

    /**
     * this method is used by the curve behaviour to turn the moveable 
     * after reaching its turning point
     * it also sets the memory cells turning flag to true in case the call 
     * of the curve behaviour could come from the cross method 
     * 
     * @param key name of the moveable object, needed for the railing memory
     * @param memoryCell memory cell for the specific moveable 
     * @param moveable movable to be railed
     * @param tile tile the moveable is currently driving on 
     * @param d direction the moveable should be turned to 
     */
    private void doCurveAproximationStep(String key, RailingMemoryCell memoryCell, MoveableObject moveable, Tile tile, Direction d){
        moveable.turn(d);
        memoryCell.setAlreadyTurned(true);
        railingMemory.put(key, memoryCell);
        curveBehaviour(key, moveable, tile, APPROXIMATION_STEP);
    }

}
