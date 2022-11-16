package de.hsrm.mi.swt_project.demo.api.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swt_project.demo.messaging.TestDTO;

@RestController
@RequestMapping("/api")
public class EditorRestController {

    @Autowired
    private SimpMessagingTemplate messaging;
    
    Logger logger = LoggerFactory.getLogger(EditorRestController.class);

    @Autowired
    public EditorRestController() {
        
    }

    @GetMapping(value = "/editor/test", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getTestString() {
        logger.info("GET Request for '/api/editor/test'");

        return "Hallo Test";
    }
    
    @GetMapping("/ServerMessage")
    public void post_ServerMessage(){
        messaging.convertAndSend("/topic/ServerMessage", new TestDTO(1,2,3,4));
    }
}
