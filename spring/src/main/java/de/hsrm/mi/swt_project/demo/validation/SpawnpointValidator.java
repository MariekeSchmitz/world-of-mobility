package de.hsrm.mi.swt_project.demo.validation;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * Extends MovementValidator to check if a Tile is a valid Spawnpoint.
 * 
 * Might need further refinement to do separate checks for Drivable and Walkable,
 * because currently a Passenger can spawn on the street and a Car can spawn on the sidewalk.
 * 
 * @author Felix Ruf
 */
public class SpawnpointValidator extends MovementValidator {

    private int xPos;
    private int yPos;

    /**
     * 
     * @param mapTiles 2D-Array of the tiles describing the map
     * @param moveableObject Object whose Spawnpoint will be validated
     * @param xPos x-Position the Object will be spawned at
     * @param yPos y-Position the Object will be spawned at
     */
    public SpawnpointValidator(Tile[][] mapTiles, MoveableObject moveableObject, int xPos, int yPos) {
        super(mapTiles, moveableObject);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public boolean validate() {
        
        if(!insideMap(xPos, yPos)) {
            return false;
        }
        Tile potetialTile = this.map[yPos][xPos];

        return canDriveOnTile(potetialTile) && canWalkOnTile(potetialTile);
    }
    
}
