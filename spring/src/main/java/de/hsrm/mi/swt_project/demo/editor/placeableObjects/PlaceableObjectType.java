package de.hsrm.mi.swt_project.demo.editor.placeableObjects;

public enum PlaceableObjectType {
    
    TREE() {
        @Override
        public PlaceableObject createPlaceableObject() {
            Tree tree = new Tree();
            return tree;
        }
    };

    public abstract PlaceableObject createPlaceableObject();
}
