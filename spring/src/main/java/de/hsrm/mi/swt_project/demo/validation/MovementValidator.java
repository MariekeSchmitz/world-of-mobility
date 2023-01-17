package de.hsrm.mi.swt_project.demo.validation;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.editor.tiles.Streetile;
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

    protected static final int APPROXIMATION_SECTIONS = 10;

    protected Tile[][] map;
    protected MoveableObject moveableCopy;

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

        float startPosX = this.moveableCopy.getxPos();
        float startPosY = this.moveableCopy.getyPos();
	
        moveableCopy.move();
        
        float endPosX = this.moveableCopy.getxPos();
        float endPosY = this.moveableCopy.getyPos();

        if (!insideMap(endPosX, endPosY)) {
            return false;
        }

        for (int[] crossedTilePos : this.approximateCrossedTiles(startPosX, startPosY, endPosX, endPosY)) {
	
            int tileRow = crossedTilePos[0];
            int tileCol = crossedTilePos[1];
	
            Tile crossedTile = this.map[tileRow][tileCol];
	
            if (!tileAllowsMovementFromPostion(crossedTile) || !canDriveOnTile(crossedTile) || !canWalkOnTile(crossedTile)) {
                return false;
            }
        }
       	
        return true;
    }

    /**
     * Approximates tile positions when moving from start position
     * to end position. This is done by dividing the line between
     * start and end positions into {@value #APPROXIMATION_SECTIONS}
     * sections.
     * 
     * For each section it is checked, if the position belongs to a
     * new tile. If this is the case, the new position is added to
     * the list of crossed tiles.
     *  
     * @param startX Start x-position of the moveable object.
     * @param startY Start y-position of the moveable object.
     * @param endX End x-position of the moveable object.
     * @param endY End y-position of the moveable object.
     * @return List containing int array with two elements representing [row][col].
     */
	
     protected List<int[]> approximateCrossedTiles(float startX, float startY, float endX, float endY) {
	
        List<int[]> crossedTiles = new ArrayList<>();
	
        int[] tileCoord = new int[2];
	
        float currentX = startX;
        float currentY = startY;

        if (this.moveableCopy.getCurrentVelocity() == 0) {
            tileCoord[0] = (int) currentY;
            tileCoord[1] = (int) currentX;
            crossedTiles.add(tileCoord);
            return crossedTiles;
        }
	
        boolean changedTile = true;
        boolean notAtEndX = false;
        boolean notAtEndY = false;

        float sgnX = 0;
        float sgnY = 0;

        switch(this.moveableCopy.getOrientation()) {

            case NORTH:
                sgnY = 1;
                break;

            case NORTH_EAST:
                sgnY = 1;
                sgnX = 1;
                break;

            case NORTH_WEST:
                sgnY = 1;
                sgnX = -1;
                break;

            case EAST:
                sgnX = 1;
                break;

            case SOUTH:
                sgnY = -1;
                break;

            case SOUTH_EAST:
                sgnY = -1;
                sgnX = 1;
                break;

            case SOUTH_WEST:
                sgnY = -1;
                sgnX = -1;
                break;

            case WEST:
                sgnX = -1;
                break;

            default:
                break;

        }
		
        do {

            if (changedTile) {
                tileCoord[0] = (int) currentY;
                tileCoord[1] = (int) currentX;
                crossedTiles.add(tileCoord);
            }
	
            // Simulate movement in direction by assuming that NORTH increases y-coordinates
            // and EAST increases x-coordinates, while SOUTH decreases y-coordinates and
            // WEST decreases y-coordinates
            currentX += sgnX * (this.moveableCopy.getCurrentVelocity() / APPROXIMATION_SECTIONS);
            currentY += sgnY * (this.moveableCopy.getCurrentVelocity() / APPROXIMATION_SECTIONS);
	
            changedTile = ((int) currentY != tileCoord[0]) || ((int) currentX != tileCoord[1]);
	
            // Multiply current and end position with sign of movement to consider movement
            // in negative direction. Only check position in direction of movement.
            notAtEndX = (sgnY == 0) && ((sgnX * currentX) <= (sgnX * endX));
            notAtEndY = (sgnX == 0) && ((sgnY * currentY) <= (sgnY * endY));           
	
        } while (notAtEndX || notAtEndY);
        
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

        for (Orientation orientation: streetTile.getAllowedDirections()) {
            switch (moveableCopy.getOrientation()) {
                case NORTH:
                    if(orientation.equals(Orientation.SOUTH)){
                        return true;
                    };
                    break;

                case SOUTH:
                    if(orientation.equals(Orientation.NORTH)){
                        return true;
                    };                        
                    break;

                case EAST:
                    if(orientation.equals(Orientation.WEST)){
                        return true;
                    };
                    break;

                case WEST:
                    if(orientation.equals(Orientation.EAST)){
                        return true;
                    };                        
                    break;

                default:
                    break;
            }
        }   

        return false;
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
