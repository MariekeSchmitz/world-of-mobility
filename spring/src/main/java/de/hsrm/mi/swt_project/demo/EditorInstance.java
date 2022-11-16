package de.hsrm.mi.swt_project.demo;

import java.util.ArrayList;
import java.util.List;

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
    public void editMap(float xPos, float yPos, EditorControls control, Tile tiletype) {
        // TODO implement function
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
}
