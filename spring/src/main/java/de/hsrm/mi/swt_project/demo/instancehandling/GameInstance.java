package de.hsrm.mi.swt_project.demo.instancehandling;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.python.util.PythonInterpreter;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.GameControl;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MotorizedObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.scripting.JythonFactory;
import de.hsrm.mi.swt_project.demo.scripting.ScriptContext;
import de.hsrm.mi.swt_project.demo.scripting.ScriptContextCache;
import de.hsrm.mi.swt_project.demo.validation.CollisionValidator;
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
    private ScriptContextCache contextCache = new ScriptContextCache();
    private String name;
    private int maximumPlayerCount;
    private int npcCount;
    
    
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
     * @param maximumPlayerCount count how many player can join the game 
     * @param npcsActivated flag if npcs are activated or not
     */
    public GameInstance(GameMap map, String name, long id, String mapSavePath, int maximumPlayerCount, boolean npcsActivated) {

        super(map, id, mapSavePath);
        this.name = name;
        this.maximumPlayerCount = maximumPlayerCount;

        ListIterator<MoveableObject> iterator = map.getNpcs().listIterator();

        if(npcsActivated){
            this.npcCount = map.getNpcs().size();
            while (iterator.hasNext()) {

                int index = iterator.nextIndex();
                MoveableObject npc = iterator.next().copy();

                String npcName = "NPC%d".formatted(index);
                moveableObjects.put(npcName, npc);
            }
        } else {
            this.npcCount = 0;
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
                moveableObjects.get(user).setCurrentVelocity(moveableObjects.get(user).getCurrentVelocity() + 0.05F);
                break;
            case SPEED_DOWN:
                moveableObjects.get(user).setCurrentVelocity(moveableObjects.get(user).getCurrentVelocity() - 0.05F);
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
        
        CollisionValidator collisionValidator = new CollisionValidator(moveableObjects);
        PythonInterpreter interpreter = JythonFactory.getInterpreter();
        interpreter.set("tiles", this.map.getTiles());
        interpreter.set("moveables", this.moveableObjects);

        for(String key : moveableObjects.keySet()) {
            MoveableObject moveableObject = moveableObjects.get(key);
            Validator movementValidator = new MovementValidator(this.map.getTiles(), moveableObject);
            collisionValidator.setMoveableObject(moveableObject);
            
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            //TODO: VALIDATOR WIEDER ANMACHEN UND NUR FÜR NPCS ANMACHEN 
            /////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            if (/*movementValidator.validate() &&*/ collisionValidator.validate()) {
                if(/*key.contains("NPC")&&*/moveableObject instanceof MotorizedObject){
                    MotorizedObject m = (MotorizedObject)moveableObject;
                    m.move(this.map.getTiles()[(int)moveableObject.getyPos()][(int)moveableObject.getxPos()]);
                } else{
                    moveableObject.move();
                }
            } else {
                moveableObject.setCurrentVelocity(0);
            }
        }
    }

    /**
     * Trigged Script for NPC
     */
    public void updateScript() {
        List<MoveableObject> allMoveables = this.moveableObjects.values().stream().toList();
        Tile[][] mapTiles = this.map.getTiles();
        for (MoveableObject movableObject : allMoveables) {
            ScriptContext context = contextCache.getContextFor(movableObject, mapTiles, allMoveables);
            movableObject.executeScript(context);
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

    /**
     * Checks if count of players is less than maximumPlayerCount
     * @return true if playerCount is less than maximumPlayerCount, false if not
     */
    public boolean playerSlotAvailable(){
        int playerCount = moveableObjects.size()- npcCount;
        if(playerCount < maximumPlayerCount) return true;
        else return false;
    }
}
