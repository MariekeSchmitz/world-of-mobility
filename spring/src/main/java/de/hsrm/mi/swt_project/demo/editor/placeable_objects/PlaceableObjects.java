package de.hsrm.mi.swt_project.demo.editor.placeable_objects;

import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;

public class PlaceableObjects implements Placeable {

    private PlaceableObjectType type;

    public PlaceableObjects(PlaceableObjectType type) {
        this.type = type;
    }

    public PlaceableObjectType getType() {
        return type;
    }

    public void setType(PlaceableObjectType type) {
        this.type = type;
    }

    @Override
    public void place(Tile tile) {

        tile.addPlaceable(this);

    }

}
