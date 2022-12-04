package de.hsrm.mi.swt_project.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.instancehandling.GameMap;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;


/**
 * Tests if a gameconfiguration is validated correctly.
 * 
 * @author Marieke Schmitz
 */

@SpringBootTest
public class GameConfigurationTest {
    
    private InstanceHandler instanceHandler = new InstanceHandler();

    @Test
    protected void validationGameNameCorrect() {

        instanceHandler.createGameInstance("", "Instance1");
        instanceHandler.createGameInstance("", "Instance2");
        instanceHandler.createGameInstance("", "Instance3");
        instanceHandler.createGameInstance("", "Instance4");

        assertFalse(instanceHandler.checkSessionNameAvailable("Instance1"));
        assertFalse(instanceHandler.checkSessionNameAvailable("Instance4"));
        assertTrue(instanceHandler.checkSessionNameAvailable("Instance10"));

    }

}
