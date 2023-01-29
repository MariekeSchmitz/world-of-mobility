package de.hsrm.mi.swt_project.demo.editor.placeableobjects;

/**
 * Objects that implement this interface can be placed on a Tile
 * @author Tom Gouthier
 */
public interface Placeable {

    /**
     * Gets type of the placeable object.
     * 
     * @return Type of the placeable object.
     */
    PlaceableObjectType getType();

    /**
     * Sets type of the placeable object.
     * 
     * @param type Type to set.
     */
    void setType(PlaceableObjectType type);
    
}
