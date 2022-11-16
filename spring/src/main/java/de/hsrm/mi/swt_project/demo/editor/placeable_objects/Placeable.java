package de.hsrm.mi.swt_project.demo.editor;


/**
 * Objects that implement this interface can be placed on a Tile
@author Tom Gouthier
 */
public interface Placeable {
    
    /**
     * places itself on given tile
    @author Tom Gouthier
    @param tile That that should be placed on map
 */
    public void place(Tile tile);
}
