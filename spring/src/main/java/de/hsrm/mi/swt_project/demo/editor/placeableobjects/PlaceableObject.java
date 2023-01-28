package de.hsrm.mi.swt_project.demo.editor.placeableobjects;

public class PlaceableObject implements Placeable {

    protected PlaceableObjectType type;

    @Override
    public PlaceableObjectType getType() {
        return type;
    }

    @Override
    public void setType(PlaceableObjectType type) {
        this.type = type;
    }

}
