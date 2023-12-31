package de.hsrm.mi.swt_project.demo.instancehandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.PlaceableControl;
import de.hsrm.mi.swt_project.demo.controls.TileControl;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.PlaceableObjectType;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.movables.MoveableType;
import de.hsrm.mi.swt_project.demo.validation.NpcPlacementValidator;
import de.hsrm.mi.swt_project.demo.validation.ScriptValidator;

/**
 * This class represents a single editor instance of the game.
 * 
 * @author Alexandra Müller
 */
public class EditorInstance extends Instance {

    private List<String> users;

    Logger logger = LoggerFactory.getLogger(EditorInstance.class);

    /**
     * Creates a new editor instance.
     * 
     * @param map the map to use for the instance
     * @param id  the id of the instance
     */
    public EditorInstance(GameMap map, long id, String mapSavePath) {
        super(map, id, mapSavePath);
        this.users = new ArrayList<String>();
    }

    /**
     * Edits the map by placing, removing or turning a tile.
     * 
     * @param xPos     the x position of the tile
     * @param yPos     the y position of the tile
     * @param control  the control option to use
     * @param tiletype the tile type associated with the control option
     */
    public void editMap(int xPos, int yPos, TileControl control, Tiletype tiletype) {
        switch (control) {
            case PLACE:
                map.addTile(tiletype.createTile(), xPos, yPos);
                break;
            case REMOVE:
                if (map.getTiles()[yPos][xPos] != null) {
                    map.removeTile(xPos, yPos);
                }
                break;
            case TURN_LEFT:
                if (map.getTiles()[yPos][xPos] != null) {
                    map.getTiles()[yPos][xPos].turn(Direction.LEFT);
                }
                break;
            case TURN_RIGHT:
                if (map.getTiles()[yPos][xPos] != null) {
                    map.getTiles()[yPos][xPos].turn(Direction.RIGHT);
                }
                break;
        }
        resetRemainingLifetime();
    }

    /**
     * Edits the map by adding or removing placeable objects on a map.
     * 
     * @param xPos
     * @param yPos
     * @param placeableControl
     * @param placeableObjectType
     * @return edit on map valid
     */
    public boolean editPlaceablesOnMap(int xPos, int yPos, PlaceableControl placeableControl,
            PlaceableObjectType placeableObjectType) {

        Tile tile = map.getTiles()[yPos][xPos];

        resetRemainingLifetime();

        switch (placeableControl) {
            case ADD:
                return map.validateAndAddPlaceableObject(tile, xPos, yPos, placeableObjectType.createPlaceableObject());
            case REMOVE:
                tile.deletePlaceable();
                return true;
        }
        return false;
    }

    /**
     * Adds a new user to the instance.
     * 
     * @param user the user to add
     */
    public void addUser(String user) {
        users.add(user);
        resetRemainingLifetime();
    }

    /**
     * Removes a user from the instance.
     * 
     * @param user the user to remove
     */
    public void removeUser(String user) {
        users.remove(user);
    }

    /**
     * Saves the map to a file.
     * 
     * @param name the name of the map
     * @author Felix Ruf, Alexandra Müller, Sascha Scheid
     */
    public void saveMap(String name) {
        File saveDir = new File(mapSavePath);

        if (this.map.getName() == null || this.map.getName().isEmpty()) {
            this.map.setName(name);
        }

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        String mapToSave = gson.toJson(this.map);

        File savePath = new File(saveDir, "%s.json".formatted(map.getName()));

        saveDir.mkdirs();

        if (!savePath.exists()) {
            try {
                if (!savePath.createNewFile()) {
                    throw new IOException("Something went wrong when creating a new file");
                }
            } catch (IOException e) {
                logger.info("IOException occured in saveMap in EditorInstance, when creating a file: ", e);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            bw.write(mapToSave);
        } catch (Exception e) {
            logger.info("Exception occured in saveMap in EditorInstance when writing to a file: ", e);
        }

    }

    public List<String> getUsers() {
        return users;
    }

    /**
     * places NPC of given type onto given x and y coordinates if valid
     * throws exception if not valid
     * 
     * @author Marie Bohnert, Tom Gouthier
     * @param x    x coordinate of moveable
     * @param y    y coordinate of moveable
     * @param type type of moveable
     * @throws NpcNotPlaceableException
     */
    public void placeNPC(float x, float y, MoveableType type) throws NpcNotPlaceableException {

        MoveableObject newNpc = type.createMovable(x, y);
        NpcPlacementValidator npcValidator = new NpcPlacementValidator(newNpc, this.map.getNpcs(), this.map.getTiles()[(int) y][(int) x]);
        logger.info("Trying to place npc with data: {}", newNpc);
        if (npcValidator.validate()) {
            this.map.addNpc(newNpc);
            logger.info("placed following npc: {}", newNpc);
        } else {
            throw new NpcNotPlaceableException();
        }
    }

    /**
     * deletes npc with given coordinates if found in npc-list
     * 
     * @param x
     * @param y
     * @author Tom Gouthier, Marie Bohnert
     * @throws NoNpcExistsOnCoordinates
     */
    public void deleteNPC(float x, float y) throws NoNpcExistsOnCoordinates {
        
        map.deleteNPC(x, y);
      
    }

    /**
     * adds script to npc with given coordinates
     * 
     * @param x      x coordinate of npc
     * @param y      y coordinate of npc
     * @param script script to load
     * @author Marie Bohnert, Tom Gouthier
     * @throws NoNpcExistsOnCoordinates
     * @throws ScriptNotValidException
     */
    public void addScriptToNpc(float x, float y, String script)
            throws NoNpcExistsOnCoordinates, ScriptNotValidException {

        logger.info("Trying to load this script: {} for npc on coordinates: {} {}", script, x, y);
        ScriptValidator scriptValidator = new ScriptValidator(script);
        if (!scriptValidator.validate()) {
            logger.info("The script wasnt valid.");
            throw new ScriptNotValidException();
        }

        for (MoveableObject npc : map.getNpcs()) {
            if (npc.getXPos() == x && npc.getYPos() == y) {
                npc.loadScript(script);
                logger.info("script was loaded for npc");
                return;
            }
        }

        logger.info("No Npc on this coordinates.");
        throw new NoNpcExistsOnCoordinates();
    }
}