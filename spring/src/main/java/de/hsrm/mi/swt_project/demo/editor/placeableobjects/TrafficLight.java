package de.hsrm.mi.swt_project.demo.editor.placeableobjects;

import de.hsrm.mi.swt_project.demo.controls.Orientation;
import de.hsrm.mi.swt_project.demo.objecthandling.TrafficLightSingleTon;
import de.hsrm.mi.swt_project.demo.objecthandling.TrafficLightState;

/**
 * Object that represents a traffic light
 * @author Astrid Klemmer
 */
public class TrafficLight extends PlaceableObject{

    private TrafficLightSingleTon trafficLightSingleTon = TrafficLightSingleTon.getInstance();

    public TrafficLight() {
        this.type = PlaceableObjectType.TRAFFIC_LIGHT;
    }

    public boolean isTrafficLightGreen(Orientation orientation){
        TrafficLightState trafficLightState = trafficLightSingleTon.getTrafficLightState();
        return ((trafficLightState.equals(TrafficLightState.NORTHSOUTH) && (orientation.equals(Orientation.NORTH) || orientation.equals(Orientation.SOUTH))) || (trafficLightState.equals(TrafficLightState.EASTWEST) && (orientation.equals(Orientation.EAST) || orientation.equals(Orientation.WEST))));
    }   
}
