package de.hsrm.mi.swt_project.demo.api.editor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swt_project.demo.instancehandling.EditorInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.Instance;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;
import de.hsrm.mi.swt_project.demo.messaging.GetListInstanceDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetMapUpdateDTO;
import de.hsrm.mi.swt_project.demo.messaging.SendMapDTO;
import de.hsrm.mi.swt_project.demo.messaging.ServerMessageDTO;

@RestController
@RequestMapping("/api/editor")
public class EditorRestController {

    @Autowired
    private SimpMessagingTemplate messaging;

    @Autowired
    private InstanceHandler instanceHandler;

    Logger logger = LoggerFactory.getLogger(EditorRestController.class);

    @Autowired
    public EditorRestController() {

    }

    /**
     * Post for Mapupdates
     * 
     * @param getMapUpdateDTO
     * @author Felix Ruf, Finn Schindel
     */
    @PostMapping(value = "/MapUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void post_MapUpdate(@RequestBody GetMapUpdateDTO getMapUpdateDTO) {
        logger.info("Post Request for Map Update: Received GetMapUpdateDTO = {}", getMapUpdateDTO.toString());

        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(0);

        // TODO add controlEnum
        editorInstance.editMap(getMapUpdateDTO.newXPos(), getMapUpdateDTO.newYPos(), null, getMapUpdateDTO.type());
    }

    @PostMapping(value = "/GetMap", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SendMapDTO post_GetMap(@RequestBody GetMapDTO getMapDTO) {
        logger.info("Post Request for Map: Received GetMapDTO = {}", getMapDTO.toString());

        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(getMapDTO.mapId());
        return SendMapDTO.from(editorInstance.getMap());
    }

    /**
     * Post for Map Save
     * 
     * @param saveMapDTO
     * @author Felix Ruf, Finn Schindel
     */
    @PostMapping(value = "/SaveMap", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void post_MapSave(@RequestBody GetMapDTO getMapDTO) {
        logger.info("Post Request for Map Save: Received SaveMapDTO = {}", getMapDTO.toString());
        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(getMapDTO.mapId());
        editorInstance.saveMap(getMapDTO.mapName());
    }

    /**
     * Post for Getting EditorInstanceList
     * @param getListInstanceDTO
     * @author Finn Schindel, Astrid Klemmer
     */
    @PostMapping(value = "/instancelist")
    public GetListInstanceDTO post_EditorList() {
        logger.info("Post Request for List form all EditorList");
        List<Instance> editorlist = instanceHandler.getEditorInstances();
        return GetListInstanceDTO.from(editorlist);
    }  

    /**
     * Post for Global Server Messages
     * 
     * @param newServerMsgDTO
     * @author Felix Ruf, Finn Schindel
     */
    @PostMapping(value = "/ServerMessage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void post_ServerMessage(@RequestBody ServerMessageDTO newServerMsgDTO) {
        long now = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(now);
        String s = new SimpleDateFormat("HH:mm").format(currentTime);
        String output = s + ": " + newServerMsgDTO.txt();
        messaging.convertAndSend("/topic/ServerMessage", new ServerMessageDTO(newServerMsgDTO.usrId(), output));
    }

    /**
     * Post for a new world instance
     * 
     * @param newWorldDTO
     * @return id, error
     * @author Marie Bohnert, Tom Gouthier, Victoria Thee
     */
    @PostMapping("/createNewWorld")
    public SendNewWorldDTO post_NewWorld(@RequestBody GetNewWorldDTO newWorldDTO) {
        String name = newWorldDTO.name();
        long id = instanceHandler.createEditorInstance(name);

        return SendNewWorldDTO.from(id, "");

    }

}
