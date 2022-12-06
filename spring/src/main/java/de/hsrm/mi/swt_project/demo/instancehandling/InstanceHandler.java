package de.hsrm.mi.swt_project.demo.instancehandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.controls.Updateable;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.movables.MoveableType;
import de.hsrm.mi.swt_project.demo.movables.Passenger;
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

    Logger logger = LoggerFactory.getLogger(InstanceHandler.class);

    protected List<Instance> instances;

    // TODO think of another solution because long can reach limit
    protected long idCounter = 1;
    @Value("${map.savedir:maps}")
    protected String mapSavePath;

    /**
     * Creates a new instance handler.
     */
    public InstanceHandler() {
        instances = new ArrayList<Instance>();

        Passenger p1 = new Passenger(0, 0, 1);
        Passenger p2 = new Passenger(5, 5, 1);

        GameMap map = new GameMap();

        map.addNpc(p1);
        map.addNpc(p2);

        Instance instance1 = new EditorInstance(map, 1);
        Instance instance2 = new GameInstance(map, "test", 1);

        this.instances.add(instance1);
        this.instances.add(instance2);
    }

    /**
     * Adds a new game instance to the handler.
     * 
     * @param mapName     the map to use for the instance
     * @param sessionName the name of the session
     * @return the id of the new instance
     */
    public long createGameInstance(String mapName, String sessionName) {
        if (mapName == null) {
            instances.add(new GameInstance(new GameMap(), sessionName, idCounter));
            return idCounter++;
        } else {
            try {
                JSONObject mapFile = new JSONObject(Files.readString(Path.of(mapSavePath +"/"+ mapName + ".json")));
                instances.add(new GameInstance(loadMap(mapFile), sessionName, idCounter));
                return idCounter++;
            } catch (IOException e) {
                // e.printStackTrace();
                logger.info("IOException occured on createGameInstance in InstanceHandler: {}", e);
            return -1;
            }
        }
    }

    /**
     * Adds a new editor instance to the handler.
     * 
     * @param mapName the map to use for the instance
     * @return idCounter
     */
    public long createEditorInstance(String mapName) {
        try {
            JSONObject mapFile = new JSONObject(Files.readString(Path.of(mapSavePath + mapName + ".json")));
            instances.add(new EditorInstance(loadMap(mapFile), idCounter));
        } catch (IOException e) {
            instances.add(new EditorInstance(new GameMap(), idCounter));
        }

        return idCounter++;
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
     * Returns a list from all gameinstances
     * 
     * @return a list form all gameinstances
     * @author Finn Schindel, Astrid Klemmer
     */
    public List<Instance> getGameInstances(){
        List<Instance> gList = new ArrayList<>();
        for (Instance instance : instances) {
            if (instance instanceof GameInstance) {
                gList.add(instance);
            }
        }
        return gList;
    }

    /**
     * Returns a list from all editorinstances
     * 
     * @return a list form all editorinstances
     * @author Finn Schindel, Astrid Klemmer
     */
    public List<Instance> getEditorInstances(){
        List<Instance> eList = new ArrayList<>();
        for (Instance instance : instances) {
            if (instance instanceof EditorInstance) {
                eList.add(instance);
            }
        }
        return eList;
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
                if (instance instanceof GameInstance) {
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
                if (instance instanceof EditorInstance) {
                    return (EditorInstance) instance;
                }
            }
        }
        return null;
    }

    public List<String> getMaps() {
        File[] directoryListing = new File(mapSavePath).listFiles();
        List<String> mapNames = new ArrayList<>();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                mapNames.add(child.getName().replace(".json", ""));
            }
            return mapNames;
        } else {
            System.out.println("No maps found");
            return null;
        }
    }

    /**
     * Checks if suggested gamename is already used or is still available
     * 
     * @param sessionName suggested gamename by gameconfiguration
     * @return if suggested gamename can be used
     */
    public Boolean checkSessionNameAvailable(String sessionName) {

        for (Instance instance : instances) {
            if (instance instanceof GameInstance) {
                String name = ((GameInstance) instance).getName();
                if (name.equals(sessionName)) {
                    return false;
                }
            }
        }
        return true;
    }
}
