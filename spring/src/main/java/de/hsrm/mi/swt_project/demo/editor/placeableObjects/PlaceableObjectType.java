package de.hsrm.mi.swt_project.demo.editor.placeableObjects;

public enum PlaceableObjectType {
    
    TREE() {
        @Override
        public PlaceableObject createPlaceableObject() {
            PlaceableObject tree = new Tree();
            tree.type = this;
            return tree;
        }
    };

    public abstract PlaceableObject createPlaceableObject();
}
