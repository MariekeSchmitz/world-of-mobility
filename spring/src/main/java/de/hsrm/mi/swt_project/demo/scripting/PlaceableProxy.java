package de.hsrm.mi.swt_project.demo.scripting;

import de.hsrm.mi.swt_project.demo.editor.placeableobjects.Placeable;
import de.hsrm.mi.swt_project.demo.editor.placeableobjects.TrafficLight;

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
    public String getType() {
        if(placeable == null){
            return "";
        }
        return this.placeable.getType().name();
    }

    /**
     * Gets state of the placeable object.
     * 
     * @return State of the placeable object.
     */
    public String getState() {
        if (placeable instanceof TrafficLight trafficLight) {
            return trafficLight.getState().name();
        }
        return "";
    }

}
