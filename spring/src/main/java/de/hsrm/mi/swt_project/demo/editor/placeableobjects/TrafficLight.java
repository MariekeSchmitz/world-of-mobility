package de.hsrm.mi.swt_project.demo.editor.placeableobjects;

import de.hsrm.mi.swt_project.demo.objecthandling.TrafficLightSingleTon;
import de.hsrm.mi.swt_project.demo.objecthandling.TrafficLightState;

/**
 * Object that represents a traffic light
 * @author Astrid Klemmer
 */
public class TrafficLight extends PlaceableObject{

    /**
     * Enum that is used to map weirdly named
     * TrafficLightState in human readable
     * state
     */
    public enum ReadableTrafficLightState {
        HORIZONTAL_GREEN,
        VERTICAL_GREEN,
        YELLOW
    } 

    public TrafficLight() {
        this.type = PlaceableObjectType.TRAFFIC_LIGHT;
    }

    /**
     * Gets state of the traffic light.
     * 
     * @return State of the traffic light. 
     *         Either "HORIZONTAL_GREEN", 
     *         "VERTICAL_GREEN" or "YELLOW"
     */
    public ReadableTrafficLightState getState() {

        TrafficLightState state = TrafficLightSingleTon.getInstance().getTrafficLightState();

        if (state.equals(TrafficLightState.EASTWEST)) {
            return ReadableTrafficLightState.HORIZONTAL_GREEN;
        }

        if (state.equals(TrafficLightState.NORTHSOUTH)) {
            return ReadableTrafficLightState.VERTICAL_GREEN;
        }

        return ReadableTrafficLightState.YELLOW;
    }

}
