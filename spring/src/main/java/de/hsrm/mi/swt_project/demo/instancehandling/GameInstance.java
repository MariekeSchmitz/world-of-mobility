package de.hsrm.mi.swt_project.demo.instancehandling;

import java.util.HashMap;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.GameControl;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.DriveableByCar;
import de.hsrm.mi.swt_project.demo.editor.tiles.tile_properties.Walkable;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.Passenger;

/**
 * This class represents a single game instance of the game.
 * 
 * @author Alexandra Müller
 */
public class GameInstance extends Instance {
    private HashMap<String, MoveableObject> moveableObjects;
    private String name;
    
    /**
     * Creates a new instance of the game.
     * 
     * @param map the map to use for the instance
     * @param name the name of the instance
     * @param id the id of the instance
     */
    public GameInstance(GameMap map, String name, long id) {
        super(map, id);
        this.moveableObjects = new HashMap<String, MoveableObject>();
        for (MoveableObject npc : map.getNpcs()) {
            moveableObjects.put(("NPC" + map.getNpcs().indexOf(npc)), npc.copy());
        }
        this.name = name;
    }

    /**
     * Moves a moveable object.
     * 
     * @param user the user that is associated with the moveable object
     * @param control the control type that is used to move the moveable object
     */
    public void moveMoveable(String user, GameControl control) {
        
        switch (control) {
            case LEFT:
                moveableObjects.get(user).turn(Direction.LEFT);
                break;
            case RIGHT:
                moveableObjects.get(user).turn(Direction.RIGHT);
                break;
            case SPEED_UP:
                moveableObjects.get(user).setCurrentVelocity(moveableObjects.get(user).getCurrentVelocity() + 0.1F);
                break;
            case SPEED_DOWN:
                moveableObjects.get(user).setCurrentVelocity(moveableObjects.get(user).getCurrentVelocity() - 0.1F);
                break;
        };

    }

   
    /**
     * Adds a new user to the instance.
     * 
     * @param user the user to add
     * @param moveableObject the moveableObject of the user
     */
    public void addPlayer(String user, MoveableObject moveableObject) {
        moveableObjects.put(user, moveableObject);
    }

    /**
     * Removes a user from the instance.
     * 
     * @param user the user to remove
     */
    public void removePlayer(String user) {
        moveableObjects.remove(user);
    }

    /**
     * Pushes updates of the instance.
     */
    @Override
    public void update() {
        
        for(MoveableObject moveableObject : moveableObjects.values()) {
            validateAndMove(moveableObject);
        }
    }

    private void validateAndMove(MoveableObject moveableObject) {

        int mapSize = this.map.getTiles().length;

        // predict new X- and Y-Pos
        float currentPosX = moveableObject.getXPos();
        float currentPosY = moveableObject.getYPos();

        float newPosX = currentPosX;
        float newPosY = currentPosY;

        float straightMovement = moveableObject.getCurrentVelocity() * moveableObject.getMaxVelocity();
        float diagonalMovement = (float) (straightMovement / Math.sqrt(2));
        
        switch (moveableObject.getOrientation()) {

            case NORTH:
                newPosY += straightMovement;
                break;

            case NORTH_EAST:
                newPosX += diagonalMovement;
                newPosY += diagonalMovement;
                break;

            case EAST:
                newPosX += straightMovement;
                break;

            case SOUTH_EAST:
                newPosX += diagonalMovement;
                newPosY -= diagonalMovement;
                break;

            case SOUTH:
                newPosY -= straightMovement;
                break;

            case SOUTH_WEST:
                newPosX -= diagonalMovement;
                newPosY -= diagonalMovement;
                break;

            case WEST:
                newPosX -= straightMovement;
                break;

            case NORTH_WEST:
                newPosX -= diagonalMovement;
                newPosY += diagonalMovement;
                break;

            default:
                break;
        }

        // check if moveable still on map
        if (!((newPosX < (mapSize + 1)) && (newPosX >= 0) && (newPosY < (mapSize + 1)) && (newPosY >= 0))) {
            moveableObject.setCurrentVelocity(0);
            return;
        }
       
        // check if moveable moves on allowed tile 
        Tile potentialTile = this.map.getTiles()[(int)newPosX][(int)newPosY];

        if (moveableObject instanceof MotorizedObject) {
            if (!(potentialTile instanceof DriveableByCar)) {
                moveableObject.setCurrentVelocity(0);
                return;
            }
        }

        if (moveableObject instanceof Passenger) {
            if (!(potentialTile instanceof Walkable)) {
                moveableObject.setCurrentVelocity(0);
                return;
            }
        } 

        moveableObject.move(newPosX, newPosY);
        
    }

    /**
     * Getter for the name of the instance.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the moveable objects.
     * 
     * @return the moveableObjects
     */
    public HashMap<String, MoveableObject> getMoveableObjects() {
        return moveableObjects;
    }
}
