package de.hsrm.mi.swt_project.demo;

import java.util.List;


 /** Classes that inherit this class are Tiles. A tile represents a single part of a Map.
     *
     * @author Tom Gouthier
     */
public abstract class Tile implements Turnable{
    
    protected Orientation orientation;
    protected List <Placeable> placedObjects;
    // protected TileType type;
    
    /** adds a placeable Object on itself
     * @param placeable
     * @author Tom Gouthier
     */
    public void addPlaceable(Placeable placeable) {

    }


    
    @Override
    public void turn(Direction direction) {

    }


}
