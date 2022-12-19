package de.hsrm.mi.swt_project.demo.editor.tiles;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.controls.Turnable;
import de.hsrm.mi.swt_project.demo.editor.placeableObjects.Placeable;

 /** 
  * Classes that inherit this class are Tiles. A tile represents a single part of a Map.
     *
     * @author Tom Gouthier
     */
public abstract class Tile implements Turnable{
    
    protected Orientation orientation = Orientation.NORTH;
    protected Placeable placedObject;
    protected Tiletype type;
    
    /** adds a placeable Object on itself
     * @param placeable
     * @author Tom Gouthier
     */
    public void addPlaceable(Placeable placeable) {
        this.placedObject = placeable;
    }

    /**
     * Turns tile by 90° to left or right
     */
    @Override
    public void turn(Direction direction) {

        if (direction == Direction.LEFT) {
            this.orientation = orientation.prev().prev();
        } else {
            this.orientation = orientation.next().next();
        }
       
    }

    public Orientation getOrientation() {
        return orientation;
    }



    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }



    public Placeable getPlacedObject() {
        return placedObject;
    }

    public void setPlacedObject(Placeable placedObject) {
        this.placedObject = placedObject;
    }

    @Override
    public String toString() {
        return "Tile [orientation=" + orientation + ", placedObjects=" + placedObject + ", type=" + type + "]";
    }

    public Tiletype getType() {
        return this.type;
    }


    

}
