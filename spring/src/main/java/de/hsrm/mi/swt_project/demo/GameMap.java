package de.hsrm.mi.swt_project.demo;

import java.util.List;

import de.hsrm.mi.swt_project.demo.movables.MoveableObject;

/**
 * Represents a 2d field of tiles and its npcs
 * 
@author Tom Gouthier
 */
public class GameMap {

    private Tile[][] tiles;
    private String name;
    private List <MoveableObject> npcs;

    
    /** adds a moveable Object to the map. e.g. a scripted car
     * @param moveable
     * @author Tom Gouthier
     */
    public void addNpc(MoveableObject moveable){
        this.npcs.add(moveable);
    }

    
    /** adds a Tile to the map at a certain position
     * @param tile
     * @param xPos
     * @param yPos
     * @author Tom Gouthier
     */
    public void addTile(Tile tile, int xPos, int yPos){
        this.tiles[yPos][xPos] = tile;
    }


    /**
     * 
     * @param xPos
     * @param yPos
     */
    public void removeTile(int xPos, int yPos) {
        this.tiles[yPos][xPos] = null; 
    }

    /** expands the map if needed
     *
     */
    private void expandMap(){

    }


    public Tile[][] getTiles() {
        return tiles;
    }


    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<MoveableObject> getNpcs() {
        return npcs;
    }


    public void setNpcs(List<MoveableObject> npcs) {
        this.npcs = npcs;
    }

    
    
}