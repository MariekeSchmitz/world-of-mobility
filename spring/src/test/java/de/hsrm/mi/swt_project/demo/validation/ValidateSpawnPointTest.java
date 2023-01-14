package de.hsrm.mi.swt_project.demo.validation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.hsrm.mi.swt_project.demo.instancehandling.GameInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidateSpawnPointTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InstanceHandler instanceHandler;

    GameInstance gameInstance;

    long gameId;

    @BeforeEach
    void setUp() {
        gameId = instanceHandler.createGameInstance("test", "validationTest");
    }

    @Test
    void getSpawnpointValidationGood() throws Exception {

        String requestParams = "?moveableObject=CAR&xPos=0&yPos=0";

        mockMvc.perform(
            get("/api/game/" + gameId + "/validate-spawnpoint" + requestParams)
            .accept(MediaType.TEXT_PLAIN_VALUE)
            ).andDo(print())
            .andExpect(status().isOk());
        
    }
}
