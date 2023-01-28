package de.hsrm.mi.swt_project.demo.objecthandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * TrafficLightSingleton manages a TrafficLightState, which can distribute the state through all instances.
 * 
 * @author Finn Schindel    
 */
public final class TrafficLightSingleTon {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static TrafficLightSingleTon instance;

    private TrafficLightState trafficLightState = TrafficLightState.NORTHSOUTH;

    /**
     * Private constructor to prevent other instance creation 
     */
    private TrafficLightSingleTon(){

    }

    /**
     * Creates an instance if it is not created yet
     * @return the instance from a TrafficLightSingleton
     */
    public static TrafficLightSingleTon getInstance(){
        if (instance == null){
            instance = new TrafficLightSingleTon();
        }
        return instance;
    }

    /**
     * Calls the next() method of TrafficLightState and returns the state
     * @return trafficlightState
     */
    public TrafficLightState next(){
        trafficLightState = trafficLightState.next();
        return trafficLightState;
    }

    /**
     * @return the TrafficlightState
     */
    public TrafficLightState getTrafficLightState(){
        return trafficLightState;
    }
}
