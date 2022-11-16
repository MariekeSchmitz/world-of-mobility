package de.hsrm.mi.swt_project.demo;

import java.util.List;


 /** 
  * Classes that inherit this class are Tiles. A tile represents a single part of a Map.
     *
     * @author Tom Gouthier
     */
public abstract class Tile implements Turnable{
    
    protected Orientation orientation;
    protected List <Placeable> placedObjects;
    protected Tiletype type;
    
    /** adds a placeable Object on itself
     * @param placeable
     * @author Tom Gouthier
     */
    public void addPlaceable(Placeable placeable) {
        this.placedObjects.add(placeable);
    }


    
    @Override
    public void turn(Direction direction) {

        if (direction == Direction.LEFT) {
            this.orientation = orientation.prev();
        } else {
            this.orientation = orientation.next();
        }
       
    }



    public Orientation getOrientation() {
        return orientation;
    }



    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }



    public List<Placeable> getPlacedObjects() {
        return placedObjects;
    }



    public void setPlacedObjects(List<Placeable> placedObjects) {
        this.placedObjects = placedObjects;
    }


}
