package de.hsrm.mi.swt_project.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    long gameId;

    @BeforeEach
    void setUp() {
        gameId = instanceHandler.createGameInstance(null, "Game1", 16, false);
    }

    @Test
    void getInstancelistGood() throws Exception {
        int amountGameItems = instanceHandler.getGameInstances().size();

        mockMvc.perform(
            get("/api/game/instancelist")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.instancelist", hasSize(amountGameItems)));
        //.andExpect(jsonPath("$.angebotid", is(a.getId()), Long.class))
        //.andExpect(jsonPath("$.beschreibung", is(a.getBeschreibung())));
        
    }

    @Test
    void postLeaveEditorGood() throws Exception {
        
        JSONObject body = new JSONObject();
        body.put("name", "John");
        body.put("type", "");
        body.put("xPos", 0);
        body.put("yPos", 0);

        mockMvc.perform(
            post("/api/game/"+gameId+"/leave-game")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body.toString())
        ).andExpect(status().isOk());
    }


}
