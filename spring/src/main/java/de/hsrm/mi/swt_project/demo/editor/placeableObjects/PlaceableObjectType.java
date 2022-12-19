package de.hsrm.mi.swt_project.demo.editor.placeableObjects;

public enum PlaceableObjectType {
    
    TREE() {
        @Override
        public PlaceableObject createPlaceableObject() {
            PlaceableObject tree = new Tree();
            tree.type = this;
            return tree;
        }
    },

    NONE() {
        @Override
        public PlaceableObject createPlaceableObject() {
            PlaceableObject none = new PlaceableObject();
            none.type = this;
            return none;
        }
    };

    public abstract PlaceableObject createPlaceableObject();
}
