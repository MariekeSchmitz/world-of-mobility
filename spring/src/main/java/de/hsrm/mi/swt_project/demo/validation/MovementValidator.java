package de.hsrm.mi.swt_project.demo.validation;

import java.util.HashSet;
import java.util.Set;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Streetile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByCar;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.Walkable;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;
import de.hsrm.mi.swt_project.demo.util.Position;

/**
 * This class validates the movement of a moveable object.
 * 
 * To do so, it simulates the move with a deep copy of
 * the moveable object. After that it checks if it will
 * still be inside the map area and if it is allowed to
 * be positioned on the tile it reaches.
 * 
 * @author Marieke Schmitz
 * @author Sascha Scheid
 * @author Finn Schindel
 */
public class MovementValidator implements Validator {

    protected static final int APPROXIMATION_SECTIONS = 10;

    protected Tile[][] map;
    protected MoveableObject moveableCopy;
    protected Position startPos;

    /**
     * Creates a new MovementValidator.
     * 
     * @param tiles    2D-Array of tiles describing the map
     * @param moveable Object of which the movement will be validated.
     *                 A deep copy of the object will be used for validation checks
     *                 to avoid messing with the state of the object!
     */
    public MovementValidator(Tile[][] tiles, MoveableObject moveable) {
        this.map = tiles;
        this.moveableCopy = moveable.copy();
    }

    @Override
    public boolean validate() {

        float startPosX = this.moveableCopy.getXPos();
        float startPosY = this.moveableCopy.getYPos();

        this.startPos = new Position((int) startPosX, (int) startPosY);
	
        moveableCopy.move();
        
        float endPosX = this.moveableCopy.getXPos();
        float endPosY = this.moveableCopy.getYPos();

        if (!insideMap(endPosX, endPosY)) {
            return false;
        }

        return this.approximateCrossedTiles(startPosX, startPosY, endPosX, endPosY)
            .stream()
            .allMatch(
                position -> this.canMoveToPosition(position)
            );
    }

    /**
     * Approximates tile positions when moving from start position
     * to end position. This is done by dividing the line between
     * start and end positions into {@value #APPROXIMATION_SECTIONS}
     * sections.
     *  
     * @param startX Start x-position of the moveable object.
     * @param startY Start y-position of the moveable object.
     * @param endX End x-position of the moveable object.
     * @param endY End y-position of the moveable object.
     * @return Set containing int array with two elements representing [row][col].
     */
     protected Set<Position> approximateCrossedTiles(float startX, float startY, float endX, float endY) {
	
        Set<Position> crossedTiles = new HashSet<>();

        float xVelocity = (endX - startX) / APPROXIMATION_SECTIONS;
        float yVelocity = (endY - startY) / APPROXIMATION_SECTIONS;

        for (int i = 1; i <= APPROXIMATION_SECTIONS; i++) {

            float xPos = startX + (i * xVelocity);
            float yPos = startY + (i * yVelocity);

            Position position = new Position((int) xPos, (int) yPos);
            crossedTiles.add(position);
        }
        
        return crossedTiles;
    } 	

    /**
     * Checks if the position is inside the map.
     * 
     * @param xPos x-position that equals the column to check
     * @param yPos y-position that equals the row to check
     * @return true if position is inside the map, else false
     */
    protected boolean insideMap(float xPos, float yPos) {

        int mapSize = this.map.length;

        boolean xPosValid = (xPos < mapSize) && (xPos >= 0);
        boolean yPosValid = (yPos < mapSize) && (yPos >= 0);

        return xPosValid && yPosValid;
    }

    /**
     * Checks if movement to the position is allowed.
     * @param position Position to move to
     * 
     * @return True if movement is allowed, else false
     */
    protected boolean canMoveToPosition(Position position) {

        if (position.equals(this.startPos)) {
            return true;
        }
        
        int tileRow = position.getYPos();
        int tileCol = position.getXPos();

        Tile crossedTile = this.map[tileRow][tileCol];

        return this.tileAllowsMovementFromPostion(crossedTile) 
            && this.canDriveOnTile(crossedTile) 
            && this.canWalkOnTile(crossedTile);
    }


     /**
      * Street tiles restrict the directions from which it is
      * allowed. This is done by checking if the orientation
      * of the moveable object is the opposite of any of the
      * allowed directions of the street tile.
      * 
      * If tile is not a street tile or moveable is not motorized
      * the movement will be allowed.
      * 
      * @param tile Tile to check allowed directions of.
      * @return true if movement to the tile is allowed, else false
      */
      protected boolean tileAllowsMovementFromPostion(Tile tile) {
 
        if (!(tile instanceof Streetile) || !(this.moveableCopy instanceof MotorizedObject)) {
            return true;
        }

        Streetile streetTile = (Streetile) tile;
        Orientation headingTo = moveableCopy.getOrientation();

        return streetTile
            .getAllowedDirections()
            .stream()
            .anyMatch(
                allowedFrom -> allowedFrom.opposite().equals(headingTo)
            );
    }

    /**
     * Checks if the moveable object is allowed to stay on the tile.
     * If the moveable object is not a motorized object, this check
     * will pass.
     * 
     * @param tile Tile the moveable object is positioned on
     * @return true if the moveable is allowed to stay on the tile, else false
     */
    protected boolean canDriveOnTile(Tile tile) {

        boolean moveableIsNotMotorized = !(this.moveableCopy instanceof MotorizedObject);
        boolean tileIsDrivable = (tile instanceof DriveableByCar);

        return moveableIsNotMotorized || tileIsDrivable;
    }

    /**
     * Checks if the moveable object is allowed to stay on the tile.
     * If the moveable object is not a passenger, this check will pass.
     * 
     * @param tile Tile the moveable object is positioned on
     * @return true if the moveable is allowed to stay on the tile, else false
     */
    protected boolean canWalkOnTile(Tile tile) {

        boolean moveableIsNotPassenger = !(this.moveableCopy instanceof Passenger);
        boolean tileIsWalkable = (tile instanceof Walkable);

        return moveableIsNotPassenger || tileIsWalkable;
    }

}
