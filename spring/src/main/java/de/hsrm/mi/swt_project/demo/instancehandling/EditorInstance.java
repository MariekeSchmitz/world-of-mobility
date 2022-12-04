package de.hsrm.mi.swt_project.demo.instancehandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.EditorControl;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;

/**
 * This class represents a single editor instance of the game.
 * 
 * @author Alexandra Müller
 */
public class EditorInstance extends Instance {

    private List<String> users;
    
    /**
     * Creates a new editor instance.
     * 
     * @param map the map to use for the instance
     * @param id the id of the instance
     */
    public EditorInstance(GameMap map, long id) {
        super(map, id);
        this.users = new ArrayList<String>();
    }

    /**
     * Edits the map by placing, removing or turning a tile.
     *  
     * @param xPos the x position of the tile
     * @param yPos the y position of the tile
     * @param control the control option to use
     * @param tiletype the tile type associated with the control option
     */
    public void editMap(int xPos, int yPos, EditorControl control, Tiletype tiletype) {
        switch(control) {
            case PLACE:
                if(map.getTiles()[yPos][xPos] == null) {
                    map.addTile(tiletype.createTile(), xPos, yPos);                  
                }
                break;
            case REMOVE:
                if(map.getTiles()[yPos][xPos] != null) {
                    map.removeTile(xPos, yPos);
                }
                break;
            case TURN_LEFT:
                if(map.getTiles()[yPos][xPos] != null) {
                    map.getTiles()[yPos][xPos].turn(Direction.LEFT);
                }
                break;
            case TURN_RIGHT:
                if(map.getTiles()[yPos][xPos] != null) {
                    map.getTiles()[yPos][xPos].turn(Direction.RIGHT);
                }
                break;
        }
    }

    /**
     * Adds a new user to the instance.
     * 
     * @param user the user to add
     */
    public void addUser(String user) {
        users.add(user);
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
     * Pushes updates of the instance.
     */
    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    /**
     * Saves the map to a file.
     * 
     * @param name the name of the map
     */
    public void saveMap(String name) {
        File saveDir = new File(mapSavePath);

        if(this.map.getName() == null || this.map.getName().isEmpty()) {
            this.map.setName(name);
        }

        JSONObject mapToSave = new JSONObject();
        mapToSave.put("Tiles", this.map.getTiles());
        mapToSave.put("Npcs", this.map.getNpcs());

        File savePath = new File(saveDir, map.getName() + ".json"); 
    
        saveDir.mkdirs();

        if (!savePath.exists()) {
            try {
                savePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(savePath))) {
            mapToSave.write(bw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
