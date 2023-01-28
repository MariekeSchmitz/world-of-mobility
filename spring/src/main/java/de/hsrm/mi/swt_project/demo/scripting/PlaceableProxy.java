package de.hsrm.mi.swt_project.demo.scripting;

import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Placeable;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.PlaceableObjectType;

/**
 * This class restricts access to the Tile class,
 * so that users are able to access but not change
 * the tile properties.
 * 
 * @author Sascha Scheid
 * @author Finn Schindel
 */
public class PlaceableProxy {
    
    protected Placeable placeable;

    /**
     * Creates a new PlaceableProxy.
     * 
     * @param placeable Placeable to restrict access to.
     */
    public PlaceableProxy(Placeable placeable) {
        this.placeable = placeable;
    } 

    /**
     * Gets type of the placeable object.
     * 
     * @return Type of the placeable object.
     */
    public PlaceableObjectType getType() {
        if(placeable == null){
            return null;
        }
        return this.placeable.getType();
    }

}
