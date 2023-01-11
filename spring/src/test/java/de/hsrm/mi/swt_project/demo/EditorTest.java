package de.hsrm.mi.swt_project.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.tools.ant.taskdefs.Delete;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.hsrm.mi.swt_project.demo.editor.tiles.Streetile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tile;
import de.hsrm.mi.swt_project.demo.editor.tiles.Tiletype;
import de.hsrm.mi.swt_project.demo.instancehandling.EditorInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;
import de.hsrm.mi.swt_project.demo.movables.MoveableType;

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
                editorId = instanceHandler.createEditorInstance("test2");
                editorInstance = instanceHandler.getEditorInstanceById(editorId);
        }

        @Test
        void postMapUpdateGood() throws Exception {
                JSONObject body = new JSONObject();
                body.put("control", "PLACE");
                body.put("type", "STREET_STRAIGHT");
                body.put("posX", 0);
                body.put("posY", 0);

                mockMvc.perform(
                                post("/api/editor/mapupdate/" + editorId)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk());
        }

        @Test
        void postGetMapGood() throws Exception {

                JSONObject body = new JSONObject();
                body.put("name", "test");
                body.put("mapId", editorId);

                mockMvc.perform(
                                post("/api/editor/getmap")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString())
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("tiles").exists())
                                .andExpect(jsonPath("npcs").exists());
        }

        @Test
        void postMapsaveGood() throws Exception {

                JSONObject body = new JSONObject();
                body.put("mapName", "test");
                body.put("mapId", editorId);

                mockMvc.perform(
                                post("/api/editor/savemap")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk());
        }

        @Test
        void postServerMessageGood() throws Exception {

                JSONObject body = new JSONObject();
                body.put("usrId", 1);
                body.put("txt", "Dies ist ein Test");

                mockMvc.perform(
                                post("/api/editor/servermessage")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk());
        }

        @Test
        void post_instancelist_good() throws Exception {
                int amountEditorItems = instanceHandler.getEditorInstances().size();

                mockMvc.perform(
                                post("/api/editor/instancelist")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.instancelist", hasSize(amountEditorItems)));

        }

        @Test
        void post_placeNpc_good() throws Exception {
                JSONObject body = new JSONObject();
                body.put("x", 1);
                body.put("y", 2);
                body.put("type", "PASSENGER");
                mockMvc.perform(post("/api/editor/" + editorId + "/placeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isOk());

                assertTrue(!editorInstance.getMap().getNpcs().isEmpty(), "npc was added");
                assertEquals(1.0f, editorInstance.getMap().getNpcs().get(0).getxPos(), "x post is correct");
                assertEquals(2.0f, editorInstance.getMap().getNpcs().get(0).getyPos(), "y pos is correct");
                assertEquals(editorInstance.getMap().getNpcs().get(0).getType(), MoveableType.PASSENGER,
                                "type is correct");
        }

        @Test
        void post_placeNpc_samePlacement() throws Exception {
                JSONObject body = new JSONObject();
                body.put("x", 2);
                body.put("y", 3);
                body.put("type", "PASSENGER");
                mockMvc.perform(post("/api/editor/" + editorId + "/placeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isOk());
                mockMvc.perform(post("/api/editor/" + editorId + "/placeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isForbidden());
                
        }

        @Test
        void post_placeNpc_ForbiddenTile() throws Exception{
                Tile tile = Tiletype.STREET_CURVE.createTile();       
                if (editorInstance.getMap().getTiles()[3][2]!=null) {
                        editorInstance.getMap().removeTile(2, 3);     
                }      
                editorInstance.getMap().addTile(tile, 2, 3);
                JSONObject body = new JSONObject();
                body.put("x", 2);
                body.put("y", 3);
                body.put("type", "PASSENGER");
                mockMvc.perform(post("/api/editor/" + editorId + "/placeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isForbidden());


        }

        @Test
        void removeNpc() throws Exception{
                JSONObject body = new JSONObject();
                body.put("x", 2);
                body.put("y", 3);
                body.put("type", "PASSENGER");
                mockMvc.perform(post("/api/editor/" + editorId + "/placeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isOk());
                assertTrue(!editorInstance.getMap().getNpcs().isEmpty(), "npc was added");
                mockMvc.perform(delete("/api/editor/" + editorId + "/removeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isOk());
                assertTrue(editorInstance.getMap().getNpcs().isEmpty(), "npc was removed");

                
        }

}
