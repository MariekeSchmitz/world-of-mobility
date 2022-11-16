package de.hsrm.mi.swt_project.demo;

import java.util.HashMap;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

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
    public void moveMovable(String user, GameControl control) {
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
        // TODO check if move is legal
    }

    /**
     * Adds a new user to the instance.
     * 
     * @param user the user to add
     * @param movableObject the movableObject of the user
     */
    public void addPlayer(String user, MoveableObject movableObject) {
        moveableObjects.put(user, movableObject);
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
        for(MoveableObject movableObject : moveableObjects.values()) {
            movableObject.move();
        }
        // TODO push updates to clients
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
}
