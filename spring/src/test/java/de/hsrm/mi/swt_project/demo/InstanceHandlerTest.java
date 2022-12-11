package de.hsrm.mi.swt_project.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    void setUp() {
        editorId = instanceHandler.createEditorInstance("");
        editorInstance = instanceHandler.getEditorInstanceById(editorId);
    }

    @Test
    void saveload_map_test() throws Exception {
        editorInstance.saveMap("testSaveLoad");
        long id = instanceHandler.createGameInstance("testSaveLoad", "testSession");
        assertTrue(id != -1, "Map has been found!");

        GameInstance gameInstance = instanceHandler.getGameInstanceById(id);
        GameMap gameMap = gameInstance.getMap();
        System.out.println(gameMap);
        assertTrue(gameMap != null, "GameMap is not null");

    }

}