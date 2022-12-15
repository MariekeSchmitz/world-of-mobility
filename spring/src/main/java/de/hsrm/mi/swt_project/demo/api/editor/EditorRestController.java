package de.hsrm.mi.swt_project.demo.api.editor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.swt_project.demo.instancehandling.EditorInstance;
import de.hsrm.mi.swt_project.demo.instancehandling.Instance;
import de.hsrm.mi.swt_project.demo.instancehandling.InstanceHandler;
import de.hsrm.mi.swt_project.demo.instancehandling.UpdateloopService;
import de.hsrm.mi.swt_project.demo.messaging.GetListInstanceDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetMapUpdateDTO;
import de.hsrm.mi.swt_project.demo.messaging.JoinEditorDTO;
import de.hsrm.mi.swt_project.demo.messaging.GetPlaceableObjectUpdateDTO;
import de.hsrm.mi.swt_project.demo.messaging.SendMapDTO;
import de.hsrm.mi.swt_project.demo.messaging.ServerMessageDTO;
import de.hsrm.mi.swt_project.demo.messaging.ValidationDTO;

@RestController
@RequestMapping("/api/editor")
public class EditorRestController {

    @Autowired
    private SimpMessagingTemplate messaging;

    @Autowired
    private InstanceHandler instanceHandler;

    @Autowired
    private UpdateloopService loopService;

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
    @PostMapping(value = "/mapupdate/{editorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postMapUpdate(@RequestBody GetMapUpdateDTO getMapUpdateDTO, @PathVariable int editorId) {
        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(editorId);

        editorInstance.editMap(getMapUpdateDTO.xPos(), getMapUpdateDTO.yPos(), getMapUpdateDTO.control(),
                getMapUpdateDTO.type());
        loopService.publishInstanceState(editorInstance);
    }

    @PostMapping(value = "/placeableObjectUpdate/{editorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationDTO postPlaceableObjectUpdate(@RequestBody GetPlaceableObjectUpdateDTO getPlaceableObjectUpdateDTO, @PathVariable int editorId) {

        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(editorId);
        Boolean placed = editorInstance.editPlaceablesOnMap(getPlaceableObjectUpdateDTO.xPos(), getPlaceableObjectUpdateDTO.yPos(), getPlaceableObjectUpdateDTO.control(), getPlaceableObjectUpdateDTO.type());

        return new ValidationDTO(placed);
    }

    /**
     * Post for getting a specific map
     * 
     * @param getMapDTO
     */
    @PostMapping(value = "/getmap", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SendMapDTO postGetMap(@RequestBody GetMapDTO getMapDTO) {
        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(getMapDTO.mapId());
        return SendMapDTO.from(editorInstance.getMap());
    }

    /**
     * Post for Mapupdates from Editor
     * 
     * @param getMapUpdateDTO
     * @author Timothy Doukhin
     */
    @PostMapping(value = "/mapupdate/editor", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postMapUpdateEditor(@RequestParam Long editorId, @RequestBody GetMapUpdateDTO getMapUpdateDTO) {
        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(editorId);
        editorInstance.editMap(getMapUpdateDTO.xPos(), getMapUpdateDTO.yPos(), getMapUpdateDTO.control(),
                getMapUpdateDTO.type());
        loopService.publishInstanceState(editorInstance);
    }

    /**
     * Post for getting the map from Editor Instance
     * 
     * @param getMapDTO
     * @author Timothy Doukhin
     */
    @GetMapping(value = "/getmap/editor")
    public SendMapDTO getMapEditor(@RequestParam Long editorId) {
        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(editorId);
        return SendMapDTO.from(editorInstance.getMap());
    }

    /**
     * Post for Map Save
     * 
     * @param saveMapDTO
     * @author Felix Ruf, Finn Schindel
     */
    @PostMapping(value = "/savemap", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postMapSave(@RequestBody GetMapDTO getMapDTO) {
        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(getMapDTO.mapId());
        editorInstance.saveMap(editorInstance.getMap().getName());
    }

    /**
     * Post for Getting EditorInstanceList
     * 
     * @param getListInstanceDTO
     * @author Finn Schindel, Astrid Klemmer
     */
    @PostMapping(value = "/instancelist")
    public GetListInstanceDTO postEditorList() {
        // logger.info("Post Request for List form all EditorList");
        List<Instance> editorlist = instanceHandler.getEditorInstances();
        return GetListInstanceDTO.from(editorlist);
    }

    /**
     * Post for Global Server Messages
     * 
     * @param newServerMsgDTO
     * @author Felix Ruf, Finn Schindel
     */
    @PostMapping(value = "/servermessage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postServerMessage(@RequestBody ServerMessageDTO newServerMsgDTO) {
        long now = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(now);
        String s = new SimpleDateFormat("HH:mm").format(currentTime);
        String output = s + ": " + newServerMsgDTO.txt();
        messaging.convertAndSend("/topic/servermessage", new ServerMessageDTO(newServerMsgDTO.usrId(), output));
    }

    /**
     * Post for a new world instance if name is unique
     * 
     * @param newWorldDTO
     * @return id, error
     * @author Marie Bohnert, Tom Gouthier, Victoria Thee
     */
    @PostMapping("/createNewWorld")
    public SendNewWorldDTO postNewWorld(@RequestBody GetNewWorldDTO newWorldDTO) {
        String name = newWorldDTO.name();

        if (instanceHandler.checkWorldNameAvailable(name)) {
            long id = instanceHandler.createEditorInstance(name);
            return SendNewWorldDTO.from(id, "");
        } else {
            return SendNewWorldDTO.from(-1, "Name not unique.");
        }

    }

    /**
     * Post creates world instance of given map name
     * 
     * @param newWorldDTO
     * @return id
     * @author Astrid Klemmer, Finn Schindel
     */
    @PostMapping("/createWorldFromMap")
    public SendNewWorldDTO postWorldFromMap(@RequestBody GetNewWorldDTO newWorldDTO) {
        
        String name = newWorldDTO.name();
        long id = instanceHandler.createEditorInstance(name);
        
        return SendNewWorldDTO.from(id, "");

    }

    /**
     * Post for adding a user to a editor instance
     * @param joinEditorRequest Dto with name of new joining user
     * @param id editor instance that user is joining
     * @author Astrid Klemmer
     */
    @PostMapping(value="/{id}/join-editor", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void joinGame(@RequestBody JoinEditorDTO joinEditorRequest , @PathVariable long id) {
        instanceHandler.getEditorInstanceById(id).addUser(joinEditorRequest.user());
    }

    /**
     * Post for removing a user from a editor instance
     * @param leaveEditorRequest Dto with name of leaving user
     * @param id editor instance that user is joining
     * @author Astrid Klemmer
     */
    @PostMapping(value="/{id}/leave-editor", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void leaveGame(@RequestBody JoinEditorDTO leaveEditorRequest , @PathVariable long id) {
        instanceHandler.getEditorInstanceById(id).removeUser(leaveEditorRequest.user());
    }

}
