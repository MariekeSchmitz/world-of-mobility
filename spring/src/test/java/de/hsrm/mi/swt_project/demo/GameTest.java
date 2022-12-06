package de.hsrm.mi.swt_project.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt_project.demo.instancehandling.EditorInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.GameInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.GameMap;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;
import de.hsrm.mi.swt_project.demo.instancehandling.Instance;

@SpringBootTest
@AutoConfigureMockMvc
public class GameTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private InstanceHandler instanceHandler;

    @BeforeEach
    public void setUp() {
        instanceHandler.createGameInstance("Map1", "Game1");
        instanceHandler.createGameInstance("Map2", "Game2");
        instanceHandler.createEditorInstance("Map3");
        instanceHandler.createGameInstance("Map4", "Game3");
    }

    @Test
    public void post_instancelist_good() throws Exception {
        int amountGameItems = instanceHandler.getGameInstances().size();

        mockMvc.perform(
            post("/api/game/instancelist")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.instancelist", hasSize(amountGameItems)));
        //.andExpect(jsonPath("$.angebotid", is(a.getId()), Long.class))
        //.andExpect(jsonPath("$.beschreibung", is(a.getBeschreibung())));
        
    }
}
