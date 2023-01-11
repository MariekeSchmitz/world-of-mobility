package de.hsrm.mi.swt_project.demo.validation;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByCar;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.Walkable;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;

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

    protected Tile[][] map;
    protected MoveableObject moveableCopy;

    /**
     * Creates a new MovementValidator.
     * 
     * @param tiles    2D-Array of tiles describing the map
     * @param moveable Object of which the movement will be validated.
     *                 A deep copy of the object will be used for validation checks
     *                 to avoid messing the state of the object!
     */
    public MovementValidator(Tile[][] tiles, MoveableObject moveable) {
        this.map = tiles;
        this.moveableCopy = moveable.copy();
    }

    @Override
    public boolean validate() {

        moveableCopy.move();

        float newPosX = this.moveableCopy.getxPos();
        float newPosY = this.moveableCopy.getyPos();

        if (!insideMap(newPosX, newPosY)) {
            return false;
        }

        int tileCol = (int) newPosX;
        int tileRow = (int) newPosY;

        Tile potentialTile = this.map[tileRow][tileCol];

        return canDriveOnTile(potentialTile) && canWalkOnTile(potentialTile);
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
