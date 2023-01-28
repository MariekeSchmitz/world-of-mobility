package de.hsrm.mi.swt_project.demo.editor.tiles;

import de.hsrm.mi.swt_project.demo.controls.Direction;
import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.controls.Turnable;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Placeable;

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

    public void deletePlaceable() {
        this.placedObject = null;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
        result = prime * result + ((placedObject == null) ? 0 : placedObject.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (orientation != other.orientation)
            return false;
        if (placedObject == null) {
            if (other.placedObject != null)
                return false;
        } else if (!placedObject.equals(other.placedObject))
            return false;
        if (type != other.type)
            return false;
        return true;
    }


    

}
