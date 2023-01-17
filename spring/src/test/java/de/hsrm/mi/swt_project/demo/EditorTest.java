package de.hsrm.mi.swt_project.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

        @AfterEach
        void cleanup() {
                instanceHandler.removeInstance(editorInstance);
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
        void get_instancelist_good() throws Exception {
                int amountEditorItems = instanceHandler.getEditorInstances().size();

                mockMvc.perform(
                                get("/api/editor/instancelist")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.instancelist", hasSize(amountEditorItems)));

        }

        @Test
        void post_placeNpc_good() throws Exception {
                JSONObject body = new JSONObject();
                body.put("x", 1);
                body.put("y", 2);
                body.put("type", "PIG");
                mockMvc.perform(post("/api/editor/" + editorId + "/placeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isOk());

                assertTrue(!editorInstance.getMap().getNpcs().isEmpty(), "npc was added");
                assertEquals(1.0f, editorInstance.getMap().getNpcs().get(0).getxPos(), "x post is correct");
                assertEquals(2.0f, editorInstance.getMap().getNpcs().get(0).getyPos(), "y pos is correct");
                assertEquals(editorInstance.getMap().getNpcs().get(0).getType(), MoveableType.PIG,
                                "type is correct");
        }

        @Test
        void post_placeNpc_samePlacement() throws Exception {
                JSONObject body = new JSONObject();
                body.put("x", 2);
                body.put("y", 3);
                body.put("type", "PIG");
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
        void post_placeNpc_ForbiddenTile() throws Exception {
                Tile tile = Tiletype.STREET_CURVE.createTile();
                if (editorInstance.getMap().getTiles()[3][2] != null) {
                        editorInstance.getMap().removeTile(2, 3);
                }
                editorInstance.getMap().addTile(tile, 2, 3);
                JSONObject body = new JSONObject();
                body.put("x", 2);
                body.put("y", 3);
                body.put("type", "SHEEP");
                mockMvc.perform(post("/api/editor/" + editorId + "/placeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isForbidden());

        }

        @Test
        void removeNpc_good() throws Exception {
                JSONObject body = new JSONObject();
                body.put("x", 2);
                body.put("y", 3);
                body.put("type", "SHEEP");
                mockMvc.perform(post("/api/editor/" + editorId + "/placeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isOk());
                assertTrue(!editorInstance.getMap().getNpcs().isEmpty(), "npc was added");
                body.remove("type");
                mockMvc.perform(delete("/api/editor/" + editorId + "/removeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isOk());
                assertTrue(editorInstance.getMap().getNpcs().isEmpty(), "npc was removed");

        }

        @Test
        void removeNpc_bad() throws Exception {
                JSONObject body = new JSONObject();
                body.put("x", 2);
                body.put("y", 3);
                mockMvc.perform(delete("/api/editor/" + editorId + "/removeNpc")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isForbidden());

        }

        @Test
        void postInvalidScript() throws Exception {
                JSONObject body = new JSONObject();
                editorInstance.getMap().addNpc(MoveableType.SHEEP.createMovable(2, 3));
                body.put("script", "import abc from xyz");
                body.put("x", 2);
                body.put("y", 3);
                mockMvc.perform(post("/api/editor/" + editorId + "/loadScript")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isForbidden());

        }

        @Test
        void postValidScript() throws Exception {
                JSONObject body = new JSONObject();
                editorInstance.getMap().addNpc(MoveableType.CAR.createMovable(2, 3));
                body.put("script", "hallo");
                body.put("x", 2);
                body.put("y", 3);
                mockMvc.perform(post("/api/editor/" + editorId + "/loadScript")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isOk());

        }

        @Test
        void postScriptforInvalidNpc() throws Exception {
                JSONObject body = new JSONObject();
                body.put("script", "hallo");
                body.put("x", 2);
                body.put("y", 3);
                mockMvc.perform(post("/api/editor/" + editorId + "/loadScript")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body.toString()))
                                .andExpect(status().isForbidden());

        }

        @Test
        void postCreateWorldFromMapGood() throws Exception {

                JSONObject body = new JSONObject();
                body.put("name", "roadMap");

                mockMvc.perform(
                                post("/api/editor/createWorldFromMap")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.error").value(""));
        }

        @Test
        void postJoinEditorGood() throws Exception {

                JSONObject body = new JSONObject();
                body.put("name", "John");

                mockMvc.perform(
                                post("/api/editor/" + editorId + "/join-editor")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk());
        }

        @Test
        void postLeaveEditorGood() throws Exception {

                JSONObject body = new JSONObject();
                body.put("name", "John");
                mockMvc.perform(
                                post("/api/editor/" + editorId + "/leave-editor")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk());
        }

}
