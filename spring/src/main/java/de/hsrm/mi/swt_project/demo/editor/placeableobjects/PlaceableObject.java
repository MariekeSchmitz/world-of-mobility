package de.hsrm.mi.swt_project.demo.editor.placeableobjects;

public class PlaceableObject implements Placeable {

    protected PlaceableObjectType type;

    public PlaceableObjectType getType() {
        return type;
    }

    public void setType(PlaceableObjectType type) {
        this.type = type;
    }

}
