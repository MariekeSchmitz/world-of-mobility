package de.hsrm.mi.swt_project.demo.api.editor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swt_project.demo.messaging.GetMapUpdateDTO;
import de.hsrm.mi.swt_project.demo.messaging.ServerMessageDTO;

@RestController
@RequestMapping("/api")
public class EditorRestController {

    @Autowired
    private SimpMessagingTemplate messaging;
    
    Logger logger = LoggerFactory.getLogger(EditorRestController.class);

    @Autowired
    public EditorRestController() {
        
    }

    /**
     * Post for Mapupdates
     * @param getMapUpdateDTO
     * @author Felix Ruf, Finn Schindel
     */
    @PostMapping(value = "/GetMapUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void post_MapUpdate(@RequestBody GetMapUpdateDTO getMapUpdateDTO) {
        logger.info("Send Info: GetMapUpdateDTO = {}", getMapUpdateDTO.toString());
    }

    /**
     * Post for Global Server Messages
     * @param newServerMsgDTO
     * @author Felix Ruf, Finn Schindel
     */
    @PostMapping(value="/ServerMessage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void post_ServerMessage(@RequestBody ServerMessageDTO newServerMsgDTO){
        long now = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(now);
        String s = new SimpleDateFormat("HH:mm").format(currentTime);
        String output = s + ": " + newServerMsgDTO.txt();
        messaging.convertAndSend("/topic/ServerMessage", new ServerMessageDTO(newServerMsgDTO.usrId(), output));
    }
}
