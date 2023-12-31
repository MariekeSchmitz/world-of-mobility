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
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.PlaceableObject;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.PlaceableObjectType;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.editor.tiles.TrafficTile;
import de.hsrm.mi.swt_project.demo.movables.MoveableType;
import de.hsrm.mi.swt_project.demo.objecthandling.TrafficLightSingleTon;
import de.hsrm.mi.swt_project.demo.objecthandling.TrafficLightState;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * This class maintains all instances of the game.
 * 
 * @author Alexandra Müller
 * @author Sascha Scheid
 */
public class InstanceHandler implements Updateable {

    private static final String JSON = ".json";

    @Autowired
    protected UpdateloopService loopservice;

    private TrafficLightSingleTon trafficLightSingleTon = TrafficLightSingleTon.getInstance();
    
    @Autowired
    protected UpdateloopInstanceInfo loopInstanceInfo;


    @Value("${instance.lifetime:1200}")
    protected long instanceLifetimeCycles;

    protected Logger logger = LoggerFactory.getLogger(InstanceHandler.class);

    protected List<Instance> instances = new ArrayList<>();

    protected long idCounter = 1;
    @Value("${map.savedir:maps}")
    protected String mapSavePath;

    /**
     * Creates a new instance handler.
     */
    public InstanceHandler() {
        instances = new ArrayList<>();
    }

    /**
     * Adds a new game instance to the handler.
     * 
     * @param mapName     the map to use for the instance
     * @param sessionName the name of the session
     * @return the id of the new instance
     */
    public long createGameInstance(String mapName, String sessionName, int maximumPlayerCount, boolean npcsActivated) {

        GameMap map;

        if (mapName == null) {

            map = new GameMap();

        } else {

            String fileName = "%s/%s.json".formatted(mapSavePath, mapName);
            Path filePath = Path.of(fileName);
            String mapFile;
    
            try {
                mapFile = Files.readString(filePath);
            } catch (IOException e) {
                logger.error("IOException occured on createGameInstance in InstanceHandler: {}\nFilename: {}", e, fileName);
                return -1;
            }
    
            map = loadMap(mapFile);

        }

        Instance instance = new GameInstance(map, sessionName, idCounter, mapSavePath, maximumPlayerCount, npcsActivated);

        instance.setLifetime(instanceLifetimeCycles);
        instances.add(instance);

        idCounter++;

        return instance.getId();
    }

    /**
     * Adds a new editor instance to the handler.
     * 
     * @param mapName the map to use for the instance
     * @return idCounter
     */
    public long createEditorInstance(String mapName) {

        if (mapName == null) {
            logger.error("Could not create game instance because no mapName was given.");
            return -1;
        }

        GameMap map;

        String fileName = "%s/%S.json".formatted(mapSavePath, mapName);
        Path filePath = Path.of(fileName);

        try {
            String mapFile = Files.readString(filePath);
            map = loadMap(mapFile);
        } catch (IOException e) {
            map = new GameMap();
            map.setName(mapName);
        }

        Instance instance = new EditorInstance(map, idCounter, mapSavePath);
        instance.setLifetime(instanceLifetimeCycles);
        instances.add(instance);

        idCounter++;

        return instance.getId();
    }

