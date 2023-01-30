package de.hsrm.mi.swt_project.demo.editor.placeableobjects;

public enum PlaceableObjectType {
    
    TREE() {
        @Override
        public PlaceableObject createPlaceableObject() {
            PlaceableObject tree = new Tree();
            tree.type = this;
            return tree;
        }
    },
    
    PIG() {
        @Override
        public PlaceableObject createPlaceableObject() {
            PlaceableObject pig = new Pig();
            pig.type = this;
            return pig;
        }
    },
    
    SHEEP() {
        @Override
        public PlaceableObject createPlaceableObject() {
            PlaceableObject sheep = new Sheep();
            sheep.type = this;
            return sheep;
        }
    },
    
    GAS_STATION() {
        @Override
        public PlaceableObject createPlaceableObject() {
            PlaceableObject gasStation = new GasStation();
            gasStation.type = this;
            return gasStation;
        }
    },
    
    TRAFFIC_LIGHT() {
        @Override
        public PlaceableObject createPlaceableObject() {
            PlaceableObject trafficLight = new TrafficLight();
            trafficLight.type = this;
            return trafficLight;
        }
    };;

    public abstract PlaceableObject createPlaceableObject();
}
