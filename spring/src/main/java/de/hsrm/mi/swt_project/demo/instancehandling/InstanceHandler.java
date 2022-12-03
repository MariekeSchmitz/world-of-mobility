package de.hsrm.mi.swt_project.demo.instancehandling;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.controls.Updateable;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.MoveableType;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * This class maintains all instances of the game.
 * 
 * @author Alexandra Müller
 * @author Sascha Scheid
 */
 public class InstanceHandler implements Updateable {

    @Autowired
    protected UpdateloopService loopservice;

    protected List<Instance> instances;

    // TODO think of another solution because long can reach limit
    protected long idCounter = 1;


    /**
     * Creates a new instance handler.
     */
    public InstanceHandler() {
        instances = new ArrayList<Instance>();
    }

    /**
     * Adds a new game instance to the handler.
     * 
     * @param mapName the map to use for the instance
     * @param sessionName the name of the session
     */
    public void createGameInstance(String mapName, String sessionName) {
        try {
            JSONObject mapFile = new JSONObject(Files.readString(Path.of("./maps/" + mapName + ".json")));
            instances.add(new GameInstance(loadMap(mapFile), sessionName, idCounter++));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Adds a new editor instance to the handler.
     * 
     * @param mapName the map to use for the instance
     */
    public void createEditorInstance(String mapName) {
        try {
            JSONObject mapFile = new JSONObject(Files.readString(Path.of("./maps/" + mapName + ".json")));
            instances.add(new EditorInstance(loadMap(mapFile), idCounter++));
        } catch (IOException e) {
            instances.add(new EditorInstance(new GameMap(), idCounter++));
        }
    }

    /**
     * Loads a map from a JSON file.
     * 
     * @param mapFile the JSON file to load the map from
     * @return the loaded map
     */
    private GameMap loadMap(JSONObject mapFile) {
        JSONArray tiles = mapFile.getJSONArray("Tiles");
        JSONArray npcs = mapFile.getJSONArray("Npcs");

        GameMap map = new GameMap();
        
        tiles.forEach(tile -> {
            JSONObject tileObject = (JSONObject) tile;
            Tiletype tileType = tileObject.getEnum(Tiletype.class, "type");
            int xPos = tileObject.getInt("xPos");
            int yPos = tileObject.getInt("yPos");
            Orientation orientation = tileObject.getEnum(Orientation.class, "orientation");
            Tile newTile = tileType.createTile();
            newTile.setOrientation(orientation);
            map.addTile(newTile, xPos, yPos);
        });

        npcs.forEach(npc -> {
            JSONObject npcObject = (JSONObject) npc;
            MoveableType npcType = npcObject.getEnum(MoveableType.class, "type");
            int xPos = npcObject.getInt("xPos");
            int yPos = npcObject.getInt("yPos");
            int maxVelocity = npcObject.getInt("maxVelocity");
            MoveableObject newNpc = npcType.createMovable(xPos, yPos, maxVelocity);
            map.addNpc(newNpc);
        });

        return map;
    }

    /**
     * Removes an instance from the handler.
     * 
     * @param instance the instance to remove
     */
    public void removeInstance(Instance instance) {
        instances.remove(instance);
    }

    /**
     * This method triggers all instances to update
     * their state. After an update of the state, 
     * the new state of an instance will be published.
     */
    public void update() {
        for (Instance instance : instances) {
            instance.update();
            loopservice.publishInstanceState(instance);
        }
    }

    /**
     * Returns the instance with the given id.
     * 
     * @param id the id of the instance
     * @return the instance with the given id
     */
    public List<Instance> getInstances() {
        return instances;
    }

    /**
     * Returns the game instance with the given id.
     * 
     * @param id the id of the instance
     * @return the instance with the given id
     */
    public GameInstance getGameInstanceById(long id) {
        for (Instance instance : instances) {
            if (instance.getId() == id) {
                if(instance instanceof GameInstance) {
                    return (GameInstance) instance;
                }
            }
        }
        return null;
    }

    /**
     * Returns the instance with the given id.
     * 
     * @param id the id of the instance
     * @return the instance with the given id
     */
    public EditorInstance getEditorInstanceById(long id) {
        for (Instance instance : instances) {
            if (instance.getId() == id) {
                if(instance instanceof EditorInstance) {
                    return (EditorInstance) instance;
                }
            }
        }
        return null;
    }
}
