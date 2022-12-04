package de.hsrm.mi.swt_project.demo.instancehandling;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.movables.MoveableObject;
import de.hsrm.mi.swt_project.demo.util.ArrayHelpers;

/**
 * Represents a 2d field of tiles and its npcs
 * 
 * @author Tom Gouthier
 * @author Sascha Scheid
 */
public class GameMap {

    public static final int DEFAULT_SIZE = 8;

    private Tile[][] tiles = new Tile[DEFAULT_SIZE][DEFAULT_SIZE];
    private String name;
    private List<MoveableObject> npcs = new ArrayList<>();

    public GameMap() {
    }

    /** adds a moveable Object to the map. e.g. a scripted car
     * @param moveable
     * @author Tom Gouthier
     */
    public void addNpc(MoveableObject moveable){
        this.npcs.add(moveable);
    }
    
    /** 
     * Adds a Tile to the game map at a certain position.
     * 
     * If the tile is placed on the edge of the current map,
     * it is assumed, that there is not enough space for further building.
     * Therefore the game map size will be expanded with the currently placed 
     * tiles staying centered.
     * 
     * @param tile Tile to place
     * @param xPos x-position of the tile
     * @param yPos y-position of the tile
     * @author Tom Gouthier
     */
    public void addTile(Tile tile, int xPos, int yPos) {
        
        if (tile == null) {
            throw new IllegalArgumentException("tile is not allowed to be null.");
        }

        if (yPos < 0 || xPos < 0 || yPos >= this.tiles.length || xPos >= this.tiles[yPos].length) {
            throw new IllegalArgumentException("tile must be placed from position (0, 0) to (%d, %d), was (%d, %d)".formatted(this.tiles.length -1, this.tiles[0].length -1, xPos, yPos));
        }

        this.tiles[yPos][xPos] = tile;

        boolean placedOnLeftEdge   = (xPos == 0);
        boolean placedOnTopEdge    = (yPos == 0);
        boolean placedOnRightEdge  = (xPos == this.tiles[yPos].length - 1);
        boolean placedOnBottomEdge = (yPos == this.tiles.length - 1);

        if (placedOnLeftEdge || placedOnTopEdge || placedOnRightEdge || placedOnBottomEdge) {
            this.expandMap();
        }

    }

    /**
     * 
     * @param xPos
     * @param yPos
     */
    public void removeTile(int xPos, int yPos) {
        this.tiles[yPos][xPos] = null; 
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

    @Override
    public String toString() {
        String tileNames = "";
        for(Tile[] tArr : tiles) {
            for(Tile t : tArr) {
                if(t != null) {
                    tileNames += t.toString() + "; ";
                } else {
                    tileNames += "Null; ";
                }
            }
        }
        
        return "GameMap [tiles=" + tileNames + ", name=" + name + ", npcs=" + npcs + "]";
    }

    
    /**
     * Creates a new 2D tile array of double size and
     * centers values from current tile array inside the
     * created one.
     * 
     * Replaces current tile array with the new one. 
     */
    private void expandMap(){
        int size = this.tiles.length * 2;
        Tile[][] newTiles = new Tile[size][size];
        ArrayHelpers.transfer2D(this.tiles, newTiles);
        this.tiles = newTiles;
    }
}