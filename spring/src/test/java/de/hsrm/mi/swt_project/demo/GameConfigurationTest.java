package de.hsrm.mi.swt_project.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;


/**
 * Tests if a gameconfiguration is validated correctly.
 * 
 * @author Marieke Schmitz
 */

@SpringBootTest
class GameConfigurationTest {
    @Autowired
    private InstanceHandler instanceHandler;

    @Test
    void validationGameNameCorrect() {

        instanceHandler.createGameInstance(null, "Instance1");
        instanceHandler.createGameInstance(null, "Instance2");
        instanceHandler.createGameInstance(null, "Instance3");
        instanceHandler.createGameInstance(null, "Instance4");

        assertFalse(instanceHandler.checkSessionNameAvailable("Instance1"));
        assertFalse(instanceHandler.checkSessionNameAvailable("Instance4"));
        assertTrue(instanceHandler.checkSessionNameAvailable("Instance10"));

    }

}
