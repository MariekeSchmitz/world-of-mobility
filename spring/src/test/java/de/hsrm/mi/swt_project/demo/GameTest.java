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

import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;

@SpringBootTest
@AutoConfigureMockMvc
class GameTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private InstanceHandler instanceHandler;

    @BeforeEach
    void setUp() {
        instanceHandler.createGameInstance("Map1", "Game1", 16, false);
        instanceHandler.createGameInstance("Map2", "Game2", 16, false);
        instanceHandler.createEditorInstance("Map3");
        instanceHandler.createGameInstance("Map4", "Game3", 16, false);
    }

    @Test
    void postInstancelistGood() throws Exception {
        int amountGameItems = instanceHandler.getGameInstances().size();

        mockMvc.perform(
            post("/api/game/instancelist")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.instancelist", hasSize(amountGameItems)));
        //.andExpect(jsonPath("$.angebotid", is(a.getId()), Long.class))
        //.andExpect(jsonPath("$.beschreibung", is(a.getBeschreibung())));
        
    }


}
