package de.hsrm.mi.swt_project.demo.instancehandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * If scheduling is enabled, this class will periodically
 * trigger the instance handler to update the instances 
 * and publish the current state to the corresponding 
 * clients.
 * 
 * The rate is at which updates are triggered can be 
 * configured externally via updateloop.tickrate and
 * is measured in milliseconds.
 * 
 * If no external configuration is present, the
 * tickrate will fall back to the default rate
 * of 50 milliseconds.
 * 
 * @author Sascha Scheid 
 */
@Component
public class UpdateloopTask {
    
    @Autowired
    private InstanceHandler instanceHandler;

    @Scheduled(fixedRateString = "${updateloop.tickrate:50}")
    public void triggerUpdate() {
        instanceHandler.update();
    }

    @Scheduled(fixedRateString = "${skriptloop.tickrate:2000}")
    public void triggerScript(){
        instanceHandler.triggerScript();
    }

}
