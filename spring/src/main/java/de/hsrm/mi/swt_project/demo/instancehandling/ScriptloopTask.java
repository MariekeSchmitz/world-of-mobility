package de.hsrm.mi.swt_project.demo.instancehandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * If scheduling is enabled, this class will periodically
 * trigger the instance handler to trigger execution of
 * of scripts. 
 * 
 * The rate is at which script execution is triggered can 
 * be configured externally via scriptloop.tickrate and
 * is measured in milliseconds.
 * 
 * If no external configuration is present, the
 * tickrate will fall back to the default rate
 * of 2000 milliseconds = 2 seconds.
 * 
 * @author Sascha Scheid
 * @author Finn Schindel
 */
@Component
public class ScriptloopTask {

    @Autowired
    private InstanceHandler instanceHandler;

    @Scheduled(fixedRateString = "${skriptloop.tickrate:2000}")
    public void triggerScriptExecution() {
        instanceHandler.triggerScript();
    }
    
}