    /**
     * Loads a map from a JSON file.
     * 
     * @param mapFile the JSON file to load the map from
     * @return the loaded map
     * @author Alexandra Müller
     * @author Felix Ruf
     */
    private GameMap loadMap(String mapFile) {
        JSONObject file = new JSONObject(mapFile);
        JSONArray tiles = file.getJSONArray("tiles");
        JSONArray npcs = file.getJSONArray("npcs");
        GameMap map = new GameMap(tiles.length());

        int yPos = 0;
        
        for (Object rowsObject : tiles) {
            JSONArray rows = (JSONArray) rowsObject;
            int xPos = 0;
            for (int i = 0; i < rows.length(); i++) {
                List<Object> ls = rows.toList();
                if (ls.get(i) != null) {
                    JSONObject tileObject = rows.getJSONObject(i);
                    Tiletype tileType = tileObject.getEnum(Tiletype.class, "type");
                    Orientation orientation = tileObject.getEnum(Orientation.class, "orientation");
                    Tile newTile = tileType.createTile();
                    newTile.setOrientation(orientation);
                    if(tileObject.has("placedObject")) {
                        JSONObject placedObject = tileObject.getJSONObject("placedObject");
                        PlaceableObjectType placeableType = placedObject.getEnum(PlaceableObjectType.class, "type");
                        PlaceableObject placeableObject = placeableType.createPlaceableObject();
                        newTile.addPlaceable(placeableObject);
                    }
                    if(tileObject.has("allowedDirections")) {
                        JSONArray allowedDirections = tileObject.getJSONArray("allowedDirections");
                        List<Orientation> allowedDirectionsList = new ArrayList<>();
                        for (Object orientationObject : allowedDirections) {
                            allowedDirectionsList.add(Orientation.valueOf((String) orientationObject));
                        }
                        ((TrafficTile)(newTile)).setAllowedDirections(allowedDirectionsList);
                    }
                    map.setTile(newTile, xPos, yPos);
                }
                xPos++;
            }
            yPos++;
        }

        npcs.forEach(npc -> {
            JSONObject npcObject = (JSONObject) npc;
            MoveableType npcType = npcObject.getEnum(MoveableType.class, "type");
            float xPosition = npcObject.getFloat("xPos");
            float yPosition = npcObject.getFloat("yPos");
            float capacity = npcObject.getFloat("capacity");
            String script = npcObject.getString("script");
            MoveableObject newNpc = npcType.createMovable(xPosition, yPosition);
            newNpc.setCapacity(capacity);
            newNpc.loadScript(script);
            map.addNpc(newNpc);
        });

        map.setName(file.getString("name"));

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

        List<Instance> toDelete = new ArrayList<>();

        for (Instance instance : instances) {

            if (instance.getRemainingLifetime() <= 0) {
                logger.info("Lifetime of instance {} ended.", instance);
                toDelete.add(instance);
                continue;
            }

            instance.update();

            if (instance instanceof GameInstance) { // Only publish state of GameInstances periodically
                loopservice.publishInstanceState(instance);
            }
        }

        for (Instance instance : toDelete) {
            logger.info("Instance deleted {}", instance);
            loopInstanceInfo.publishInstanceInfoState(instance, "DELETE");
            this.instances.remove(instance);
        }

    }

    public void triggerScript() {
        for (Instance instance : instances) {
            if (instance instanceof GameInstance gameinstance) {     
                gameinstance.updateScript();
            }
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
    public List<Instance> getGameInstances() {
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
    public List<Instance> getEditorInstances() {
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

            if (instance.getId() == id && instance instanceof GameInstance gameInstance) {
                return gameInstance;
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

            if (instance.getId() == id && instance instanceof EditorInstance editorInstance) {
                return editorInstance;
            }

        }

        return null;
    }

    public List<String> getMaps() {
        
        File[] directoryListing = new File(mapSavePath).listFiles();
        List<String> mapNames = new ArrayList<>();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                mapNames.add(child.getName().replace(JSON, ""));
            }
        }

        return mapNames;
    }

    /**
     * Checks if suggested gamename is already used or is still available
     * 
     * @param sessionName suggested gamename by gameconfiguration
     * @return if suggested gamename can be used
     */
    public boolean checkSessionNameAvailable(String sessionName) {

        for (Instance instance : instances) {

            if (instance instanceof GameInstance gameInstance) {

                String name = gameInstance.getName();
                if (name.equals(sessionName)) {
                    return false;
                }

            }
        }
        
        return true;
    }

    /**
     * Checks if suggested WorldName is available
     * 
     * @param worldname name to be checked
     * @return if suggested World Name is unique or not
     */
    public boolean checkWorldNameAvailable(String worldname) {
        for (String name : getMaps()) {
            if (name.equals(worldname)) {

                return false;
            }
        }

        for (Instance instance : getEditorInstances()) {
            String mapName = instance.getMap().getName();

            if (mapName.equals(worldname)) {
                return false;
            }
        }

        return true;
    }

    public TrafficLightState getTrafficLightState() {
        return trafficLightSingleTon.getTrafficLightState();
    }
}
