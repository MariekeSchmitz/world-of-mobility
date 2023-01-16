package de.hsrm.mi.swt_project.demo.instancehandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swt_project.demo.messaging.GetInstanceInfoDTO;

@Service
public class UpdateloopInstanceInfo {
    
    public static final String GAME_TOPIC = "/topic/game/instanceInfo";
    public static final String EDITOR_TOPIC = "/topic/editor/instanceInfo";
    
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
    public void publishInstanceInfoState(Instance instance) {
        System.out.println("define if editor or game");
        if (instance instanceof GameInstance gameInstance) {
            this.publishGameInstanceInfoState(gameInstance);
        } else if (instance instanceof EditorInstance editorInstance) {
            System.out.println("is game");
            this.publishEditorInstanceInfoState(editorInstance);
        }
    }

    /**
     * This method initiates sends the state of an instance
     * to the {@value #GAME_TOPIC} topic.
     * 
     * @param instance GameInstance thats state will be sent to the topic.
     */
    protected void publishGameInstanceInfoState(GameInstance instance) {

        final long id = instance.getId();
        
        final GetInstanceInfoDTO dto = GetInstanceInfoDTO.from(instance);
        final String destination = GAME_TOPIC;
       
        messaging.convertAndSend(destination, dto);    
    }

    /**
     * This method initiates sends the state of an instance
     * to the {@value #EDITOR_TOPIC} topic.
     * 
     * @param instance EditorInstance thats state will be sent to the topic.
     */
    protected void publishEditorInstanceInfoState(EditorInstance instance) {

        final long id = instance.getId();
        System.out.println("update this instance " + id);

        final GetInstanceInfoDTO dto = GetInstanceInfoDTO.from(instance);
        System.out.println("send this dto " + dto);

        final String destination = EDITOR_TOPIC;
        System.out.println("send to  " + destination);


        messaging.convertAndSend(destination, dto);
    }
}
