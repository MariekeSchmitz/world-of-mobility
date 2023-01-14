package de.hsrm.mi.swt_project.demo.instancehandling;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.GameControl;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.validation.MovementValidator;
import de.hsrm.mi.swt_project.demo.validation.SpawnpointValidator;
import de.hsrm.mi.swt_project.demo.validation.Validator;

/**
 * This class represents a single game instance of the game.
 * 
 * @author Alexandra Müller
 */
public class GameInstance extends Instance {

    private Map<String, MoveableObject> moveableObjects = new HashMap<>();
    private String name;
    
    /**
     * Creates a new instance of the game.
     * 
     * NPCs that are placed on the map are deep-copied into
     * the game instance to avoid cross-instance changes when
     * creating multiple instances using the same map.
     * 
     * @param map the map to use for the instance
     * @param name the name of the instance
     * @param id the id of the instance
     */
    public GameInstance(GameMap map, String name, long id, String mapSavePath) {

        super(map, id, mapSavePath);
        this.name = name;

        ListIterator<MoveableObject> iterator = map.getNpcs().listIterator();

        while (iterator.hasNext()) {

            int index = iterator.nextIndex();
            MoveableObject npc = iterator.next().copy();

            String npcName = "NPC%d".formatted(index);
            moveableObjects.put(npcName, npc);
        }
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
        }

        resetRemainingLifetime();
    }

   
    /**
     * Adds a new user to the instance.
     * 
     * @param user the user to add
     * @param moveableObject the moveableObject of the user
     */
    public void addPlayer(String user, MoveableObject moveableObject) {
        moveableObjects.put(user, moveableObject);
        resetRemainingLifetime();
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

        super.update();

        for(MoveableObject moveableObject : moveableObjects.values()) {

            Validator movementValidator = new MovementValidator(this.map.getTiles(), moveableObject);

            if (movementValidator.validate()) {
                moveableObject.move();
            } else {
                moveableObject.setCurrentVelocity(0);
            }
        }
    }

    /**
     * Trigged Script for NPC
     */
    public void updateScript() {
        for(MoveableObject movableObject : moveableObjects.values()) {
            movableObject.executeScript();
        }
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
    public Map<String, MoveableObject> getMoveableObjects() {
        return moveableObjects;
    }

    /**
     * Cheacks wether the MoveableObject can spawn at the given location
     * 
     * @param moveableObject the Moveable Object to check for
     * @param xPos x-Position the Object will be spawned at
     * @param yPos y-Position the Object will be spawned at
     * @return boolean value that indicates wether the Object can Spawn at the given location
     */
    public boolean validateSpawnpoint(MoveableObject moveableObject, int xPos, int yPos) {
        SpawnpointValidator spawnpointValidator = new SpawnpointValidator(this.map.getTiles(), moveableObject, xPos, yPos);
        return spawnpointValidator.validate();
    }
}
