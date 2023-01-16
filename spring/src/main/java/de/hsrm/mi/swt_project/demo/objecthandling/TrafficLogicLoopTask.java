package de.hsrm.mi.swt_project.demo.objecthandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Task that handles switching between two possible TrafficLightStates on a configurable timer globally.
 *  @author Timothy Doukhin, Finn Schindel
 */
@Component
public class TrafficLogicLoopTask {
    private TrafficLightState trafficLightState = TrafficLightState.NORTHSOUTH;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(fixedDelayString = "${trafficLight.tickrate:7000}")
    public void switchTrafficLight() {
        trafficLightState = trafficLightState.next();
    }

    public TrafficLightState getTrafficLightState(){
        return trafficLightState;
    }
}
