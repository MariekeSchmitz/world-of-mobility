package de.hsrm.mi.swt_project.demo.editor;

import ch.qos.logback.core.subst.Token.Type;

public class PlaceableObjects implements Placeable{

    private PlaceableObjectType type;

    @Override
    public void place(Tile tile) {
        
        tile.addPlaceable(this);
        
    }


    
}
