package de.hsrm.mi.swt_project.demo.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.LinkedList;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import de.hsrm.mi.swt_project.demo.usermanagement.UserList;
import de.hsrm.mi.swt_project.demo.usermanagement.UserServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserServiceImpl userservice;

        @Autowired
        private UserList userlist;

        @AfterEach
        void cleanup() throws Exception {
                userlist.setUserList(new LinkedList<String>());
        }

        @Test
        void loginValid() throws Exception {
                JSONObject body = new JSONObject();
                body.put("name", "MarieTom");

                mockMvc.perform(
                                post("/api/user/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk());

                assertFalse(userlist.getUserList().isEmpty());
        }

        @Test
        void usernameTooShort() throws Exception {
                JSONObject body = new JSONObject();
                body.put("name", "a");

                mockMvc.perform(
                                post("/api/user/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isForbidden());

                assertTrue(userlist.getUserList().isEmpty());
        }

        @Test
        void usernameNotUnique() throws Exception {
                JSONObject body = new JSONObject();
                body.put("name", "MarieTom");

                mockMvc.perform(
                                post("/api/user/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk());

                mockMvc.perform(
                                post("/api/user/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isForbidden());
                assertTrue(userlist.getUserList().size() == 1);
        }

        @Test
        void logoutValid() throws Exception {
                JSONObject body = new JSONObject();
                body.put("name", "MarieTom");

                mockMvc.perform(
                                post("/api/user/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(body.toString()))
                                .andExpect(status().isOk());

                mockMvc.perform(
                                delete("/api/user/logout?username=MarieTom"));

                assertTrue(userlist.getUserList().isEmpty());
        }

}
