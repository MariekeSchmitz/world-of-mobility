package de.hsrm.mi.swt_project.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
class EditorTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private InstanceHandler instanceHandler;

    EditorInstance editorInstance;

    long editorId;

    @BeforeEach
    void setUp() {
        editorId = instanceHandler.createEditorInstance("test");
        
    }

    @Test
    void post_map_update_good() throws Exception {

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

    @Test
    void post_getMap_good() throws Exception {

        JSONObject body = new JSONObject();
        body.put("mapName", "test");
        body.put("mapId", editorId);

        mockMvc.perform(
            post("/api/editor/getmap")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body.toString())
            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("tiles").exists())
            .andExpect(jsonPath("npcs").exists());
    }

    // TODO: This test does't work currently. NullPointerException on MapSave for missing mapSavePath. Maybe I just don't understand the @Value annotation in Instance 
    // @Test void post_mapsave_good() throws Exception {

    //     JSONObject body = new JSONObject();
    //     body.put("mapName", "test");
    //     body.put("mapId", editorId);

    //     mockMvc.perform(
    //         post("/api/editor/savemap")
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .content(body.toString())
    //     ).andExpect(status().isOk());
    // }

    @Test
    void post_servermessage_good() throws Exception {
        
        JSONObject body = new JSONObject();
        body.put("usrId", 1);
        body.put("txt", "Dies ist ein Test");

        mockMvc.perform(
            post("/api/editor/servermessage")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body.toString())
        ).andExpect(status().isOk());
    }

    @Test
    void post_instancelist_good() throws Exception {
        int amountEditorItems = instanceHandler.getEditorInstances().size();

        mockMvc.perform(
            post("/api/editor/instancelist")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.instancelist", hasSize(amountEditorItems)));

    }


}
