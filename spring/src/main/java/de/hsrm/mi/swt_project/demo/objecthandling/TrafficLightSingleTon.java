package de.hsrm.mi.swt_project.demo.objecthandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TrafficLightSingleTon {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static TrafficLightSingleTon instance;

    private TrafficLightState trafficLightState = TrafficLightState.NORTHSOUTH;

    private TrafficLightSingleTon(){

    }

    public static TrafficLightSingleTon getInstance(){
        if (instance == null){
            instance = new TrafficLightSingleTon();
        }
        return instance;
    }

    public TrafficLightState next(){
        trafficLightState = trafficLightState.next();
        return trafficLightState;
    }

    public TrafficLightState getTrafficLightState(){
        return trafficLightState;
    }
}
