package de.hsrm.mi.swt_project.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.hsrm.mi.swt_project.demo.instancehandling.EditorInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;

@SpringBootTest
@AutoConfigureMockMvc
public class EditorTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private InstanceHandler instanceHandler;

    EditorInstance editorInstance;

    @BeforeEach
    public void setUp() {
        instanceHandler.createEditorInstance("");
        
    }

    @Test
    public void post_map_update_good() throws Exception {

        JSONObject body = new JSONObject();
        body.put("mapName", "Test");
        body.put("mapId", "0");
        body.put("control", "PLACE");
        body.put("type", "STREET_STRAIGHT");


        mockMvc.perform(
            post("/api/editor/mapupdate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body.toString())
            ).andExpect(status().isOk());


    }

}
