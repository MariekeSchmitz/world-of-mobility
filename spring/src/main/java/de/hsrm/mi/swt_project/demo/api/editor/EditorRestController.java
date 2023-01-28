package de.hsrm.mi.swt_project.demo.api.editor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import de.hsrm.mi.swt_project.demo.instancehandling.UpdateloopInstanceInfo;
import de.hsrm.mi.swt_project.demo.instancehandling.NoNpcExistsOnCoordinates;
import de.hsrm.mi.swt_project.demo.instancehandling.NpcNotPlaceableException;
import de.hsrm.mi.swt_project.demo.instancehandling.ScriptNotValidException;
import de.hsrm.mi.swt_project.demo.instancehandling.UpdateloopService;
import de.hsrm.mi.swt_project.demo.messaging.EditorUserListDTO;
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

    @Autowired
    private UpdateloopInstanceInfo loopInstanceInfo;

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
    public ValidationDTO postPlaceableObjectUpdate(@RequestBody GetPlaceableObjectUpdateDTO getPlaceableObjectUpdateDTO,
            @PathVariable int editorId) {

        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(editorId);
        boolean placed = editorInstance.editPlaceablesOnMap(getPlaceableObjectUpdateDTO.xPos(),
                getPlaceableObjectUpdateDTO.yPos(), getPlaceableObjectUpdateDTO.control(),
                getPlaceableObjectUpdateDTO.type());
        if (placed) {
            loopService.publishInstanceState(editorInstance);
        }
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
    @GetMapping(value = "/instancelist")
    public GetListInstanceDTO postEditorList() {
        List<Instance> editorlist = instanceHandler.getEditorInstances();
        logger.info("Post-Req instancelist - all EditorInstances {}", editorlist);
        return GetListInstanceDTO.from(editorlist);
    }


    /**
     * Post for Global Server Messages
     * 
     * @param newServerMsgDTO
     * @author Felix Ruf, Finn Schindel
     */
    @PostMapping(value = "/servermessage/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postServerMessage(@RequestBody ServerMessageDTO newServerMsgDTO, @PathVariable long id) {
        long now = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(now);
        String s = new SimpleDateFormat("HH:mm").format(currentTime);
        String output = newServerMsgDTO.txt() + " " + s;
        String dest = "/topic/editor/servermessage/"+id;
        messaging.convertAndSend(dest, new ServerMessageDTO(newServerMsgDTO.usrId(), output));
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
        logger.info("Post-Req createWorldFromMap - create new Instance {}", name);
        long id = instanceHandler.createEditorInstance(name);
        return SendNewWorldDTO.from(id, "");
    }

    /**
     * Post for adding a user to a editor instance
     * 
     * @param joinEditorRequest Dto with name of new joining user
     * @param id                editor instance that user is joining
     * @author Astrid Klemmer
     */
    @PostMapping(value = "/{id}/join-editor", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void joinGame(@RequestBody JoinEditorDTO joinEditorRequest, @PathVariable long id) {
        String user = joinEditorRequest.user();
        logger.info("Post-Req join-editor - add user {}", user);
        instanceHandler.getEditorInstanceById(id).addUser(user);
        loopInstanceInfo.publishInstanceInfoState(instanceHandler.getEditorInstanceById(id), "CREATE");
    }

    /**
     * Post for removing a user from a editor instance
     * 
     * @param leaveEditorRequest Dto with name of leaving user
     * @param id                 editor instance that user is joining
     * @author Astrid Klemmer
     */
    @PostMapping(value = "/{id}/leave-editor", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void leaveGame(@RequestBody JoinEditorDTO leaveEditorRequest, @PathVariable long id) {
        String user = leaveEditorRequest.user();
        logger.info("Post-Req leave-editor - delete user {}", user);
        instanceHandler.getEditorInstanceById(id).removeUser(user);
        loopInstanceInfo.publishInstanceInfoState(instanceHandler.getEditorInstanceById(id), "CREATE");
    }

    /**
     * Get for getting userlist from a editor instance
     * 
     * @param id editor instance that user is joining
     * @return EditorUserList DTO with all users
     * @author Astrid Klemmer & Marieke Schmitz
     */
    @GetMapping(value = "/userlist/{id}")
    public EditorUserListDTO userlistEditor(@PathVariable long id) {
        List<String> userlist = instanceHandler.getEditorInstanceById(id).getUsers();
        return EditorUserListDTO.from(userlist);
    }

    /**
     * POST Endpoint to place npcs
     * 
     * @param id  editor instance id
     * @param npc DTO with data needed to place a npc on a certain location
     * @author Marie Bohnert, Tom Gouthier
     */
    @PostMapping("/{id}/placeNpc")
    public void placeNpc(@PathVariable long id, @RequestBody PlaceNpcDTO npc) {
        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(id);
        try {
            editorInstance.placeNPC(npc.x(), npc.y(), npc.type());
            loopService.publishInstanceState(editorInstance);

        } catch (NpcNotPlaceableException error) {
            throw error;
        }

    }

    /**
     * DELETE Endpoint to delete npcs
     * 
     * @param id  editor instance id
     * @param npc DTO with data needed to remove a npc on a certain location
     * @author Marie Bohnert, Tom Gouthier
     */
    @DeleteMapping("/{id}/removeNpc")
    public void removeNpc(@PathVariable long id, @RequestBody RemoveNpcDTO npc) {

        EditorInstance editorInstance = instanceHandler.getEditorInstanceById(id);
        try {
            editorInstance.deleteNPC(npc.x(), npc.y());
            loopService.publishInstanceState(editorInstance);
        } catch (NoNpcExistsOnCoordinates e) {
            throw e;
        }

    }

    /**
     * 
     * 
     * @param id        editor instance identifier
     * @param scriptDTO sent from client
     * @author Tom Gouthier, Marie Bohnert
     */
    @PostMapping("/{id}/loadScript")
    public void postNPCScript(@PathVariable long id, @RequestBody GetScriptDTO scriptDTO) {

        EditorInstance instance = instanceHandler.getEditorInstanceById(id);
        try {
            instance.addScriptToNpc(scriptDTO.x(), scriptDTO.y(), scriptDTO.script());
        } catch (NoNpcExistsOnCoordinates | ScriptNotValidException e) {
            throw e;
        }
    }

}
