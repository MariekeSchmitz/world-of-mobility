package de.hsrm.mi.swt_project.demo.instancehandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swt_project.demo.messaging.SendGameUpdateDTO;
import de.hsrm.mi.swt_project.demo.messaging.SendMapDTO;

/**
 * This class provides methods for publishing
 * the state of an instance to the clients. 
 * 
 * Updated state will be sent to different topics
 * depending if instance is an editor instance or
 * a game instance.
 * 
 * Topics are
 * 
 * {@value #GAME_TOPIC} - Topic for game instances
 * {@value #EDITOR_TOPIC} - Topic for editor instances
 * 
 * @author Sascha Scheid
 */
@Service
public class UpdateloopService {

    public static final String GAME_TOPIC = "/topic/game";
    public static final String EDITOR_TOPIC = "/topic/editor";
    
    @Autowired
    private SimpMessagingTemplate messaging;

    /**
     * This method initiates sending the state of an instance
     * to the corresponding topic.
     * 
     * Will send the state to {@value #GAME_TOPIC} if instance is
     * a game instance or to {@value #EDITOR_TOPIC} if instance
     * is an editor instance.
     * 
     * @param instance Instance thats state will be sent to the topic.
     */
    public void publishInstanceState(Instance instance) {
        if (instance instanceof GameInstance gameInstance) {
            this.publishGameInstanceState(gameInstance);
        } else if (instance instanceof EditorInstance editorInstance) {
            this.publishEditorInstanceState(editorInstance);
        }
    }

    /**
     * This method initiates sends the state of an instance
     * to the {@value #GAME_TOPIC} topic.
     * 
     * @param instance GameInstance thats state will be sent to the topic.
     */
    protected void publishGameInstanceState(GameInstance instance) {

        final long id = instance.getId();
        
        final SendGameUpdateDTO dto = SendGameUpdateDTO.from(instance.getMoveableObjects());
        final String destination = String.format("%s/%d", GAME_TOPIC, id);
       
        messaging.convertAndSend(destination, dto);    
    }

    /**
     * This method initiates sends the state of an instance
     * to the {@value #EDITOR_TOPIC} topic.
     * 
     * @param instance EditorInstance thats state will be sent to the topic.
     */
    protected void publishEditorInstanceState(EditorInstance instance) {

        final long id = instance.getId();

        final SendMapDTO dto = SendMapDTO.from(instance.getMap());
        final String destination = String.format("%s/%d", EDITOR_TOPIC, id);

        messaging.convertAndSend(destination, dto);
    }

}
