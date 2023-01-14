package de.hsrm.mi.swt_project.demo;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swt_project.demo.instancehandling.EditorInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.GameInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.GameMap;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;

@SpringBootTest
@AutoConfigureMockMvc
class InstanceHandlerTest {

    @Autowired
    private InstanceHandler instanceHandler;

    EditorInstance editorInstance;

    long editorId;

    Logger logger;

    @BeforeEach
    void setUp() {
        logger = LoggerFactory.getLogger(getClass());
        editorId = instanceHandler.createEditorInstance("");
        editorInstance = instanceHandler.getEditorInstanceById(editorId);
    }

    @Test
    void woBinIch() throws Exception {
        logger.info("Current path: {}", Paths.get("").toAbsolutePath().toString());
    }

    @Test
    void saveloadMapTest() throws Exception {
        editorInstance.saveMap("testSaveLoad");
        long id = instanceHandler.createGameInstance("testSaveLoad", "testSession");
        logger.info("ID of instance is: {}", id);
        assertNotEquals(id, -1, "Map has been found");

        GameInstance gameInstance = instanceHandler.getGameInstanceById(id);
        GameMap gameMap = gameInstance.getMap();
        assertTrue(gameMap != null, "GameMap is not null");

    }

}