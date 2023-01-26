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
    Logger logger = LoggerFactory.getLogger(getClass());

    private TrafficLightSingleTon trafficLightSingleTon = TrafficLightSingleTon.getInstance();

    @Scheduled(fixedDelayString = "${trafficLight.tickrate:7000}")
    public void switchTrafficLight() {
        trafficLightSingleTon.next();
        try {
            Thread.sleep(1000, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        trafficLightSingleTon.next();
    }

}
