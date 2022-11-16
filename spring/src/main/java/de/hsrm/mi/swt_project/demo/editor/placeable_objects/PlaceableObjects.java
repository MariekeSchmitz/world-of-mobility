package de.hsrm.mi.swt_project.demo.editor.placeable_objects;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;

public class PlaceableObjects implements Placeable{

    private PlaceableObjectType type;

    @Override
    public void place(Tile tile) {
        
        tile.addPlaceable(this);
        
    }


    
}
